class Game {

    static gameStatus = {
        menu: "menu",
        play: "play"
    };

    static boardStatus = {
        play: "play",
        ready: "ready",
        gameOver: "game_over",
        newHighScore: "new_high_score"
    };

    static htmlAttrName = "status";

    static gameEl = document.getElementById("display");
    static boardEl = document.getElementById("game_board");

    static isSoundPlaying = false;

    static openMenu = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.gameStatus.menu); };

    static startGameReady = () => {
        Util.setAttributeValue(this.gameEl, this.htmlAttrName, this.gameStatus.play);
        Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardStatus.ready);
    };

    static startGame = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardStatus.play); };

    static gameOver = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardStatus.gameOver); };

    static newHighscore = () => { Util.setAttributeValue(this.boardEl, this.htmlAttrName, this.boardStatus.newHighScore); };

    static toggleSound = () => {

        let playSound = () => {};
        let stopSound = () => {};

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
    };

}