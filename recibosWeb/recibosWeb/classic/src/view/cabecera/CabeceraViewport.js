Ext.define('recibosWeb.view.cabecera.CabeceraViewport', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.cabecera_cabeceraviewport',
    requires  : ['iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.cabecera.master.CabeceraMaster',
        'recibosWeb.view.cabecera.detail.CabeceraDetailMaster',
        'recibosWeb.view.cabecera.CabeceraViewportCtrl',
        'recibosWeb.view.cabecera.CabeceraViewportVM'],
    layout    : 'card',
    cls       : 'cabecera viewport',
    controller: 'cabecera-viewport',
    viewModel : {
        type: 'cabecera-viewport'
    },
    items: [
        {
            xtype      : 'cabecera_cabeceramaster',
            reference  : 'cabecera_cabeceramaster',
            dockedItems: [
                {
                    xtype: 'ux-maintoolbar',
                    dock : 'top',
                    cls  : 'main-toolbar',
                    scale: 'medium',
                    items: [
                        'add',
                        'edit',
                        '-',
                        'delete',
                        '-',
                        '-',
                        'excel',
                        'pdf'
                    ]
                }
            ]
        },
        {
            xtype    : 'cabecera_cabeceradetailmaster',
            reference: 'cabecera_cabeceradetailmaster'
        }
    ],
    html: '<div class="top-shadow"></div>'
});
