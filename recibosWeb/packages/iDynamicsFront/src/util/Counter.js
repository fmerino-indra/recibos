Ext.define("iDynamicsFront.util.Counter", {
    extend            : 'Ext.panel.Panel',
    alias             : 'widget.uxcounter',
    cls               : 'counters',
    store             : null,
    valueField        : "value",
    counterField      : "count",
    displayField      : "text",
    nameIconClsCounter: "iconCss",
    name              : '',

    config: {
        store: null
    },

    updateStore: function (store) {
        this.view = Ext.create('Ext.view.View', {
            dataIndex      : this.dataIndex,
            store          : store,
            tpl            : new Ext.XTemplate(
                '<tpl for=".">',
                '<div class="ux-view-item">',
                '<span class="icon {' + (this.cssField || this.valueField) + '} {' + this.nameIconClsCounter + '}"></span>',
                '<span class="text">' + '{' + this.displayField + '}</span>',
                '<span class="count">{' + this.counterField + '}</span>',
                '</div>',
                '</tpl>'),
            multiSelect    : false,
            overItemCls    : 'x-item-over',
            selectedItemCls: 'x-item-selected',
            itemSelector   : 'div.ux-view-item'
        }).on('selectionchange', this.onSelectionChange, this);
        this.insert(0, this.view);
    },

    initComponent: function () {
        var me = this;

        me.field = Ext.widget("hiddenfield", {
            name: me.name
        });
        me.items = me.field;

        me.callParent(arguments);
        //me.addEvents('counterclick');
    },

    getView: function () {
        return this.view;
    },

    onSelectionChange: function (view, records) {
        var me = this;
        if (records.length) {
            me.field.setValue(records[0].get(me.valueField));
            me.fireEvent('counterselected', view, records[0]);
        }
    },

    getSelectionModel: function () {
        return this.view.getSelectionModel();
    }
});
