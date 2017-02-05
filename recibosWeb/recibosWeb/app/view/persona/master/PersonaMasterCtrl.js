Ext.define('recibosWeb.view.persona.master.PersonaMasterCtrl', {
    extend: 'Ext.app.ViewController',
    alias : 'controller.persona-master',

    control: {
        'persona_personagrid': {
            beforerender: 'personaLoad'
        }
    },

    /**
     * @autogenerated
     *
     */
    personaLoad: function () {
        var me = this;
        // TODO FMM
        //me.personaSearch();
    },

    personaReset: function () {
        this.lookupReference('persona_personasearchform').getForm().reset();
    },


    onSelectionChange: function (grid, selecion) {
//        this.getViewModel().set('hasAnyActive', this.hasAnyActive(true));
//        this.getViewModel().set('hasAnyInactive', this.hasAnyActive(false));
    },

    /**
     * @autogenerated
     * Load parameters after the search button has been pressed
     * add parameters to a hashmap, and the last step, launches search
     *
     * @param searchBtn button pressed
     *
     */
    personaSearch: function () {
        var me = this, store, search = me.lookupReference("persona_personasearchform"),
            params = search.getForm().getValues(), value, filters;

        filters = [
            {
                property: 'filter',
                value   : true
            }
        ];

        store = me.getViewModel().getStore('personas');
debugger;
        Ext.each(Ext.Object.getKeys(params), function (name) {
            value = params[name];
            if (value !== '' && value !== null) {
                filters[filters.length] = {
                    property: name,
                    value   : value
                };
                console.log('property:' + name + ' value:' + value);
//                recibosWeb.lib.Logger.info('property ' + name + 'value ' + value);
            }
        });

        store.setRemoteFilter(true);
        store.clearFilter(true);
        store.filter(filters, null, true);

        store.loadPage(1, {
            callback: function (records, operation, success) {
                if (!success) {
                    if (operation.getError().code === null) {
                        me.error(operation.getError());
                    }
                } else {
                    //TODO ..
                }
            }
        });
    },

    personasDataChanged : function (personas) {
        this.getViewModel().set('personasCount', personas.getCount() > 0 ? personas.getTotalCount() : 0);
    }
});
