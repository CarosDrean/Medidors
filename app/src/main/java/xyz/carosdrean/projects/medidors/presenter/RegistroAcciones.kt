package xyz.carosdrean.projects.medidors.presenter

import android.content.Context
import xyz.carosdrean.projects.medidors.db.ConstructorRegistros
import xyz.carosdrean.projects.medidors.pojo.Acciones

class RegistroAcciones (private val context: Context): IRegistroAcciones {

    private var registros: ConstructorRegistros? = null

    override fun ingresarRegistros(acciones: Acciones) {
        registros = ConstructorRegistros(context)
        registros!!.ingresarRegistro(acciones)
    }

    override fun actualizarRegistroHoy(accion: Acciones, fecha: String): Array<String?> {
        registros = ConstructorRegistros(context)
        val tiempos = arrayOfNulls<String>(3)
        try {
            val accions = registros!!.obtenerAcciones(accion, fecha)
            tiempos[0] = accions.gettOrado()
            tiempos[1] = accions.gettLeido()
            if(tiempos[0] == ""){
                tiempos[0] = "Sin registros"
                tiempos[1] = "Sin registros"
            }
        } catch (e: Exception) {
            tiempos[0] = "Sin registros"
            tiempos[1] = "Sin registros"
        }

        return tiempos
    }

    override fun ingresarNombre(nombre: String) {
        registros = ConstructorRegistros(context)
        registros!!.ingresarNombre(nombre)
    }

    override fun obtenerNombre(): String {
        registros = ConstructorRegistros(context)
        return try {
            registros!!.obtenerNombre()
        }catch (e:Exception){
            "Usuario"
        }
    }
}