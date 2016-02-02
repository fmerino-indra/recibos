/**
 * Objeto menu de la aplicacion.
 * Dependencias t() para i18n
 */
Ext.define('iDynamicsFront.menu.Menu', {
    extend: 'Ext.panel.Panel',
    xtype: 'app-menu',
    alias: 'widget.uxmenu',
    itemId: 'app-menu',
    reference: 'mainmenu',
    requires: ['iDynamicsFront.menu.MenuController'],
    //atributos configurables
    // Datos json del menu
    hash: null,
    height: 20,
    controller: 'uxmenu',
    // Preguntar a Juan ...
    xtypeMappings: null,
    controllerPermissions: {},

    //opciones de la toolbar
    items: null,


    initComponent: function() {
        var me = this,
            item, items, hash, newItems = [];

        me.defaults = {
            scale: me.scale
        };

        me.store.load({
            callback: function(records, operation, success) {
                me.buildMenu(records);
            }
        });

        me.callParent();
    },

    /**
    */
    buildMenu: function(records) {
        var me = this;


        // Buscamos la opción inicial (controlador inicial) de la app
        for (var i = 0; i < records.length; i++) {
            var currentRecord = records[i];

            me.controllerPermissions[currentRecord.get("name")] = currentRecord.get("permission");

            if ((currentRecord.get("initView")===true || currentRecord.get("initView")==="true") && me.visibilidadMenu(currentRecord.get('permission'))) {
                var firstMenuOptionName = currentRecord.get('name'),
                    firstMenuOptionIsPopup = currentRecord.get('isPopup');
            }
            if (currentRecord.get("items") && currentRecord.get("items").length > 0) {
                for (var j = 0; j < currentRecord.get("items").length; j++) {
                    var currentItem = currentRecord.get("items")[j];

                    if (currentItem.permission)
                        me.controllerPermissions[currentItem.name] = currentItem.permission;

                    if (currentItem.initView && me.visibilidadMenu(currentView.permission)) {
                        var firstMenuOptionName = currentItem.name,
                            firstMenuOptionIsPopup = currentItem.isPopup;
                    }
                }
            }
        }

        // Si no se ha enviado una opción inicial con initView...
        if (!firstMenuOptionName) {
            for (var i = 0; i < records.length; i++) {
                if (me.visibilidadMenu(records[i].get('permission'))) {
                    var firstMenuOptionName = records[i].get('name'),
                        firstMenuOptionIsPopup = records[i].data.isPopup;
                    break;
                }
            }
        }

        var menuItems = [];
        me.hash = {};

        //convertimos los datos en una hash y creamos los items del menu
        Ext.each(records, function(item) {

            if (me.visibilidadMenu(item.get('permission'))) {
                var itemsMenu = item.get('items');
                var iconCls;
                //SI SE NECESITA ICONO SE ESPECIFICA AQUI ASI --> = 'icon-' + item.data.name;
                me.hash[item.get('name')] = me.mayusculaPrimeraLetra(item.get('name')) + 'Controller';
                var subItems = me.obtenerMenuItems(itemsMenu);
                var hasSubmenu = false;
                if (subItems && subItems.length > 0) {
                    hasSubmenu = true;
                }

                menuItems[menuItems.length] = {
                    xtype: 'button',
                    text: t('menu.' + item.get('labelkeyi18n')), 
                    option: item.get('name'),
                    isPopup: item.get('isPopup'),
                    cls : item.get('name') == firstMenuOptionName ? 'x-btn-pressed' : '',
                    //cls : 'con-icono',
                    //iconCls : iconCls,
                    submenu: hasSubmenu,
                    menu: subItems,
                    handler: 'onMenuOptionSelected'
                    //handler: item.get('name')
                }
            }
        });

        me.xtypeMappings = me.hash;
        me.add(menuItems);
        // me.select(firstMenuOptionName, firstMenuOptionIsPopup);
        
        me.eventHandlers.select(me, firstMenuOptionName, firstMenuOptionIsPopup);

    },

    /**
    */
    visibilidadMenu: function(permission) {

        //miro si tengo el permiso
        if (!permission || permission === null || permission === "") {
            //Si la pantalla no tiene permiso, la muestro siempre
            return true;
        }
        // else if (AppConfig.hasPermissions(permission)) {
        // //si tiene permiso definido y lo tiene el usuario muestro
        // return true;
        // } 
        //oculto
        return false;
    },

    /**
     Calcula los items de los diferentes niveles del menu recursivamente
     @private
    */
    obtenerMenuItems: function(items) {
        var me = this,
            menuItems = null;
        if (items && items.length > 0 && items !== '') {
            menuItems = [];
            Ext.each(items, function(item) {
                var itemsMenu = null;

                if (me.visibilidadMenu(item.permission)) {
                    if (item.items) {
                        itemsMenu = item.items;
                    }
                    var iconCls;
                    //SI SE NECESITA ICONO SE ESPECIFICA AQUI ASI --> = 'icon-' + item.name;
                    // @jcfernandezc: Me he encontrado esto aquí y creo que es una errata.
                    //me.hash[item.name] = me.maysmayusculaPrimeraLetraPrimera(item.name) + 'Controller';
                    // @jcfernandezc: así que lo he sustituido por esto:
                    me.hash[item.name] = me.mayusculaPrimeraLetra(item.name) + 'Controller';

                    menuItems[menuItems.length] = {
                        text: t('menu.' + item.labelkeyi18n), 
                        plain: true,
                        option: item.name,
                        isPopup: item.isPopup,
                        //cls : 'menu-item',
                        //iconCls : iconCls,
                        handler: 'onMenuOptionSelected',
                        menu: me.obtenerMenuItems(itemsMenu)
                    };
                }

            });
        }

        return menuItems;
    },

    /**
     Convierte en mayusculas la primera letra de una cadena dada
    */
    mayusculaPrimeraLetra: function(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    },


    /* API */


    //TODO: pendiente abrir una opcion inicial
    eventHandlers: {
        select: function(tb, option, esPopup) {
            tb.fireEvent('selectMenu', option, esPopup);
        }   
    }



});
