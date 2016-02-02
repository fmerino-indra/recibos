/**
 * El plugin TreeViewDragDrop no permite soltar elementos dentro de elementos hoja (isLeaf === true). 
 * En este caso, sólo nos deja soltar el nodo antes (before) o después (after) pero no dentro (append).
 */
Ext.define('iDynamicsFront.tree.ViewDropZone',{
	override: 'Ext.tree.ViewDropZone',

    /**
     * @cfg {Boolean} allowLeafInserts
     * Esta nueva propiedad nos permitirá configurar si queremos que sea posible soltar un nodo
     * (o una selección de nodos) bajo un nodo hoja en modo "append" (insertándolos como hijos
     * del nodo hoja). El comportamiento estándar del Drag&Drop en árboles, al soltar una
     * selección bajo un nodo hoja sólo permite posicionar la selección antes o después, pero no
     * como hija de dicho nodo.
     */
    allowLeafInserts: false,

	getPosition: function(e, node) {
        var view = this.view,
            record = view.getRecord(node),
            y = e.getY(),
            // antes:
            // noAppend = record.isLeaf(),
            // <UX override>
            noAppend = this.allowLeafInserts ? false : record.isLeaf(),
            noBelow = false,
            region = Ext.fly(node).getRegion(),
            fragment;        

        // If we are dragging on top of the root node of the tree, we always want to append.
        if (record.isRoot()) {
            return 'append';
        }

        // Return 'append' if the node we are dragging on top of is not a leaf else return false.
        if (this.appendOnly) {
            return noAppend ? false : 'append';
        }

        if (!this.allowParentInserts) {
            noBelow = record.hasChildNodes() && record.isExpanded();
        }

        fragment = (region.bottom - region.top) / (noAppend ? 2 : 3);
        if (y >= region.top && y < (region.top + fragment)) {
            return 'before';
        }
        else if (!noBelow && (noAppend || (y >= (region.bottom - fragment) && y <= region.bottom))) {
            return 'after';
        }
        else {
            return 'append';
        }
    }
});