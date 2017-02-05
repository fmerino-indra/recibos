Ext.define('recibosWeb.view.preemision.PreemisionViewportCtrl', {
//    extend  : 'Ext.app.ViewController',
    extend: 'iDynamicsFront.util.ViewController',
    alias   : 'controller.preemision-viewport',
    requires: [
        'recibosWeb.model.Cabecera',
        'recibosWeb.model.Emision'
    ],

    control: {
        'preemision_preemisiongrid'                 : {
            itemdblclick   : 'preemisionOnDataListDblClick',
//            selectionchange: 'preemisionOnDatalistSelectionChange'
			selectionchange: 'onSelectionChange'
        },
        'ux-maintoolbar button'               : {
            click: 'onAction'
        },
        'preemision_preemisionmaster ux-maintoolbar': {
            beforerender: 'onBeforeToolbarRender'
        }
    },

    // FMM From iDynamics
    onAction: function (btn) {
        var me = this, action = btn.action;
        me.executeAction(action, btn);
    },
    // FMM From iDynamics
    executeAction: function (action) {
        var me = this;
        if (action) {
            if (typeof me[action] === 'function') {
                var args = Ext.toArray(arguments);
                me[action].apply(me, args.slice(1, args.length));
            } else {
                me.warn('Action "' + action + '" is not defined.');
            }
        } else {
            me.warn('No action defined to this event: ' + action);
        }
    },


    onBeforeToolbarRender: function (tbar) {
        var toDisable = ['deleteRecord', 'editRecord', 'resetPass'];
/*      TODO FMM
        Ext.each(toDisable, function (action) {
            tbar.down('button[action=' + action + ']').disable();
        });*/
    },

    /**
     * Load the selected record's detail, after making double click.
     *
     * @param {Ext.selection.RowModel} model
     * @param view
     */
    preemisionOnDataListDblClick: function (view, model) {
        var me = this,
            detailMaster = me.lookupReference("preemision_preemisiondetailmaster"),
            detailForm = detailMaster.down('preemision_preemisiondetailform'),
            detailGrid = detailMaster.down('preemision_preemisiondetailgrid'),
            detailMasterController, dependencies;

        detailForm.getForm().reset();
        me.getView().getLayout().next();

        detailMasterController = detailMaster.getController();
        dependencies = detailMasterController.preemisionReloadDependentStores();

        RSVP.all(dependencies).then(function () {
        	detailMasterController.preemisionViewDetail(model);
        });

    },

    newRecord: function (btnNewRecord) {
        var me = this, viewport = me.getView(), layout = viewport
            .getLayout(), detail = viewport.down('preemision_preemisiondetailform'), form = detail
            .getForm();

        if (layout.getActiveItem() === viewport.items.getAt(0)) {
            layout.setActiveItem(1);
        }
        detail.setTitle(t('preemision.detail.title'));
        form.reset();
        form.loadRecord(Ext.create('recibosWeb.model.Preemision'));
        form.clearInvalid();
        form.getFields().getAt(0).focus();
    },

    /**
     * @autogenerated
     * Redirect to the page selected record modified
     * load the object model
     * if not exist, launches a message indicating that
     *
     *
     * @param btn button pressed
     *
     */
    editRecord: function (btnEditRecord) {
        var me = this, rows, master;

        master = btnEditRecord.up(me.getView()).down(
            'preemision_preemisionmaster');
        rows = master.getSelection();

        if (rows && rows.length > 0) {
            me.preemisionOnDataListDblClick(btnEditRecord, rows[0]);
        } else {
            var wObject = {
                title   : i18n.commons.msgs.noSelectionRowTitle,
                messages: i18n.commons.msgs.noSelectionRowMsg
            };
            me.warning(wObject);
        }

    },
    /**
     * @autogenerated
     * Redirect to the previous page
     *
     *
     * @param btn button pressed
     *
     */
    back      : function () {
        var me = this;
        me.getView().down('preemision_preemisionmaster').getController().preemisionLoad();
        me.getView().getLayout().setActiveItem(0);
    },

    /**
     * @autogenerated
     * Delete the selected record, asking if you are sure of making the operation
     * If the answer is yes, the server invokes
     *
     * @param btn button pressed
     *
     */
    deleteRecord: function (btnDeleteRecord) {
        var me = this, viewport = me.getView(), layout = viewport
            .getLayout(), activeItem = layout.getActiveItem(), record, dataList = viewport
            .down('preemision_preemisiongrid'), store;

        if (activeItem === viewport.items.getAt(1)) {
            record = activeItem.getUsuario();
            if (record.data.id === 0) {
                layout.prev();
                return;
            }
        } else {
            record = dataList.getSelectionModel().getSelection()[0];
        }

        store = dataList.getStore();
        me.log('Prepared to remove preemision with id: "' + record.data.id
            + '"');
        Ext.MessageBox.confirm(t('commons.msgs.warning'),
            t('commons.msgs.question.delete'),
            function (action) {
                if (action === 'yes') {
                    me.preemisionDeleteAction(record, function () {
                        store.remove(record);
                        if (layout.getActiveItem() === viewport.items
                                .getAt(1)) {
                            layout.setActiveItem(0);
                        }
                    });
                    store.reload();
                }
            });
    },

    /**
     * @autogenerated
     * Deletes the selected entry from the cache
     *
     * @param {Ext.data.Model}
     *            record
     * @param {Function}
     *            function to execute in the callback
     */
    preemisionDeleteAction: function (record, fn) {
        var me = this;
        record.erase({
            success: function (record, operation) {
                if (typeof fn === 'function') {
                    fn();
                    me.info(t('commons.msgs.operacionCorrecta'));
                }
            },
            failure: function (record, operation) {
                //me.error(operation.getError());
            }
        });
    },

    getIdsFromSelection: function (getEnabled) {
        var me = this, selection, getByEnable;
        selection = me.lookupReference('preemision_preemisiondetailmaster').down('preemision_preemisiondetailgrid').getSelectionModel().getSelection();

        getByEnable = Ext.Array.filter(selection, function (record) {
            return record.get('devuelto') === getEnabled;
        });

        return Ext.Array.map(getEnabled !== undefined ? getByEnable : selection, function (record) {
            return record.get('id');
        });
    },

    emitir: function (emitir) {
        var me = this;
        selection = me.lookupReference('preemision_preemisionmaster').down('preemision_preemisiongrid').getSelectionModel().getSelection();

        var search = me.lookupReference('preemision_preemisionmaster').down("preemision_preemisionsearchform");
        var parameters = search.getForm().getValues();
        var year = Number(parameters['anyo']);
        var mes = selection[0].data.codigoMes;
        Ext.Ajax.request({
            url    : Ext.util.Format.format('{0}/emisiones/emitir', Environment.getBaseUrl()),
        params : {anyo: year, codigoMes: mes},
            success: function (response) {
            	var vista = me.getView().down('preemision_preemisionmaster');
            	me.info(Ext.util.Format.format('{0} {1}', 'Se ha generado la emisión para el mes de:', mes));
                vista.getController().preemisionSearch();
            },
            failure: function (response) {
                me.error(response);
            }
        });
        
    },
    generar: function (emitir) {
        var me = this;
        selection = me.lookupReference('preemision_preemisionmaster').down('preemision_preemisiongrid').getSelectionModel().getSelection();

        var search = me.lookupReference('preemision_preemisionmaster').down("preemision_preemisionsearchform");
        var parameters = search.getForm().getValues();
        var year = Number(parameters['anyo']);
        var mes = selection[0].data.codigoMes;
        Ext.Ajax.request({
            url    : Ext.util.Format.format('{0}/emisiones/generar', Environment.getBaseUrl()),
        params : {anyo: year, codigoMes: mes},
            success: function (response) {
            	var vista = me.getView().down('preemision_preemisionmaster');
            	me.info(Ext.util.Format.format('{0} {1}', 'Se ha generado la emisión para el mes de:', mes));
                vista.getController().preemisionSearch();
            },
            failure: function (response) {
                me.error(response);
            }
        });
        
    },
    onSelectionChange: function (grid, selecion) {
    	this.comprobarBotones();
    },

    comprobarBotones: function () {
        this.getViewModel().set('canAnyEmitted', this.canAnyEmitted(true));
        this.getViewModel().set('canAnyGenerate', this.canAnyGenerate());
    },
    canAnyEmitted: function () {
        var hasAny = false, selection;
        selection = this.getView().down('preemision_preemisiongrid').getSelectionModel().getSelection();
        var canAnyEmit = false;
        if (selection.length) {
        	canAnyEmit = Ext.Array.contains(Ext.Array.map(selection, function (record) {
                return (record.get('fechaMes') != null);
            }), false);
        }

        return canAnyEmit;
    },
    canAnyGenerate: function () {
        var hasAny = false, selection;
        selection = this.getView().down('preemision_preemisiongrid').getSelectionModel().getSelection();
        var canAnyGenerate = false;
        if (selection.length) {
        	canAnyGenerate = Ext.Array.contains(Ext.Array.map(selection, function (record) {
                return (record.get('fechaMes') == null);
            }), false);
        }

        return canAnyGenerate;
    }
    
});