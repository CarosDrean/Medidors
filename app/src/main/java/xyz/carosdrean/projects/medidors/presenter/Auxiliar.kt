package xyz.carosdrean.projects.medidors.presenter

import java.util.*

class Auxiliar {
    fun obtenerFecha():String {
        val calendario = GregorianCalendar()
        return formatearfecha (calendario.get(Calendar.DAY_OF_MONTH),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.YEAR))
    }

    fun formatearfecha(dia: Int, mes: Int, ano: Int): String{
        return (String.format("%02d", dia)
                + "/" + String.format("%02d", mes + 1)
                + "/" + ano)
    }
}