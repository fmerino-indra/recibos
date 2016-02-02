
/*
 * searchFieldN
 */

Ext.define('iDynamicsFront.util.UxDialogFieldN', {
    extend 		: 'Ext.container.Container',
    alias 		: 'widget.uxdialogfieldn',
    requires 	: ["iDynamicsFront.util.SelectionDialog"],   
    editable 	: false,
    cls			: 'ux-dialog-fieldn',

    margin		: '0 0 10 0',

    
    mixins      : [
        //This mixin provides a common interface for the logical behavior and state of form fields, including:
        'Ext.form.field.Field'
    ],
    
    dialogWidget	: null,
    selectionWidget	: null,
    displayField	: null,
    store			: null,
    labelWidth		: 350,
    inputWidth		: 350,
    labelText		: null,    //deprecated en favor de fieldLabel
    fieldLabel      : null,   // 
    valueField		: null,
    autoSearch		: null,
    defaultHeight	: 61,
    minHeight		: null,
    maxHeight		: null,
    title			: null,
    /**
     * Requerido o no.
     */
    allowBlank      : true,    
    
    initComponent: function(){
    	var me = this, minHeight = null, maxHeight = null;
    	
    	if(me.displayField === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio labelField de ', this.self.getName());
        };

        if(me.selectionWidget === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio selectionWidget de ', this.self.getName());
        };
        
        if(me.valueField === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio valueField de ', this.self.getName());
        };

        if (!me.fieldLabel) me.fieldLabel = me.labelText;

        var selWidget=null;
        var selStore=null;
        var extraFields=[];
        if (typeof me.selectionWidget == "string") {
            selWidget=me.selectionWidget;
        } else {
            selWidget = me.selectionWidget.view;
            extraFields=me.selectionWidget.extraFields;
            selStore=me.selectionWidget.searchStore;
        }

	    me.dialogWidget = {
	        xtype				:'uxselectiondialog',
	        cls					: 'locatorDialog',
	        btnAcceptText 		: t('buttons.aceptar'),
	        btnCancelText 		: t('buttons.cancelar'),
	        controlItemDblClick : false, 
	        selectionWidget		: selWidget,
            selectionStore      : selStore, 
            extraFields         : extraFields,  // luego se sobreescriben                        
	        autoSearch			: me.autoSearch,
	        title				: me.title
	    };
    	
    	var label = {
    		xtype	: 'label',
    		width	: me.labelWidth,
    		text	: me.labelText,
    		cls		: 'x-form-item-label'
    	};
    	
    	// calculamos el tama�o del boundList dependiento del max y el min pasado por parametro
    	if(me.minHeight){minHeight = me.minHeight;}
    	if(me.maxHeight){maxHeight = me.maxHeight;}
    	if(!minHeight && maxHeight){minHeight = maxHeight;}
    	if(minHeight && !maxHeight){maxHeight = minHeight;}
    	if(!minHeight && !maxHeight){maxHeight = me.defaultHeight; minHeight = me.defaultHeight;}
    	
    	var boundList = Ext.widget('boundlist', {
			store		: me.store,
			displayField: me.displayField,
			width		: me.inputWidth,
			minHeight	: minHeight,
			maxHeight	: maxHeight,
			autoScroll	: true
    	});
    	
    	boundList.on('selectionchange', function(selModel, models, evt){
    		me.selectionChangeBoundList(selModel, models, evt, me);
    	});
    	
    	var btnCnt = {
    		xtype	: 'container',
    		cls		: 'btns-container-boundlist',
    		items	: [{
    			xtype		: 'button',
    			text		: 'Eliminar',
    			cls			: 'ux-btn',
    			name		: 'boundListDelete',
    			disabled	: true,
    			listeners	: {click: me.onRemoveClick}
    		},{
    			xtype		: 'button',
    			text		: 'Añadir',
    			cls	 		: 'ux-btn-highlighted',
    			listeners	: {click: me.onAddClick, scope : me}
    		}]
    	
    	};
    	
    	me.items = [label, boundList, btnCnt];
    	
    	me.callParent();
    	
    },
    
    selectionChangeBoundList : function(selModel, models, evt, scope){
    	var me = scope || this, btnsDelete, btn;
    	
    	btn = me.down('button[name=boundListDelete]');
	    
    	if(models.length > 0){
	    	btn.enable();
    	}else{
    		btn.disable();
    	}
    
    },
    
    onAddClick : function(){
        var me=this;
		//var dlg = Ext.widget(me.dialogWidget);
        


                //Construyo  campos adicionales, los declarativos `los dinamicos si los hubiere
                me.dialogWidget.extraFields = [];

                if (me.selectionWidget.extraFields) {
                    Ext.Array.forEach(me.selectionWidget.extraFields, function(f) {
                         me.dialogWidget.extraFields.push(f);
                    })                     
                }

                if( me.dinamicExtraFields) {
                    Ext.Array.forEach(me.dinamicExtraFields, function(f) {
                         me.dialogWidget.extraFields.push(f);
                    }) 
                }
                var dlg = Ext.widget(me.dialogWidget); //Construyo el dialogo



		//Capturo los eventos definidos por convenio, acceptselection y cancelselection
        dlg.on({
        	
            acceptselection: {fn: function(dialog){
                var me=this;

                var selectionView=null;
                if (typeof me.selectionWidget == "string") {
                    selectionView = dlg.down(me.selectionWidget);
                } else {
                    selectionView = dlg.down(me.selectionWidget.view);
                }

                
                var selection = selectionView.getSelection();
                if (selection.length>0){
                	Ext.Array.each(selection, function(item, index, countriesItSelf) {
	                    me.setRecord(item);
					});
                } else if(selection){
                	me.setRecord(selection);
                }
                //dlg.close();
                 
            }, scope: this},
            
            cancelselection: {fn: function(dialog){
               //alert("Cancelado")
            }, scope: this}
        });

    },
    
    onRemoveClick : function(){
    	var me = this, boundList;
    	boundList = Ext.ComponentQuery.query('boundlist', me.up('uxdialogfieldn'))[0];
    	boundList.getStore().remove(boundList.getSelectionModel().selected.items);
    },
    
    setRecord: function(record){
        //Esperamos en record un Ext.data.Model
        var me=this, dato = {}, array, existe = false;
        
        array = me.down('boundlist').getStore().data.items;
        dato[me.valueField] = record.data[me.valueField];
        dato[me.displayField] = record.data[me.displayField];
        
        Ext.Array.each(array, function(item, index, itemItSelf) {
		    if (item.data[me.valueField] === dato[me.valueField]) {
		    	existe = true;
		        return false; // break here
		    }
		});
		
        if(!existe){
        	me.down('boundlist').getStore().add(dato);
        }
        
    },
    
    getSubmitValue: function() {
        return this.getValue();
    },
    
    getData : function(){
    	var me = this, datos = me.store.data.items, len = datos.length, valor, valores = [];
    	while(len--){
    		valor = {};
    		valor[me.valueField] = datos[len].data[me.valueField];
    		valor[me.displayField] = datos[len].data[me.displayField];
    		valor['version'] = '-1';
    		valores[len] = valor; 
    	}
    	
    	return valores;
    	
    },
    
    getName : function(){
   		return this.name;
    },
    

    getActiveErrors : function() {
        var me = this;
        return me.getErrors();
    },

    isValid : function() {
        var me = this;
        return me.disabled || Ext.isEmpty(me.getErrors());
    },

    // metodos del mixin Field

    getValue: function(value) {
        var me = this;
      return me.getData(); 
    },    

    setValue : function(){
       
    },    

    getErrors : function(){
            var me=this;
            
            //console.info('En getErrors de ' + me.fieldLabel + ' que tiene ' + me.getValue().length + ' elementos y allowBlank:' + me.allowBlank);
            if (!me.allowBlank && me.getValue().length===0) {
                
                //console.info('----- ' + me.fieldLabel + ' Es requerido ');
 //               return [{id: me.fieldLabel, msg: 'Es requerido'}]
                return ['Este campo es obligatorio']
            }
            return ;
    }
    
});