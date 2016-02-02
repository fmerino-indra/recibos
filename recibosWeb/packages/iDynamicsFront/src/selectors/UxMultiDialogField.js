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
 *               iconCls : 'ux-multidialogfield-searchIcon'
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
Ext.define('iDynamicsFront.selectors.UxMultiDialogField', {
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.uxmultidialogfield',
    requires: ['iDynamicsFront.selectors.UxSelectionDialog'],

    componentCls: 'ux-multidialogfield',

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

    /**
     * Nombre
     * @type {String}
     */
    name: null,

    /**
     * Representa la ventana de dialogo nueva que abre para seleccionar elementos
     * @type {Object} widget
     */
    dialogWidgetConfig: {},
    
    /**
     * Glyph que se le assigna al boton aceptar 
     * */
    acceptBtnGlyph: null,
    
    /**
     * Glyph que se le assigna al boton cancelar 
     * */
   	cancelBtnGlyph: null,

    /**
     * Configuracion del field puente, allowBlank, invalidCls,...
     * @type {Ext.form.field.Text}
     */
    fieldConfig: {},

    /**
     * [triggerConfig description]
     * @type {Object}
     */
    triggerConfig: {},

    /**
     * [selectionWidget description]
     * @type {Object}
     */
    selectionWidget: null, //No necesario si lo metemos en dialogWidgetConfig

    /**
     * Se usa al llamar a getValue() y se usa para saber si ya está seleccionado el record.
     * @type {String}
     */
    valueField: 'value',

    /**
     * Se usa en la columna del grid y al exportar la descripción
     * @type {String}
     */
    displayField: 'name',

    /**
     * Hace el componente de solo lectura, dejando el trigger sin handler
     * @type {Boolean}
     */
    readOnly: false,

    /**
     * Label del componente.
     * @type String
     */
    fieldLabel: '',
    
    /**
     * Posici�n del label. 'left', 'right', 'bottom', 'top'. Defecto 'left'
     * @type String
     */
    labelAlign: 'left',
    
    /**
     * glyph para el bot�n aceptar
     * @type 
     */
    acceptBtnGlyph: null,
    
    /**
     * glyph para el bot�n cancelar
     * @type 
     */
   	cancelBtnGlyph: null,
    
    /**
     * [initComponent description]
     */
    initComponent: function() {
        var me = this,
            triggerConfig, fieldConfig;

        me.layout = {
            type: 'hbox',
            align: 'top'
        };

        fieldConfig = Ext.merge({
            name: me.name,
            fieldLabel: me.fieldLabel,
            labelAlign: me.labelAlign,
            xtype: "textfield",
            height: 0,
            invalidCls: 'x-form-invalid-multidialogfield',
            width: 0,
            style	: 'left: -40000px !important',
            getValue: function() {
                return me.getValue();
            },
            getSubmitValue: function() {
                return me.getSubmitValue();
            },
            setValue: function(value) {
                return me.setValue(value);
            },
            isValid: function() {
                var me = this,
                    valido = false;

                if (me.allowBlank) {
                    valido = true;
                } else {
                    //Algun contenido?
                    if (me.getValue() && me.getValue().length > 0) {
                        valido = true;
                    }
                }

                if (!valido) {
                    //console.debug('isValid:'+me.name +  me.getActiveErrors()  );
                    me.setActiveError(t('validation.required')); // Este campo es obligatorio
                } else {
                    me.unsetActiveError();
                }


                //console.debug('isValid:'+me.name +  (valido? ' es OK': ' es Erroneo')  );
                return valido;
            }
        }, me.fieldConfig);




        triggerConfig = Ext.merge({
            height: 18,
            width: 18,
            margin: "0 0 0 3",
            xtype: "button",
            text: " ",
            cls: "ux-multidialogfield-trigger",
            iconCls: 'ux-multidialogfield-triggerIcon',
            scope: me,
            handler: me.readOnly ? null: me.onTriggerHandler
        }, me.triggerConfig);
		
        // Calculamos la altura dependiendo si el label es (top, bottom) o (left,right)
        var heightCal = me.height ? me.height : 50;
        if(me.labelAlign && (me.labelAlign === 'top' || me.labelAlign === 'bottom')){
        	heightCal -= 20;
        }
        me.items = [{
                //flex: me.flexGrid,
        		flex	: 1,
                height: heightCal,
                xtype: "grid",
                cls: "ux-multidialogfield-grid",
                hideHeaders: true,
                disableSelection: true,
                fillColumn: false,
                store: Ext.create('Ext.data.Store', {
                    fields: [me.displayField, me.valueField]
                }),
                columns: [{
                    flex: 1,
                    dataIndex: me.displayField,
                    cls: '',
                    tdCls: 'ux-multidialogfield-displayCol'
                }, {
                    width: 30,
                    dataIndex: 'falseindex',
                    cls: '',
                    tdCls: 'ux-multidialogfield-removeBtnCol',
                    listeners: {
                        click: function(grid, cell, rowIndex, cellIndex, e, record, row) {
                            me.procesaRemoveElemento(record);
                        }
                    }
                }]
            },
            triggerConfig,
            fieldConfig

            /*{
               xtype : "textfield",
               name : me.name,
               height : 0, width: 0,
               allowBlank : false,
               getValue : function() {
                  return me.getValue();
               },
               getSubmitValue : function() {
                  return me.getSubmitValue();
               },
               setValue : function(value) {
                  return me.setValue(value);
               }
            }*/
        ];
        me.callParent();

        var f = me.down('textfield')
        f.on('validitychange', me.onValidityChange);
    },


    onValidityChange: function(field, isValid, evnt) {
        var me = this,
            grid = me.up('uxmultidialogfield').down('grid');;

        //console.debug('onValidityChange:'+field.name + (isValid? ' es OK': ' es Erroneo') );
        if (isValid) {
            grid.removeCls(me.invalidCls); //'x-form-invalid-multidialogfield');//.   x-form-invalid-field x-form-invalid-field-default
        } else {
            grid.addCls(me.invalidCls);
            //TODO: punto de exrtension para añadir tooltip con el error
            // var tip = Ext.create('Ext.tip.ToolTip', {
            //     target: grid,
            //     autoHide : true,
            //     html: me.getActiveError( )
            // });        
        }


    },
    /**
     * @event
     * [onTriggerHandler description]
     * @param  {Ext.Button.button} btn Boton
     */
    onTriggerHandler: function(btn) {
        var me = this,
            dialogWidgetConfig, store = me.getStore();
        
        dialogWidgetConfig = Ext.merge({
            xtype: 'uxselectiondialog2',
            cls: 'locatorDialog',
            target: btn, //Metemos un target por si queremos usar un callout en vez de un uxselectiondialog
            //btnAcceptText : t('commons.button.acceptText'),
            btnAcceptText: t('commons.button.addText'),
            //btnCancelText : t('commons.button.cancelText'),
            btnCancelText: t('commons.button.closeText'),
            acceptBtnGlyph: me.acceptBtnGlyph,
   			cancelBtnGlyph: me.cancelBtnGlyph,
            controlItemDblClick: true,
            selectionWidget: me.selectionWidget,
            title : me.title,
            width: 800,
            height: 600,
            autoClose: false,
            aceptDisabledIfNoSelection: true,
            listeners: {
                scope: me,
                acceptselection: me.onAcceptSelection,
                cancelselection: me.onCancelSelection
            }
        }, me.dialogWidgetConfig);

        //debugger 

        me.dlg = Ext.widget(dialogWidgetConfig);
        //Hacemos que el grid muestre distintas las filas ya seleccionadas (incluidas en este otro store, no es una seleccion controlada por el grid)
        me.dlg.injectedtablepanel.getView().getRowClass = function(record, index) { //http://docs.sencha.com/extjs/4.2.2/#!/api/Ext.view.Table-method-getRowClass
            if (store.findExact(me.valueField, record.get(me.valueField)) != -1) {
                //debugger
                //console.log(me.valueField+ " : "+ record.get(me.valueField))
                return "ux-multidialogfield-selected";
            } else {
                return "";
            }
        };
        me.dlg.show();

        //Lanzar Evento dialogCreation(dlg)
    },

    /**
     * @event
     * [onAcceptSelection description]
     * @param  {Object} dialog
     * @param  {Object} selectionModel
     */
    onAcceptSelection: function(dialog, selectionModel) {
        var me = this,
            fieldGrid = me.getGrid(),
            store = fieldGrid.getStore(),
            selection = selectionModel.getSelection(),
            view = me.dlg.injectedtablepanel.getView(),
            field = me.down('textfield'),
            preserveScrollOnRefresh,
            index, objRec={};
        if (selection != null) {
            Ext.suspendLayouts();

            Ext.Array.each(selection, function(record, index) {
                
                objRec[me.displayField ] = record.get(me.displayField);
                objRec[me.valueField ] = record.get(me.valueField);

                index = store.find(me.valueField, record.get(me.valueField) );
                if (index == -1) {
                    store.add(record);
                    //me.fireEvent('addRecord', me, objRec); //record.get(me.valueField)
                } else {
                    //store.remove(record)
                    store.removeAt(index);
                }
            });
            fieldGrid.getView().refresh(); //FIX: si no, a veces no repinta bien
            Ext.resumeLayouts(true);
            selectionModel.deselectAll();
            preserveScrollOnRefresh = view.preserveScrollOnRefresh;
            view.preserveScrollOnRefresh = true;
            view.refresh(); //Repintamos para que actualice las clases de fila
            view.preserveScrollOnRefresh = preserveScrollOnRefresh;
            field.validate();
        }
    },

    /**
     * @event
     * [onCancelSelection description]
     * @param  {Object} dialog
     * @param  {Object} selectionModel
     */
    onCancelSelection: function(dialog, selectionModel) {
        var me = this;
        Ext.getBody().focus();
        me.dlg.close();
	    
		
    },

    /**
     * [procesaRemoveElemento description]
     * @param  {Object} record
     */
    procesaRemoveElemento: function(record) {
        var me = this,
            store = me.getStore(),
            index = store.indexOf(record),
            field = me.down('textfield');

        store.remove(record);
        me.fireEvent('removeRecord', me, record.get(me.valueField), index);
        field.validate();
    },

    /**
     * [getStore description]
     * @return {Object} Store
     */
    getStore: function() {
        var me = this,
            grid = me.getGrid(),
            store = (grid ? grid.getStore() : null);
        return store;
    },
    
    getGrid: function() {
        var me = this;
        return me.down("grid");
    },    

    /**
     * [getValue description]
     * @return {Array} records
     */
    getValue: function() {
        var me = this,
            store = me.getStore(),
            ret = [];

        if (store === null) return null; //FMA
        store.each(function(record) {
            ret[ret.length] = record.get(me.valueField);
        });
        return (ret.length > 0 ? ret : null);
        //No se retornan arrays de tamaño 0 ...
    },

    /**
     * [getSubmitValue description]
     * @return {String}
     */
    getSubmitValue: function() {
        return this.getValue();
    },

    /**
     * [setValue description]
     * @param {Array} velues
     */
    setValue: function(values) {
        var me = this,
            store = me.getStore();
        if (store) {
            store.removeAll();
           // if (Ext.isArray(value)) {
                store.add(values);
           // }
           // 
        }

    },

    /**
     * Para añadir filtros dinamicos a la busqueda
     * @param {Array} fields
     * {
     *    field    : 'campo',
     *    value    : 'valor'
     *    visible  : false,   opcional
     *    editable : false    opcional
     * }
     */
    setDinamicExtraFields: function(fields) {
        var me = this;
        me.dialogWidgetConfig.selectionWidget.dinamicExtraFields = fields;
    }
});
