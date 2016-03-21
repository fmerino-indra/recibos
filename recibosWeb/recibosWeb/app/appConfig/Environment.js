/**
 * Configuración del entorno. En cuanto es requerida se configura con los datos del Manifest (app.json) segun el entorno.
 * Es importante que se requiera la primera en Application.js
 */
Ext.define('recibosWeb.appConfig.Environment', {
    singleton: true,

    /**
     * Nombre Alternativo para poder referenciar esta clase de forma generica en el paquete IdynamicsFront
     * sin necesidad de conocer el namespace especifico de la aplicacion
     */
    alternateClassName: ['Environment'],

    config: {
        baseUrl        : Ext.manifest.baseUrl || null,
        applicationPath: Ext.manifest.applicationPath,
        servicesPath   : Ext.manifest.servicesPath,
        /**
         * Permite saltarse de una vez toda la configuracionde configProxies
         */
        useMockups     : Ext.manifest.useMockups,
        /**
         * Array de todos los proxies de la aplicacion (al menos en development)
         */
        configProxies  : Ext.manifest.configProxies
    },

    constructor  : function (config) {
//debugger;
        this.initConfig(config);
    },
    i18n         : function () {
        iDynamicsFront.lib.I18n.translate.call(arguments);
    },
    /**
     * Determina si un proxy recupera los datos de local (mock) o de un servicio remoto
     * @param {String} prxyName
     */
    isProxyRemote: function (prxyName) {
//debugger;
        var me = this;

        if (!me.getUseMockups())
            return true;

        // en prevencion IE8
        // var newArrTmp = me.getConfigProxies().filter(function(cfgProxy) {
        // return (cfgProxy.name == prxyName);
        // });

        var prxis = me.getConfigProxies();
        var newArrTmp = [];
        var found = false;
        for (var i = 0, j = prxis.length; i < j; i++) {
            if (prxis[i].name == prxyName) {
                found = true;
                newArrTmp.push(prxis[i]);
                break;
            }
        }
        ;

        if (newArrTmp.length == 0) {
            return true;
        } else {
            return !newArrTmp[0].useMockup;
        }

    }
});    