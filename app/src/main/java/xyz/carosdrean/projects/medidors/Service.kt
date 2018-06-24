package xyz.carosdrean.projects.medidors

import android.app.IntentService
import android.content.Intent
import android.util.Log

class Service : IntentService("Medidor") {

    override fun onHandleIntent(intent: Intent?) {
        Log.i("Service Medidor", "Servicio Ejecutandose")
    }
}