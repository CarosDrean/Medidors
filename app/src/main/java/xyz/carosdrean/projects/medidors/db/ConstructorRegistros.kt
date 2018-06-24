package xyz.carosdrean.projects.medidors.db

import android.content.ContentValues
import android.content.Context
import xyz.carosdrean.projects.medidors.pojo.Acciones

class ConstructorRegistros(private val context: Context) {
    fun obtenerAcciones(accion: Acciones, fecha: String): Acciones {
        val db = BaseDatos(context)
        return db.obtenerRegistrfos(accion, fecha)
    }

    fun obtenerNombre(): String{
        val db = BaseDatos(context)
        return db.recuperarNombre()
    }

    fun ingresarRegistro(acciones: Acciones) {
        val db = BaseDatos(context)
        insertarRegistrosAcciones(db, acciones)
    }

    private fun insertarRegistrosAcciones(db: BaseDatos, accion: Acciones) {
        val contentValues = ContentValues()
        contentValues.put(ConstantesBaseDatos.FECHA, accion.fecha)
        contentValues.put(ConstantesBaseDatos.TIEMPO_ORADO, accion.gettOrado())
        contentValues.put(ConstantesBaseDatos.TIEMPO_LEIDO, accion.gettLeido())
        db.ingresarRegistro(contentValues)
    }

    fun ingresarNombre(nombre: String){
        val db = BaseDatos(context)
        insertarNombre(db, nombre)
    }

    fun insertarNombre(db: BaseDatos, nombre: String){
        val contentValues = ContentValues()
        contentValues.put(ConstantesBaseDatos.NOMBRE, nombre)
        db.ingresarNombre(contentValues)
    }
}