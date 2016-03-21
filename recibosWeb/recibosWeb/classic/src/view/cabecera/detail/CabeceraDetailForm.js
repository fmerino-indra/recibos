Ext.define('recibosWeb.view.cabecera.detail.CabeceraDetailForm', {
    extend         : 'Ext.form.Panel',
    alias          : 'widget.cabecera_cabeceradetailform',
    requires       : ['iDynamicsFront.selectors.UxLocatorField',
        'iDynamicsFront.util.UxDialogFieldN',
        'iDynamicsFront.utils.UxMainToolbar'
    ],
    model          : 'recibosWeb.model.CabeceraDTO',
    modelValidation: true,
    cls            : 'card detail usuario ux-validation-form fa',

    layout: 'column',
/*
    managePadding: true,
*/
    autoScroll: true,

    initComponent: function () {
        this.title = t('cabecera.detail.title');
        this.dockedItems = [
            {
                xtype: 'ux-maintoolbar',
                dock : 'top',
                cls  : 'main-toolbar',
                scale: 'medium',
                items: ['back', '-',
                    {text: 'devolver', action: 'devolver', bind: {disabled: '{!hasAnyPaid}'}, glyph : Glyphs.getIcon('unlock_alt')},
                    {text: 'anular', action: 'anular', bind: {disabled: '{!hasAnyReturned}'}, glyph : Glyphs.getIcon('lock')}
                ]
            }
        ];

        this.items = [
            {
                columnWidth:.15,
                layout : 'vbox',
                items : [
                    {
                        xtype     : 'textfield',
                        fieldLabel: t('cabecera.items.id'),
                        name      : 'id',
                        bind      : "{cabecera.id}",
                        editable  : false,
                        align     : 'right'
                    }
                ]
            },
            {
                columnWidth: .10,
                layout: 'vbox',
                items: [
                    {
                        xtype     : 'textfield',
                        fieldLabel: t('cabecera.items.codigoMes'),
                        bind      : "{cabecera.codigoMes}",
                        name      : 'codigoMes',
                        editable  : false
                    }
                    ,
                    {
                        xtype     : 'textfield',
                        fieldLabel: t('cabecera.items.periodo'),
                        bind      : "{cabecera.periodo}",
                        name      : 'periodo',
                        editable  : false
                    }
                ]
            },
            {
                columnWidth:.25,
                layout : 'vbox',
                items : [
                    {
                        xtype       : 'datefield',
                        format      : 'd-m-Y',
                        fieldLabel  : t('cabecera.items.fechaEmision'),
                        bind        : "{cabecera.fechaEmision}",
                        name        : 'fechaEmision',
//                        width       : 250,
                        editable    : false,
                        readOnly    : true
                    }
                    ,
                    {
                        xtype     : 'datefield',
                        format    : 'd-m-Y',
                        fieldLabel: t('cabecera.items.fechaEnvio'),
                        bind      : "{cabecera.fechaEnvio}",
                        name      : 'fechaEnvio',
//                        width     : 250,
                        editable  : false,
                        readOnly  : true
                    }

                ]
            },
            {
                columnWidth: .25,
                layout: 'vbox',
                items: [
                    {
                        xtype     : 'numberfield',
                        fieldLabel: t('cabecera.items.importe'),
                        bind      : "{cabecera.importe}",
                        name      : 'importe',
                        editable  : false,
                        format   : '0,000.00',
                        hideTrigger: true,
                        keyNavEnabled: false,
                        mouseWheelEnabled: false
                    },
                    {
                        xtype     : 'numberfield',
                        fieldLabel: t('cabecera.items.importeDevuelto'),
                        bind      : "{cabecera.importeDevuelto}",
                        name      : 'importeDevuelto',
                        editable  : false,
                        format   : '0,000.00',
                        hideTrigger: true,
                        keyNavEnabled: false,
                        mouseWheelEnabled: false
                    }
                ]
            },
            {
                columnWidth: .25,
                layout: 'form',
                items: [
                    {
                        xtype: 'textfield',
                        fieldLabel: t('cabecera.items.concepto'),
                        bind: "{cabecera.concepto}",
                        name: 'concepto',
                        editable: false
                    }
                ]
            }

        ];


        this.resetButton = Ext.create('Ext.button.Button', {
            xtype  : 'button',
            text   : t('commons.buttons.reset'),
            handler: 'cabeceraResetEdit',
            ui     : 'plain',
            scale  : 'medium',
            glyph  : Glyphs.getIcon('undo')

        });

        this.saveButton = Ext.create('Ext.button.Button', {
            xtype   : 'button',
            text    : t('commons.buttons.guardar'),
            scale   : 'medium',
            ui      : 'highlight',
            handler : 'cabeceraSave',
            formBind: true,
            glyph   : Glyphs.getIcon('save')
        });
        this.buttons = [this.resetButton, this.saveButton];

        this.callParent();
    },

    afterRender: function () {
        var me = this;
        me.callParent();
        if (me.cabecera !== null) {
            me.setCabecera(me.cabecera);
        }
    },

    /**
     * Load a {@link recibosWeb.model.Operacion} instance
     *
     * @param {recibosWeb.model.Operacion}
     *            operacion
     */
    setCabecera: function (cabecera) {
        var me = this;
        if (cabecera) {
            me.getForm().reset();
            me.cabecera = cabecera;
            me.getForm().loadRecord(cabecera);
        }
    },

    getCabecera: function (cabecera) {
        var me = this, form = me.getForm();
        form.updateRecord(form.getRecord());
        me.cabecera = form.getRecord();
        return me.cabecera;
    }
});
