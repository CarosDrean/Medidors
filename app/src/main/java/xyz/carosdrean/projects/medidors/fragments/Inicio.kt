package xyz.carosdrean.projects.medidors.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.Model
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.alert_tiempo.view.*
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.fragment_inicio.view.*
import xyz.carosdrean.projects.medidors.Frases

import xyz.carosdrean.projects.medidors.R
import xyz.carosdrean.projects.medidors.pojo.Acciones
import xyz.carosdrean.projects.medidors.pojo.Frase
import xyz.carosdrean.projects.medidors.presenter.Auxiliar
import xyz.carosdrean.projects.medidors.presenter.DataGrafico
import xyz.carosdrean.projects.medidors.presenter.RegistroAcciones


class Inicio : Fragment() {

    var pojo: Acciones? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_inicio, container, false)
        swichs(v.switchOrar, 1, v)
        swichs(v.switchLeer, 2, v)

        v.ver_registro.setOnClickListener { verRegistro() }
        v.agregar_frase.setOnClickListener { agregarFrase() }
        v.card_agregar.setOnClickListener { verFrases() }

        Glide.with(context!!).load(R.drawable.portada).into(v.ic_portada)
        Glide.with(context!!).load(R.drawable.ic_frases).into(v.ic_frases)
        Glide.with(context!!).load(R.drawable.ic_balanza).into(v.ic_comenzar)
        Glide.with(context!!).load(R.drawable.ic_estadisticas).into(v.ic_estadistica)

        graficos(v)

        fraseInicial(v)

        return v
    }

    private fun swichs(accion: SwitchCompat, posicion: Int, v: View){
        accion.setOnCheckedChangeListener { compoundButton, b ->
            if(b)alertTiempo(accion, posicion, v)
        }
    }

    private fun fraseInicial(v: View) {
        val referencia = FirebaseDatabase.getInstance().reference.child("frases").limitToLast(1)
        val frasePrincipal = v.frase_principal
        referencia.addChildEventListener(object : ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val frase = Frase(p0.child("texto").value.toString(), p0.child("id").value.toString())
                if(frase.id != null) {
                    frasePrincipal.text = frase.texto
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    private fun alertTiempo(accion: SwitchCompat, posicion: Int, view: View){
        val aux = Auxiliar()
        val registrarAcciones = RegistroAcciones(context!!)
        val tiempoCero = "00:00:00"
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
        val inflater: LayoutInflater = activity!!.layoutInflater
        val v: View = inflater.inflate(R.layout.alert_tiempo, null)
        builder.setView(v).setCancelable(false)
        val alert = builder.create()
        val apagar: SwitchCompat = v.switchTerminar

        val cronometro = v.cronometro
        cronometro.base = SystemClock.elapsedRealtime()
        cronometro.start()

        apagar.isChecked = true
        apagar.setOnCheckedChangeListener { compoundButton, b ->
            if(!b){
                cronometro.stop()
                alert.dismiss()
                accion.isChecked = false
                var hora = cronometro.text.toString()
                if(hora.length == 5)hora = "00:$hora"
                if(hora.length == 7)hora = "0$hora"
                pojo = when(posicion){
                    1 ->{
                        Acciones(aux.obtenerFecha(), hora, tiempoCero)
                    }
                    else -> Acciones(aux.obtenerFecha(), tiempoCero, hora)
                }
                registrarAcciones.ingresarRegistros(pojo!!)

                graficos(view)
            }
        }
        alert.show()
    }

    fun graficos(v: View){
        val data = DataGrafico(context!!)
        data.dataGrafico(v.graph)
    }

    fun verRegistro(){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.content, Registros()).commit()
    }

    fun verFrases(){
        val i = Intent(context, Frases::class.java)
        startActivity(i)
    }

    fun agregarFrase(){
        val agregar = AgregarFrase()
        agregar.show(fragmentManager, "Agregar Frase")
    }


}
