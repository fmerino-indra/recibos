Ext.define("iDynamicsFront.util.SearchForm", {
    extend  : "Ext.form.Panel",
    requires: [
        'iDynamicsFront.data.searchengines.DSL',
        'iDynamicsFront.data.searchengines.Simple',
        'iDynamicsFront.util.Counter'
    ],
    alias   : "widget.idf-searchform",

    /*

     */
    sortersCfg: null,

    /*

     */
    bindSorters: true,

    /*

     */
    bindFilters: true,

    config: {

        /* si hay resetBtnCfg esta configuración se mezcla con la del boton de search */
        searchBtnCfg: null,

        /* si hay resetBtnCfg esta configuración se mezcla con la del boton de reset */
        resetBtnCfg: null,

        /* Si createButtons es false los botones no se crean y habría que llamar a mano a reset() y search() desde otra parte. */
        createButtons: true,
        /*

         */
        searchEngine : null,

        /*

         */
        store: null,

        /*

         */
        groupers: [],

        /*

         */
        referenced: null,

        /*

         */
        sorters: null,

        /*

         */
        sortDirection: 'ASC'
    },

    /*

     */
    remoteFilter: true,

    loadOnCounterClick: true,

    /*

     */
    remoteSort: true,

    statics: {

        /*

         */
        searchEngines: {
            DSL    : 'DSL',
            SIMPLE : 'Simple',
            DEFAULT: 'Default'
        }
    },

    /**
     * @event beforesearch
     * Se dispara al pulsar el botón de búsqueda, justo antes de realizarla. Si retorna false, cancela la acción por defecto
     * @param {iDynamicsFront.util.SearchForm} this
     * @param {Object} params los parámetros por los que se va a buscar
     */

    /**
     * @event search
     * Se dispara cuando se va a realizar la búsqueda, justo antes de producirse la llamada al servidor.
     * @param {iDynamicsFront.util.SearchForm} this
     * @param {Object} filter los filtros en formato de objeto plano por los que se va a buscar
     */

    /**
     * @event beforereset
     * Se dispara al pulsar el botón de reseteo, justo antes de realizarla. Si retorna false, cancela la acción por defecto
     * @param {iDynamicsFront.util.SearchForm} this
     */

    /**
     * @event reset
     * Se dispara al pulsar el botón de reseteo. Provoca el reseteo del formulario
     * @param {iDynamicsFront.util.SearchForm} this
     */

    /**
     * Si se envía la propiedad de configuración referenced, se encarga de preparar la ordenación automática y la posibilidad de arrastrar y soltar el valor de las celdas
     * del grid referenciado sobre el formulario, para poder filtrar por esos valores.
     *
     * @param referenced propiedad de configuración que puede contener las columnas y la vista de un grid si se desea enlazar el formulario con él.
     * El contenido de referenced debe ser una referencia del grid que se desea enlazar y, a su vez, este debe publicar las dos propiedades: view y columns.
     */
    updateReferenced: function (referenced) {
        if (this.bindSorters) {
            this.prepareSorters(referenced.columns);
        }

        if (referenced.view) {
            this.initializePatientDragZone(referenced.view);
            this.initializeDropZone();
        }
    },

    /**
     * Se encarga de escuchar los distintos eventos que interesen del store una vez este prepado, para realizar tareas de sincronización
     * @param {Ext.data.Store} store
     */
    updateStore: function (store) {
        store.on('sort', this.storeSortChanged, this);
    },

    /**
     * Prepara la sincronización con el combo de ordenación cuando u nst
     * @param store
     */
    storeSortChanged: function (store) {
        this.unsubscribeSortersComboEvents();
        this.sortersCombo.setValue(store.getSorters().getAt(0).getProperty());
        this.sortersDirectionCombo.setValue(store.getSorters().getAt(0).getDirection());
        this.subscribeSortersComboEvents();
    },

    /**
     *
     * @param view
     * @param columns
     */
    initializePatientDragZone: function (view) {
        var me = this;
        view.dragZone = Ext.create('Ext.dd.DragZone', view.getEl(), {
            ddGroup    : 'FilterGrid',
            getDragData: function (e) {
                var dataIndex, data = {}, sourceEl = e.getTarget(view.cellSelector, 10), d;
                if (sourceEl) {
                    dataIndex = view.grid.columnManager.columns[sourceEl.cellIndex].dataIndex;
                    if (me.getForm().findField(dataIndex) !== null) {
                        d = sourceEl.cloneNode(true);
                        d.id = Ext.id();
                        data[dataIndex] = sourceEl.innerText;
                        view.dragData = {
                            sourceEl   : sourceEl,
                            repairXY   : Ext.fly(sourceEl).getXY(),
                            ddel       : d,
                            patientData: data
                        };
                        return view.dragData;
                    }
                }
            },

            getRepairXY: function () {
                return this.dragData.repairXY;
            }
        });
    },

    /**
     *
     */
    initializeDropZone: function () {
        var me = this, compDropTargetEl = me.getEl().dom;
        Ext.create('Ext.dd.DropTarget',
            compDropTargetEl, {
                ddGroup    : 'FilterGrid',
                notifyEnter: function (ddSource, e, data) {
                    me.body.stopAnimation();
                    me.body.highlight();
                },
                notifyDrop : function (ddSource, e, data) {
                    me.getForm().setValues(data.patientData);
                    me.search();
                    return true;
                }
            });
    },

    afterRender: function () {
        var me = this;
        this.callParent();
        me.mon(me.getEl(), 'keyup', function () {
            if (arguments[0].getKey() === Ext.EventObject.ENTER) {
                me.search();
            }
        }, {delegate: 'input'});
    },

    /**
     *
     */
    createSortDirectionCombo: function () {
        this.sortersDirectionCombo = Ext.widget('combo', {
            fieldLabel    : 'Dirección',
            value         : this.getSortDirection(),
            valueField    : 'value',
            displayField  : 'text',
            minChars      : 0,
            queryMode     : 'local',
            forceSelection: true,
            store         : Ext.create('Ext.data.Store', {
                fields: ['text', 'value'],
                data  : [
                    {text: 'Ascendente', value: 'ASC'}, {
                        text : 'Descendente',
                        value: 'DESC'
                    }
                ]
            })
        });
    },

    /**
     *
     */
    createSortersCombo: function () {
        var me = this;
        this.sortersCombo = Ext.widget('combo', {
            fieldLabel    : 'Ordenar por',
            valueField    : 'value',
            displayField  : 'text',
            minChars      : 0,
            queryMode     : 'local',
            forceSelection: true,
            store         : Ext.create('Ext.data.Store', {fields: ['text', 'value'], data: me.getSorters()})
        });
    },

    /**
     *
     */
    subscribeSortersComboEvents: function () {
        //this.sortersCombo.on('select', this.sort, this);
        this.sortersCombo.on('change', this.sort, this); //Para contemplar el caso de que lo dejemos vacío usamos el change
        this.sortersDirectionCombo.on('select', this.changeDirection, this);
    },

    /**
     *
     */
    unsubscribeSortersComboEvents: function () {
        //this.sortersCombo.un('select', this.sort, this);
        this.sortersCombo.un('change', this.sort, this);
        this.sortersDirectionCombo.un('select', this.changeDirection, this);
    },

    initComponent: function () {
        if (this.getCreateButtons()) {
            var searchBtnDefaultCfg, resetBtnDefaultCfg;
            searchBtnDefaultCfg = {
                text     : this.searchText || 'Search',
                glyph	 : Glyphs ? Glyphs.getIcon('search') : '',
                // ui       : 'highlight',
                listeners: {click: this.search, scope: this}
            };
            resetBtnDefaultCfg = {
                text     : this.resetText || 'Reset',
                glyph	 : Glyphs ? Glyphs.getIcon('eraser') : '',
                listeners: {click: this.reset, scope: this}
            };
            this.cls = 'search-form ' + (this.cls || '');
            this.buttons = [
                (this.getResetBtnCfg() ? Ext.merge(resetBtnDefaultCfg, this.getResetBtnCfg()) : resetBtnDefaultCfg),
                '->',
                (this.getSearchBtnCfg() ? Ext.merge(searchBtnDefaultCfg, this.getSearchBtnCfg()) : searchBtnDefaultCfg)
            ];
        }
        /*
         this.buttons = [
         {
         text     : this.searchText || 'Search',
         listeners: {click: this.search, scope: this}
         },
         '->',
         {
         text     : this.resetText || 'Reset',
         listeners: {click: this.reset, scope: this}
         }
         ];*/
        this.callParent();
        if (this.counters) {
            this.createCounter();
            this.insert(0, this.counters);
        }
    },

    /**
     *
     */
    search: function () {
        var filter = this.createFilter();
        //una de las utilidades del beforesearch puede ser actualizar el filtro si quisiéramos añadir parámetros adicionales a los que aparezcan en el formulario
        //volviendo a llamar al createFilter con parámetros que se añadirán a los del formulario
        if (this.fireEvent('beforesearch', this, filter) !== false) {
            this.fireEvent('search', this, filter);
            this.getStore().loadPage(1);
        }
    },

    reset: function () {
        if (this.fireEvent('beforereset') !== false) {
            this.fireEvent('reset');
            this.getForm().reset();
        }
    },

    /**
     *
     * @returns {*}
     */
    getFormParams: function () {
        var params, sortersComboDisabled, sortersDirectionComboDisabled;

        if (this.sortersCombo) {
            sortersComboDisabled = this.sortersCombo.isDisabled();
            sortersDirectionComboDisabled = this.sortersDirectionCombo.isDisabled();
            //Para que no envíe ciertos campos en el filtro los deshabilito
            this.sortersCombo.disable(true);
            this.sortersDirectionCombo.disable(true);
        }

        params = this.getValues();
        if (this.sortersCombo) {
            //y luego recupero su estado.
            this.sortersCombo.setDisabled(sortersComboDisabled);
            this.sortersDirectionCombo.setDisabled(sortersDirectionComboDisabled);
        }
        return params;
    },

    /**
     *
     * @param extraParams
     */
    createFilter: function (extraParams) {
        var store = this.getStore(), searchEngine = this.getSearchEngine() || iDynamicsFront.util.SearchForm.searchEngines.DEFAULT, params;

        extraParams = extraParams || {};
        params = Ext.apply(this.getFormParams(), extraParams);
        if (searchEngine === iDynamicsFront.util.SearchForm.searchEngines.DEFAULT) {
            store.clearFilter(true);
            if (this.remoteFilter && !store.getRemoteFilter()) {
                store.setRemoteFilter(true);
            }
            store.filter(this.getFilter(params), null, true);
        } else {
            store.getProxy().extraParams = this.getFilter(params);
        }

        return params;
    },

    onCounterSelected: function (view, record) {
        var me = this;
        me.search(record);
        //Lanzo evento por si en el controlador se quiere hacer algo
        //me.fireEvent("search", me, record);
    },

    privates: {
        getFilter: function (params) {
            return iDynamicsFront.data.searchengines[this.getSearchEngine() || iDynamicsFront.util.SearchForm.searchEngines.DEFAULT].buildFilter(params);
        },

        sort: function (combo, data) {
            if (this.sortersCombo.getValue() === null || this.sortersCombo.getValue() === "") {
                this.getStore().sorters.clear();
                this.getStore().load();
            } else {
                this.getStore().sort(this.sortersCombo.getValue(), this.getSortDirection());
            }
        },

        prepareSorters: function (columns) {
            var store = this.getStore();
            if (this.remoteSort && !store.getRemoteSort()) {
                store.setRemoteSort(true);
            }
            if (!this.getSorters() && columns) {
                this.setSorters(Ext.Array.map(Ext.Array.filter(columns, function (col) {
                    return col.isSortable();
                }), function (col) {
                    return {text: col.text, value: col.dataIndex};
                }));
            }
            this.createSortersCombo();
            this.createSortDirectionCombo();
            this.add(this.sortersCombo, this.sortersDirectionCombo);
            this.subscribeSortersComboEvents();
        },

        changeDirection: function (combo, value) {
            this.setSortDirection(value.get('value'));
            this.sort();
        },
        createCounter  : function () {
            var me = this, counters = me.counters || [], ct;

            me.counters = Ext.Array.map(counters, function (counter) {
                //TODO eliminar xtype del objeto counter si lo hubiese
                ct = Ext.create("iDynamicsFront.util.Counter", counter);
                if (me.loadOnCounterClick) {
                    ct.on("counterselected", me.onCounterSelected, me);
                }
                return ct;
            });
        }
    }
});