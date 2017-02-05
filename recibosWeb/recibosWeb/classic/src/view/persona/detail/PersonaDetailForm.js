Ext.define('recibosWeb.view.persona.detail.PersonaDetailForm', {
    extend         : 'Ext.form.Panel',
    alias          : 'widget.persona_personadetailform',
    requires       : ['iDynamicsFront.selectors.UxLocatorField',
        'iDynamicsFront.util.UxDialogFieldN',
        'iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.persona.detail.PersonaDetailCtrl'],
    model          : 'recibosWeb.model.Persona',
    modelValidation: false,
    cls            : 'card detail usuario ux-validation-form fa',


    controller   : 'persona-detail',
    viewModel    : {
        type: 'persona-detail'
    },
    fieldDefaults: {
        labelPad      : 1,
        labelSeparator: ':',
        width         : 550,
        labelWidth    : 150
    },
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    autoScroll: true,

    initComponent: function () {
        this.title = t('persona.detail.title');

        this.dockedItems = [
            {
                xtype: 'ux-maintoolbar',
                dock : 'top',
                cls  : 'main-toolbar',
                scale: 'medium',
//                items: ['back', '-', 'add', '-', 'delete']
                items: ['back']
            }
        ];

        this.items = [
            {
                xtype     : 'textfield',
                fieldLabel: t('persona.items.id'),
                name      : 'id',
                bind      : "{persona.id}",
                editable  : false,
                align     : 'right'
                	
            }
            ,
            {
                xtype       : 'textfield',
                fieldLabel: t('persona.items.nombre'),
                name      : 'nombre',
                bind      : "{nombre}",
                editable  : true
            }
            ,
            {
                xtype       : 'textfield',
                fieldLabel: t('persona.items.nif'),
                name      : 'nif',
                bind      : "{persona.nif}",
                editable  : true
            }
            ,
            {
                xtype       : 'textfield',
                fieldLabel: t('persona.items.domicilio'),
                name      : 'domicilio',
                bind      : "{persona.domicilio}",
                editable  : true
            }
            ,
            {
                xtype       : 'textfield',
                fieldLabel: t('persona.items.cp'),
                name      : 'cp',
                bind      : "{persona.cp}",
                editable  : true
            }
            ,
            {
                xtype       : 'textfield',
                fieldLabel: t('persona.items.poblacion'),
                name      : 'poblacion',
                bind      : "{persona.poblacion}",
                editable  : true
            }
            ,
            {
                xtype       : 'textfield',
                fieldLabel: t('persona.items.tfno'),
                name      : 'tfno',
                bind      : "{persona.tfno}",
                editable  : true
            }
//            ,
//            {
//            	xtype     : 'gridpanel',
//            	name      : 'suscripciones',
//            	id        : 'suscripciones',
//            	multiselect:false,
//            	columns : [
//           	            {
//        	                header   : 'euros',
//        	                dataIndex: 'euros',
//        	                flex     : 1
//        	            },
//           	            {
//        	                header   : t('persona.items.nif'),
//        	                dataIndex: '{euros}',
//        	                flex     : 1
//        	            }
//            	         ]
//            }
        ];

        this.resetButton = Ext.create('Ext.button.Button', {
            xtype  : 'button',
            text   : 'Limpiar',//t('commons.buttons.reset'),
            handler: 'personaResetEdit',
//            ui     : 'plain',
            scale  : 'medium',
            glyph  : Glyphs.getIcon('undo')

        });

        this.saveButton = Ext.create('Ext.button.Button', {
            xtype   : 'button',
            text    : 'Guardar',//t('commons.buttons.guardar'),
            scale   : 'medium',
            ui      : 'highlight',
            handler : 'personaSave',
            formBind: true,
            glyph   : Glyphs.getIcon('save')
        });
        this.buttons = [this.resetButton, this.saveButton];

        this.callParent();
    },

    afterRender: function () {
        var me = this;
        me.callParent();
        if (me.persona !== null) {
            me.setPersona(me.persona);
        }
    },

    /**
     * Load a {@link recibosWeb.model.Operacion} instance
     *
     * @param {recibosWeb.model.Operacion}
     *            operacion
     */
    setPersona: function (persona) {
        var me = this;
        if (persona) {
            me.getForm().reset();
            me.persona = persona;
            me.getForm().loadRecord(persona);
        }
    },

    getPersona: function (persona) {
        var me = this, form = me.getForm();
        form.updateRecord(form.getRecord());
        me.persona = form.getRecord();
        return me.persona;
    }
});
