Ext.define('recibosWeb.view.periodo.detail.PeriodoDetailForm', {
    extend         : 'Ext.form.Panel',
    alias          : 'widget.periodo_periododetailform',
    requires       : ['iDynamicsFront.selectors.UxLocatorField',
        'iDynamicsFront.util.UxDialogFieldN',
        'iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.periodo.detail.PeriodoDetailCtrl'],
    model          : 'recibosWeb.model.Periodo',
    modelValidation: true,
    cls            : 'card detail usuario ux-validation-form fa',


    controller   : 'periodo-detail',
//    viewModel    : {
//        type: 'periodo-detail'
//    },
    fieldDefaults: {
        labelPad      : 1,
        labelSeparator: ':',
        width         : 550,
        labelWidth    : 150
    },

    autoScroll: true,

    initComponent: function () {
        this.title = t('periodo.detail.title');
        this.dockedItems = [
            {
                xtype: 'ux-maintoolbar',
                dock : 'top',
                cls  : 'main-toolbar',
                scale: 'medium',
                items: ['back', '-', 'add', '-', 'delete', '-',
                    {text: 'desactivar', reference: 'enableBtn', handler: 'enable'},
                    {text: 'resetear contraseña', handler: 'resetPass', glyph: Glyphs.getIcon('refresh')}]
            }
        ];
        this.items = [
            {
                xtype     : 'textfield',
                fieldLabel: t('periodo.items.codigo'),
                name      : 'codigo',
                bind      : "{periodo.codigo}"
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('periodo.items.nombre'),
                bind      : "{periodo.nombre}",
                name      : 'nombre'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('periodo.items.frecuencia'),
                bind      : "{periodo.frecuencia}",
                name      : 'frecuencia'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('periodo.items.anticipacion'),
                bind      : "{periodo.anticipacion}",
                name      : 'anticipacion'
            }
            /*-ITEMS- detail:##,oldPassword,passwordConfirmation,roleId,username,password,name,surname,email,enabled,creationDate,*/
        ];

        this.resetButton = Ext.create('Ext.button.Button', {
            xtype  : 'button',
            text   : t('commons.buttons.reset'),
            handler: 'periodoResetEdit',
            ui     : 'plain',
            scale  : 'medium',
            glyph  : Glyphs.getIcon('undo')

        });

        this.saveButton = Ext.create('Ext.button.Button', {
            xtype   : 'button',
            text    : t('commons.buttons.guardar'),
            scale   : 'medium',
            ui      : 'highlight',
            handler : 'periodoSave',
            formBind: true,
            glyph   : Glyphs.getIcon('save')
        });
        this.buttons = [this.resetButton, this.saveButton];

        this.callParent();
    },

    afterRender: function () {
        var me = this;
        me.callParent();
        if (me.periodo !== null) {
            me.setPeriodo(me.periodo);
        }
    },

    /**
     * Load a {@link recibosWeb.model.Operacion} instance
     *
     * @param {recibosWeb.model.Operacion}
     *            operacion
     */
    setPeriodo: function (periodo) {
        var me = this;
        if (periodo) {
            me.getForm().reset();
            me.periodo = periodo;
            me.getForm().loadRecord(periodo);
        }
    },

    getPeriodo: function (periodo) {
        var me = this, form = me.getForm();
        form.updateRecord(form.getRecord());
        me.periodo = form.getRecord();
        return me.periodo;
    }
});
