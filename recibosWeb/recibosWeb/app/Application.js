/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('recibosWeb.Application', {
    extend: 'Ext.app.Application',
    
    name: 'recibosWeb',
    namespaces: ['recibosWeb', 'APP'],

//    alternateClassName: 'APP',

    requires: [ //'iDynamicsFront.lib.I18n',
        'recibosWeb.lib.I18n',
        'iDynamicsFront.lib.Logger',
        'iDynamicsFront.lib.GlyphCatalog',
        'recibosWeb.view.main.Main',
        'recibosWeb.view.Viewport',
        'recibosWeb.appConfig.Environment',
        'recibosWeb.appConfig.Initializer'
    ],
    stores: [
        // TODO: add global / shared stores here
    ],
    init: function () {
        var me = this;
        recibosWeb.lib.I18n.setBundleMessages(new iDynamicsFront.util.i18n.Bundle({
            bundle: 'application',
            lang: 'es',
            path: "resources/i18n",
            autoLoad: true,
            listeners: {
                loaded: function () {
                    // Se hace aquí, en lugar de en app.js (configuración) porque si no se cre
                    // sobre la marcha.
                    recibosWeb.appConfig.Initializer.init();
                    me.mainView = Ext.create('recibosWeb.view.Viewport');
                }
            }
        }))
    },

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
