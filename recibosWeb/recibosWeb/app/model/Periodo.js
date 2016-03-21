Ext.define('recibosWeb.model.Periodo', {
    extend: 'Ext.data.Model',
    requires   : [
        'iDynamicsFront.data.Proxy'
    ],

//    requires: ['Ext.data.proxy.Rest'],
/*
    requires  : [
        'Ext.data.proxy.Rest',
        'Ext.data.reader.Json'
             ],
*/
    idProperty : 'codigo',
    fields: [
        {name:'nombre', type:"string", useNull:true}
        ,{name:'frecuencia'}
        ,{name:'anticipacion'}
        ,{name:'codigo', type:"string", useNull:true}

    ],
/*
    proxy     : {
        type     : 'rest',
        url      : '/recibosWeb/periodos',
        reader   : {
            type : 'json'
        }
    }
*/

    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.Periodo',
        mockupUrl: 'data/periodosListado.json',
        remoteUrl: 'periodos'
//        ,
//        url      : '/periodos'
    }

});
