class Networking {

    // handles error responses from the server
    static handleAjaxError = (jqXHR, status, error) => {
        console.log("Request Error:");
        console.log("-Status:");
        console.log(status);
        console.log("-Error Data:");
        console.log(error);
    };

    static _sendRequest = (data, callback) => {
        let url = window.location.href + "servlet/PakuPaku"; // adjusts the URL so it works on different machines

        $.ajax({
            url: url,
            data: data,
            type: "POST",
            success: callback,
            timeout: 100,
            error: this.handleAjaxError,
            converters: {"text json": this.parseRawData} // 'custom' default JSON parser
        });
    };

    // sends a request to the server for a new frame
    static sendFrameData = () => {
        let data = {
            //frameId: this.vars.frameNumber++
        };
        this._sendRequest(data, Game.processFrame)
    };

    static sendMenuRequest = callback => {
        let data = {
            type: "menu"
        };
        this._sendRequest(data, callback);
    };

    static sendGameReadyRequest = callback => {
        let data = {
            type: "game_ready"
        };
        this._sendRequest(data, callback);
    };

    static sendStartGameRequest = () => {
        let data = {
            type: "start_game"
        };
        this._sendRequest(data, () => {}); // no data returned = no callback.
    };

    static sendGetFrameRequest = (callback) => {
      let data = {
          type: "send_frame"
      };
      this._sendRequest(data, callback);
    };

    static sendInput = keycode => {
        let data = {
            type:   "input",
            input:  keycode
        };
        this._sendRequest(data, ()=>{}); //no data returned = no callback
    };

    static parseRawData = rawData => { return JSON.parse(rawData).data; };
}