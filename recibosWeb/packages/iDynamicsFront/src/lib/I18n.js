/*
Gestor de textos internacionalizados
*/
Ext.define('iDynamicsFront.lib.I18n', {
    singleton: true,
	alternateClassName: 'I18nFront',
    requires: ['iDynamicsFront.locale.*'],
    locale: 'es',


    currentLocale: function () {
        return this.locale;
    },
    setLocale: function (l) {
        this.locale = l;
    },

    //Este metodo deberia cargar primero los locales de arquitectura. => I18n2.registerLocale(locale, iDynamicsFront.locale.Es.keys);
    registerLocale: function (locale, data) {
        this.locale = locale;
        this.data || (this.data = {});

        //Este metodo deberia cargar primero los locales de arquitectura.
        if (data !== undefined) {
            // y de aplicacion
            this.register(locale, data);
        } else {
            this.register(locale, this.getLocaleStatics(locale));            
        }

    },

    translate: function (key) {
        var key = this.resourceKey(this.currentLocale(), key);
        var value = this.data[key];

        return Ext.isDefined(value) ? value : 't(-' + key + '-)';
    },

    privates: {
        register: function (prefix, object) {
            var self = this;
            Ext.Object.each(object, function (key, value) {
                var key = self.resourceKey(prefix, key);

                if (Ext.isObject(value)) {
                    return self.register(key, value);
                } else {
                    //console.debug('I18n, meto data[' + key + ']=' + value)
                    self.data[key] = value;
                }
            });
        },

        resourceKey: function (prefix, key) {
            return prefix + '.' + key;
        }
    },
    getLocaleStatics: function(l){
        if ( l === 'es' ) {
            return iDynamicsFront.locale.Es.keys;
        } else if ( l === 'en' ) {
            return iDynamicsFront.locale.En.keys;          
        } else if ( l === 'pt' ) {
            return iDynamicsFront.locale.Pt.keys;          
        }
    }
});


Ext.require('iDynamicsFront.lib.I18n', function(){
    //PlanesCuenta.lib.Logger.info("i18n start...");
    iDynamicsFront.lib.I18n.registerLocale('es');//, iDynamicsFront.locale.Es.keys);
    //PlanesCuenta.lib.Logger.info("i18n fin...");
// TODO FMM Reorganizar el i18n
//    window.i18ntraslate = window.t = Ext.bind(iDynamicsFront.lib.I18n.translate, iDynamicsFront.lib.I18n);
});



/*(function () {
    window.t = Ext.bind(iDynamicsFront.lib.I18n.translate, iDynamicsFront.lib.I18n);
 })();  */ 
 