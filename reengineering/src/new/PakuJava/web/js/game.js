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

    static defaultFruit = [ // Display Right -> Left
        13,//key
        9, //ship
        7, //grape
        5, //apple
        11,//bell
        3, //orange/peach
        2, //strawberry
        1 //cherry
    ];
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
        if(!(state in this.boardStates))
            throw "Error: " + state + " is not a valid attribute for " + this.boardEl.id;

        Util.setAttributeValue(this.boardEl, this.htmlAttrName, state);
        this.boardState = state;
    }

    static setGameState(state) {
        if(!(state in this.gameStates))
            throw "Error: " + state + " is not a valid attribute for " + this.gameEl.id;

        Util.setAttributeValue(this.gameEl, this.htmlAttrName, state);
        this.gameState = state;
    }

    static openMenu = () => {
        this.setGameState(this.gameStates.menu);
        Board.setLifeCount(Board.startingLifeCount);
        Board.updateAllFruits(this.defaultFruit);
        Util.stopAudio();
        Networking.sendMenuRequest((data) => {
            //Game.updateHighScoreList(data.highscoreList); // Backend returns array, not object as is needed to update the list
        });
    };

    static startGameReady = () => {
        document.paku.moveToStartingPos();
        Ghost.moveToStartingLocations();
        // timeout gives entities time to move to their starting positions
        setTimeout(() => {
            this.setGameState(this.gameStates.play);
            this.setBoardState(this.boardStates.ready);
            let audioDuration = Util.playAudio("ready");
            setTimeout(this.startGame, audioDuration);
            Networking.sendGameReadyRequest((data) => {
                Board.updateHighScore(data.highscore);
            });
        }, 1000);
    };

    static startGame = () => {
        this.setBoardState(this.boardStates.play);
        Networking.sendStartGameRequest();
    };

    static gameOver = () => { this.setBoardState(this.boardStates.gameOver); };

    static newHighscore = () => { this.setGameState(this.gameStates.newHighScore); };

    static handleEnterKey = () => {
        if(this.gameState === this.gameStates.menu)
            this.newGame();
    };

    static handleEscKey = () => {
      if(this.gameState === this.gameStates.play)
          this.openMenu();
    };

    // state should be a boolean value
    static setSound = (state) => {
        for(let el of this.soundElList)
            Util.setAttributeValue(el, this.htmlAttrName, state);

        this.isSoundOn = state; // update JS sound State.

        if(!state && Util.currentlyPlayingAudio) // if stopping audio, and audio is currently playing, stop playback now
            Util.stopAudio();
    };

    static toggleSound = () => { this.setSound(!this.isSoundOn); };

    static updateHighScoreList = (scoreList) => { //assume an object is formatted as {"KEY": SCORE, ...}
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