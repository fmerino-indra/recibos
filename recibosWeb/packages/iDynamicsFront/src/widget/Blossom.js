Ext.define('iDynamicsFront.widget.Blossom', {
    extend : 'Ext.Widget',
    requires : [],
    alias : 'widget.blossom',
    config : {
        cls : 'blossom'
    },

    value : [0,0,0,0],
    
    petals : ['TL', 'TR', 'BL', 'BR'],
    
    // The element template passed to Ext.Element.create()
    element : {
        tag : 'div',
        cls : 'blossom',
        reference : 'element',
        listeners : {
            click : 'onClick'
        }
    },

    constructor : function(config) {
        var me = this;
        // It is important to remember to call the Widget superclass constructor
        // when overriding the constructor in a derived class. This ensures that
        // the element is initialized from the template, and that initConfig() is
        // is called.
        Ext.apply(me, config);

        this.callParent([config]);

        
        if (me.value) {
            me.maxVal = Ext.Array.max(me.value);
            me.draw();
        }
       
    },
    
    setValue: function(value) {
        var me = this;
        me.value = value;
        me.maxVal = Ext.Array.max(me.value);
        me.redraw();
    },
    
    redraw : function() {
        var me=this;
        me.updatePetal(0);     
        me.updatePetal(1);     
        me.updatePetal(2);     
        me.updatePetal(3);     
    },
    
    updatePetal: function (position) {
        var me=this,
            squareSide = me.width / 2 - 2, // por el margin 1
            innerSquareSide = squareSide * me.value[position] / me.maxVal, 
            filler = squareSide - innerSquareSide + 'px',
            cls = '.petal' + me.petals[position],
            petal = me.element.child(cls),
            wi = petal.child('div');

        wi.setStyle('width', innerSquareSide + 'px');
        wi.setStyle('height', innerSquareSide + 'px');   
        
        if (position == 0) {  
            // Top Left
            wi.setStyle('margin-left', filler);
            wi.setStyle('margin-top', filler);
            wi.setStyle('border-radius', '50% 50% 0 50%');

        } else if (position == 1) {
            // Top Right
            wi.setStyle('margin-top', filler);
            wi.setStyle('margin-right', filler);
            wi.setStyle('border-radius', '50% 50% 50% 0');
            //me.updatePetalRadius(wi, filler, '50% 50% 50% 0');
        } else if (position == 2) {
            // Bottom Left          
            wi.setStyle('margin-left', filler);
            wi.setStyle('margin-bottom', filler);
            wi.setStyle('border-radius', '50% 0 50% 50%');
            //me.updatePetalRadius(wi, filler, '50% 0 50% 50%');
        } else if (position == 3) {
            // Bottom Right
            wi.setStyle('margin-right', filler);
            wi.setStyle('margin-bottom', filler);
            wi.setStyle('border-radius', '0 50% 50% 50%');
            //me.updatePetalRadius(wi, filler, '0 50% 50% 50%');
        }        
        
        
           
    },
    draw : function() {
        var me=this,
            p0 = me.createPetal(0),
            p1 = me.createPetal(1),
            p2 = me.createPetal(2),
            p3 = me.createPetal(3);

        me.element.appendChild(p0);
        me.element.appendChild(p1);
        me.element.appendChild(p2);
        me.element.appendChild(p3);

        // After calling the superclass constructor, the Element is available and
        // can safely be manipulated. Reference Elements are instances of
        // Ext.Element, and are cached on each Widget instance by reference name.
        Ext.getBody().appendChild(me.element);        
    },
        
    createPetal : function(position) {
        var me = this, 
            squareSide = me.width / 2 - 2, // por el margin 1
            innerSquareSide = squareSide * me.value[position] / me.maxVal, 
            filler = squareSide - innerSquareSide + 'px', 
            wrapperDiv = {
                tag : 'div',
                cls: 'petal' + me.petals[position],
                width : squareSide,
                height : squareSide,
                style : 'display: block; margin: 1px; float: left'
            }, innerDiv = {
                tag : 'div',
                style : 'display: block; float: left',
                html: '<span class="el-number">' +  me.label[position] +'</span>'
            }
            // , label = {
                // tag: 'span',
                // cls: 'el-number'
            // }
            ;

        console.log('squareSide:' + squareSide + ', innerSquareSide:' + innerSquareSide);

        var w = Ext.Element.create(wrapperDiv), 
            wi = Ext.Element.create(innerDiv);
            // sp = Ext.Element.create(label);

        w.setStyle('width', squareSide + 'px');
        w.setStyle('height', squareSide + 'px');

        wi.setStyle('width', innerSquareSide + 'px');
        wi.setStyle('height', innerSquareSide + 'px');

        if (position == 0) {  
            // Top Left
            wi.addCls('petalo TL');
            wi.setStyle('margin-left', filler);
            wi.setStyle('margin-top', filler);
            wi.setStyle('border-radius', '50% 50% 0 50%'); 
        } else if (position == 1) {
            // Top Right
            wi.addCls('petalo TR');
            wi.setStyle('margin-top', filler);
            wi.setStyle('margin-right', filler);
            wi.setStyle('border-radius', '50% 50% 50% 0');
        } else if (position == 2) {
            // Bottom Left
            wi.addCls('petalo BL');            
            wi.setStyle('margin-left', filler);
            wi.setStyle('margin-bottom', filler);
            wi.setStyle('border-radius', '50% 0 50% 50%');
        } else if (position == 3) {
            // Bottom Right
            wi.addCls('petalo BR');
            wi.setStyle('margin-right', filler);
            wi.setStyle('margin-bottom', filler);
            wi.setStyle('border-radius', '0 50% 50% 50%');
        }

        // wi.appendChild(sp);
        w.appendChild(wi);
        return w;
    },

    onClick : function() {
        // listeners use this Widget instance as their scope
        //console.log('element clicked', this);
    },

    onInnerClick : function() {
        // access the innerElement reference by name
        //console.log('inner element clicked', this.innerElement);
    }
});
