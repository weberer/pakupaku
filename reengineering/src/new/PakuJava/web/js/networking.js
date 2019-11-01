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
        let url = window.location.href + "servlet/PakuPaku";

        $.ajax({
            //url: "http://localhost:8080/paku_war_exploded/servlet/PakuPaku",
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
        console.log("Sending Menu Request");
        let data = {
            type: "menu"
        };

        this._sendRequest(data, (rawData) => {
            let data = JSON.parse(rawData).data;
            let highscoreList = data.highscore_list;
            //Game.updateHighScoreList(highscoreList); // Backend returns array, not object as is needed to update the list
        });
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