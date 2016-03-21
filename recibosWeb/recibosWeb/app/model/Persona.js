Ext.define('recibosWeb.model.Persona', {
    extend: 'recibosWeb.model.Base',
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
        ,{name:'id', type:"int", useNull:true}
    ]
});
