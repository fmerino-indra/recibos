Ext.define('recibosWeb.view.suscripcion.master.SuscripcionMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.suscripcion_suscripcionmaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'recibosWeb.view.suscripcion.master.SuscripcionSearchForm',
        'recibosWeb.view.suscripcion.master.SuscripcionGrid',
        'Ext.layout.container.Border',
        'recibosWeb.view.suscripcion.master.SuscripcionMasterCtrl',
        'recibosWeb.view.suscripcion.master.SuscripcionMasterVM'
    ],

    controller: 'suscripcion-master',
    viewModel : {
        type: 'suscripcion-master'
    },

    initComponent: function () {
        this.title = "Lista de Suscripcions"//t('suscripcion.dataList.title');
        this.items = [
            {
                xtype    : 'suscripcion_suscripcionsearchform',
                reference: 'suscripcion_suscripcionsearchform',
                region   : 'west',
                split    : true
//                height: 155
            },
            {
                xtype    : 'suscripcion_suscripciongrid',
                reference: 'suscripcion_suscripciongrid',
                bind     : {
                    store: '{suscripcions}',
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
