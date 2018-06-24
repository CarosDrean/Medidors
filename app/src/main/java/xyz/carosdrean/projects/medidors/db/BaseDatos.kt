package xyz.carosdrean.projects.medidors.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import xyz.carosdrean.projects.medidors.pojo.Acciones

class BaseDatos(private val context: Context): SQLiteOpenHelper(context, ConstantesBaseDatos.DATABASE_NAME,
        null, ConstantesBaseDatos.DATABASE_VERSION) {

    private var horaO = 0
    private var horaL = 0
    private var minO = 0
    private var minL = 0
    private var segO = 0
    private var segL = 0

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreteTable: String = ("CREATE TABLE " + ConstantesBaseDatos.TABLE_NAME + " ("
                + ConstantesBaseDatos.ID_REGISTRO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ConstantesBaseDatos.FECHA + " TEXT, "
                + ConstantesBaseDatos.TIEMPO_ORADO + " TEXT, "
                + ConstantesBaseDatos.TIEMPO_LEIDO + " TEXT "
                + ")")

        val queryCreteTableNombre: String = ("CREATE TABLE " + ConstantesBaseDatos.TABLE_NAME_DOS + " ("
                + ConstantesBaseDatos.NOMBRE + " TEXT "
                + ")")

        db?.execSQL(queryCreteTableNombre)
        db?.execSQL(queryCreteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXIST " + ConstantesBaseDatos.TABLE_NAME)
        db?.execSQL("DROP TABLE IF EXIST " + ConstantesBaseDatos.TABLE_NAME_DOS)
        onCreate(db)
    }

    fun obtenerRegistrfos(accion: Acciones, fecha: String): Acciones {
        var tiempoOracion = ""
        var tiempoLectura = ""
        val query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_NAME + " WHERE " + ConstantesBaseDatos.FECHA + " = '" + fecha + "'"
        val db = this.writableDatabase
        val registros = db.rawQuery(query, null)
        //aqui esta el error no se ejecuta el query
        while (registros.moveToNext()) {
            sacarTiempo(registros.getString(2), 2)        //devuelve oracion
            tiempoOracion = formatearDigito(horaO) + ":" + formatearDigito(minO) + ":" + formatearDigito(segO)

            sacarTiempo(registros.getString(3), 3)        //devuelve lectura
            tiempoLectura = formatearDigito(horaL) + ":" + formatearDigito(minL) + ":" + formatearDigito(segL)

        }

        accion.settOrado(tiempoOracion)
        accion.settLeido(tiempoLectura)

        db.close()

        return accion
    }

    fun ingresarRegistro(contentValues: ContentValues) {
        val db = this.writableDatabase
        db.insert(ConstantesBaseDatos.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun ingresarNombre(contentValues: ContentValues){
        val db = this.writableDatabase
        db.insert(ConstantesBaseDatos.TABLE_NAME_DOS, null, contentValues)
        db.close()
    }

    fun recuperarNombre(): String{
        var nombre = ""
        val query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_NAME_DOS
        val db = this.writableDatabase
        val registros = db.rawQuery(query, null)
        while (registros.moveToNext()) {
            nombre = registros.getString(0)
        }
        db.close()
        return nombre
    }

    private fun formatearDigito(digito: Int): String {
        return String.format("%02d", digito)
    }

    fun sacarTiempo(tiempo: String, opcion: Int){
        val hora = Integer.parseInt(tiempo.substring(0, 2))
        val minuto = Integer.parseInt(tiempo.substring(3, 5))
        val segundo = Integer.parseInt(tiempo.substring(6))

        sumarTiempo(hora, minuto, segundo, opcion)
    }

    fun sumarTiempo(hora: Int, minuto: Int, segundo: Int, opcion: Int) {
        when (opcion) {
            2 -> {
                horaO += hora
                minO += minuto
                segO += segundo
            }
            3 -> {
                horaL += hora
                minL += minuto
                segL += segundo
            }
        }
        rectificarSuma(opcion)
    }

    fun rectificarSuma(opcion: Int) {
        when (opcion) {
            2 -> {
                while (segO > 59) {
                    minO += 1
                    segO -= 59
                }
                while (minO > 59) {
                    horaO += 1
                    minO -= 59
                }
            }
            3 -> {
                while (segL > 59) {
                    minL += 1
                    segL -= 59
                }
                while (minL > 59) {
                    horaL = horaO + 1
                    minL -= 59
                }
            }
        }
    }
}