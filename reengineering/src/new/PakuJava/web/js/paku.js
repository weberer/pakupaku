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
        let me = document.paku;
        me.setX(12.5); //he starts at a half square for some reason.
        me.setY(22);
        me.changeDirection(MovingEntity.directions.left);
    };

    // call in init method
    static createPaku = () => {
        document.paku = new Paku();
    }
}