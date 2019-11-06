class Paku extends MovingEntity {
    constructor() {
        super('paku');
        this.stylesheet = "paku.css";
        this.cssElementName = "#paku";
    }

    static attrNames = {
        direction: 'direction',
        status: 'status'
    };

    // valid status for paku
    static statuses = ["go, stop"];

    // updates 'status' of pauk
    changeStatus = status => this.setAttr(this.constructor.attrNames.status, status);

    moveToStartingPos = () => {
        let me = window.paku;
        me.setX(12.5); //he starts at a half square for some reason.
        me.setY(22);
        me.changeDirection(MovingEntity.directions.left);
    };

    update = (data) => {
        let me = window.paku;
        me.setX(data.location.x - 1); // -1 in x and y accounts for the UI not using game boarders in its calculations
        me.setY(data.location.y - 1);
        me.changeDirection(data.location.direction);
        Board.setLifeCount(data.lives);
    };

    handleLostLife = () => {
        let audioDuration = Util.playAudio('lost_life') || 1.4;
        this.moveToStartingPos();
        Ghost.moveToStartingLocations();
        console.log(audioDuration);
        setTimeout(() => {
            Game.setGameReady(Util.startInterval);
        }, audioDuration);
    };


    // call in init method
    static createPaku = () => {
        window.paku = new Paku();
    }
}