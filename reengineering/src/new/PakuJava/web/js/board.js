class Board {

    static lifeElPrefix = "life_";
    static maxLifeCount = 3;
    static lifeElements = [];

    static lifeStatuses = {
        alive: "show",
        dead: "hide"
    };

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
                let lifeStatus = this.lifeStatuses.dead;
                if (life < numLives)
                    lifeStatus = this.lifeStatuses.alive;

                Util.setAttributeValue(this.lifeElements[life], Game.htmlAttrName, lifeStatus);
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
}