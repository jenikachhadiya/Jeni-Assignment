package com.example.app.model

data class Services(

    var id: Int,
    var image: Int,
    var line: Int,
    var header: String,
    var name: String,
    var button: Int,
    var list: ArrayList<Listitem>

)

data class Listitem(
    var image: Int,
    var text: String
)
