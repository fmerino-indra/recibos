/**
 * Ux Plugin: TreeFunctions
 * ------------------------
 * Descripción: proporciona funcionalidad frecuentemente utilizada en TreePanels,
 * como añadir nodos, eliminar nodos, subir y bajar nodos en la lista,
 * cambiar el padre de los nodos, etc.
 */
Ext.define('iDynamicsFront.plugins.Tree', {
    extend: 'Ext.plugin.Abstract',
    alias: 'plugin.treefunctions',
    pluginId: 'TreeFunctions',


    /**
     * init
     * ----
     * Esta función será llamada al inicializar la vista. Debemos tener en cuenta que
     * los plugin funcionan como Singleton, es decir, no son instanciados.
     */
    init: function(treePanel) {
        var me = this;

        // Añadimos a la tabla los distintos métodos que nos servirán para realizar
        // las distintas modificaciones sobre el árbol.		
        treePanel.changeNodeParent = me.changeNodeParent;
        treePanel.moveNodeUp = me.moveNodeUp;
        treePanel.moveNodeDown = me.moveNodeDown;
        treePanel.deleteNodes = me.deleteNodes;
        treePanel.addNodes = me.addNodes;
        treePanel.addBrother = me.addBrother;
        // para ponerlo en modo lecttura, de forma que no aparece el menu contextual
        treePanel.menuHidden = me.pluginConfig.menuHidden || false;
        treePanel.readOnly = me.readOnly;

        treePanel.on("beforeselect", me.onBeforeSelect, me);

        // Estos son los nuevos eventos que se van a disparar desde el treePanel.
        // La función addEvents es opcional en ExtJS4, y está deprecated en ExtJS5, de manera
        // que sólo se ejecutará en ExtJS4, y aún así no tiene ningún efecto. De todas 
        // maneras, nos conviene tenerla aquí para saber qué eventos se van a disparar.

        if (Ext.getVersion().major < 5) // Sólo en v < ExtJS 5
            treePanel.addEvents({
	            'beforechangenodeparent': true, // Antes de cambiar el padre de un nodo.
	            // Al devolver false cancela el cambio.
	            // Argumentos: nuevo padre (nodo), nuevo hijo (nodo).

	            'changenodeparent': true, // Tras cambiar el padre de un nodo.
	            // Argumentos: nuevo padre (nodo), nuevo hijo (nodo).

	            'beforemovenode': true, // Antes de mover un nodo (en cualquier dirección).
	            // Al devolver false cancela el cambio.
	            // Argumentos: nodo a mover, dirección (true -> arriba, false -> abajo).

	            'movenode': true, // Tras mover un nodo (en cualquier dirección).
	            // Argumentos: nodo movido,  dirección (true -> arriba, false -> abajo).

	            'beforemovenodeup': true, // Antes de mover arriba un nodo.
	            // Al devolver false cancela el cambio.
	            // Argumentos: nodo a mover.

	            'movenodeup': true, // Tras mover arriba un nodo.
	            // Argumentos: nodo movido.

	            'beforemovenodedown': true, // Antes de mover abajo un nodo.
	            // Al devolver false cancela el cambio.
	            // Argumentos: nodo a mover.

	            'movenodedown': true, // Tras mover abajo un nodo.
	            // Argumentos: nodo movido.

	            'beforedeletenode': true, // Antes de borrar un nodo.
	            // Al devolver false cancela el cambio.
	            // Argumentos: nodo a borrar.

	            'deletenode': true, // Tras borrar un nodo.
	            // Argumentos: nodo borrado.

	            'beforeaddnode': true, // Antes de añadir un nodo.
	            // Al devolver false cancela el cambio.
	            // Argumentos: padre, hijo a añadir.

	            'addnode': true // Tras añadir un nodo.
	                // Argumentos: padre, hijo añadido.
        });

        // Habilitar toolbar (pendiente de hacer y revisar)
        if (me.pluginConfig.toolbarConfig) {
            // TODO
        }

        // Habilitar menú contextual
        if (me.pluginConfig.contextMenu) {
            treePanel.contextMenu = Ext.create('Ext.menu.Menu', {
                items: me.buildContextMenu(),
                buttonAlign: 'left',
                treePanel: treePanel
            });


            treePanel.on('itemcontextmenu', function(treeGrid, record, item, index, e, eOpts) {
            	if (this.menuHidden) {
            		e.preventDefault();
            		return;
            	}
                this.contextMenu.showAt(e.getXY());
                e.preventDefault();
            }, treePanel);
        }
    },

    /**
     * Desactiva el menu contextual
     * @return {[type]} [description]
     */
	readOnly: function (val) {
        this.menuHidden = val;
	},

    changeNodeParent: function(newParent, children) {
        var me = this,
            plugin = this.getPlugin('TreeFunctions'),
            pluginConfig = plugin.pluginConfig;

        if (!Ext.isArray(children))
            children = [children];

        // Para cada nodo
        for (var i = 0; i < children.length; i++) {
            var child = children[i],
                oldParent = child.parent;

            if (!me.fireEvent('beforechangenodeparent', newParent, child))
                continue;

            // Si el nodo tenía un padre (debería tenerlo siempre)
            if (oldParent) {
                // Lo eliminamos del padre viejo
                oldParent.remove(child);

                // Si el padre viejo ya no tiene hijos, ahora debería
                // ser leaf.
                if (!oldParent.childNodes.length)
                    oldParent.set("leaf", true);
            }

            newParent.appendChild(child);

            // Si se especifica expandOnChange, expandimos el nuevo
            // padre.
            if (pluginConfig.expandOnChange)
                newParent.expand();

            me.fireEvent('changenodeparent', newParent, child);
        }
    },

    moveNodeUp: function(children) {
        var me = this,
            plugin = this.getPlugin('TreeFunctions'),
            pluginConfig = plugin.pluginConfig;

        // En primer lugar, comprobemos si se ha especificado un atributo para ser
        // utilizado como ordenación.
        if (pluginConfig.sortParam) {
            // Este es el atributo del modelo por el cual ordenaremos.
            var sortParam = pluginConfig.sortParam,
                sortDirection = pluginConfig.sortDirection || 'ASC';

            // No queremos controlar que todos los nodos sean hijos del mismo padre, y
            // sería demasiado complejo el cambiar todos los nodos a la vez. Por eso,
            // si se pasa como argumento de la función un array, haremos que se llame
            // a la función para cada elemento.
            if (Ext.isArray(children)) {
                Ext.each(children, function(child) {
                    me.moveNodeUp(child);
                });
            } else {
                // Movemos "arriba" un nodo.
                // En primer lugar, obtenemos una referencia al padre del nodo, así
                // como el número que indica el orden de este nodo con respecto
                // a sus hermanos.
                var child = children,
                    parent = child.parentNode,
                    childOrder,
                    minDifference = -1,
                    previousNode = null;

                if (!me.fireEvent('beforemovenode', child, true) || !me.fireEvent('beforemovenodedown', child))
                    return;

                // Para evitar errores, normalizamos el orden.
                plugin.normalizeOrder(parent.childNodes, sortParam);
                childOrder = child.get(sortParam);

                // Ahora debemos iterar sobre los hijos del padre (hermanos del nodo).
                // Al estar moviendo el nodo arriba, lo que queremos hacer es lo
                // siguiente: calcular la diferencia de orden entre el hermano y el nodo
                // a mover hacia arriba. De esta manera, lo que buscamos es el nodo
                // inmediatamente anterior en la ordenación, sin necesitar que la orde-
                // nación sea continua [1,2,3,...]. Esto nos servirá para poder admitir
                // también ordenaciones un tanto "erráticas" [-10, 0, 99, null,...].
                // Una vez tenemos el nodo anterior al que queremos mover, invertimos
                // sus órdenes.

                Ext.each(parent.childNodes, function(sibling) {
                    var valueDifference = child.get(sortParam) - sibling.get(sortParam);
                    if (valueDifference > 0 && (minDifference < 0 || valueDifference < minDifference)) {
                        // Estamos tratando con un nodo anterior.
                        minDifference = valueDifference;
                        previousNode = sibling;
                    }
                });

                if (previousNode) {
                    // Si hemos encontrado el nodo inmediatamente anterior, invertimos
                    // el orden
                    child.set(sortParam, previousNode.get(sortParam));
                    previousNode.set(sortParam, childOrder);
                } else {
                    // Si no, es que no hay nodos anteriores a éste.
                }

                // Normalizamos los valores para que vayan de 1 a n.
                plugin.normalizeOrder(parent.childNodes, sortParam);
                me.store.sort(sortParam, sortDirection);

                me.fireEvent('movenode', child, true);
                me.fireEvent('movenodeup', child);
            }

        }

    },

    moveNodeDown: function(children) {
        var me = this,
            plugin = this.getPlugin('TreeFunctions'),
            pluginConfig = plugin.pluginConfig;

        // En primer lugar, comprobemos si se ha especificado un atributo para ser
        // utilizado como ordenación.
        if (pluginConfig.sortParam) {
            // Este es el atributo del modelo por el cual ordenaremos.
            var sortParam = pluginConfig.sortParam,
                sortDirection = pluginConfig.sortDirection || 'ASC';

            // No queremos controlar que todos los nodos sean hijos del mismo padre, y
            // sería demasiado complejo el cambiar todos los nodos a la vez. Por eso,
            // si se pasa como argumento de la función un array, haremos que se llame
            // a la función para cada elemento.
            if (Ext.isArray(children)) {
                Ext.each(children, function(child) {
                    me.moveNodeDown(child);
                });
            } else {
                // Movemos "arriba" un nodo.
                // En primer lugar, obtenemos una referencia al padre del nodo, así
                // como el número que indica el orden de este nodo con respecto
                // a sus hermanos.
                var child = children,
                    parent = child.parentNode,
                    childOrder,
                    minDifference = 1,
                    previousNode = null;

                if (!me.fireEvent('beforemovenode', child, false) || !me.fireEvent('beforemovenodeup', child))
                    return;

                // Para evitar errores, normalizamos el orden.				
                plugin.normalizeOrder(parent.childNodes, sortParam);
                childOrder = child.get(sortParam);

                // Ahora debemos iterar sobre los hijos del padre (hermanos del nodo).
                // Al estar moviendo el nodo abajo, lo que queremos hacer es lo
                // siguiente: calcular la diferencia de orden entre el hermano y el nodo
                // a mover hacia arriba. De esta manera, lo que buscamos es el nodo
                // inmediatamente posterior en la ordenación, sin necesitar que la orde-
                // nación sea continua [1,2,3,...]. Esto nos servirá para poder admitir
                // también ordenaciones un tanto "erráticas" [-10, 0, 99, null,...].
                // Una vez tenemos el nodo anterior al que queremos mover, invertimos
                // sus órdenes.

                Ext.each(parent.childNodes, function(sibling) {
                    var valueDifference = child.get(sortParam) - sibling.get(sortParam);
                    if (valueDifference < 0 && (minDifference > 0 || valueDifference > minDifference)) {
                        // Estamos tratando con un nodo anterior.
                        minDifference = valueDifference;
                        previousNode = sibling;
                    }
                });

                if (previousNode) {
                    // Si hemos encontrado el nodo inmediatamente anterior, invertimos
                    // el orden
                    child.set(sortParam, previousNode.get(sortParam));
                    previousNode.set(sortParam, childOrder);
                } else {
                    // Si no, es que no hay nodos anteriores a éste.
                }

                // Normalizamos los valores para que vayan de 1 a n.
                plugin.normalizeOrder(parent.childNodes, sortParam);
                me.store.sort(sortParam, sortDirection);

                me.fireEvent('movenode', child, false);
                me.fireEvent('movenodedown', child);
            }

        }
    },

    deleteNodes: function(children) {
        var me = this;
        // Actuamos de manera distinta en función de si el argumento es un
        // array o un elemento.
        if (Ext.isArray(children)) {
            Ext.each(children, function(child) {

                if (!me.fireEvent('beforedeletenode', child))
                    return;

                child.parentNode.removeChild(child);

                me.fireEvent('deletenode', child);
            });
        } else {

            if (!me.fireEvent('beforedeletenode'), children)
                return;

            children.parentNode.removeChild(children);

            me.fireEvent('deletenode', children);
        }
        me.fireEvent('afterdeletenode', children);
    },

    /**
     * [addNodes description]
     * @param {[type]} parent   [description]
     * @param {[type]} newNodes [description]
     */
    addNodes: function(parent, newNodes, newCfg) {
        var me = this,
            plugin = this.getPlugin('TreeFunctions'),
            pluginConfig = plugin.pluginConfig,
            defaultChildConfig = newCfg || { leaf: true };

        if (!newNodes) {
            if (!me.fireEvent('beforeaddnode', parent, defaultChildConfig))
                return;

            var newChild = parent.appendChild(defaultChildConfig);
            // Si se especifica expandOnChange, expandimos el padre.
            if (pluginConfig.expandOnChange)
                parent.expand();

            me.fireEvent('addnode', parent, newChild);
        } else {
            if (Ext.isArray(newNodes)) {
                Ext.each(newNodes, function(newNode) {
                    me.addNodes(newNode);
                });
            } else {
                if (!me.fireEvent('beforeaddnode', parent, newNodes))
                    return;

                var newNode = newNodes;
                parent.appendChild(newNode);
                // Si se especifica expandOnChange, expandimos el padre.
                if (pluginConfig.expandOnChange)
                    parent.expand();

                me.fireEvent('addnode', parent, newNodes);
            }
        }


    },

    /**
     * Crea un nodo al mismo nivel que el seleccionado. El numero de orden se obtiene a partir del ultimo hijo
     * @param {[type]} nodo [description]
     */
    addBrother: function(nodo) {
		var me = this,
            plugin = this.getPlugin('TreeFunctions'),
            pluginConfig = plugin.pluginConfig,
            defaultChildConfig = {
                leaf: true
            };

		var padre = nodo.parentNode;
		// TODO: y si no tiene hijos?
		var ultimoHijo = padre.lastChild.get(pluginConfig.sortParam);

 		defaultChildConfig = Ext.merge({orden : ultimoHijo + 1}, defaultChildConfig);

		me.addNodes(padre, null, defaultChildConfig);       
    },

    normalizeOrder: function(children, sortParam) {
    	var me = this;
            //plugin = this.getPlugin('TreeFunctions');
            
        //debugger
        // Ordenamos la lista de nodos por su sortParam.
        var sortBy = sortParam || me.pluginConfig.sortParam;
        children.sort(function(a, b) {
            var aOrder = a.get(sortBy);
            var bOrder = b.get(sortBy);

            return aOrder - bOrder;
        });

        // Ahora les asignamos un sortParam entre 1 y n.
        var currentOrder = 1;
        Ext.each(children, function(child) {
            child.set(sortBy, currentOrder);
            currentOrder++;
        });
    },

    buildContextMenu: function() {
        var me = this,
            menuConfig = me.pluginConfig.contextMenu,
            configuration = {
                moveNodeUp: true,
                moveNodeDown: true,
                addNodes: true,
                deleteNodes: true
            },
            menuItems = [];

        if (menuConfig === true) {

        } else {
            Ext.apply(configuration, menuConfig);
        }

        // Empezamos a construir las opciones del menú contextual.

        if (me.pluginConfig.sortParam) {

            if (configuration.moveNodeUp) {
                menuItems.push({
                    text: 'Subir',
                    action: 'moveup',
                    handler: me.contextMenuProxyHandler
                });
            }

            if (configuration.moveNodeDown) {
                menuItems.push({
                    text: 'Bajar',
                    action: 'movedown',
                    handler: me.contextMenuProxyHandler
                });
            }

        }

        if (configuration.deleteNodes) {
            menuItems.push({
                text: 'Eliminar elemento',
                action: 'deletenodes',
                handler: me.contextMenuProxyHandler
            });
        }

        // Anadir hijo
        if (configuration.addNodes) {
            menuItems.push({
                text: 'Añadir un nuevo elemento debajo',
                action: 'addnodes',
                handler: me.contextMenuProxyHandler
            });
        }
        // Añadir hermano
        if (configuration.addBrother) {
            menuItems.push({
                text: 'Añadir un nuevo elemento hermano',
                action: 'addbrother',
                handler: me.contextMenuProxyHandler
            });
        }
        return menuItems;
    },

    contextMenuProxyHandler: function(menuItem) {
        var treePanel = menuItem.up("menu").treePanel,
            selection = treePanel.getSelectionModel() ? treePanel.getSelectionModel().getSelection() : null;
           

        if (!selection || (selection && !selection.length))
            return;

        switch (menuItem.action) {
            case 'moveup':
                treePanel.moveNodeUp(selection);
                break;
            case 'movedown':
                treePanel.moveNodeDown(selection);
                break;
            case 'deletenodes':
                treePanel.deleteNodes(selection);
                break;
            case 'addnodes':
                Ext.each(selection, function(item) {
                    treePanel.addNodes(item);
                });
                break;
            case 'addbrother':
                treePanel.addBrother(selection[0]);
                break;
        }
    },


    // Funciones para controlar la selección / multiselección

    onBeforeSelect: function(selModel, record, index, eOpts){
           	
		if (!this.getCmp().multiSelect){
			//si no es multi seleccionable, no hago nada
			return;
		}
    	
   	    // No podemos dejar seleccionar filas de distintos padres.
        // En primer lugar, vemos si ya existe un elemento seleccionado.
        var selection = selModel.getSelection();

        // Si no hay nada seleccionado, y este es el primer elemento, dejamos seleccionar.
        if(!selection.length)
            return true;

        // Llegados a este punto, tenemos que comprobar que el padre de este elemento es
        // el mismo que el de los seleccionados.            

        var selectionRecord = selection[0];

        if(selectionRecord.parentNode !== record.parentNode){                
            return false;
        }

        return true;
    }

});
