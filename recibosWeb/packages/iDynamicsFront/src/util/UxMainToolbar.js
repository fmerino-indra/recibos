Ext.define('iDynamicsFront.utils.UxMainToolbar', {
    extend            : 'Ext.toolbar.Toolbar',
    alternateClassName: 'Ux.utils.UxMainToolbar',
    alias             : 'widget.ux-maintoolbar',

    //atributos configurables
    cls       : null,
    defaultCls: 'ux-toolbar',
    dock      : 'top',
    scale     : 'medium',
    noLabels  : false,

    //opciones de la toolbar
    items: null,

    initComponent: function () {
        var me = this, item, items, hash, newItems = [];

        me.defaults = {scale: me.scale};

        // construimos los diferentes css que tiene que tener la toolbar
        me.cls = me.defaultCls + ' ' + me.cls + ' ' + me.scale;
        if (me.noLabels) {
            me.cls = me.cls + ' noLabels';
        }

        items = me.items;

        //construyo un hash con las posibles opciones
        hash = me.hash = me.rellenarHash();

        Ext.Array.each(items, function (hitem) {
            if (typeof hitem === 'string' && (item = me.hash[hitem])) {
                if (me.noLabels) {
                    item.text = null;
                }
                newItems[newItems.length] = item
            } else {
                newItems[newItems.length] = hitem
            }
        })

        me.items = newItems;

        me.callParent();
    },

    rellenarHash: function () {
        return {
            'back'       : {
                iconCls: 'icon-arrowLeft',
                action : 'back',
                text   : t('commons.buttons.back'),
                tooltip: t('commons.buttons.tooltip.back'),
                frame  : false,
                glyph  : Glyphs.getIcon('long_arrow_left')
            },
            'edit'       : {
                iconCls: 'icon-edit',
                action : 'editRecord',
                text   : t('commons.buttons.Editar'),
                tooltip: t('commons.buttons.tooltip.Editar'),
                frame  : false,
                glyph  : Glyphs.getIcon('pencil')
            },
            'add'        : {
                iconCls: 'icon-fileAdd',
                action : 'newRecord',
                text   : t('commons.buttons.addText') + "...",
                tooltip: t('commons.buttons.tooltip.addText'),
                frame  : false,
                glyph  : Glyphs.getIcon('plus')
            },
            'duplicate'  : {
                iconCls: 'icon-layers',
                action : 'duplicateRecord',
                text   : t('commons.buttons.duplicate'),
                tooltip: t('commons.buttons.tooltip.duplicate'),
                frame  : false
            },
            'delete'     : {
                iconCls: 'icon-fileRemove',
                action : 'deleteRecord',
                text   : t('commons.buttons.deleteText'),
                tooltip: t('commons.buttons.tooltip.deleteText'),
                frame  : false,
                glyph  : Glyphs.getIcon('minus')
            },
            'recover'    : {
                iconCls: 'icon-export',
                action : 'recover',
                text   : t('commons.buttons.recover'),
                tooltip: t('commons.buttons.tooltip.recover'),
                frame  : false
            },
            'requestAuth': {
                iconCls: 'icon-clock',
                action : 'requestAuth',
                text   : t('commons.buttons.requestAuth'),
                tooltip: t('commons.buttons.tooltip.requestAuth'),
                frame  : false
            },
            'authorize'  : {
                iconCls: 'icon-shield',
                action : 'authorize',
                text   : t('commons.buttons.authorize'),
                tooltip: t('commons.buttons.tooltip.authorize'),
                frame  : false
            },
            'reject'     : {
                iconCls: 'icon-cancel',
                action : 'reject',
                text   : t('commons.buttons.reject'),
                tooltip: t('commons.buttons.tooltip.reject'),
                frame  : false
            },
            'createGroup': {
                iconCls: 'icon-list',
                action : 'createGroup',
                text   : t('commons.buttons.createGroup'),
                tooltip: t('commons.buttons.tooltip.createGroup'),
                frame  : false
            },
            'editGroup'  : {
                iconCls: 'icon-indentIncrease',
                action : 'editGroup',
                text   : t('commons.buttons.editGroup'),
                tooltip: t('commons.buttons.tooltip.editGroup'),
                frame  : false
            },
            'excel'      : {
                iconCls: 'icon-fileExport',
                action : 'excel',
                text   : t('commons.buttons.excel'),
                pressed: false,
                tooltip: t('commons.buttons.tooltip.excel'),
                frame  : false,
                glyph  : Glyphs.getIcon('file_excel_o')
            },
            'pdf'        : {
                iconCls: 'icon-fileExport',
                action : 'pdf',
                text   : t('commons.buttons.pdf'),
                pressed: false,
                tooltip: t('commons.buttons.tooltip.pdf'),
                frame  : false,
                glyph  : Glyphs.getIcon('file_pdf_o')
            }
        }
    }
});
