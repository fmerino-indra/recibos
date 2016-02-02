//Semejante al Callout pero basado en un objeto window. De esta forma permite por ejemplo que sea modal. 

Ext.define('iDynamicsFront.util.WindowCallout', {
  extend: 'Ext.Container',
  extend: 'Ext.Window', //Para que pueda ser modal, tener título, etc
  alias: 'widget.windowcallout',
  cls: 'default',
  componentCls: 'x-ux-callout',
  floating: true,
  shadow: false,
  
  //shadow: "frame",
  //shadowOffset : 50,

  header: false,  modal:false, resizable: false, draggable:false, 
  
  padding: 16,
  config: {
    /**
    		@cfg {Ext.Component} Target {@link Ext.Component} (optional).
    */

    target: null,
    /**
    		@cfg {String} Position relative to {@link #target} - see {@link Ext.Element#alignTo} for valid values.
    */

    relativePosition: 'c-c',
    /**
    		@cfg {Array} X and Y offset relative to {@link #target} (optional).
    */

    relativeOffsets: null,
    /**
    		@cfg {String} Callout arrow location - valid values: none, top, bottom, left, right, top-right, top-left, bottom-right, bottom-left, left-top, left-bottom, right-top, right-bottom
    */

    calloutArrowLocation: 'none',
    /**
    		@cfg {Number} Duration in milliseconds for the fade in animation when a callout is shown.
    */

    fadeInDuration: 200,
    /**
    		@cfg {Number} Duration in milliseconds for the fade out animation when a callout is hidden.
    */

    fadeOutDuration: 200,
    /**
    		@cfg {Boolean} Indicates whether to automatically hide the callout after a mouse click anywhere outside of the callout.
    */

    autoHide: true,
    /**
    		@cfg {Number} Duration in milliseconds to show the callout before automatically dismissing it.  A value of 0 will disable automatic dismissal.
    */

    dismissDelay: 0,
    
    recolocaSiSeSale:false, //si se sale lo mueve y le modifica una clase. 
    destruyeAlOcultar:false,
    reduceSiSeSale:false
  },
  /**
  	@protected
  	@property {Object} The dismissal timer id.
  */

  dismissTimer: null,
  /**
  	@inheritdoc
  */

  initComponent: function() {
    var me=this;
    if (Ext.getVersion('extjs') && Ext.getVersion('extjs').isLessThan('4.1.0')) {
      Ext.applyIf(this, this.config);
    }
    if (me.recolocaSiSeSale) {
      me.on("aftersetposition", function(callout){
         me.recolocaSiSeHaSalido();
      })
    }
    if (me.reduceSiSeSale) {
      me.on("aftersetposition", function(callout){
         me.reduceSiSeHaSalido();
      })
    }
    
    if (me.destruyeAlOcultar) {
      me.on("hide", function(callout){
         me.destroy();
      })
    }    
    return this.callParent(arguments);
  },
  /**
  	@inheritdoc
  */

  destroy: function() {
    this.clearTimers();
    return this.callParent(arguments);
  },
  /**
  	@inheritdoc
  */

  show: function() {
    var elementOrComponent; 

    this.callParent(arguments);
    this.removeCls(['top', 'bottom', 'left', 'right', 'top-left', 'top-right', 'bottom-left', 'bottom-right', 'left-top', 'left-bottom', 'right-top', 'right-bottom']);
    if (this.getCalloutArrowLocation() !== 'none') {
      this.addCls(this.getCalloutArrowLocation());
    }
    if (this.getTarget() != null) {
      elementOrComponent = Ext.isString(this.getTarget()) ? Ext.ComponentQuery.query(this.getTarget())[0] : this.getTarget();
      this.getEl().anchorTo(elementOrComponent.el || elementOrComponent, this.getRelativePosition(), this.getRelativeOffsets() || [0, 0], false, 50, Ext.bind(function() {
        this.afterSetPosition(this.getEl().getLeft(), this.getEl().getRight());
        this.fireEvent( "aftersetposition", this, [this.getEl().getLeft(), this.getEl().getRight()]); 
      }, this));
    }
    if (!(this.dismissTimer != null) && this.getDismissDelay() > 0) {
      this.dismissTimer = Ext.defer(this.hide, this.getDismissDelay(), this);
    }
    this.fireEvent( "aftershow", this); //Esto salta después de haber sido movido ...
    return this;
  },
  /**
  	@inheritdoc
  */

  hide: function() {
    this.clearTimers();
    this.getEl().removeAnchor();
    return this.callParent(arguments);
  },
  /**
  	@protected
  	@method
  	Clear any timers that potentially be running.
  */

  clearTimers: function() {
    if (this.dismissTimer != null) {
      clearTimeout(this.dismissTimer);
    }
    this.dismissTimer = null;
  },
  /**
  	@inheritdoc
  */

  onShow: function() {
    this.callParent(arguments);
    this.mon(Ext.getDoc(), 'mousedown', this.onDocMouseDown, this);
    
    //Extendido para que soporte pickers dentro sin cerrarse
    this.prepareFields(); //Para evitar problemas con campos como las combos cuyos campos quedan detrás del propio callout
    this.createpickerFieldCatalog();
    
    
    this.getEl().setOpacity(0.0);
    this.getEl().fadeIn({
      duration: this.getFadeInDuration()
    });
  },
  
  createpickerFieldCatalog: function (){
    var me=this,
      pickerfields;
    //if (me.pickerFieldCatalog == null){
      me.pickerFieldCatalog = this.query("pickerfield")
    //}
    //debugger
  },
  
  prepareFields: function (){ //Extendido Para soportar combos dentro 
    var me=this,
      combos=this.query("combobox");
    for (var i=0; combos!=null && i<combos.length; i++){
      combos[i].createPicker(); //Regenero porque si no la segunda vez el picker queda detrás del callout
    }
  },
  
  
  /**
  	@inheritdoc
  */

  onHide: function(animateTarget, cb, scope) {
    this.mun(Ext.getDoc(), 'mousedown', this.onDocMouseDown, this);
    this.getEl().fadeOut({
      duration: this.getFadeOutDuration(),
      callback: function() {
        this.getEl().hide();
        this.afterHide(cb, scope);
      },
      scope: this
    });
  },
  /**
  	@protected
  	Handles a 'mousedown' event on the current HTML document.
  */

  onDocMouseDown: function(event) {
    //Extendido para que soporte pickers dentro sin cerrarse
    /*if (this.getAutoHide() && !event.within(this.getEl())) {
      this.hide();
    }*/
    var me=this;
    if (this.getAutoHide() && !event.within(this.getEl()) && !me.eventoDentroDeUnPicker(event) ) {
      this.hide();
    }
  },
  
  eventoDentroDeUnPicker: function (event) { //Extendido para que soporte pickers dentro sin cerrarse
    var me=this, 
      pickerFieldCatalog = me.pickerFieldCatalog, 
      pickerfield, picker;
    for (var i=0; (pickerfield=pickerFieldCatalog[i])!=null ; i++) {
      var picker = pickerfield.getPicker();
      if (picker && picker.isVisible() && event.within(picker.getEl())) {
         return true
      }
    }
    return false;
  },
  
  reduceSiSeHaSalido: function (){
      var callout=this,
         viewSize = Ext.getBody().getViewSize(),
         viewHeight = viewSize.height,
         viewWidth = viewSize.width,
         position = callout.getPosition(),
         x = position[0],
         y = position[1],
         h = callout.getHeight(),
         w = callout.getWidth(),
         margin = 10;
      if (y<margin){ //Se sale por arriba
         callout.setMaxHeight( h-margin+y )
         callout.setPosition(x,margin)
      }
      //TODO: resto de direcciones 
  },
  
  recolocaSiSeHaSalido: function() {
      var callout=this,
         viewSize = Ext.getBody().getViewSize(),
         viewHeight = viewSize.height,
         viewWidth = viewSize.width,
         position = callout.getPosition(),
         x = position[0],
         y = position[1],
         h = callout.getHeight(),
         w = callout.getWidth(),
         margin = 10,
         movido=false;
      if (y + h > viewHeight - margin) {
         callout.setPosition( x, viewHeight - h - margin);
         y = callout.getPosition()[1];
         movido=true;
      }
      if (x + w > viewWidth - margin) {
         callout.setPosition( viewWidth - w - margin, y);
         movido=true;
      }  
      if (movido) {
         callout.removeCls("callout-notmoved");
         callout.addCls("callout-moved");
      } else {
         callout.removeCls("callout-moved");
         callout.addCls("callout-notmoved");
      }
   }  
  
});
