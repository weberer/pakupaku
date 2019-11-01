class Networking {

    static vars = {
        frameNumber: 0,
        frameInterval: 120,
        intervalId: null,
        requestUrl: "servlet/PakuPaku"
    };

    // handles error responses from the server
    static handleAjaxError = (jqXHR, status, error) => {
        console.log("Request Error:");
        console.log("-Status:");
        console.log(status);
        console.log("-Error Data:");
        console.log(error);
    };

    static _sendRequest = (data, callback) => {
        let url = window.location.href + this.vars.requestUrl;
        $.ajax({
            url: url,
            data: data,
            type: "POST",
            crossDomain: true,
            success: callback,
            timeout: 400,
            error: this.handleAjaxError
        });
    };

    // sends a request to the server for a new frame
    static sendFrameData = () => {
        let data = {
            frameId: this.vars.frameNumber++
        };

        if(Util.lastKeyPressed) {
            data["keycode"] = Util.lastKeyPressed;
            Util.lastKeyPressed = null;
        }
        this._sendRequest(data, this.success);
    };

    static sendMenuRequest = () => {
        let data = {
            type: "menu"
        };

        this._sendRequest(data, this.success);
    };

    static sendStartGameRequest = () => {
        let data = {
            type: "start_game"
        };

        this._sendRequest(data, this.success);
    };

    static success = (responseData) => {
        let data = JSON.parse(responseData);
        console.log(data);
    }
}