Ext.define('recibosWeb.view.persona.master.PersonaMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.persona-master',
    requires: [
        'recibosWeb.model.Persona'
    ],
    stores: {
        personas: {
            model       : 'recibosWeb.model.Persona',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 20,
            remoteFilter: true,
            listeners: { datachanged: 'personasDataChanged' }
        }
    }
});