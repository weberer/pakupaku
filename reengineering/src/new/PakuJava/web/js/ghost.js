class Ghost extends MovingEntity {
    constructor(id) {
        super(id);
        this.constructor.stylesheet = "ghost.css";
        this.constructor.cssElementName = ".ghost";
    }

    static attrNames = {
        direction: 'direction',
        state: 'state'
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
}