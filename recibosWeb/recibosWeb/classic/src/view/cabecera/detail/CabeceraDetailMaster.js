Ext.define('recibosWeb.view.cabecera.detail.CabeceraDetailMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.cabecera_cabeceradetailmaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'Ext.layout.container.Border',
        'recibosWeb.view.cabecera.detail.CabeceraDetailMasterCtrl',
// TODO FMM        'recibosWeb.view.cabecera.detail.CabeceraDetailFormCtrl',
        'recibosWeb.view.cabecera.detail.CabeceraDetailForm',
        'recibosWeb.view.cabecera.detail.CabeceraDetailGrid',
        'recibosWeb.view.cabecera.detail.CabeceraDetailMasterVM'

    ],

    controller   : 'cabecera-detailmaster',
    viewModel : {
        type: 'cabecera-detailmaster'
    },

//    title     : t('cabecera.detail.title'),
    header : false,
    items: [

        {
            xtype    : 'cabecera_cabeceradetailform',
            reference: 'cabecera_cabeceradetailform',
            region   : 'north',
            split    : true
        }
        ,
        {
            xtype    : 'cabecera_cabeceradetailgrid',
            reference: 'cabecera_cabeceradetailgrid',
            region   : 'center',
            border   : false,
            listeners: {
                selectionchange: 'onSelectionChange'
            }

        }
    ]
})
;
