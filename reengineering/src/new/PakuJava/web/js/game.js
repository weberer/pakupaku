class Game {

    static gameStates = {
        menu: "menu",
        play: "play",
        newHighScore: "new_high_score"
    };

    static boardStates = {
        play: "play",
        ready: "ready",
        gameOver: "game_over"
    };

    static htmlAttrName = "state";

    static gameElId = "display";
    static gameEl = null;
    static boardElId = "game_board";
    static boardEl = null;
    static soundElListId = "sound_display";
    static soundElList = null;
    static gameState = null;
    static boardState = null;
    static lastScoreElId = "last_score";
    static lastScoreEl = null;
    static highscoreInitialsPrevix = "highscore_init_";
    static highscoreScorePrefix = "highscore_score_";

    static highScoreListCount = 5;
    static isSoundOn = true; // sound defaults to 'ON'

    static setBoardState(state) {
        if(!(state in this.boardStates)) //TODO: TESTED
            throw "Error: " + state + " is not a valid attribute for " + this.boardEl.id;

        Util.setAttributeValue(this.boardEl, this.htmlAttrName, state);
        this.boardState = state;
    }

    static setGameState(state) { //TODO: TESTED
        if(!(state in this.gameStates))
            throw "Error: " + state + " is not a valid attribute for " + this.gameEl.id;

        Util.setAttributeValue(this.gameEl, this.htmlAttrName, state);
        this.gameState = state;
    }

    static openMenu = () => {
        this.setGameState(this.gameStates.menu);
        Board.setLifeCount(Board.startingLifeCount);
        Board.updateAllFruits([13,9,7,5,11,3,2,1]); // R->L, key, ship, grape, apple, bell, orange, strawberry, cherry
        Util.stopAudio();

        Networking.sendMenuRequest();
    };

    static startGameReady = () => {
       this.setGameState(this.gameStates.play);
       this.setBoardState(this.boardStates.ready);
       let audioDuration = Util.playAudio("ready");
        setTimeout(this.startGame, audioDuration);

        Networking.sendStartGameRequest();
    };

    static startGame = () => { this.setBoardState(this.boardStates.play) }; //TODO: Element Switch Tested

    static gameOver = () => { this.setBoardState(this.boardStates.gameOver); }; //TODO: Tested

    static newHighscore = () => { this.setGameState(this.gameStates.newHighScore); }; //TODO: Element Switch tested. Will need to alter the Input Check routine in some way to check for enter key down to submit

    static handleEnterKey = () => { //TODO: TESTED
        if(this.gameState === this.gameStates.menu)
            this.newGame();
    };

    static handleEscKey = () => { //TODO: TESTED
      if(this.gameState === this.gameStates.play)
          this.openMenu();
    };

    // state should be a boolean value
    static setSound = (state) => { //TODO: TESTED
        for(let el of this.soundElList)
            Util.setAttributeValue(el, this.htmlAttrName, state);

        this.isSoundOn = state; // update JS sound State.

        if(!state && Util.currentlyPlayingAudio) // if stopping audio, and audio is currently playing, stop playback now
            Util.stopAudio();
    };

    static toggleSound = () => { this.setSound(!this.isSoundOn); };  //TODO: TESTED

    static updateHighScoreList = (scoreList) => { //assume an object {"KEY": SCORE
        let keys = Object.keys(scoreList);
        if(keys.length > this.highScoreListCount)
            throw "Error: Too many high scores. A maximum of " + this.highScoreListCount + " scores can be displayed. "
                + keys.length + " were passed in.";

        for(let i = 0; i < keys.length; i++)
        {
            let scoreEl = document.getElementById(this.highscoreScorePrefix + i);
            let initialsEl = document.getElementById(this.highscoreInitialsPrevix + i);
            initialsEl.innerText = keys[i];
            scoreEl.innerText = Util.padScore(scoreList[keys[i]]);
        }
    };

    static updateLastScore = (newScore) => {
        this.lastScoreEl.innerHtml = Util.padScore(newScore);
    };

    static newGame = () => {
      Board.newGame();
      this.startGameReady();
    };

    static init() {
        // set size of the view container
        Util.getRootCssElement();
        Util.heightAdjust();
        Ghost.createGhosts();
        Paku.createPaku();
        Board.initBoard();

        //Get Elements for Game Class
        this.gameEl = document.getElementById(this.gameElId);
        this.boardEl = document.getElementById(this.boardElId);
        this.soundElList = document.getElementsByClassName(this.soundElListId);
        this.lastScoreEl = document.getElementById(this.lastScoreElId);

        // Set initial Game States
        this.setSound(true);
        this.openMenu();
        this.updateLastScore(0);
    };

}