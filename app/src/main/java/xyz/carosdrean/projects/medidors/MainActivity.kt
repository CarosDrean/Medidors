package xyz.carosdrean.projects.medidors

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import xyz.carosdrean.projects.medidors.fragments.Inicio
import xyz.carosdrean.projects.medidors.fragments.Registros
import xyz.carosdrean.projects.medidors.fragments.SobreMi

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_inicio -> {
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.content, Inicio()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_registros -> {
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.content, Registros()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sobre -> {
                transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.content, SobreMi()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content, Inicio()).commit()

        servicio()
    }

    private fun servicio(){
        val i = Intent(this, Receiver::class.java)
        val pi = PendingIntent.getBroadcast(this, Receiver.REQUEST_CODE, i, PendingIntent.FLAG_UPDATE_CURRENT)
        val t = System.currentTimeMillis()
        val interval = 1 * 3 * 1000
        val alarm = this.getSystemService(android.content.Context.ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, t, interval.toLong(), pi)
    }
}
