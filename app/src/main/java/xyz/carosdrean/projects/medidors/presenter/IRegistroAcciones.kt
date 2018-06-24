package xyz.carosdrean.projects.medidors.presenter

import xyz.carosdrean.projects.medidors.pojo.Acciones

interface IRegistroAcciones {
    fun ingresarRegistros(acciones: Acciones)
    fun actualizarRegistroHoy(accion: Acciones, fecha: String): Array<String?>
    fun ingresarNombre(nombre: String)
    fun obtenerNombre(): String
}