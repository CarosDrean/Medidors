package xyz.carosdrean.projects.medidors.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_sobre_mi.view.*

import xyz.carosdrean.projects.medidors.R


class SobreMi : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_sobre_mi, container, false)

        Glide.with(context!!).load(R.drawable.acercade).into(v.ic_portada_sobre)
        Glide.with(context!!).load(R.drawable.ic_frases).into(v.ic_descripcion)
        // Glide.with(context!!).load(R.drawable.ic_balanza).into(v.ic_acercade)
        Glide.with(context!!).load(R.drawable.ic_footer).into(v.footer)

        v.switchNotUno.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) saveAlarma("notificacion uno", "16:00") else saveAlarma("notificacion uno", "25:00")
        }
        v.switchNotDos.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) saveAlarma("notificacion dos", "23:00") else saveAlarma("notificacion dos", "25:00")
        }

        v.switchNotUno.isChecked = validar("notificacion uno") == "16:00"
        v.switchNotDos.isChecked = validar("notificacion dos") == "23:00"

        return v
    }

    private fun validar(campo: String): String {
        val sp = activity?.getSharedPreferences("config", Context.MODE_PRIVATE)
        val notUno = sp?.getString(campo, "25:00")
        return notUno!!
    }

    private fun saveAlarma(campo: String, valor: String) {
        val sp = activity?.getSharedPreferences("config", Context.MODE_PRIVATE)
        val editor = sp?.edit()
        editor?.putString(campo, valor)?.apply()
    }

}
