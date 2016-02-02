 Ext.define('iDynamicsFront.widget.Petal', {
     extend: 'Ext.Widget',
     requires: [],
     alias: 'widget.petal',
     config: {
         position: null,
         widthFrame:null,
         width: null
     },
     
     // The element template passed to Ext.Element.create()
     element: {
         tag : 'div',
         cls : 'frame',
         reference: 'element',

         children: [{
             tag : 'div',
             cls : 'petal',   // + topleft segun position
             reference: 'petal'
         }]
     },

     constructor: function(config) {
         // It is important to remember to call the Widget superclass constructor
         // when overriding the constructor in a derived class. This ensures that
         // the element is initialized from the template, and that initConfig() is
         // is called.
         this.callParent([config]);
         this.element.height = config.width;
         
         // After calling the superclass constructor, the Element is available and
         // can safely be manipulated. Reference Elements are instances of
         // Ext.Element, and are cached on each Widget instance by reference name.
         Ext.getBody().appendChild(this.element);
     },

     onClick: function() {
         // listeners use this Widget instance as their scope
         //console.log('element clicked', this);
     },

     onInnerClick: function() {
         // access the innerElement reference by name
         //console.log('inner element clicked', this.innerElement);
     }
 });