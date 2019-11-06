class Util {
    // Class variables
    static rootStyle = null;
    static aspectRatio = {
        height: 2, // 100
        width: 3.2 // 160
    };
    // This method updates adjusts the height/width relationship to allow the screen to fit in windows
    // where the height > calculated width
    static heightAdjust = () => {
        let height =window.innerHeight;
        let width = window.innerWidth;
        if(height > (width * this.aspectRatio.height / this.aspectRatio.width))
            this.setPropertyValue("global.css", ":root","--screen_height", (width / this.aspectRatio.width * this.aspectRatio.height)+ "px");
    };

    // Sets the value of a given CSS property/value. Throws an exception if no such property exists.
    static setPropertyValue = (sheetName, elementName, propertyName, newValue) => {
        let cssElement = this.getCssElement(sheetName, elementName);

        if(cssElement.style && cssElement.style.getPropertyValue(propertyName))
            cssElement.style.setProperty(propertyName, newValue);
        else
            throw "Exception: Property " + propertyName + " does not exist";
    };

    // Gets the :root pseudo element from 'globals.css'. This element can then be used
    // to update CSS variables using javascript.
    static getRootCssElement = () => this.rootStyle =  this.getCssElement("global.css", ":root");

    static getCssElement= (sheetName, elementName) => {
        let el;

        for(let sheet of document.styleSheets)
            if(sheet.href.includes(sheetName))
                for(let rule of sheet.cssRules)
                    if(rule.selectorText === elementName)
                        el = rule;
        return el;
    };

    static setAttributeValue = (element, attrName, value) => element.setAttribute(attrName, value);

    static setText = (elementId, text) => { document.getElementById(elementId).innerText = text };

    static directionKeyCodes = ["KeyW", "KeyD", "KeyS", "KeyA", "ArrowUp", "ArrowRight", "ArrowDown", "ArrowLeft"];

    // Since requests are sent on a cycle, not reactionary, update the 'last key pressed' variable in Util.
    // when the next request is sent, this value will be used.
    static handleKeypress = event => {
        let keycode = event.code;

        if(this.directionKeyCodes.includes(keycode))
            Networking.sendInput(keycode);
        else
            switch(keycode) {
                case "KeyR": //TODO: REMOVE, DEBUG ONLY
                    Networking.sendFrameRequest();
                    break;
                case "KeyQ": // TODO: REMOVE, DEBUG ONLY
                    this.stopInterval();
                    window.paku.stopWaka(); // It's annoying if it continues
                    break;
                case "KeyO":
                    Game.toggleSound();
                    break;
                case "Enter":
                    Game.handleEnterKey();
                    break;
                case "NumpadEnter":
                    Game.handleEnterKey();
                    break;
                case "Escape":
                    Game.handleEscKey();
                    break;
                // Default case is invalid key, which doesn't need to be processed.
            }
    };

    // begins sending Ajax requests to the server at regular intervals
    static startInterval = () => {
        this.intervalId = setInterval(Networking.sendFrameRequest, window.frameInterval);
    };

    // changes the interval Ajax requests are sent to the server
    static updateInterval = (newInterval) => {
        window.frameInterval = newInterval;
        this.stopInterval();
        this.startInterval();
    };

    // stops sending requests to the server at regular intervals. Throws error if no interval exists
    static stopInterval = () => {
        if(this.intervalId) {
            clearInterval(this.intervalId);
            this.intervalId = null;
        }
    };

    // plays a sound file associated with an html element. returns the duration of the sound file in ms
    static playAudio = (name) => {
        let audio = document.getElementById('audio_' + name);
        if(!audio)
            throw "Error: An <audio> element with id audio_" + name + " does not exist.";
        let duration = audio.duration * 1000; // convert returned s into ms
        audio.currentTime = 0;
        audio.play();
        return duration
    };

    static stopAudio = (name) => {
        if(name)
        {
            let audio = name;
            if(typeof name === "string")
                audio = document.getElementById('audio_' + name); // get html Element if type string
            else if(typeof name !== "object")
                throw "Error: Value of 'name' in Util.stopAudio() must be either of type string or type object.";

            if(audio) {
                audio.pause();
                audio.currentTime = 0; // fixes bug in blink based browsers where audio doesn't rewind after playing
            }
        }
    };

    static stopAllAudio = () => {
      let audioElements = document.getElementsByTagName('audio');
      Array.from(audioElements).forEach((element) => {
          this.stopAudio(element);
      })
    };

    // Takes in volume level from 0-1
    static setAudioVolume = (volume) => {
        let audioElements = document.getElementsByTagName("audio");
        Array.from(audioElements).forEach((element) => {
            element.volume = volume;
        });
    };

    // pads a string or integer out to length 8 with '0''s
    static padScore(score)
    {
        let scoreLength = 8; //number of digits in a score string

        if(Number.isInteger(score))
            score = score.toString();
        return score.padStart(scoreLength, '0');
    }

}