Ext.define('iDynamicsFront.data.searchengines.Default', {
    singleton         : true,
    alternateClassName: 'DefaultQuery',

    buildFilter: function (params) {
        var createFilterForKey = function (key) {
debugger;        	
            //devolvemos null en los casos que esté vacío y ern los que no, devolvemos un objeto con formato {property: nombre_propiedad, value: valor_para_esa_propiedad}
            return !Ext.isEmpty(params[key]) ? {property : key, value : params[key]} : null;
        };

        //se debe limpiar el array de filtros de vuelta de los objetos vacíos
        return Ext.Array.clean(Ext.Array.map(Ext.Object.getKeys(params), createFilterForKey));
    }
});