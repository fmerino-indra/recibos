Ext.define('recibosWeb.view.preemision.detail.PreemisionDetailForm', {
    extend         : 'Ext.form.Panel',
    alias          : 'widget.preemision_preemisiondetailform',
    requires       : ['iDynamicsFront.selectors.UxLocatorField',
        'iDynamicsFront.util.UxDialogFieldN',
        'iDynamicsFront.utils.UxMainToolbar'
    ],
    model          : 'recibosWeb.model.PreemisionDTO',
    modelValidation: true,
    cls            : 'card detail usuario ux-validation-form fa',

    layout: 'column',
/*
    managePadding: true,
*/
    autoScroll: true,

    initComponent: function () {
        this.title = t('preemision.detail.title');
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
                        fieldLabel: t('preemision.items.id'),
                        name      : 'id',
                        bind      : "{preemision.id}",
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
                        fieldLabel: t('preemision.items.codigoMes'),
                        bind      : "{preemision.codigoMes}",
                        name      : 'codigoMes',
                        editable  : false
                    }
                    ,
                    {
                        xtype     : 'textfield',
                        fieldLabel: t('preemision.items.periodo'),
                        bind      : "{preemision.periodo}",
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
                        fieldLabel  : t('preemision.items.fechaEmision'),
                        bind        : "{preemision.fechaEmision}",
                        name        : 'fechaEmision',
//                        width       : 250,
                        editable    : false,
                        readOnly    : true
                    }
                    ,
                    {
                        xtype     : 'datefield',
                        format    : 'd-m-Y',
                        fieldLabel: t('preemision.items.fechaEnvio'),
                        bind      : "{preemision.fechaEnvio}",
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
                        fieldLabel: t('preemision.items.importe'),
                        bind      : "{preemision.importe}",
                        name      : 'importe',
                        editable  : false,
                        format   : '0,000.00',
                        hideTrigger: true,
                        keyNavEnabled: false,
                        mouseWheelEnabled: false
                    },
                    {
                        xtype     : 'numberfield',
                        fieldLabel: t('preemision.items.importeDevuelto'),
                        bind      : "{preemision.importeDevuelto}",
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
                        fieldLabel: t('preemision.items.concepto'),
                        bind: "{preemision.concepto}",
                        name: 'concepto',
                        editable: false
                    }
                ]
            }

        ];


        this.resetButton = Ext.create('Ext.button.Button', {
            xtype  : 'button',
            text   : t('commons.buttons.reset'),
            handler: 'preemisionResetEdit',
            ui     : 'plain',
            scale  : 'medium',
            glyph  : Glyphs.getIcon('undo')

        });

        this.saveButton = Ext.create('Ext.button.Button', {
            xtype   : 'button',
            text    : t('commons.buttons.guardar'),
            scale   : 'medium',
            ui      : 'highlight',
            handler : 'preemisionSave',
            formBind: true,
            glyph   : Glyphs.getIcon('save')
        });
        this.buttons = [this.resetButton, this.saveButton];

        this.callParent();
    },

    afterRender: function () {
        var me = this;
        me.callParent();
        if (me.preemision !== null) {
            me.setPreemision(me.preemision);
        }
    },

    /**
     * Load a {@link recibosWeb.model.Operacion} instance
     *
     * @param {recibosWeb.model.Operacion}
     *            operacion
     */
    setPreemision: function (preemision) {
        var me = this;
        if (preemision) {
            me.getForm().reset();
            me.preemision = preemision;
            me.getForm().loadRecord(preemision);
        }
    },

    getPreemision: function (preemision) {
        var me = this, form = me.getForm();
        form.updateRecord(form.getRecord());
        me.preemision = form.getRecord();
        return me.preemision;
    }
});
