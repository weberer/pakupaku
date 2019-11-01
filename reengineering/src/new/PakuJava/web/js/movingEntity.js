class MovingEntity {
   constructor(id) {
       this.id = id;
       this.element = document.getElementById(id);

       // if no HTML elements with this id, throw error
       if(!this.element)
           throw "Invalid Moving Entity Id: " + this.id;
   }

   stylesheet = null;
   cssElementName = null;
   static xPropName = '--x_coord';
   static yPropName = '--y_coord';
   static directions = {
       up:      "up",
       down:    "down",
       right:   "right",
       left:    "left"
   };

   // updates 'x' value of the associated HTML element by modifying CSS custom properties
   setX = newX => this.setProperty(this.constructor.xPropName, newX);

   // updates 'y' value of the associated HTML element by modifying CSS custom properties
   setY = newY => this.setProperty(this.constructor.yPropName, newY);

    // updates the direction an entity is facing. This direction is only which way the srpite faces.
    // It is NOT related to the direction of movement
   changeDirection = direction => this.setAttr(this.constructor.attrNames.direction, direction);

   // update HTML attributes of current entity
   setAttr = (attrName, value) => Util.setAttributeValue(this.element, attrName, value);

   // update CSS custom property of current entity
   setProperty = (property, value) => Util.setPropertyValue(this.stylesheet, this.cssElementName, property, value);

}