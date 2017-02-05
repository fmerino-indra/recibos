/**
 * Componente que permite acumular elementos seleccionados desde un dialogo que abre.
 *
 *      @example
 *       {
 *            xtype : 'uxmultidialogfield',
 *            name : 'paises',
 *            fieldLabel : 'Paises',
 *            reference : 'paises',
 *            height : 53,
 *            width : 400,
 *            valueField : 'cdpais',
 *            displayField : 'dspais',
 *            triggerConfig : {
 *               iconCls : 'idf-searchfield-searchIcon'
 *            }, //Para que salga lupa en vez del +
 *            dialogWidgetConfig : {//Configuración del dialogo que sale, solo es necesario lo del selectionWidget
 *               width : 800,
 *               height : 500,
 *               modal : true,
 *               selectionWidget : {
 *                  xtype : 'paisesmaster',
 *                  multiSelect : true
 *               } //*necesario que soporte eso multiSelect:true para que el grid tenga multiseleccion, el componente no transforma la vista para hacerlo multiseleccion
 *            }
 *       },
 *
 */
Ext.define('iDynamicsFront.selectors.SearchField', {
    extend  : 'Ext.form.FieldContainer',
    alias   : 'widget.idf-searchfield',
    requires: ['iDynamicsFront.selectors.UxSelectionDialog'],

    componentCls: 'idf-searchfield',

    //parametros obligatorios
    /*
     valueField : null,
     displayField : null,
     selectionWidget : null,
     onLocatorOpen: null,

     //Opcionales
     dialogWidth: null,
     dialogHeight: null,
     */

    // ver http://www.eekboom.de/ClearButton.html
    //plugins: ['clearbutton'],

    //PARAMETROS
    name              : null,
    dialogWidgetConfig: null,
    selectionWidget   : null, //No necesario si lo metemos en dialogWidgetConfig
    valueField        : 'value', //Se usa al llamar a getValue() y se usa para saber si ya está seleccionado el record.
    displayField      : 'name', //Se usa en la columna del grid y al exportar la descripción
    displayTpl		  : null,
    triggerConfig     : {},
    multiSelect       : false,
    selectorHeight    : 50,
    autoComplete      : false,
    //readOnly          : false,
    comboCfg          : {},
    //value             : null,
    allowBlank        : true,
    /**
     * Glyph que se le assigna al boton aceptar 
     * */
    acceptBtnGlyph: null,
    
    /**
     * Glyph que se le assigna al boton cancelar 
     * */
   	cancelBtnGlyph: null,

    valuesStore:null, //Si no se especifica se crea una store con un modelo que solo tiene displayfield y valuefield
	config:{
		store: null,
		value: null
	},
    
    initComponent: function () {
        var me = this,
            triggerConfig, comboConfig;
//FMM        
//debugger;        
        me.cls = me.cls + (me.dialogWidgetConfig ? ' advanced' : ' basic');
        me.triggerConfig = triggerConfig = Ext.merge(
            {
                cls    : "idf-searchfield-trigger",
                // iconCls: 'idf-searchfield-triggerIcon',
                glyph  : Glyphs.getIcon('search'),
                scope  : me,
                handler: me.onTriggerHandler
            }
            , me.triggerConfig);
        me.valuesStore = ( me.valuesStore ? me.valuesStore : Ext.create('Ext.data.Store',{fields:[me.displayField,me.valueField]}) );
        me.items = [];

        if (me.multiSelect) {
            me.items.push(
                {
                    flex            : 1,
                    xtype           : "grid",
                    cls             : "idf-searchfield-grid",
                    hideHeaders     : true,
                    disableSelection: true,
                    height          : me.selectorHeight,
                    fillColumn      : false,
                    store           : me.valuesStore,
                    listeners       : {
                        itemdblclick: me.onItemDblClick,
                        scope       : me
                    },
                    columns         : [
                        {
                            flex     : 1,
                            dataIndex: me.displayField,
                            cls      : '',
                            tdCls    : 'idf-searchfield-displayCol',
                            renderer : function(value, metadata, record){
                            	
                            	if(me.displayTpl){
                            		if(!me.displayTpl.isTemplate){
				                		var sal = new Ext.Template(me.displayTpl); 
				                		return sal.evaluate(record.data);
				                	}else{
					                	return me.displayTpl.evaluate(record.data);
				                	}
                            	}else{
                            		return value;
                            	}
                            } 
                        },
                        {
                            width    : 15,
                            dataIndex: 'falseindex',
                            cls      : '',
                            tdCls    : 'idf-searchfield-removeBtnCol',
                            listeners: {
                                click: function (grid, cell, rowIndex, cellIndex, e, record, row) {
                                	if( !grid.up('idf-searchfield').hasCls('x-item-disabled') ){
	                                    me.procesaRemoveElemento(record);
                                	}
                                }
                            }
                        }
                    ]
                }
            );
        }
        if (!me.multiSelect || (me.multiSelect && me.autoComplete)) {
            comboConfig = Ext.applyIf({
                anchor        : '100%',
                queryMode     : 'remote',
                displayField  : me.displayField,
                editable      : me.autoComplete,
                forceSelection: me.autoComplete,
                valueField    : me.valueField,
                value         : me.value,
                allowBlank    : me.allowBlank,
                queryParam    : me.queryParam || me.displayField,
                store         : me.store || Ext.create('Ext.data.Store', {fields: [me.displayField, me.valueField]}),
                readOnly      : me.readOnly,
                listeners     : {
                    select: me.onComboSelect,
                    scope : me
                },
                getValue      : function () {
                    return null;
                }
            }, me.comboCfg);

            if (me.dialogWidgetConfig) {
                comboConfig.triggers = {
                    picker: triggerConfig
                }
            }
            if (!me.autoComplete && me.dialogWidgetConfig) {
                comboConfig.onTriggerClick = Ext.emptyFn;
            }
            me.combo = Ext.widget('combobox', comboConfig);
            me.items.splice(0, 0, me.combo);
        }

        if (me.multiSelect && !me.autoComplete) {
            me.layout = {
                type : 'hbox',
                align: 'stretch'
            };
            me.items.push(Ext.merge(
                {
                    height: 18,
                    width : 18,
                    margin: "0 0 0 3",
                    xtype : "button",
                    text  : " "
                },
                me.triggerConfig
            ));
        } else {
            me.layout = {type: 'anchor'};
        }
        me.items.push({
            xtype         : 'hidden',
            name          : me.name,
            getValue      : function () {
                return me.getValue();
            },
            getSubmitValue: function () {
                return me.getSubmitValue();
            },
            setValue      : function (value) {
               return me.setValue(value);
            },
            reset         : function () {
               return me.reset();
            }

        });
        me.callParent(arguments);
    },

    initEvents: function () {
        this.callParent(arguments);
        this.valuesStore.on('datachanged', this.onStoreDataChanged, this);
    },

    onComboSelect: function (combo, record) {
        var valuesStore;
        if (!this.multiSelect) {
            valuesStore = this.getValuesStore();
            valuesStore.removeAll();
            valuesStore.commitChanges();
        } else {
            combo.clearValue();
        }
        this.getValuesStore().add(record);
    },

    onTriggerHandler: function (btn) {
        this.launchSearcher(btn);
    },

    launchSearcher: function (btn, data) {
        var me = this,
            dialogWidgetConfig, store, searchForm, searchData = {};
debugger;
        if (me.dialogWidgetConfig) {
            store = me.getValuesStore();
            dialogWidgetConfig = Ext.merge({
                xtype                     : 'uxselectiondialog',
                cls                       : 'locatorDialog',
                target                    : btn, //Metemos un target por si queremos usar un callout en vez de un uxselectiondialog
                //btnAcceptText : $AC.i18n('commons.buttons.acceptText'),
                btnAcceptText             : me.btnAcceptText || t('commons.buttons.addText'),
                //btnCancelText : $AC.i18n('commons.buttons.cancelText'),
                btnCancelText             : me.btnCancelText || t('commons.buttons.closeText'),
                acceptBtnGlyph			  : me.acceptBtnGlyph,
   				cancelBtnGlyph			  : me.cancelBtnGlyph,
                controlItemDblClick       : true,
                selectionWidget           : Ext.applyIf(me.selectionWidget, {multiSelect: me.multiSelect}),
                width                     : 800,
                height                    : 600,
                autoClose                 : false,
                aceptDisabledIfNoSelection: true,
                listeners                 : {
                    scope          : me,
                    acceptselection: me.onAcceptSelection,
                    cancelselection: me.onCancelSelection
                }
            }, me.dialogWidgetConfig);
            me.dlg = Ext.widget(dialogWidgetConfig.xtype, dialogWidgetConfig);
            if (data) {
                searchForm = me.dlg.down('form');
                if (searchForm) {
                    searchData[me.displayField] = data.get(me.displayField);
                    searchForm.loadRecord(Ext.create('Ext.data.Model', searchData));
                }
            }
            //Hacemos que el grid muestre distintas las filas ya seleccionadas (incluidas en este otro store, no es una seleccion controlada por el grid)
            me.dlg.injectedtablepanel.getView().getRowClass = function (record, index) {
                if (store.findExact(me.valueField, record.get(me.valueField)) != -1) {
                    //debugger
                    //console.log(me.valueField+ " : "+ record.get(me.valueField))
                    return "idf-searchfield-selected"
                } else {
                    return "";
                }
            };
            me.dlg.show();
        }
    }
    ,

    onItemDblClick: function (grid, record) {
        this.launchSearcher(grid, record);
    },

    onAcceptSelection: function (dialog, selectionModel) {
        var me = this,
            store = me.getValuesStore(),
            selection = selectionModel.getSelection(),
            view = me.dlg.injectedtablepanel.getView(),
            preserveScrollOnRefresh, displayFieldValue;
        if (selection != null) {
            if (!me.multiSelect) {
                store.removeAll();
            }
            Ext.Array.each(selection, function (record, index) {
                index = store.find(me.valueField, record.get(me.valueField));
                if (index == -1) {
                    store.add(record)
                } else {
                    //store.remove(record)
                    store.removeAt(index);
                }
            });
            if (!me.multiSelect) {
                me.combo.setValue(store.getAt(0).get(me.valueField));
                
                // Comprobamos si existe un displayTpl para mostrarlo en vez del displayField
                if(me.displayTpl){
                	if(!me.displayTpl.isTemplate){
                		var sal = new Ext.Template(me.displayTpl); 
                		displayFieldValue = sal.evaluate(store.getAt(0).data);
                	}else{
	                	displayFieldValue = me.displayTpl.evaluate(store.getAt(0).data);
                	}
                }else{
                	displayFieldValue = store.getAt(0).get(me.displayField);
                }
                
                me.combo.setRawValue(displayFieldValue);
                me.dlg.close();
            } else {
                selectionModel.deselectAll();
                preserveScrollOnRefresh = view.preserveScrollOnRefresh;
                view.preserveScrollOnRefresh = true;
                view.refresh(); //Repintamos para que actualice las clases de fila
                view.preserveScrollOnRefresh = preserveScrollOnRefresh;
                me.fireChangeEvent('update'); //Lanzo onchange TODO:: operation puede ser add o remote depende del elemento seleccionado 
            }
        }
    }
    ,

    onCancelSelection: function (dialog, selectionModel) {
        var me = this;
        me.dlg.close();
    }
    ,

    procesaRemoveElemento: function (record) {
        var me = this,
            store = me.getValuesStore();
        // TODO: añadir un beforeDelete para poder para el borrado
        //me.beforeDelete();
        store.remove(record);
        me.fireChangeEvent('delete'); //Lanzo onchange 
    }
    ,

    getValuesStore: function () {
        return this.valuesStore;
    }
    ,

    getValue: function (value) {
        var me = this,
            store = me.getValuesStore(),
            value,
            ret = [];

        if (me.multiSelect) {
            store.each(function (record) {
                if (me.returnType === 'string') {
                    ret[ret.length] = record.get(me.valueField);
                } else {
                    ret[ret.length] = record.data;
                }

            });
        } else {
            if (me.returnType === 'string') {
                var record = store.getAt(0);
                return record ? record.get(me.valueField) : null;
            } else {
                value = store.getAt(0);
                ret = value ? value.data : null;
            }
        }

        return ret ? (Ext.isObject(ret) ? ret : (ret.length > 0 ? ret : null)) : null; //No se retornan arrays de tamaño 0 ...
    }
    ,

    getSubmitValue: function () {
        return this.getValue();
    }
    ,

    setValue: function (value) {
        var me = this, 
            store = me.getValuesStore(),
            extractValue, comboStoreValue, valueFieldValue, displayFieldValue;
            
        
        if (Ext.isObject(value)) {
            extractValue = function (field) {
               var val = null;
               Ext.each(field.split('.'), function (key) {
                   val = !val ? value[key] : val[key];
               });
               return val;
            };
            if (!me.multiSelect) {
                store.removeAll();
                store.commitChanges();
                comboStoreValue = {};
                valueFieldValue = extractValue(me.valueField, value);
                
                // Comprobamos si existe un displayTpl para mostrarlo en vez del displayField
                if(me.displayTpl){
                	if(!me.displayTpl.isTemplate){
                		var sal = new Ext.Template(me.displayTpl); 
                		displayFieldValue = sal.evaluate(value);
                	}else{
	                	displayFieldValue = me.displayTpl.evaluate(value);
                	}
                }else{
                	displayFieldValue = extractValue(me.displayField, value);
                }
                
                comboStoreValue[me.displayField] = displayFieldValue;
                comboStoreValue[me.valueField] = valueFieldValue;
                //Si esta configuración no está incluida, cuando el combobox es readOnly, si seleccionas el componente y pulsas una tecla, se despliega el store del combo
                // ya que captura el evento keyup
                me.combo.lastMutatedValue = displayFieldValue;
                if (Ext.Array.indexOf(Ext.Array.map(me.combo.getStore().data.items, function (item) {
                        return item.get([me.valueField]);
                    }), valueFieldValue) === -1) {
                    me.combo.getStore().add(comboStoreValue);
                }
                me.combo.setValue(valueFieldValue);
                store.add(value);
            } 
        }else{
        	
	        if (me.multiSelect) { //Caso multiselect: esperamos un array y segun lo que venga lo cargamos como modelos o como datos para la store
	            if (store) {
	                store.removeAll();
	                if (Ext.isArray(value) && value.length>0){
	                    if (value[0] && value[0].isModel){
	                        store.add(value);
	                    } else {
	                        store.loadData(value);
	                    }
	                }
	                me.fireChangeEvent('load');
	            }          
	        }
        
        }
    },
    
    /* 
     * Se lanza el evento change de que ha cambiado el valor del componente
     * El evento lanza los atributos:
     * 	-Elemento input donde tiene almacenado los valores
     * 	-Los valores
     * 	-Operacion que se hace para lanzar el evento change
     * 		.load (cuando se carga los valores del componente a traves del setValue)
     * 		.update (cuando se actualiza los valores desde el popup, puede ser añadir o eliminar elementos)
     * 		.delete (cuando se elimina desde el aspa)
     */
    fireChangeEvent: function (operation){
      var me=this,
         hiddenfield = me.down("hiddenfield");
      if (hiddenfield){
      	
         hiddenfield.fireEvent('change', hiddenfield, me.getValue(), operation);
         me.fireEvent('change', operation, me.getValue());
      }
    },    

    onStoreDataChanged: function (store) {
        this.fireEvent('change', this, store);
    },

    setReadOnly: function (readOnly) {
        var me = this;
        if(me.combo) {
            me.combo.setReadOnly(readOnly);
        }
        if(readOnly) {
            this.addCls('read-only');
        } else {
            this.removeCls('read-only');
        }
        this.readOnly = readOnly;
    },
    reset      : function () {
        var me = this;
        me.getValuesStore().removeAll();
    },

    isReadOnly : function () {
        return this.readOnly || false;
    }

});