/**
 * Menu controller, 
 * Mejor un mixin?
 */
Ext.define('iDynamicsFront.menu.MenuController', {
	extend: 'Ext.app.ViewController',
    alias: 'controller.uxmenu',

	
	
    init: function () {
        var me = this;
        Logger.info('Initializing ' + me.self.getName() + ' listeners');
    },
    
    onMenuOptionSelected: function(btn) {
        var me = this,
            menu=me.getView();
            
        // quito la clase    x-btn-pressed de todos
        Ext.Array.each(menu.items.items, function(btn){btn.removeCls('x-btn-pressed');});
            
        if (!btn.submenu) {
	        menu.fireEvent('selectMenu', btn.option, btn.isPopup);  	
        }
    } 

})
;
