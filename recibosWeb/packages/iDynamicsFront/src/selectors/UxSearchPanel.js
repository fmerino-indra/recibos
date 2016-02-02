Ext.define('iDynamicsFront.selectors.UxSearchPanel', {
   extend : 'Ext.panel.Panel',
   alias : 'widget.uxsearchpanel',

   componentCls : 'uxsearchpanel',

   //Parametros
   store : null,
   displayField : null,
   comboConfig : {}, //Opcional
   gridConfig : {}, //Opcional
   colRenderer : null, //Opcional, se "mezcla" con el renderer que marca las letras buscadas

   //layout:{type:'vbox', align:'stretch'},
   layout : {
      type : 'anchor'
   },

   initComponent : function() {
      var me = this, comboConfig, gridConfig, rendererDefault, renderer;
      comboConfig = Ext.merge({
         xtype : "combo",
         anchor : "100%", //margin:0,  //height:10,
         padding : 0,
         margin : 0,
         cls : "uxsearchpanel-combo",
         displayField : me.displayField,
         minChars : 0,
         forceSelection : false,
         triggerAction : 'all',
         store : me.store,
         plugins : ['clearbutton'],
         listConfig : {
            style : "display:none important!",
            cls : 'ux-uxsearchpanel-combolist',
            disabled : true,
            hidden : true,
            loadMask : false,
            minWidth : 0,
            maxWidth : 0,
            maxHeight : 0,
            width : 0,
            shadow : false,
            tpl : '-'/*tpl : '<tpl for="."><div class="x-boundlist-item">xxx</div></tpl>'*/
         },
         listeners : {
            beforequery : function(queryEvent, eOpts) {//Esto para que pueda poner paginado al combo...
               var extra_p = {};
               extra_p[queryEvent.combo.queryParam] = queryEvent.query;
               me.store.getProxy().extraParams = (me.store.getProxy().extraParams ? Ext.merge(me.store.getProxy().extraParams, extra_p) : extra_p);
               //debugger //Necesito queryParam del combo
               //me.store.getProxy().extraParams =
               //Ext.merge (me.store.getProxy().extraParams
            }
         }
      }, me.comboConfig);
      //para listConfig ver http://docs.sencha.com/extjs/4.1.3/#!/api/Ext.view.BoundList-cfg-tpl

      rendererDefault = function(value, metaData, record, rowIndex, colIndex, store, view) {
         var search = me.getCombo().getRawValue(), index;
         if (value != null) {
            if (search != null && search != "") {
               return value.replace(new RegExp(search, "gi"), function(str) {
                  return '<span class="uxsearchpanel-foundresalted">' + str + '</span>';
               });
            } else {
               return value;
            }
         } else {
            return "";
         }
      };
      if (me.colRenderer) {
         renderer = function(value, metaData, record, rowIndex, colIndex, store, view) {
            return me.colRenderer(rendererDefault(value, metaData, record, rowIndex, colIndex, store, view), metaData, record, rowIndex, colIndex, store, view);
         };
      } else {
         renderer = rendererDefault;
      };
      gridConfig = Ext.merge({
         xtype : "grid",
         anchor : "100%",
         store : me.store,
         fillColumn : false,
         hideHeaders : true,
         columns : [{
            flex : 1,
            dataIndex : me.displayField,
            menuDisabled : true,
            renderer : renderer
         }]
      }, me.gridConfig);

      me.items = [
      //{ style:"border:1px solid red", height:10},
      comboConfig,
      //{ style:"border:1px solid blue", height:10},
      {
         xtype : "component",
         autoEl : "div",
         cls : "sepHorizPanelColAgrup",
         height : 1
      },
      //{ style:"border:1px solid green", height:10},
      gridConfig];
      me.callParent();

   },

   getCombo : function() {
      return (this.combo == null ? this.combo = this.down("combo") : this.combo);
   },
   getGrid : function() {
      return (this.grid == null ? this.grid = this.down("grid") : this.grid);
   }
});
