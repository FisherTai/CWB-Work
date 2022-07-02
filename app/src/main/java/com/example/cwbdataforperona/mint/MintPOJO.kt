package com.example.cwbdataforperona.mint

data class MintPOJO(var isImage: Boolean = false) {

    var startTime: String = ""
        set(value) {
            field = value.replace("-", "/")
        }

    var endTime: String = ""
        set(value) {
            field = value.replace("-", "/")
        }

    var parameterName: String = ""

    var parameterUnit: String = ""
}