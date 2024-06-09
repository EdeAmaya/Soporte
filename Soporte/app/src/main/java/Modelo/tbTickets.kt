package Modelo

data class tbTickets(
    val uuid: String,
    var titulo: String,
    var descripcion: String,
    var autor: String,
    var email: String,
    var fechaCreacion: String,
    var estado: String,
    var fechaFinalizacion: String
)
