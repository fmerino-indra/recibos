Ext.define('recibosWeb.model.Persona', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy'
    ],
    idProperty : 'id',
    fields: [
        {name:'nombre', type:"string", useNull:true}
        ,{name:'nif', type:"string", useNull:true}
        ,{name:'sufijo', type:"string", useNull:true}
        ,{name:'domicilio', type:"string", useNull:true}
        ,{name:'cp', type:"string", useNull:true}
        ,{name:'poblacion', type:"string", useNull:true}
        ,{name:'tfno', type:"string", useNull:true}
        ,{name:'idAntigua', type:"int", useNull:true}
        ,{name:'id', type:"int"}
    ],
    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.Persona',
        mockupUrl: 'data/personasListado.json',
        remoteUrl: 'personas'
//        ,
//        url      : '/periodos'
/*
        ,
        reader: {
            getResponseData: function(response) {
                try {
                    var data = Ext.decode(response.responseText);
                }
                catch (ex) {
                    Ext.Error.raise({
                        response: response,
                        json: response.responseText,
                        parseError: ex,
                        msg: 'Unable to parse the JSON returned by the server: ' + ex.toString()
                    });
                };
                return data;
            }
        }
*/
    }


});
