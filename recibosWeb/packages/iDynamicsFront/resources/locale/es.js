
        //TODO: prefijo para reconocer estas propiedades cuando las vemos en la aplicacion?
        var i18ntextsA = {
        	
        	/*
        	 *
        	 * INFORMACION GENERAL
        	 * 
        	 * */
        	
            date:   {
            	grid:     'd.m.Y',
            	gridHora: 'd.m.Y H:i',
                fullDate: 'M d Y',
                date:     'date("M d")',
                year:     'date("Y")',
                time:     'date("g:i a")'
            },
            general: {
                comments:         'Comments',
                createNew:        'Create new',
                name:             'iDynamicsFront',
                title:            'Arq. front Extjs CC',
                settings:         'Preferences',
                dateTimeSettings: 'Date/Time settings',
                subscription:     'Subscription',
                accounting:       'Accounting',
                contexts:         'Contexts',
                auto:             'Auto',
                sync:             'Sync',
                "delete":           'Delete'
                
            },

            errors: {
                unexpected:             'Error when processing request',
                currentPasswordInvalid: 'Current password is not valid'
            },

            tooltips: {
                closeWindow:       'Closes the window',
                "delete":            'Delete',
                createProject:     'Add new project',
                deleteProject:     'Delete project',
                sortingDropHint:   'Move to its new position'
            },

            dialogs: {
                deleteUser:    {
                    notAllowedTitle:      'Operation not completed',
                    defaultCantBeDeleted: "The default user can't be deleted!",
                    title:                'Delete Area',
                    question:             'Are you sure you want to delete this Area and all its content?'
                },
                deleteRol: {
                    title:    'Rol/s to delete?',
                    question: 'Are you sure you want to delete selected Rol(s) and all its content?'
                }
            },

            login: {
                title:         'Login',
                email:         'email',
                password:      'password',
                resetPassword: 'Forgot your password?',
                unauthorized:  'Invalid login (user not registered or wrong password). Please, try again',
                capslock:      'Capslock is enabled. You may enter the wrong passowrd.'
            },

            buttons: {
                enter:           'Enter',
                cancel:          'Cancel',
                close:           'Close',
                change:          'Change',
                save:            'Save',
                enable:          'Enable',
                disable:         'Disable',
                complete:        'Complete',
                focus:           'Focus',
                "delete":          'Delete',
                createNew:       'Create new...',
                exportData:      'Export data',
                exportToXML:     'Export data to XML',
                exportToExcel:   'Export data to Excel',
                resetPassword:   'Reset password...',
                deleteAccount:   'Delete account...',
                upgrade:         'Upgrade...',
                addAction:       'Add action',
                actionToProject: 'Convert into project',
                groupActions:    'Group',
                ungroupActions:  'Ungroup',
                aceptar:		 'Aceptar',
                cancelar:		 'Cancelar'
            },

			commons : {
				button: {
					buscar : 'Buscar', 
					limpiar : 'Limpiar',
                    acceptText: 'Aceptar',
                    cancelText: 'Cancelar',
                    addText: 'Añadir',
                    closeText: 'Cerrar'
				},
                msgs : {
                    operacionCorrecta : 'Operacion realizada correctamente'
                },
				sinResultados:     'No se han encontrado datos.',
				searchForm : { 
					contadores: 'Contadores',
					busquedaAvanzada: 'Avanzada',
					configuracion: 'Configuración',
					agruparPor : 'Agrupar por...',
					ordenarPor : 'Ordenar por...'
				}
			},


            connection: {
                status: {
                    '403': 'No está autorizado (error: 403). Contacte con el administrador.',
                    '500': 'Se ha producido un error en el servicio. Contacte con el administrador.',
                    '404': 'No se ha encontrato el recurso. Contacte con el administrador.',
                    '401': 'No está autorizado (error: 401). Contacte con el administrador.'
                }
            },
            validation: {
                required : 'Este campo es obligatorio'
            }
        }
;



