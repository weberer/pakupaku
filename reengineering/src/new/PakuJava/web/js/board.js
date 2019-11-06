class Board {

    static lifeElPrefix = "life_";
    static maxLifeCount = 4;
    static startingLifeCount = 3;
    static menuLifeCount = 2;
    static lifeElements = [];

    static liveStatuses = {
        alive: "show",
        dead: "hide"
    };

    static fruitMappings = { // map backend fruit representation to match the frontend
        0: "empty",
        1: "cherry",
        2: "strawberry",
        3: "peach",
        5: "apple",
        7: "grape",
        9: "ship",
        11: "bell",
        13: "key"
    };

    static fruitElementPrefix = "fruit_";
    static fruitAttrName = "type";
    static emptyFruit = "none";
    static fruitCount = 8;

    static bonusFruitElId = "bonus_fruit";
    static bonusFruitEl = null;

    static gameHighScoreElId = "high_score";
    static gameHighScoreEl = null;
    static gameScoreElId = "your_score";
    static gameScoreEl = null;

    static visiblePellet = 1; // maps value passed in from backend to the ui visible pellet state.
    static boardSize = {columns: 26, rows: 29};
    static pelletStates = {show: "show", hide: "hidden"};

    static getpelletElement = (x, y) => {
        return document.getElementById("p" + y + "_" + x);
    };

    static updatePellets = (pelletArr) => {
        for(let row = 0; row < pelletArr.length; row++)
            for(let column = 0; column < pelletArr[row][0].length; column++) // 3d array is temporary fix for issue SEF2-48
            {
                let pelletEl = this.getpelletElement(column, row);
                if(pelletEl)
                {
                    let pelletData = pelletArr[row][0][column]; // 3d array is temporary fix FOR ISSUE SEF2-48
                    let pelletState = pelletData === this.visiblePellet ? this.pelletStates.show : this.pelletStates.hide;
                    Util.setAttributeValue(pelletEl, Game.htmlAttrName, pelletState);
                }
            }
    };

    static resetPellets = () => {
        for(let row = 0; row < this.boardSize.rows; row++)
            for(let column = 0; column < this.boardSize.columns; column++)
            {
                let pelletEl = this.getpelletElement(column, row);
                if(pelletEl)
                    Util.setAttributeValue(pelletEl, Game.htmlAttrName, this.pelletStates.show);
            }
    };

    // numLives specified on from 1 - Board.maxLifeCount
    static setLifeCount(numLives) {
        if (numLives < -1 || numLives > this.maxLifeCount)
            throw "Error: " + numLives + " lives were specified. Paku must have between 0 and " + this.maxLifeCount + " lives";
        else {
            for(let life = 0; life < this.maxLifeCount; life++) {
                let lifeState = this.liveStatuses.dead;
                if (life < numLives)
                    lifeState = this.liveStatuses.alive;

                Util.setAttributeValue(this.lifeElements[life], Game.htmlAttrName, lifeState);
            }
        }
    }

    // Call in init function;
    static createLives = () => {
        for (let lifeNum = 0; lifeNum < this.maxLifeCount; lifeNum++)
        {
            let elementId = this.lifeElPrefix + lifeNum,
                el = document.getElementById(elementId);
            this.lifeElements.push(el);
        }
    };

    static updateFruit = (fruitId, type) => {
        let el = document.getElementById(this.fruitElementPrefix + fruitId);
        Util.setAttributeValue(el, this.fruitAttrName, type);
    };

    static updateBonusFruit = (data) => {
        let bonusDisplayType = this.fruitMappings["0"];

        if(data.active) // data.fruit is true when showing a fruit, else false
            bonusDisplayType = this.fruitMappings[data.type];

        Util.setAttributeValue(this.bonusFruitEl, this.fruitAttrName, bonusDisplayType);
    };

    static clearFruit = () => {
      for(let i = 0; i < this.fruitCount; i++)
          this.updateFruit(i, this.emptyFruit);
    };


    static updateAllFruits = (fruitKeyArray) => {
        this.clearFruit();
        for(let i = 0; i < fruitKeyArray.length; i++)
        {
            let fruit = fruitKeyArray[i];
            if(!this.fruitMappings.hasOwnProperty(fruit))
                throw "Error: Invalid fruit key " + fruit;
             this.updateFruit(i, this.fruitMappings[fruit]);
        }
    };

    static updateScore = (newScore) => {
        if(!Number.isInteger(newScore))
            throw "Error: New Score must be an integer. " + newScore + " was received";
        this.gameScoreEl.innerText = Util.padScore(newScore);
    };

    static updateHighScore = (newScore) => {
        if(!Number.isInteger(newScore))
            throw "Error: New Score must be an integer. " + newScore + " was received";
        this.gameHighScoreEl.innerText = Util.padScore(newScore);
    };


    static initBoard = () => {
        this.createLives();

        //get score elements
        this.gameScoreEl = document.getElementById(this.gameScoreElId);
        this.gameHighScoreEl = document.getElementById(this.gameHighScoreElId);
        this.bonusFruitEl = document.getElementById(this.bonusFruitElId);
    };

    static newGame = () => {
        this.setLifeCount(2);
        this.updateAllFruits([0,0,0,0,0,0,0,1]); // only the cherry
        this.updateScore(0);
    }
}