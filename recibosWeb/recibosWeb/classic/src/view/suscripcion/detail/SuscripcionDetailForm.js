Ext.define('recibosWeb.view.suscripcion.detail.SuscripcionDetailForm', {
    extend         : 'Ext.form.Panel',
    alias          : 'widget.suscripcion_suscripciondetailform',
    requires       : ['iDynamicsFront.selectors.UxLocatorField',
        'iDynamicsFront.util.UxDialogFieldN',
        'iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.suscripcion.detail.SuscripcionDetailCtrl'],
    model          : 'recibosWeb.model.Suscripcion',
    modelValidation: false,
    cls            : 'card detail usuario ux-validation-form fa',


    controller   : 'suscripcion-detail',
    viewModel    : {
        type: 'suscripcion-detail'
    },
    fieldDefaults: {
        labelPad      : 1,
        labelSeparator: ':',
        width         : 550,
        labelWidth    : 150
    },

    autoScroll: true,

    initComponent: function () {
        var master = Ext.create('viewmodel.persona-master');

        me.storePersona = master.getStore('personas');
        //me.personasStore = Ext.create('');

        this.title = t('suscripcion.detail.title');
        this.dockedItems = [
            {
                xtype: 'ux-maintoolbar',
                dock : 'top',
                cls  : 'main-toolbar',
                scale: 'medium',
                items: ['back', '-', 'add', '-', 'delete']
            }
        ];
        this.items = [
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.id'),
                name      : 'id',
                bind      : "{suscripcion.id}",
                editable  : false,
                align     : 'right'
                	
            }
            ,
            {
                xtype       : 'datefield',
                format      : 'd-m-Y',
                fieldLabel: t('suscripcion.items.fechaInicio'),
                bind      : "{suscripcion.fechaInicio}",
                editable  : false,
                name      : 'fechaInicio'
            }
            ,
            {
                xtype       : 'datefield',
                format      : 'd-m-Y',
                fieldLabel: t('suscripcion.items.fechaBaja'),
                bind      : "{suscripcion.fechaBaja}",
                editable  : false,
                name      : 'fechaBaja'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.nombre'),
                bind      : "{suscripcion.nombre}",
                editable  : false,
                name      : 'nombre'
            },
            {
                xtype       : 'idf-searchfield',
                fieldLabel  : t('suscripcion.items.nombre'),
                name        : 'partyentitytype',
                reference   : 'partyentitytype',
                allowBlank  : false,
                store       : me.storePersona,
                displayField: 'nombre',
                lastQuery   : '',
                queryMode   : 'local',
                valueField  : 'id',
                width       : 550
            },
            
            {
                xtype             : 'idf-searchfield',
//                reference         : 'recognitionqueuename',
                fieldLabel        : t('suscripcion.items.nombre'),
                name              : 'nameSearch',
                store             : me.storePersona,
                displayField      : 'nombre',
                lastQuery         : '',
                queryMode         : 'local',
                width             : 550,
                valueField        : 'id',
                triggerConfig     : {iconCls: 'ux-multidialogfield-searchIcon'},
                dialogWidgetConfig: { //Configuraci�n del dialogo que sale, solo es necesario lo del selectionWidget
                    width          : 950,
                    height         : 650,
                    modal          : true,
                    title          : 'Suscriptores',
                    selectionWidget: {
                        xtype: 'persona_personamaster',
                        multiSelect : true
                    }
                }
            },

            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.euros'),
                bind      : "{suscripcion.euros}",
                name      : 'euros'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.divisa'),
                bind      : "{suscripcion.divisa}",
                editable  : false,
                name      : 'divisa'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.periodo'),
                bind      : "{suscripcion.periodo}",
                name      : 'periodo'
            }
            ,
            {
                xtype     : 'checkboxfield',
                fieldLabel: t('suscripcion.items.activo'),
                bind      : "{suscripcion.activo}",
                editable  : false,
                name      : 'activo'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.concepto'),
                bind      : "{suscripcion.concepto}",
                editable  : false,
                name      : 'concepto'
            }
            ,
            {
                xtype     : 'textfield',
                fieldLabel: t('suscripcion.items.iban'),
                bind      : "{suscripcion.iban}",
                name      : 'iban'
            }
        ];

        this.resetButton = Ext.create('Ext.button.Button', {
            xtype  : 'button',
            text   : t('commons.buttons.reset'),
            handler: 'suscripcionResetEdit',
            ui     : 'plain',
            scale  : 'medium',
            glyph  : Glyphs.getIcon('undo')

        });

        this.saveButton = Ext.create('Ext.button.Button', {
            xtype   : 'button',
            text    : t('commons.buttons.guardar'),
            scale   : 'medium',
            ui      : 'highlight',
            handler : 'suscripcionSave',
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
