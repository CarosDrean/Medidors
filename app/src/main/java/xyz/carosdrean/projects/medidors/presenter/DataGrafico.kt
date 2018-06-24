package xyz.carosdrean.projects.medidors.presenter

import android.content.Context
import android.view.View
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import xyz.carosdrean.projects.medidors.R
import xyz.carosdrean.projects.medidors.pojo.Acciones
import java.util.*

class DataGrafico (private val context: Context){

    private var oraciones = arrayOfNulls<Double>(7)
    private var lecturas= arrayOfNulls<Double>(7)

    private fun max(): Double{
        var mayor = 100.0
        for (i in oraciones.indices){
            if(oraciones[i]!! > mayor)mayor = oraciones[i]!!
            if(lecturas[i]!! > mayor)mayor = lecturas[i]!!
        }
        return mayor
    }

    private fun datos(grafi: GraphView){
        val graf = grafi

        graf.removeAllSeries()
        graf.gridLabelRenderer.verticalAxisTitle = "Minutos"
        graf.gridLabelRenderer.horizontalAxisTitle = "Ultimos 7 dias"
        graf.viewport.isXAxisBoundsManual = true
        graf.viewport.setMaxX(6.5)
        graf.viewport.isYAxisBoundsManual = true
        graf.viewport.setMaxY(max() + 5)
        val series = LineGraphSeries(arrayOf(
                DataPoint(0.0, oraciones[6]!!.toDouble()),
                DataPoint(1.0, oraciones[5]!!.toDouble()),
                DataPoint(2.0, oraciones[4]!!.toDouble()),
                DataPoint(3.0, oraciones[3]!!.toDouble()),
                DataPoint(4.0, oraciones[2]!!.toDouble()),
                DataPoint(5.0, oraciones[1]!!.toDouble()),
                DataPoint(6.0, oraciones[0]!!.toDouble())))
        graf.addSeries(series)
        val series2 = LineGraphSeries(arrayOf(
                DataPoint(0.0, lecturas[6]!!.toDouble()),
                DataPoint(1.0, lecturas[5]!!.toDouble()),
                DataPoint(2.0, lecturas[4]!!.toDouble()),
                DataPoint(3.0, lecturas[3]!!.toDouble()),
                DataPoint(4.0, lecturas[2]!!.toDouble()),
                DataPoint(5.0, lecturas[1]!!.toDouble()),
                DataPoint(6.0, lecturas[0]!!.toDouble())))
        graf.addSeries(series2)
        val limite = LineGraphSeries(arrayOf(
                DataPoint(0.0, 60.0),
                DataPoint(6.0, 60.0)))
        graf.addSeries(limite)

        limite.color = context.resources.getColor(R.color.rojo)
        limite.title = "Minimo  "
        series.title = "Oracion  "
        series.color = context.resources.getColor(R.color.colorAccent)
        series2.title = "Lectura  "
        graf.legendRenderer.isVisible = true
        graf.legendRenderer.align = LegendRenderer.LegendAlign.TOP
    }

    private fun datosGrafico(registros: Array<String?>): Array<Double?>{
        var hora: Int
        var minuto: Int
        var segundo : Int
        val datosGraf = arrayOfNulls<Double>(7)
        for (i in registros.indices){
            hora = Integer.parseInt(registros[i]!!.substring(0, 2))
            minuto = Integer.parseInt(registros[i]!!.substring(3, 5)) + (hora * 60)
            segundo = Integer.parseInt(registros[i]!!.substring(6))
            if(segundo > 60) minuto += (segundo / 60)
            datosGraf[i] = minuto.toDouble()
        }
        return datosGraf
    }

    fun dataGrafico(graf: GraphView){
        val dias = ultimosDias()
        val acciones = Acciones()
        val registrarAcciones = RegistroAcciones(context!!)
        val oracion = arrayOfNulls<String>(7)
        val lectura = arrayOfNulls<String>(7)

        for (i in dias.indices){
            val registros = registrarAcciones.actualizarRegistroHoy(acciones, dias[i]!!)
            if(registros[0] != null){
                if(registros[0] == "Sin registros" || registros[0] == ""){
                    registros[0] = "00:00:00"
                    registros[1] = "00:00:00"
                }
                oracion[i] = registros[0]
                lectura[i] = registros[1]
            }
        }
        oraciones = datosGrafico(oracion)
        lecturas = datosGrafico(lectura)
        datos(graf)
    }

    private fun ultimosDias(): Array<String?>{
        val aux = Auxiliar()
        val calendario = GregorianCalendar()
        val dias = arrayOfNulls<String>(7)
        for (i in dias.indices){
            dias[i] = aux.formatearfecha(calendario.get(Calendar.DAY_OF_MONTH),
                    calendario.get(Calendar.MONTH), calendario.get(Calendar.YEAR))
            calendario.add(Calendar.DATE, -1)
        }
        return dias
    }
}