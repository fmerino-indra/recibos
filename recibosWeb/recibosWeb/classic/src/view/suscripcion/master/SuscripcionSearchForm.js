Ext.define('recibosWeb.view.suscripcion.master.SuscripcionSearchForm', {
    extend       : 'Ext.form.Panel',
    alias        : 'widget.suscripcion_suscripcionsearchform',
    requires     : ['Ext.layout.container.Column'],
    border       : false,
    collapsible  : true,
    autoScroll   : true,
    layout       : 'vbox',
    dialogMode   : false,
    cls          : 'search-form',
    defaults     : {
        anchor: '100%'
    },
    fieldDefaults: {
        labelAlign    : 'left',
        labelPad      : 1,
        labelSeparator: ':'
    },

    initComponent: function () {
        this.title = 'Criterios de Búsqueda';
        this.dockedItems = [
            {
                dock: 'bottom',
                xtype: 'toolbar',
                defaults: {scale: 'medium'},
                items: [
                    {
                        xtype: 'button',
                        text: 'Limpiar',//t('commons.buttons.limpiar'),
                        cls: 'ux-btn',
                        //handler: 'suscripcionReset',
                        glyph: Glyphs.getIcon('eraser')
                    },
                    {
                        xtype: 'tbfill'
                    },
                    {
                        xtype: 'button',
                        text: 'Buscar',//t('commons.buttons.buscar'),
//                        ui     : 'highlight',
                        cls: 'ux-btn',
                        handler: 'suscripcionSearch',
                        glyph: Glyphs.getIcon('search')
                    }
                ]
            }
        ];
        this.items = [
            {
                columnWidth: 0.33,
                style: 'margin-left:10px;margin-bottom:10px',
                xtype: 'textfield',
                name: 'nombre',
                cls: 'search-element',
                fieldLabel: t('suscripcion.items.nombre')
            },
/*
            {
                xtype: 'checkbox',
                name: 'activo',
                width: 100,
                padding: '8px 0px 0px 9px',
                cls: 'search-element',
                fieldLabel: t('suscripcion.items.active')

            },
*/
            {
                xtype: 'checkboxgroup',
                fieldLabel: 'Activos (S/N)',
                // Arrange checkboxes into two columns, distributed vertically
                columns: 2,
                vertical: true,
                name: 'activo',
                items: [
                { boxLabel: 'Activos', name: 'activo', inputValue: true, checked: true  },
                { boxLabel: 'No activos', name: 'noActivo', inputValue: false}
            ]}
        ];
        this.callParent();
    }


});
