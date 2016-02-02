Ext.define('recibosWeb.model.Periodo', {
    extend: 'Ext.data.Model',
    requires  : [
        'Ext.data.proxy.Rest',
        'Ext.data.reader.Json'
             ],
    
    idProperty : 'codigo',
    fields: [
        {name:'nombre', type:"string", useNull:true}
        ,{name:'frecuencia'}
        ,{name:'anticipacion'}
        ,{name:'codigo', type:"string", useNull:true}

    ],
    proxy     : {
        type     : 'rest',
        url      : '/periodos',
        reader   : {
            type : 'json'
        }
    }
    
});
