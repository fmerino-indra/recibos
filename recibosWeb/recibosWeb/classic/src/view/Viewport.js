/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('recibosWeb.view.Viewport', {
    extend    : 'Ext.container.Viewport',
    requires  : [
        'Ext.plugin.Viewport',
        'recibosWeb.view.Viewer',
        'recibosWeb.view.Clock',
        'recibosWeb.view.MenuList'
    ],
    xtype     : 'app-main',
    controller: 'viewport',
//    viewModel : 'viewport',

    layout: 'fit',
    cls   : 'app-viewport',

    items: [
        {
            layout  : 'border',
            minWidth: 940,
            cls     : 'main-container',
            border  : false,
            items   : [
                {
                    region: 'north',
                    xtype : 'menulist',
                    height: 60
                }, {
                    region: 'center',
                    xtype : 'viewer'
                }, {
                    region: 'east',
                    width : 0
                }, {
                    region: 'south',
                    xtype : 'clock',
                    height: 30,
                    border: false
                }
            ]
        }
    ]
});
