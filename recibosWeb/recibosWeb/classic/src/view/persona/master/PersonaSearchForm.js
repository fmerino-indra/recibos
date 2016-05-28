Ext.define('recibosWeb.view.persona.master.PersonaSearchForm', {
    extend       : 'Ext.form.Panel',
    alias        : 'widget.persona_personasearchform',
    requires     : ['Ext.layout.container.Column'],
    border       : false,
    collapsible  : true,
    autoScroll   : true,
    layout       : 'anchor',
    dialogMode   : false,
    cls          : 'search-form',
    defaults     : {
        anchor: '100%'
    },
    fieldDefaults: {
        labelAlign    : 'left',
        labelPad      : 1,
        labelSeparator: ':'
    },

    initComponent: function () {
        this.title = 'Criterios de Búsqueda';
        this.dockedItems = [
            {
                dock    : 'bottom',
                xtype   : 'toolbar',
                defaults: {scale: 'medium'},
                items   : [
                    {
                        xtype  : 'button',
                        text   : 'Limpiar',//t('commons.buttons.limpiar'),
                        cls    : 'ux-btn',
                        //handler: 'personaReset',
                        glyph  : Glyphs.getIcon('eraser')
                    },
                    {
                        xtype: 'tbfill'
                    },
                    {
                        xtype  : 'button',
                        text   : 'Buscar',//t('commons.buttons.buscar'),
//                        ui     : 'highlight',
                        cls    : 'ux-btn',
                        handler: 'personaSearch',
                        glyph  : Glyphs.getIcon('search')
                    }
                ]
            }
        ];
        this.items = [
            {
                xtype : 'container',
                layout: {
                    type : 'hbox',
                    align: 'stretch'
                },
                border: false,
                items : [
                        {
                            style      : 'margin-left:10px;margin-bottom:10px',
                            xtype      : 'textfield',
                            name       : 'nif',
                            cls        : 'search-element',
                            fieldLabel : t('persona.items.nif')
                        }
                ]

            }
        ];
        this.callParent();
    }


});
