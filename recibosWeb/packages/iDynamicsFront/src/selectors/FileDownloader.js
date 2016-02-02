Ext.define('iDynamicsFront.selectors.FileDownloader', {
    extend: 'Ext.Component',
    alias: 'widget.filedownloader',
    autoEl: {
        tag: 'iframe', 
        cls: 'x-hidden', 
        src: Ext.SSL_SECURE_URL
    },
    load: function(config){
        var e = this.getEl();
        e.dom.src = config.url + 
            (config.params ? '?' + Ext.urlEncode(config.params) : '');
        e.dom.onload = function() {
            if(e.dom.contentDocument.body.childNodes[0].wholeText == '404') {
                Ext.Msg.show({
                    title: 'Attachment missing',
                    msg: 'The document you are after can not be found on the server.',
                    buttons: Ext.Msg.OK,
                    icon: Ext.MessageBox.ERROR   
                })
            }
        }
    }
});