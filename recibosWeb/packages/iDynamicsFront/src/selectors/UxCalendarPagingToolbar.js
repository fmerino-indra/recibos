Ext.define('iDynamicsFront.selectors.UxCalendarPagingToolbar', {
   extend : 'Ext.toolbar.Paging',
   alias : 'widget.uxcalendarpagingtoolbar',
   componentCls : 'ux-calendarpagingtoolbar',
    
   initComponent : function(){
      var me=this;
      me.callParent();
      me.addEvents("userInteractionLoadCallback");
   },

   // private
   onPagingKeyDown : function(field, e){
        var me = this,
            k = e.getKey(),
            pageData = me.getPageData(),
            increment = e.shiftKey ? 10 : 1,
            pageNum;

        if (k == e.RETURN) {
            e.stopEvent();
            pageNum = me.readPageFromInput(pageData);
            if (pageNum !== false) {
                pageNum = Math.min(Math.max(1, pageNum), pageData.pageCount);
                if(me.fireEvent('beforechange', me, pageNum) !== false){
                    //me.store.loadPage(pageNum); //EDITADO
                    me.store.loadPage(pageNum, { callback : function(records, operation, success){me.fireEvent('userInteractionLoadCallback', me, records, operation, success)} });
                }
            }
        } else if (k == e.HOME || k == e.END) {
            e.stopEvent();
            pageNum = k == e.HOME ? 1 : pageData.pageCount;
            field.setValue(pageNum);
        } else if (k == e.UP || k == e.PAGE_UP || k == e.DOWN || k == e.PAGE_DOWN) {
            e.stopEvent();
            pageNum = me.readPageFromInput(pageData);
            if (pageNum) {
                if (k == e.DOWN || k == e.PAGE_DOWN) {
                    increment *= -1;
                }
                pageNum += increment;
                if (pageNum >= 1 && pageNum <= pageData.pageCount) {
                    field.setValue(pageNum);
                }
            }
        }
    },
    

    moveFirst : function(){
        if (this.fireEvent('beforechange', this, 1) !== false){
            //this.store.loadPage(1);//EDITADO
            this.store.loadPage(1, { callback : function(records, operation, success){me.fireEvent('userInteractionLoadCallback', me, records, operation, success)} });
        }
    },


    movePrevious : function(){
        var me = this,
            prev = me.store.currentPage - 1;

        if (prev > 0) {
            if (me.fireEvent('beforechange', me, prev) !== false) {
                //me.store.previousPage(); //EDITADO
                me.store.previousPage({ callback : function(records, operation, success){me.fireEvent('userInteractionLoadCallback', me, records, operation, success)} });
            }
        }
    },


    moveNext : function(){
        var me = this,
            total = me.getPageData().pageCount,
            next = me.store.currentPage + 1;

        if (next <= total) {
            if (me.fireEvent('beforechange', me, next) !== false) {
                //me.store.nextPage();//EDITADO
                me.store.nextPage({ callback : function(records, operation, success){me.fireEvent('userInteractionLoadCallback', me, records, operation, success)} });
            }
        }
    },


    moveLast : function(){
        var me = this,
            last = me.getPageData().pageCount;

        if (me.fireEvent('beforechange', me, last) !== false) {
            //me.store.loadPage(last);//EDITADO
            me.store.loadPage(last, { callback : function(records, operation, success){me.fireEvent('userInteractionLoadCallback', me, records, operation, success)} });
        }
    },


    doRefresh : function(){
        var me = this,
            current = me.store.currentPage;

        if (me.fireEvent('beforechange', me, current) !== false) {
            //me.store.loadPage(current);//EDITADO
            me.store.loadPage(current, { callback : function(records, operation, success){me.fireEvent('userInteractionLoadCallback', me, records, operation, success)} });
        }
    }
   

});



