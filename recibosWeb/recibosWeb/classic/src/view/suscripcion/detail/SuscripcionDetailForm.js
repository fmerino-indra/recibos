Ext.define('recibosWeb.view.suscripcion.detail.SuscripcionDetailForm', {
    extend         : 'Ext.form.Panel',
    alias          : 'widget.periodo_periododetailform',
    requires       : ['iDynamicsFront.selectors.UxLocatorField',
        'iDynamicsFront.util.UxDialogFieldN',
        'iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.suscripcion.detail.SuscripcionDetailCtrl'],
    model          : 'recibosWeb.model.Suscripcion',
    modelValidation: true,
    cls            : 'card detail usuario ux-validation-form fa',


    controller   : 'suscripcion-detail',
//    viewModel    : {
//        type: 'suscripcion-detail'
//    },
    fieldDefaults: {
        labelPad      : 1,
        labelSeparator: ':',
        width         : 550,
        labelWidth    : 150
    },

    autoScroll: true,

    initComponent: function () {
        this.title = t('suscripcion.detail.title');
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
                fieldLabel: t('suscripcion.items.codigo'),
                name      : 'codigo',
                bind      : "{suscripcion.codigo}"
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.nombre'),
                bind      : "{suscripcion.nombre}",
                name      : 'nombre'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.frecuencia'),
                bind      : "{suscripcion.frecuencia}",
                name      : 'frecuencia'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.anticipacion'),
                bind      : "{suscripcion.anticipacion}",
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
        if (me.suscripcion !== null) {
            me.setSuscripcion(me.suscripcion);
        }
    },

    /**
     * Load a {@link recibosWeb.model.Operacion} instance
     *
     * @param {recibosWeb.model.Operacion}
     *            operacion
     */
    setSuscripcion: function (suscripcion) {
        var me = this;
        if (suscripcion) {
            me.getForm().reset();
            me.suscripcion = suscripcion;
            me.getForm().loadRecord(suscripcion);
        }
    },

    getSuscripcion: function (suscripcion) {
        var me = this, form = me.getForm();
        form.updateRecord(form.getRecord());
        me.suscripcion = form.getRecord();
        return me.suscripcion;
    }
});
