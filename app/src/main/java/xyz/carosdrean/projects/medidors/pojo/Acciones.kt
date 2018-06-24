package xyz.carosdrean.projects.medidors.pojo

class Acciones {
    var fecha: String? = null
    private var tOrado: String? = null
    private var tLeido: String? = null

    constructor() {}

    constructor(fecha: String, tOrado: String, tLeido: String) {
        this.fecha = fecha
        this.tOrado = tOrado
        this.tLeido = tLeido
    }

    fun gettOrado(): String? {
        return tOrado
    }

    fun settOrado(tOrado: String) {
        this.tOrado = tOrado
    }

    fun gettLeido(): String? {
        return tLeido
    }

    fun settLeido(tLeido: String) {
        this.tLeido = tLeido
    }
}