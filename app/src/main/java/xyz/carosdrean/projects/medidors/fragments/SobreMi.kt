package xyz.carosdrean.projects.medidors.fragments


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
        Glide.with(context!!).load(R.drawable.ic_balanza).into(v.ic_acercade)
        Glide.with(context!!).load(R.drawable.ic_footer).into(v.footer)

        return v
    }


}
