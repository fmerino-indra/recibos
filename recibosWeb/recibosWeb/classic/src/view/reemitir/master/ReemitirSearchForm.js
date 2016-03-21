Ext.define('recibosWeb.view.reemitir.master.ReemitirSearchForm', {
    extend       : 'Ext.form.Panel',
    alias        : 'widget.reemitir_reemitirsearchform',
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
        this.title = t('cabecera.searchForm.title');
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
                        //handler: 'reemitirReset',
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
                        handler: 'reemitirSearch',
                        glyph  : Glyphs.getIcon('search')
                    }
                ]
            }
        ];
        this.items = [
            {
                xtype : 'container',
                border: false,
                layout: 'column',
                flex  : 2,
                items : [
                    {
                        xtype      : 'container',
                        border     : false,
                        layout     : 'column',
                        columnWidth: 1,
                        items      : [
                            {
                                columnWidth: 1,
                                layout     : 'column',
                                style      : 'margin-left:10px',
                                flex       : 1,
                                items      : [
                                    {
                                        columnWidth: 0.33,
                                        style      : 'margin-left:10px;margin-bottom:10px',
                                        xtype      : 'textfield',
                                        name       : 'nombre',
                                        cls        : 'search-element',
                                        fieldLabel : t('cabecera.items.nombre')
                                    }
                                ]
                            }
                        ]
                    }
                ]

            }
        ];
        this.callParent();
    }


});
