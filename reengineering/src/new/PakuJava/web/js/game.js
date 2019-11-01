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

    static openMenu = () => { Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameState.menu); }; //TODO: TESTED

    static startGameReady = () => { //TODO: TESTED
        Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameState.play);
        Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardState.ready);
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