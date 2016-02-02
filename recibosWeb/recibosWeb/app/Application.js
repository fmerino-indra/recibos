/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('recibosWeb.Application', {
    extend: 'Ext.app.Application',
    
    name: 'recibosWeb',
    namespaces: ['recibosWeb', 'APP'],

    alternateClassName: 'APP',

    requires: [ //'iDynamicsFront.lib.I18n',
        'recibosWeb.lib.I18n',
    'iDynamicsFront.lib.Logger',
    'iDynamicsFront.lib.GlyphCatalog'],
    stores: [
        // TODO: add global / shared stores here
    ],
    
    launch: function () {
        recibosWeb.lib.I18n.setBundleMessages(new iDynamicsFront.util.i18n.Bundle({
            bundle: 'application',
            lang: 'es',
            path: "resources/i18n",
            autoLoad: true
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
