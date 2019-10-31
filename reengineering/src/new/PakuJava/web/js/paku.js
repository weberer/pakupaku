class Paku extends MovingEntity {
    constructor() {
        super('paku');
        this.constructor.stylesheet = "paku.css";
        this.constructor.cssElementName = "#paku";
    }

    static attrNames = {
        direction: 'direction',
        status: 'status'
    };

    // valid status for paku
    static statuses = ["go, stop"];

    // updates 'status' of pauk
    changeStatus = status => this.setAttr(this.constructor.attrNames.status, status);

    // call in init method
    static createPaku = () => {
        document.paku = new Paku();
    }
}