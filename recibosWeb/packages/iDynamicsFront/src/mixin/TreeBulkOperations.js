/**
 * Mixin que retorna un objeto con los nodos "sucios" de un treestore para enviarlas todas de una vez al servidor.
 * Un treestore puede realizar algo parecido con la propiedad batchActions, pero se envian una peticion de cada tipo 
 * con los nodos implicados. 
 * Con este mixin podemos enviar de una vez todo.
 */
Ext.define('iDynamicsFront.mixin.TreeBulkOperations', {


    getDirtyNodes: function() {

        var me = this,
            storeTree = me.getStore();

        //Obtengo solo el objeto data de cada modelo para no enviar informacion que no nos sirve de nada
        var arrayActualizadas = new Array();
        for (var i = 0; i < storeTree.getUpdatedRecords().length; i++) {
            arrayActualizadas.push(storeTree.getUpdatedRecords()[i].data);
        }

        var arrayNuevos = new Array();
        for (var i = 0; i < storeTree.getNewRecords().length; i++) {
            arrayNuevos.push(storeTree.getNewRecords()[i].data);
        }

        var arraysBorrados = new Array();
        for (var i = 0; i < storeTree.getRemovedRecords().length; i++) {
            arraysBorrados.push(storeTree.getRemovedRecords()[i].data);
        }

        return {
            updated: arrayActualizadas,
            created: arrayNuevos,
            deleted: arraysBorrados
        }

    }



});
