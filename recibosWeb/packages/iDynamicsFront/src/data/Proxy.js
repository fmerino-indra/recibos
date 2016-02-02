/**
 * Objeto proxy extendido para poder hacer uso de los mockups (datos json locales)
 * Refs: http://skirtlesden.com/articles/custom-proxies
 */
Ext.define('iDynamicsFront.data.Proxy', {
    extend: 'Ext.data.proxy.Rest',
    alias: 'proxy.idfproxy',
    requires: ['Ext.data.proxy.Rest'],

   

    config: {
    /* Url Local */
        mockupUrl: null,   
        /* Url del servicio */
        remoteUrl: null
    },

    initialUrl: null,
    
    // updateMockupUrl : function (aaa) {   
    // },
    /**
     * Se llama desde setMockupUrl() cuando el valor ha cambiado, tras applyMockupUrl()
     * Solo es utilizable en development y via snippet de chrome (para evitar desplegar codigo malo) para poder probar juegos de datos diferentes
     * Aprovecho para poner la url
     */
    updateMockupUrl : function (newValue, oldValue) {
        
        me = this;
        me.setUrl(Environment.getBaseUrl() + Environment.getApplicationPath() + newValue);
    },
    /**
     * vuelve a poner la url del constructor
     */
    resetUrl : function() {
        if (this.isRemote) {
            this.setUrl(Environment.getBaseUrl() + Environment.getServicesPath() + this.initialUrl);
        } else {
            this.setMockupUrl(this.initialUrl);
            this.setUrl(Environment.getBaseUrl() + Environment.getApplicationPath() + this.initialUrl);
        }        
    },

    isRemote: false,

    /* conmutador */
    urlMode: 'remote', //remote [defecto]  o mockup

    /*
     * Determina si usa paginacion. Por defecto siempre es paginado, para desactivarlo a false
     */
    paging: true,

    reader: {
        type: "json",
        rootProperty: "data",
        messageProperty: 'info',
        successProperty: 'success',
        totalProperty: 'totalCount'
    },
    writer: {
        writeAllFields: true
    },

    constructor: function(config) {
        this.urlMode = config.mode || 'remote';
        this.name = config.name || '';

        //solo si viene a false no uso paginado
        if (config.paging !== undefined && !config.paging) {
             Ext.apply(config, {pageParam: null, startParam: null, limitParam: null});
        }

        this.callParent(arguments);
        debugger
        /* Esto me lo puedo llevar e Environmnet... */
        this.isRemote = Environment.isProxyRemote(config.name);
        if (this.isRemote) {
            this.setUrl(Environment.getBaseUrl() + Environment.getServicesPath() + config.remoteUrl);
        } else {
            this.setUrl(Environment.getBaseUrl() + Environment.getApplicationPath() + config.mockupUrl);
        }
        //console.info('idfproxy:' + config.name+ ', '+this.getUrl());
        
    },

    /**
     * Cambio en caliente del origen de datos
     */
    toogleUrl: function() {
        var me = this;
        if (me.isRemote) {
            me.setUrl(PlanesCuenta.environment.servicesPath + me.config.remoteUrl);
        } else {
            me.setUrl(me.config.mockupUrl);
        }
    },
    /**
     * Cambio en caliente del origen de datos Mock (simulacion refresco)
     * name es una cadena que se pospone al servicio, antrs de .json
     */
    mockRefreshUrl: function(name) {

    },

     /**
     * Para datos locales se omite la concatenacion del id de modo que retorne un json definido en mockupUrl y no mockupUrl/23
     * Es decir, se saca de la url para ponerlo en la query string  mockupUrl?id=23
     * @param {Object} request
     */
    buildUrl: function(request) {
        var me        = this;

        if ( !me.isRemote ) {
            me.appendId = false;
             // Para probar en local cambio el metodo ({create: 'POST', read: 'GET', update: 'PUT', destroy: 'DELETE'})
            if (request.getAction() === 'destroy') {
                request.setAction('read');                
            } else if (request.getAction() === 'update') {
                request.setAction('read');
            }
        }

        return me.callParent([request]);
    }
});
