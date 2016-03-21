Ext.define('recibosWeb.view.reemitir.ReemitirViewport', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.reemitir_reemitirviewport',
    requires  : ['iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.reemitir.master.ReemitirMaster',
        'recibosWeb.view.reemitir.detail.ReemitirDetailMaster',
        'recibosWeb.view.reemitir.ReemitirViewportCtrl',
        'recibosWeb.view.reemitir.ReemitirViewportVM'],
    layout    : 'card',
    cls       : 'reemitir viewport',
    controller: 'reemitir-viewport',
    viewModel : {
        type: 'reemitir-viewport'
    },
    items: [
        {
            xtype      : 'reemitir_reemitirmaster',
            reference  : 'reemitir_reemitirmaster',
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
            xtype    : 'reemitir_reemitirdetailmaster',
            reference: 'reemitir_reemitirdetailmaster'
        }
    ],
    html: '<div class="top-shadow"></div>'
});
