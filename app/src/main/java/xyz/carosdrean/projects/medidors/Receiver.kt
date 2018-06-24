package xyz.carosdrean.projects.medidors

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import xyz.carosdrean.projects.medidors.pojo.Acciones
import xyz.carosdrean.projects.medidors.presenter.Auxiliar
import xyz.carosdrean.projects.medidors.presenter.RegistroAcciones
import java.util.*

class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, Service::class.java)
        context!!.startService(i)
        val calendario = Calendar.getInstance()
        val hora = String.format("%02d", calendario.get(Calendar.HOUR_OF_DAY))
        val min = String.format("%02d", calendario.get(Calendar.MINUTE))
        val horaSistema = "$hora:$min"
        if (horaSistema == "16:00" || horaSistema == "23:00") {
            notification(context, actualizarHoy(context))
        }
    }

    private fun notification(contexto: Context, t: String) {
        val notificationIntent = Intent(contexto, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val contentIntent = PendingIntent.getActivity(contexto, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(contexto)
        builder.setTicker("")
                .setFullScreenIntent(contentIntent, true)
                .setContentTitle("Situacion ")
                .setContentText(t)
                .setContentInfo("Info")
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(contexto.resources, R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_stat_recurso_34x)
                .setSound(defaultSound)
                .addAction(android.R.drawable.ic_menu_send, "ORAR", contentIntent)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(t)
                        .setBigContentTitle("Medidor Espiritual")
                        .setSummaryText("Tu Situacion"))
        val notificacion = builder.build()

        val notificationManager = contexto.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val ID = 1
        notificationManager.notify(ID, notificacion)
    }

    fun actualizarHoy(contexto: Context): String{
        val aux = Auxiliar()
        val acciones = Acciones()
        val registrarAcciones = RegistroAcciones(contexto)
        val registros = registrarAcciones.actualizarRegistroHoy(acciones, aux.obtenerFecha())
        return if(registros[0] != null){
            sacarSituacion(contexto, registros[0]!!)
        } else ""
    }

    fun sacarSituacion(contexto: Context, tiempo: String): String {
        return if (tiempo != "Sin registros"){
            val hora = Integer.parseInt(tiempo.substring(0, 2))
            val minuto = Integer.parseInt(tiempo.substring(3, 5))

            when {
                hora > 1 -> contexto.getString(R.string.verde)
                minuto > 30 -> contexto.getString(R.string.amarillo)
                else -> contexto.getString(R.string.rojo)
            }
        }else{
            contexto.getString(R.string.muy_rojo)
        }
    }

    companion object {
        const val REQUEST_CODE = 12345
    }
}