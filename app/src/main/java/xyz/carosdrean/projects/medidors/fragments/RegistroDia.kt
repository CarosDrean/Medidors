package xyz.carosdrean.projects.medidors.fragments


import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_registro_dia.view.*

import xyz.carosdrean.projects.medidors.R


class RegistroDia : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_registro_dia, container, false)
        Glide.with(context!!).load(R.drawable.ic_balanza).into(v.ic_tcontando)
        v.registroR.text = arguments!!.getString("fecha")
        v.tvrOrado.text = arguments!!.getString("orado")
        v.tvrLeido.text = arguments!!.getString("leido")
        return v
    }


}
