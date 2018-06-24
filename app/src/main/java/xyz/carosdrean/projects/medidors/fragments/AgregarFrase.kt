package xyz.carosdrean.projects.medidors.fragments


import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.*
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_agregar_frase.view.*

import xyz.carosdrean.projects.medidors.R

class AgregarFrase : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_frase, container, false)
        val texto = view.findViewById(R.id.frase_agregar) as EditText
        view.frase_agregar.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                dialog.window!!.decorView.setPadding(0, 0, 0, 0)
                if(texto.text.toString() != "") guardarFrase(texto.text.toString())
                dialog.dismiss()
            }
            false
        }
        texto.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                var d = view.nota.drawable
                d = DrawableCompat.wrap(d)
                DrawableCompat.setTint(d, ContextCompat.getColor(context!!, R.color.colorAccent))
            }
        }
        view.btn_agregar.setOnClickListener {
            if(texto.text.toString() != "") guardarFrase(texto.text.toString())
            dismiss()
        }

        dialog.window!!.decorView.setPadding(0, 0, 0, 700)
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        return view
    }

    fun guardarFrase(dato: String){
        /*val referencia = FirebaseDatabase.getInstance().reference
        val key = referencia.push().key
        referencia.child("frases").child(key).setValue(dato)*/
    }


}
