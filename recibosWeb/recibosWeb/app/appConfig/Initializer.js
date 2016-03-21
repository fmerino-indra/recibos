Ext.define('recibosWeb.appConfig.Initializer', {
    singleton: true,
    requires:  ['recibosWeb.appConfig.initializers.Ajax'],

    INITIALIZERS: [
        'Ajax'
    ],

    init: function () {
        Ext.each(this.INITIALIZERS, this.runInitializer, this);
    },

    runInitializer: function (initializerName) {

//        Editran.lib.Logger.info("Creando instancia del initializer:" + initializerName);

        var initializer = Ext.create("recibosWeb.appConfig.initializers." + initializerName);
        initializer.run();
    }
    
  
});

