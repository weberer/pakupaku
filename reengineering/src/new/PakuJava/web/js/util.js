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

    static currentlyPlayingAudio = null;

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
        if(Game.isSoundOn)
        {
            let audio = document.getElementById('audio_' + name);
            if(!audio)
                throw "Error: An <audio> element with id audio_" + name + " does not exist.";
            let duration = audio.duration * 1000; // convert returned s into ms
            this.currentlyPlayingAudio = audio;
            audio.play();
            setTimeout(this.stopAudio, duration);
            return duration
        }
    };

    static stopAudio = () => {
        if(this.currentlyPlayingAudio)
        {
            this.currentlyPlayingAudio.pause();
            this.currentlyPlayingAudio = null;
        }
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