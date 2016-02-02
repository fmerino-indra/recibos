/**
 * Aporta una serie de métodos para facilitar el trabajo con imágenes como precargas para evitar las visualizaciones de
 * imágenes en pasos en la aplicación
 */
Ext.define('iDynamicsFront.util.Image', {
    singleton: true,

    /**
     * Recive una url de una imagén
     * @param {String} url de la imagen a cargar
     * @returns {RSVP/Promise} Promesa que indica cuando se ha cargado correctamente o con error
     */
    preloadImage: function (url) {
        return new RSVP.Promise(function (resolve, reject) {
            var image = new Image();
            image.onload = resolve;
            image.onerror = reject;
            image.src = url;
        });
    },

    /**
     * Recibe un array de urls de imágenes
     * @param {String[]} array de urls
     * @returns {RSVP/Promise} Promesa que indica cuando se han cargado correctamente o con error
     */
    preloadImages: function (imageUrls) {
        var me = this;
        return new RSVP.Promise(function (resolve, reject) {
            RSVP.all(Ext.Array.map(imageUrls, me.preloadImage)).then(function () {
                resolve();
            }, function () {
                reject();
            });
        });
    }
});
