//searchField

Ext.define('iDynamicsFront.util.LocatorField', {
    extend: 'Ext.form.field.Text',
    alias: 'widget.uxlocatorfield',
    componentCls: 'ux-locatorfield',
    requires: ['iDynamicsFront.util.SelectionDialog'],
    //parametros obligatorios
    valueField: null,
    displayField: null,
    displayFieldIni: null,
	widthPopup : null,
	heightPopup: null,
    fieldLabel: null,
    acceptBtnGlyph: null,
    cancelBtnGlyph: null,


    /* Objeto para paremetrizar el modo de seleccion
    modo simple, 
    selectionWidget : 'empleadosmaster',

    Con campos adicionales
    selectionWidget : {
        view : 'empleadosmaster',
        extraFields : [{
            field : 'estado',
            value : 'activo'
            //visible  : false,
            //editable : false
        }]
    }

    Especificando el store (por si queremos llamar a otro servicio)
    selectionWidget : {
        view : 'empleadosmaster',
        searchStore : 'xxxxxx'
    } 
    */
    selectionWidget: null,
    onLocatorOpen: null,
    autoSearch: null,
    autoClose: null,
    title: null,
    readOnly:false,
    /*Para añadir filtros dinamicos a la busqueda
    [{
        field    : 'campo',
        value    : 'valor'
        visible  : false,
        editable : false
    }]
    */
    dinamicExtraFields: null,

    triggers: {
        onTriggerClick: {
            cls: 'my-foo-trigger',
            handler: function() {
                var me = this;
                //Construyo  campos adicionales, los declarativos `los dinamicos si los hubiere
                me.dialogWidget.extraFields = [];

                if (me.selectionWidget.extraFields) {
                    Ext.Array.forEach(me.selectionWidget.extraFields, function(f) {
                        me.dialogWidget.extraFields.push(f);
                    })
                }

                if (me.dinamicExtraFields) {
                    Ext.Array.forEach(me.dinamicExtraFields, function(f) {
                        me.dialogWidget.extraFields.push(f);
                    })
                }
                var dlg = Ext.widget(me.dialogWidget); //Construyo el dialogo


                //Capturo los eventos definidos por convenio, acceptselection y cancelselection
                dlg.on({
                    acceptselection: {
                        fn: function(dialog, view) {
                            var me = this;

                            var selection = view.getSelection();

                            if (selection.length > 0) {
                                me.setRecord(selection[0]);
                            } else if (selection) {
                                me.setRecord(selection);
                            }
                            //Me quedo solo con el primero 

                        },
                        scope: this
                    },

                    cancelselection: {
                        fn: function(dialog) {
                            //alert("Cancelado")
                        },
                        scope: this
                    }
                });
            }
        }
    },
    initComponent: function() {
        var me = this;

        //debugger
        Logger.info(me.fieldLabel + ' ' + me.allowBlank);
        if (me.valueField === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio valueField.');
        }
        if (me.selectionWidget === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio selectionWidget.');
        }
        // ver el tipo de selectionWidget

        var selWidget = null;
        var selStore = null;
        var extraFields = [];
        if (typeof me.selectionWidget == "string") {
            selWidget = me.selectionWidget;
        } else {
            selWidget = me.selectionWidget.view;
            extraFields = me.selectionWidget.extraFields;
            selStore = me.selectionWidget.searchStore;
        }

        me.dialogWidget = {
            xtype: 'uxselectiondialog',
            cls: 'locatorDialog',
            btnAcceptText: t('buttons.aceptar'),
            btnCancelText: t('buttons.cancelar'),
            controlItemDblClick: true,
            selectionWidget: selWidget,
            selectionStore: selStore,
            extraFields: extraFields, // luego se sobreescriben
            autoSearch: me.autoSearch,
            autoClose: me.selectionWidget.autoClose || me.autoClose ,
            title: me.title,
            width: me.widthPopup,
            height: me.heightPopup,
            acceptBtnGlyph : me.acceptBtnGlyph,
            cancelBtnGlyph: me.cancelBtnGlyph
        };
        if (me.onLocatorOpen != null) {
            /*
            me.onTriggerCustom = function(oDialogField, dialogWidget) {
               me.onLocatorOpen(oDialogField, dialogWidget);
            }*/
            me.onTriggerCustom = me.onLocatorOpen;
        }

        if (me.readOnly) {
            me.triggers = null;
        }
        me.callParent();
    },

    /**
     *
     */
    setRecord: function(record) {
        //Esperamos en record un Ext.data.Model
        var me = this;

        me._record = record; //No se usa para nada por ahora
        if (me.displayField != null) {
            me.setValue(record.get(me.valueField), record.get(me.displayField));
        } else {
            me.setValue(record.get(me.valueField));
        }
        //me.fireEvent('change');
      
    },

    setValue: function(value, displayValue) {
        //displayValue es opcional (valor a mostrar)
        var me = this;

        if (displayValue == null) {
            displayValue = value;
        }
 

        // Set the value of this field.
        //me.value = value;
        me.setRawValue(displayValue);
        //return me;
        //esto para que lance por ejemplo el evento onchange
        return me.mixins.field.setValue.call(me, value);    
    },

    getValue: function(value) {
        var me = this;
        if (me.value != null)
            return me.value;
        else
            return me.getRawValue();
    },

    getSubmitValue: function() {
        return this.getValue();
    },

    setDinamicExtraFields: function(fields) {
        var me = this;
        me.dinamicExtraFields = fields;
    }


});
