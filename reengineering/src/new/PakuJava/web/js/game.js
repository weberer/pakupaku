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

    static isSoundPlaying = false;

    static openMenu = () => { Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameState.menu); }; //TODO: TESTED

    static startGameReady = () => { //TODO: TESTED
        Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameState.play);
        Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardState.ready);
    };

    static startGame = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardState.play); }; //TODO: Element Switch Tested

    static gameOver = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardState.gameOver); }; //TODO: Tested

    static newHighscore = () => { Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameState.newHighScore); }; //TODO: Element Switch tested. Will need to alter the Input Check routine in some way to check for enter key down to submit

    static toggleSound = () => {

        let soundState = 

        if(this.isSoundPlaying)
            stopSound();
        else
            playSound();
    };

    static init() {
        Board.createLives();
        Util.getRootCssElement();
        Util.heightAdjust();
        Ghost.createGhosts();
        Paku.createPaku();
        Board.createLives();
        this.gameEl = document.getElementById("display");
        this.boardEl = document.getElementById("game_board");
        this.openMenu();
    };

}