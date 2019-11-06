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

    static defaultFruit = [ // Display Left -> Right
        1, //cherry
        2, //strawberry
        3, //orange/peach
        11,//bell
        5, //apple
        7, //grape
        9, //ship
        13 //key
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
    static highscoreInitialsPrefix = "highscore_init_";
    static highscoreScorePrefix = "highscore_score_";

    static highScoreListCount = 5;
    static isSoundOn = true; // sound defaults to 'ON'

    static gameOverDuration = 5000; // Time the 'game over' text is show. 5s.

    static setBoardState(state) {
        if(!(Object.values(this.boardStates).includes(state)))
            throw "Error: " + state + " is not a valid attribute for " + this.boardEl.id;

        Util.setAttributeValue(this.boardEl, this.htmlAttrName, state);
        this.boardState = state;
    }

    static setGameState(state) {
        if(!(Object.values(this.gameStates).includes(state)))
            throw "Error: " + state + " is not a valid attribute for " + this.gameEl.id;

        Util.setAttributeValue(this.gameEl, this.htmlAttrName, state);
        this.gameState = state;
    }

    static openMenu = () => {
        this.setGameState(this.gameStates.menu);
        Board.resetPellets();
        Board.setLifeCount(Board.menuLifeCount);
        Board.updateAllFruits(this.defaultFruit);
        Util.stopAudio();
        Util.stopInterval();
        Networking.sendMenuRequest((data) => {
            Game.updateHighScoreList(data.highscore_list); // Backend returns array, not object as is needed to update the list
        });
    };

    static startGameReady = () => {
        window.paku.moveToStartingPos();
        Ghost.moveToStartingLocations();
        // timeout gives entities time to move to their starting positions
        setTimeout(() => {
            this.setGameState(this.gameStates.play);
            this.setBoardState(this.boardStates.ready);
            let audioDuration = Util.playAudio("ready");
            setTimeout(this.startGame, audioDuration);
            Board.setLifeCount(Board.startingLifeCount);
            Networking.sendGameReadyRequest((data) => {
                Board.updateHighScore(data.highscore);
            });
        }, 1000);
    };

    static startGame = () => {
        this.setBoardState(this.boardStates.play);
        Networking.sendStartGameRequest();
        Util.startInterval();
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
        if(scoreList.length !== this.highScoreListCount)
            throw "Error: wrong number of scores in high score list";

        for(let i = 0; i < scoreList.length; i++) {
            let splitScore = scoreList[i].split('='); //Initials stored @ index 0; score stored @ index 1 after split
            let initials = splitScore[0];
            let highscore = Util.padScore(splitScore[1]);

            Util.setText(this.highscoreInitialsPrefix + i, initials);
            Util.setText(this.highscoreScorePrefix + i, highscore);
        }
    };

    static updateLastScore = (newScore) => {
        this.lastScoreEl.innerHtml = Util.padScore(newScore);
    };

    static processFrame = (data) => {
        console.log(data);
        Board.updateScore(data.score);
        Board.updateAllFruits(data.fruitList);
        window.paku.update(data.paku);
        Ghost.updateAllGhosts(data.ghosts);
        Board.updatePellets(data.board);
        if(data.game_state !== this.boardStates.play) {
            Util.stopInterval(); // state has changed, stop requesting new frames and deal with this one.

            let state = data.game_state;
            console.log(state);

            if(state === this.boardStates.gameOver)
                this.handleGameOver();
        }
    };

    static handleGameOver = () => {
        Util.stopInterval();
        this.gameOver();
        setTimeout(this.openMenu, this.gameOverDuration);

    };

    static newGame = () => {
      Board.newGame();
      this.startGameReady();
    };

    static init = () => {
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

        window.frameInterval = 200;
    };

}