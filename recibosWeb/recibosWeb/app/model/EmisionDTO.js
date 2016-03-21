Ext.define('recibosWeb.model.EmisionDTO', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy',
        'Ext.data.proxy.Rest'
    ],

    idProperty : 'id',
    fields: [
        {name:'id', type:"int", useNull:true}
        ,{name:'cabeceraDTO'}
        ,{name:'devuelto', type:"boolean", useNull:true}
        ,{name:'importe', type:"number", useNull:true}
        ,{name:'divisa', type:"string", useNull:true}
        ,{name:'reenviado', type:"boolean", useNull:true}
        ,{name:'primero', type:"boolean", useNull:true}
        ,{name:'ultimo', type:"boolean", useNull:true}
        ,{name:'titular', type:"string", useNull:true}
        ,{name:'titularId', type:"int", useNull:true}
        ,{name:'dto', reference:{
            type: 'CabeceraDTO',
            association: 'UsersByOrganization',
            role: 'cabDTO',
            inverse: 'emisiones'
        }
        }

    ]
});
