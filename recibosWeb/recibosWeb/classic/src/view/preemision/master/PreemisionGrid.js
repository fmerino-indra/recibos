Ext.define('recibosWeb.view.preemision.master.PreemisionGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.preemision_preemisiongrid',
    cls          : 'data-list usuarios',
    multiSelect  : true,
/*
    viewModel : {
        type: 'preemision-master'
    },
*/
    bind     : {
        store: '{preemisions}'
//        title: 'Listado de usuarios [{usuarios.totalCount}]'
    },


//    glyph      : Glyphs.getIcon('bars'),
//    viewConfig   : {
//        getRowClass: function (record) {
//            return record.get('enabled') ? "enabled" : "disabled";
//        }
//    },
    initComponent: function () {
        this.columns =
            [
                {
                    header   : t('preemision.items.mes'),
                    dataIndex: 'mes',
                    flex     : 1.5
                    //,
                    //renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('preemision.items.fechaEmision'),
                    dataIndex: 'fechaMes',
                    flex     : 1,
                    renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('preemision.items.importe'),
                    dataIndex: 'importeMes',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                }
                ,
                {
                    header   : t('preemision.items.importeDevuelto'),
                    dataIndex: 'devueltoMes',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                }
                ,
                {
                    header   : t('preemision.items.fechaEmision'),
                    dataIndex: 'fechaTrimestre',
                    flex     : 1,
                    renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('preemision.items.importe'),
                    dataIndex: 'importeTrimestre',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                }
                ,
                {
                    header   : t('preemision.items.importeDevuelto'),
                    dataIndex: 'devueltoTrimestre',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                }
                ,
                {
                    header   : t('preemision.items.fechaEmision'),
                    dataIndex: 'fechaSemestre',
                    flex     : 1,
                    renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('preemision.items.importe'),
                    dataIndex: 'importeSemestre',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                }
                ,
                {
                    header   : t('preemision.items.importeDevuelto'),
                    dataIndex: 'devueltoSemestre',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                }
                ,
                {
                    header   : t('preemision.items.fechaEmision'),
                    dataIndex: 'fechaAnyo',
                    flex     : 1,
                    renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('preemision.items.importe'),
                    dataIndex: 'importeAnyo',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
                    //                    format   : '0,000.00'
                }
                ,
                {
                    header   : t('preemision.items.importeDevuelto'),
                    dataIndex: 'devueltoAnyo',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    renderer: function(value) {
                        // evaluates `value` to append either `person' or `people`
                    	if (value) {
                    		return Ext.util.Format.number(value, '0,000.00');
                    	} else {
                    		return '';
                    	}    	
                    } 
//                    format   : '0,000.00'
                }
            ];
        /* TODO FMM */
        this.bbar = [
            '->'
            ,
            {
                xtype      : 'pagingtoolbar',
                displayInfo: true,
                cls        : 'paging-toolbar',
                bind       : {
                    store: '{preemisions}'
                }
            }];

        this.callParent();
    }
/* TODO FMM
    ,


    translate: function () {
        var me = this;
        me.setTitle(t('listado.de.datos'));
        console.log('preemision_preemisiongrid.translate.ini');
        me.down('#codigo').setText(t('preemision.items.codigo'));
        me.down('#nombre').setText(t('preemision.items.nombre'));
        me.down('#frecuencia').setText(t('preemision.items.frecuencia'));
        me.down('#anticipacion').setText(t('preemision.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('preemision_preemisiongrid.translate.fin');
    }
    */
})
;
