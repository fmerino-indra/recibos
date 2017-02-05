//Ext.define('recibosWeb.model.CertificadoDTOProxy', {
//	extend: 'iDynamicsFront.data.Proxy',
//	alias: 'proxy.certificado-proxy',
//	requires: ['iDynamicsFront.data.Proxy'],
//	url: 'suscripciones/{id}/certificado',
//    // Replace the buildUrl method
//    buildUrl: function(request) {
//        var me        = this, operation, records, record, url, id;
//        debugger;
//        operation = request.getOperation();
//        records = operation.getRecords() || [];
//        record = records[0];
//        url       = me.getUrl(request);
//
//        id = record ? record.getId() : operation.id;
//        
//        // Here's the part honoring your URL format
//        if (me.isValidId(id)) {
//            url = url.replace('{id}', id);
//        } else {
//            throw new Error('A valid id is required');
//        }
//
//        // That's enough, but we lose the cache buster param (see bellow)
//        //        return url;
//
//        // If we want the cache buster param (_dc=...) to be added,
//        // we must call the superclass, which will read the url from
//        // the request.
//        request.url = url;
//        return Ext.data.proxy.Rest.superclass.buildUrl.apply(this, arguments);
//    }        
//});

Ext.define('recibosWeb.model.CertificadoDTO', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy'
    ],

    requires: ['Ext.data.proxy.Rest'],

    idProperty : 'year',
    fields: [
        {name:'year', type:"int", useNull:false}
        ,{name:'sumImporte'}
        ,{name:'nombre'}
        ,{name:'count', type:"number", useNull:false}
        ,{
            name:'persona',
            reference: {
                type:"Persona",
                association: 'personaCertificadoDTO',
                role:'personas',
                inverse: 'certificados'
            }
        }
    ],
    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.CertificadoDTO',
        mockupUrl: 'data/certificadoListado.json',
        remoteUrl: 'certificados'
        
    }

});
