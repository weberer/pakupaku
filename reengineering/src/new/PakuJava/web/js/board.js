class Board {

    static lifeElPrefix = "life_";
    static maxLifeCount = 3;
    static lifeElements = [];

    static liveStatuses = {
        alive: "show",
        dead: "hide"
    };

    static fruitMappings = {
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

    static gameHighScoreElId = "high_score";
    static gameHighScoreEl = null;
    static gameScoreElId = "your_score";
    static gameScoreEl = null;

    static startingLifeCount = 2;

    static getpelletElement = () => {

    };

    static updatePellets = (pelletArr) => {

    };

    // numLives specified on from 1 - Board.maxLifeCount
    static setLifeCount(numLives) {
        if (numLives < 0 || numLives > this.maxLifeCount)
            throw "Error: " + numLives + " lives were specified. Paku can only have between 0 and " + this.maxLifeCount + " lives";
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
        for (let lifeNum = 0; lifeNum < 3; lifeNum++)
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
    };

    static newGame = () => {
        this.setLifeCount(2);
        this.updateAllFruits([1]); // only the cherry
        this.updateScore(0);
    }
}