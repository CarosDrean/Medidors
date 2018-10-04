package xyz.carosdrean.projects.medidors

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_frases.*
import xyz.carosdrean.projects.medidors.adapter.FraseViewholder
import xyz.carosdrean.projects.medidors.pojo.Frase

class Frases : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frases)

        val toolbar = toolbar_frases
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Frases"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_close)

        obtenerFrases()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun obtenerFrases() {
        val referencia = FirebaseDatabase.getInstance().reference.child("frases")
        val recycler = lista_frases
        recycler.setHasFixedSize(true)
        val mlManager = LinearLayoutManager(this)
        // esto es para invertir el layout manager
        mlManager.reverseLayout = true
        mlManager.stackFromEnd = true
        recycler.layoutManager = mlManager

        val options = FirebaseRecyclerOptions.Builder<Frase>().setQuery(referencia.orderByKey(), Frase::class.java).build()

        val adapter = object : FirebaseRecyclerAdapter<Frase, FraseViewholder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FraseViewholder {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_frase, parent, false)
                return FraseViewholder(v)
            }

            override fun onBindViewHolder(holder: FraseViewholder, position: Int, model: Frase) {
                holder.setFrase(model.texto)
                holder.cerrar.setOnClickListener {
                    referencia.child(model.id).removeValue()
                }
            }

        }
        adapter.startListening()
        recycler.adapter = adapter

    }
}
