class Game {

    static gameState = {
        menu: "menu",
        play: "play",
        newHighScore: "new_high_score"
    };

    static boardState = {
        play: "play",
        ready: "ready",
        gameOver: "game_over"
    };

    static htmlAttrName = "state";

    static gameEl = null;
    static boardEl = null;
    static soundElList = null;

    static isSoundPlaying = true; // sound defaults to 'ON'
    static gameState = null; // tracks the state of gameEl in real time

    static setGameState(state) {
        if(!(state in this.gameState))
            throw "Error: " + state + " is not a valid state for " + this.gameEl.id;
        Util.setAttributeValue(this.gameEl, this.htmlAttrName, state);
        this.gameState = state;
    }

    static setBoardState(state) {
        if(!(state in this.boardState))
            throw "Error: " + state + " is not a valid state for " this.gameEl.id;
        Util.setAttributeValue(this.boardEl, this.htmlAttrName, state);
        this.boardState = state;
    }

    static openMenu = () => { this.setGameState(this.gameState.menu); }; //TODO: TESTED

    static startGameReady = () => { //TODO: TESTED
        this.setGameState(this.gameState.play);
        this.setBoardState(this.boardState.ready);
    };

    static startGame = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardState.play); }; //TODO: Element Switch Tested

    static gameOver = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardState.gameOver); }; //TODO: Tested

    static newHighscore = () => { Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameState.newHighScore); }; //TODO: Element Switch tested. Will need to alter the Input Check routine in some way to check for enter key down to submit

    // state should be a boolean value
    static setSound = (state) => {
        for(let el of this.soundElList)
            Util.setAttributeValue(el, this.htmlAttrName, state);

        this.isSoundPlaying = state; // update JS sound State.
    };

    static toggleSound = () => { this.setSound(!this.isSoundPlaying); };

    static handleEnterKey = () => { if()};

    static handleEscKey = () => { this.openMenu(); };

    static init() {
        Board.createLives();
        Util.getRootCssElement();
        Util.heightAdjust();
        Ghost.createGhosts();
        Paku.createPaku();
        Board.createLives();

        //Get Elements for Game Class
        this.gameEl = document.getElementById("display");
        this.boardEl = document.getElementById("game_board");
        this.soundElList = document.getElementsByClassName("sound_display");

        // Set initial Game States
        this.openMenu();
        this.setSound(true);
    };

}