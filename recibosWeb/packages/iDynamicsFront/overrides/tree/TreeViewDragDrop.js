Ext.define('iDynamicsFront.tree.TreeViewDragDrop',{
	override: 'Ext.tree.plugin.TreeViewDragDrop',

    /**
     * @cfg {Boolean} allowLeafInserts
     * Ver overrides\tree\ViewDropZone.js (Ext.overrides.tree.ViewDropZone)  
     */
    allowLeafInserts: false,

    onViewRender : function(view) {
        var me = this,
            scrollEl;

        if (me.enableDrag) {
            if (me.containerScroll) {
                scrollEl = view.getEl();
            }
            me.dragZone = new Ext.tree.ViewDragZone(Ext.apply({
                view: view,
                ddGroup: me.dragGroup || me.ddGroup,
                dragText: me.dragText,
                displayField: me.displayField,
                repairHighlightColor: me.nodeHighlightColor,
                repairHighlight: me.nodeHighlightOnRepair,
                scrollEl: scrollEl
            }, me.dragZone));
        }

        if (me.enableDrop) {
            me.dropZone = new Ext.tree.ViewDropZone(Ext.apply({
                view: view,
                ddGroup: me.dropGroup || me.ddGroup,
                allowContainerDrops: me.allowContainerDrosp,
                appendOnly: me.appendOnly,
                allowParentInserts: me.allowParentInserts,
                expandDelay: me.expandDelay,
                dropHighlightColor: me.nodeHighlightColor,
                dropHighlight: me.nodeHighlightOnDrop,
                sortOnDrop: me.sortOnDrop,
                containerScroll: me.containerScroll,
                // <UX override>
                // Pasamos el allowLeafInserts desde la configuración del
                // plugin a la configuración del ViewDropZone para permitir
                // o no "soltar" nodos en nodos hoja con "append".
                allowLeafInserts: me.allowLeafInserts
            }, me.dropZone));
        }
    }
    

});