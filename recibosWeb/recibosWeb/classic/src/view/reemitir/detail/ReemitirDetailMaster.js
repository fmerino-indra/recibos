Ext.define('recibosWeb.view.reemitir.detail.ReemitirDetailMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.reemitir_reemitirdetailmaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'Ext.layout.container.Border',
        'recibosWeb.view.reemitir.detail.ReemitirDetailMasterCtrl',
        'recibosWeb.view.reemitir.detail.ReemitirDetailForm',
        'recibosWeb.view.reemitir.detail.ReemitirDetailGrid',
        'recibosWeb.view.reemitir.detail.ReemitirDetailMasterVM'

    ],

    controller   : 'reemitir-detailmaster',
    viewModel : {
        type: 'reemitir-detailmaster'
    },

//    title     : t('reemitir.detail.title'),
    header : false,
    items: [

        {
            xtype    : 'reemitir_reemitirdetailform',
            reference: 'reemitir_reemitirdetailform',
            region   : 'north',
            split    : true
        }
        ,
        {
            xtype    : 'reemitir_reemitirdetailgrid',
            reference: 'reemitir_reemitirdetailgrid',
/*
            bind     : {
                store: '{reemitirs}',
                title: 'Listado de usuarios [{usuarios.totalCount}]'
            },
*/
            region   : 'center',
            border   : false,
            listeners: {
                selectionchange: 'onSelectionChange'
            }

        }
    ]
})
;
