class Util {
    // Class variables
    static rootStyle;
    static lastKeyPressed;
    static frameNumber = 0;
    static frameInterval= 120; //Send a request every second
    static intervalId;
    static requestURL = "http://localhost:8080/pakupaku/servlet/PakuPakuServlet";
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

    // Since requests are sent on a cycle, not reactionary, update the 'last key pressed' variable in Util.
    // when the next request is sent, this value will be used.
    static handleKeypress = keycode => {
        switch(keycode) {
            case "KeyW":
                paku.changeDirection(Paku.directions.up);
                break;
            case "KeyD":
                paku.changeDirection(Paku.directions.right);
                break;
            case "KeyS":
                paku.changeDirection(Paku.directions.down);
                break;
            case "KeyA":
                paku.changeDirection(Paku.directions.left);
                break;
            case "KeyO":
                Game.toggleSound();
                break;
            case "Enter":
                Game.handleEnterKey();
                break;
            case "Escape":
                Game.handleEscapeKey();
                break;

        }

        Util.lastKeyPressed = keycode;
    };

    static handleAjaxSuccess = (data) => {
        console.log(data);
    };

    // handles error responses from the server
    static handleAjaxError = (jqXHR, status, error) => {
        console.log("Request Error:");
        console.log(jqXHR);
        console.log("Error Data:");
        console.log(error);
    };

    // sends a request to the server for a new frame
    static sendFrameData = () => {
        let data = {
            frameId: this.frameNumber++
        };

        if(this.lastKeyPressed) {
            data["keycode"] = Util.lastKeyPressed;
            this.lastKeyPressed = null;
        }

        $.ajax({
            url: Util.requestURL,
            data: data,
            type: "POST",
            crossDomain: true,
            success: this.handleAjaxSuccess,
            timeout: 400,
            error: this.handleAjaxError
        });
    };

    // begins sending Ajax requests to the server at regular intervals
    static startInterval = () => {
        this.intervalId = setInterval(this.sendFrameData, this.frameInterval);
    };

    // changes the interval Ajax requests are sent to the server
    static updateInterval = (newInterval) => {
        this.frameInterval = newInterval;
        this.stopInterval();
        this.startInterval();
    };

    // stops sending requests to the server at regular intervals. Throws error if no interval exists
    static stopInterval = () => {
        if(this.intervalId)
            clearInterval(this.intervalId);
        else
            throw "No interval to stop";
    }
}