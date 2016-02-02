Ext.define('APP.view.MenuListCtrl', {
    extend: 'Ext.app.ViewController',
    alias : 'controller.menulist',

    onChangeCard: function (bt) {
        var viewer = bt.up('viewport').down('viewer'),
            viewportCtrl = bt.up('viewport').getController(),
            stompClient, level, rx, local;
        if (bt.index === 0) {
            stompClient = viewportCtrl.stompClient;
            level = viewportCtrl.level;
            rx = viewportCtrl.rx;
            local = viewportCtrl.local;
            rx ? SubSystemsService.getInitialLoad({level: viewportCtrl.getCookie('level')}) : local ? '' :  stompClient.send("/monitoring/nodes", {}, JSON.stringify({level: viewportCtrl.getCookie('level')}));
        }
        if (viewer.getLayout().getActiveItem() !== viewer.items.items[bt.index]) {
            viewer.getLayout().setActiveItem(bt.index);
        }
    },
    onClickUser:function(bt){
        var me=this, rx=bt.up('viewport').getController().rx;
        if(rx){
            Ext.Ajax.request({
                url: '/users/1',
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    console.dir(obj);
                }
            });
        }
    }
});
