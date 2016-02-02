Ext.define('recibosWeb.lib.I18n', {
	singleton         : true,
	alternateClassName: 'I18n',
	requires          : ['iDynamicsFront.util.i18n.Bundle'],

	currentLocale: function () {
		return 'es';
	},

	registerLocale: function (locale, data) {
		this.data || (this.data = {});

		this.register(locale, data);
	},

	setBundleMessages : function (bundle) {
		this.bundleMessages = bundle;
	},

	i18n: function (str, nameSpace) {
		var name, getFromCamelCase = function (name) {
			return (name.replace(/([A-Z])/g, "  $1").toLowerCase()).replace(/[a-z]/, function () {
				return arguments[0].toUpperCase();
			});
		};

		try {
			name = this.bundleMessages.getMsg(str);
			if (!name) {
				name = this.bundleApplication.getMsg(str);
				if (!name) {
					name = "-" + str.split('.')[str.split('.').length - 1] + "-";
					return getFromCamelCase(name);
				} else {
					return name;
				}
			} else {
				return name;
			}
		} catch (e) {
			name = "-" + str.split('.')[str.split('.').length - 1] + "-";
			return getFromCamelCase(name);
		}
	},

	translate: function (key) {
		return this.i18n(key);
		//var key = this.resourceKey(this.currentLocale(), key);
		//var value = this.data[key];
		//
		//return Ext.isDefined(value) ? value : 't(' + key + ')';
	}
	,

	privates: {
		register: function (prefix, object) {
			var self = this;
			Ext.Object.each(object, function (key, value) {
				var key = self.resourceKey(prefix, key);

				if (Ext.isObject(value)) {
					return self.register(key, value);
				} else {
					self.data[key] = value;
				}
			});
		}
		,

		resourceKey: function (prefix, key) {
			return prefix + '.' + key;
		}
	}
})
;

Ext.require('recibosWeb.lib.I18n', function () {
//	recibosWeb.lib.Logger.info("i18n start...");
	recibosWeb.lib.I18n.registerLocale('es',iDynamicsFront.locale.Es.keys);
//	Editran.lib.Logger.info("i18n fin...");
//	$AC.i18n = window.t = Ext.bind(recibosWeb.lib.I18n.translate, recibosWeb.lib.I18n);
	window.t = Ext.bind(recibosWeb.lib.I18n.translate, recibosWeb.lib.I18n);
});

