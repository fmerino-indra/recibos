Ext.define('recibosWeb.view.persona.master.PersonaMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.persona_personamaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'recibosWeb.view.persona.master.PersonaSearchForm',
        'recibosWeb.view.persona.master.PersonaGrid',
        'Ext.layout.container.Border',
        'recibosWeb.view.persona.master.PersonaMasterCtrl',
        'recibosWeb.view.persona.master.PersonaMasterVM'
    ],

    controller: 'persona-master',
    viewModel : {
        type: 'persona-master'
    },

    initComponent: function () {
        this.title = "Lista de Personas"//t('persona.dataList.title');
        this.items = [
            {
                xtype    : 'persona_personasearchform',
                reference: 'persona_personasearchform',
                region   : 'west',
                split    : true
//                height: 155
            },
            {
                xtype    : 'persona_personagrid',
                reference: 'persona_personagrid',
                bind     : {
                    store: '{personas}',
                    title: 'Listado de usuarios [{usuarios.totalCount}]'
                },
                region   : 'center',
                border   : false,
                listeners: {
                    selectionchange: 'onSelectionChange'
                }
            }
        ];
        this.callParent();
    },
    getSelection : function () {
        var me = this, table, selModel;
        table = me.down('grid');
        selModel = table.getSelectionModel();
        return selModel.getSelection();
    }
})
;
