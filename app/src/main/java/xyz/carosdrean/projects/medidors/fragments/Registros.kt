package xyz.carosdrean.projects.medidors.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_registros.view.*

import xyz.carosdrean.projects.medidors.R
import xyz.carosdrean.projects.medidors.pojo.Acciones
import xyz.carosdrean.projects.medidors.presenter.Auxiliar
import xyz.carosdrean.projects.medidors.presenter.DataGrafico
import xyz.carosdrean.projects.medidors.presenter.RegistroAcciones
import java.util.*


class Registros : Fragment() {

    private var v:View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_registros, container, false)

        actualizarCalendario(v!!)
        calendarioClick(v!!)

        v!!.agregar.setOnClickListener { agregarRegistro() }
        v!!.agregar2.setOnClickListener { agregarRegistro() }

        Glide.with(context!!).load(R.drawable.ic_estadisticas).into(v!!.ic_estadistica)
        Glide.with(context!!).load(R.drawable.ic_balanza).into(v!!.ic_registrodia)
        Glide.with(context!!).load(R.drawable.ic_estado).into(v!!.ic_comovoy)
        Glide.with(context!!).load(R.drawable.ic_balanza).into(v!!.ic_mes)
        Glide.with(context!!).load(R.drawable.registro).into(v!!.ic_portada_registros)

        graficos(v!!)

        return v
    }

    override fun onResume() {
        super.onResume()
        actualizarHoy(v!!)
    }

    fun graficos(v: View){
        val data = DataGrafico(context!!)
        data.dataGrafico(v.graph)
    }

    private fun actualizarCalendario(v: View){
        val calendario = v.calendarioR
        val fecha = Calendar.getInstance()
        calendario.setSelectedDate(fecha)
    }

    private fun agregarRegistro(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.content, Inicio()).commit()
    }

    fun calendarioClick(v: View){
        val registroAcciones = RegistroAcciones(context!!)
        val accion = Acciones()
        v.calendarioR.setOnDateChangedListener{widget, date, selected ->
            if(selected){
                var orado: String
                var leido: String
                val fechaSelect = (String.format("%02d",date.day)
                        + "/" + String.format("%02d",date.month + 1)
                        + "/" + date.year)
                try {
                    val registro = registroAcciones.actualizarRegistroHoy(accion, fechaSelect)
                    orado = registro[0]!!
                    leido = registro[1]!!
                }catch (e:Exception){
                    orado = "Sin registros"
                    leido = "Sin registros"
                }
                //alertRegistro("Registro del $fechaSelect", orado, leido)
                registroDia(orado, leido, "Registro del $fechaSelect")
            }
        }
    }

    fun registroDia(orado: String, leido: String, fecha: String){
        val args = Bundle()
        args.putString("orado", orado)
        args.putString("leido", leido)
        args.putString("fecha", fecha)
        val registro = RegistroDia()
        registro.arguments = args
        registro.show(fragmentManager, "Registro Dia")
    }

    fun actualizarHoy(v: View){
        val aux = Auxiliar()
        val acciones = Acciones()
        val registrarAcciones = RegistroAcciones(context!!)
        val registros = registrarAcciones.actualizarRegistroHoy(acciones, aux.obtenerFecha())
        if(registros[0] != null){
            v.tvOrado.text = registros[0]
            v.tvLeido.text = registros[1]
            sacarSituacion(registros[0]!!, v)
        }
    }

    fun sacarSituacion(tiempo: String, v:View) {
        val imagen: Int
        val textoSituacion: String
        if (tiempo != "Sin registros"){
            val hora = Integer.parseInt(tiempo.substring(0, 2))
            val minuto = Integer.parseInt(tiempo.substring(3, 5))

            when {
                hora > 1 -> {
                    textoSituacion = getString(R.string.verde)
                    imagen = R.drawable.verde
                }
                minuto > 30 -> {
                    textoSituacion = getString(R.string.amarillo)
                    imagen = R.drawable.amarillo
                }
                else -> {
                    textoSituacion = getString(R.string.rojo)
                    imagen = R.drawable.rojo
                }
            }
        }else{
            textoSituacion = getString(R.string.muy_rojo)
            imagen = R.drawable.rojo
        }
        v.situacion!!.text = textoSituacion
        Glide.with(context!!).load(imagen).into(v.estado)
    }

}
