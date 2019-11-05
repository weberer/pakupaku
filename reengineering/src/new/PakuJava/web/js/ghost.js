class Ghost extends MovingEntity {
    constructor(id) {
        super(id);
        this.stylesheet = "ghost.css";
        this.cssElementname = "#" + id;
    }

    static attrNames = {
        direction: 'direction',
        state: 'state'
    };

    static startingPos = {
        blaine: {
            x: 15,
            y: 13
        },
        hinky: {
            x: 10,
            y: 13
        },
        kinky: {
            x: 12.5,
            y: 13,
        },
        stinky: {
            x: 12.5,
            y: 10
        }
    };

    // holds list of all ghosts
    static ghosts = [];

    // valid ghost states
    static states = ["normal", "eaten", "scared", "scaredExpiring"];

    // adds a new ghost to the array
    static addGhost = ghost => this.ghosts.push(ghost);

    // updates state of the associated ghost
    changeState = state => this.setAttr(this.constructor.attrNames.state, state);

    // updates the state of all ghosts
    static updateAllGhostStates = state => {

      this.ghosts.forEach((ghost) => {
            ghost.changeState(state);
        })
    };

    static updateAllGhosts = (ghostData) => {
        this.ghosts.forEach((ghost) => {
            let data = ghostData[ghost.id];
            let blinking = data.blinking;
            ghost.setX1(data.location.x - 1); // -1 in x and y accounts for the UI not using game boarders in its calculations
            ghost.setY1(data.location.y - 1);
            ghost.changeDirection(data.location.direction);
            if(blinking)
                ghost.changeState("scaredExpiring");
            else
                ghost.changeState(data.ghost_state);
        });
    };

    /* Temp fix for movement updates*/
    setX1 = (x) => {
        let cssElement = Util.getCssElement("ghost.css", "#" + this.id);

        if(cssElement.style && cssElement.style.getPropertyValue("--x_coord"))
            cssElement.style.setProperty("--x_coord", x);
        else
            throw "Exception: Property " + "propertyName" + " does not exist";
    };

    setY1 = (x) => {
        let cssElement = Util.getCssElement("ghost.css", "#" + this.id);

        if(cssElement.style && cssElement.style.getPropertyValue("--y_coord"))
            cssElement.style.setProperty("--y_coord", x);
        else
            throw "Exception: Property " + "propertyName" + " does not exist";
    };

    // call in init();
    static createGhosts() {
        let stinky = new Ghost('stinky');
        let kinky = new Ghost('kinky');
        let hinky = new Ghost('hinky');
        let blaine = new Ghost('blaine');

        Ghost.addGhost(stinky);
        Ghost.addGhost(kinky);
        Ghost.addGhost(hinky);
        Ghost.addGhost(blaine);
    }

    static moveToStartingLocations = () => {
        this.ghosts.forEach((ghost) => {
            let name = ghost.id;
            let x = this.startingPos[name].x;
            let y = this.startingPos[name].y;
            ghost.setX1(x);
            ghost.setY1(y);
        })
    }
}