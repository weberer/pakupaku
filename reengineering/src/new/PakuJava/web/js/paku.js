class Paku extends MovingEntity {
    constructor() {
        super('paku');
        this.stylesheet = "paku.css";
        this.cssElementName = "#paku";
    }

    static attrNames = {
        direction: 'direction',
        state: 'state'
    };

    // valid status for paku
    static states = ["go", "stop", "warping"];
    wakaing = false;

    // updates 'state' of pauk
    changeState = state => this.setAttr(this.constructor.attrNames.state, state);

    moveToStartingPos = () => {
        let me = window.paku;
        me.setX(12.5); //he starts at a half square for some reason.
        me.setY(22);
        me.changeDirection(MovingEntity.directions.left);
    };

    update = (data) => {
        let me = window.paku;
        let state = "go";
        me.setX(data.location.x - 1); // -1 in x and y accounts for the UI not using game boarders in its calculations
        me.setY(data.location.y - 1);
        me.changeDirection(data.location.direction);
        Board.setLifeCount(data.lives);
        if(data.warping)
            state = "warping";
        else if(!data.moved) {
            state = "stop";
            me.stopWaka();
        }
        me.startWaka();
        me.changeState(state);
    };

    handleLostLife = () => {
        this.stopWaka();
        let audioDuration = Util.playAudio('lost_life') || 1.4;
        this.moveToStartingPos();
        Ghost.moveToStartingLocations();
        setTimeout(() => {
            Game.setGameReady(Util.startInterval);
        }, audioDuration);
    };

    startWaka = () => {
        if(!this.wakaing)
        {
            Util.playAudio("eating");
            this.wakaing = true;
        }
    };

    stopWaka = () => {
        console.log("Stop Waka");
        Util.stopAudio("eating");
        this.wakaing = false;
    };


    // call in init method
    static createPaku = () => {
        window.paku = new Paku();
    }
}