Ext.define('recibosWeb.view.suscripcion.SuscripcionViewportCtrl', {
//    extend  : 'Ext.app.ViewController',
    extend: 'iDynamicsFront.util.ViewController',
    alias   : 'controller.suscripcion-viewport',
    requires: ['recibosWeb.model.Suscripcion'],

    control: {
        'suscripcion_suscripciongrid'                 : {
            itemdblclick   : 'suscripcionOnDataListDblClick',
//            selectionchange: 'suscripcionOnDatalistSelectionChange'
            selectionchange: 'onSelectionChange'
        },
        'ux-maintoolbar button'               : {
            click: 'onAction'
        },
        'suscripcion_suscripcionmaster ux-maintoolbar': {
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
    onSelectionChange: function (grid, selecion) {
    	this.comprobarBotones();
    },

    comprobarBotones: function () {
        this.getViewModel().set('hasAnyActive', this.hasAnyReturned(true));
        this.getViewModel().set('hasAnyInactive', this.hasAnyReturned(false));
        this.getViewModel().set('hasSelected', this.hasSelected());
    },
    /**
     *
     * @param selection
     * @param active
     * @returns {boolean} true si hay alg�n activo seleccionado y false si hay alg�n inactivo
     */
    hasAnyReturned: function (active) {
        var hasAny = false, selection;
        selection = this.getView().down('suscripcion_suscripciongrid').getSelectionModel().getSelection();
        if (selection.length) {
            hasAny = Ext.Array.contains(Ext.Array.map(selection, function (record) {
                return record.get('activo');
            }), active);
        }

        return hasAny;
    },

    /**
    *
    * @returns {boolean} true si hay alg�n seleccionado y false si no hay
    */
   hasSelected: function () {
       var hasAny = false, selection;
       selection = this.getView().down('suscripcion_suscripciongrid').getSelectionModel().getSelection();
       return selection.length;
   },

    /**
     * @autogenerated
     * Enable delete button
     * @param {Ext.selection.RowModel}
     *            selModel
     * @param {Editran.model.Operacion[]}
     *            selected
     */
    suscripcionOnDatalistSelectionChange: function (selModel, selected) {
        ;
/* TODO FMM
        var me = this, btns = ['deleteRecord', 'editRecord', 'resetPass'], master, isDisabled = selected.length === 0;

        master = me.getView().down('suscripcion_suscripcionmaster');
        Ext.Array.each(btns, function (btn) {
            master.down(Ext.util.Format.format('button[action={0}]', btn)).setDisabled(isDisabled);
        });
*/
    },

    /**
     * Load the selected record's detail, after making double click.
     *
     * @param {Ext.selection.RowModel} model
     * @param view
     */
    suscripcionOnDataListDblClick: function (view, model) {
        var me = this, detail = me.lookupReference("suscripcion_suscripciondetailform"), dependencies, detailFormController;

        detail.getForm().reset();
        me.getView().getLayout().next();
debugger;
        detailFormController = me.getView().down('suscripcion_suscripciondetailform').getController();
        dependencies = detailFormController.suscripcionReloadDependentStores();
        RSVP.all(dependencies).then(function () {
            detailFormController.suscripcionViewDetail(model);
        });
    },

    newRecord: function (btnNewRecord) {
        var me = this, viewport = me.getView(), layout = viewport
            .getLayout(), detail = viewport.down('suscripcion_suscripciondetailform'), form = detail
            .getForm();

        if (layout.getActiveItem() === viewport.items.getAt(0)) {
            layout.setActiveItem(1);
        }
        detail.setTitle(t('suscripcion.detail.title'));
        form.reset();
        form.loadRecord(Ext.create('recibosWeb.model.SuscripcionDTO'));
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
            'suscripcion_suscripcionmaster');
        rows = master.getSelection();

        if (rows && rows.length > 0) {
            me.suscripcionOnDataListDblClick(btnEditRecord, rows[0]);
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
        me.getView().down('suscripcion_suscripcionmaster').getController().suscripcionLoad();
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
            .down('suscripcion_suscripciongrid'), store;

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
//        me.log('Prepared to remove suscripcion with id: "' + record.data.id
//            + '"');
        Ext.MessageBox.confirm(t('commons.msgs.warning'),
            t('commons.msgs.question.delete'),
            function (action) {
                if (action === 'yes') {
                    me.suscripcionDeleteAction(record, function () {
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
    suscripcionDeleteAction: function (record, fn) {
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
        selection = me.lookupReference('suscripcion_suscripcionmaster').down('suscripcion_suscripciongrid').getSelectionModel().getSelection();

        getByEnable = Ext.Array.filter(selection, function (record) {
            return record.get('activo') === getEnabled;
        });

        return Ext.Array.map(getEnabled !== undefined ? getByEnable : selection, function (record) {
            return record.get('id');
        });
    },

    enable: function () {
        this.setDisabled(false);
    },

    disable: function () {
        this.setDisabled(true);
    },

    setDisabled: function (desactive) {
        var me = this, ids;
        ids = me.getIdsFromSelection(desactive);
        Ext.Ajax.request({
            url    : Ext.util.Format.format('{0}/suscripciones/{1}', Environment.getBaseUrl(), desactive? 'desactivar' : 'activar'),
            params : {id: ids},
            success: function (response) {
                me.info(Ext.util.Format.format('{0} {1} {2}', desactive ? 'Desactivados' : 'Activados', ids.length, ids.length > 1 ? 'suscripciones' : 'suscripción'));
                me.getView().down('suscripcion_suscripcionmaster').getController().suscripcionSearch();
            },
            failure: function (response) {
                me.error(response);
            }
        });
    },

    certificado: function() {
    	var me = this, selection, id, record;
        selection = me.lookupReference('suscripcion_suscripcionmaster').down('suscripcion_suscripciongrid').getSelectionModel().getSelection();
        record = selection[0];
        win = Ext.ComponentQuery.query('certificadoWindow')[0];//mn.lookupReference('commandWindow');
        if (!win) {
            win = Ext.create('recibosWeb.view.suscripcion.certificado.CertificadoWindow');
        }

        win.getViewModel().set('selectedSuscription', record);
        win.show();
    },
    onBeforeShow: function (mn, eOpts) {
    	debugger;
    },
    
    resetPass: function () {
        var me = this, ids;
        ids = me.getIdsFromSelection();
        Ext.Ajax.request({
            url    : Ext.util.Format.format('{0}/usrusuarios/{1}', $AC.getWebPath(), 'reset-passwd'),
            params : {id: ids},
            success: function (response) {
                me.info(Ext.util.Format.format('Contraseña reseteada a {0} {1}', ids.length, ids.length > 1 ? 'usuarios' : 'usuario'));
                me.getView().down('usuario_usuariomaster').getController().usuarioLoad();
            },
            failure: function (response) {
                //me.error(response);
            }
        });
    }
});
