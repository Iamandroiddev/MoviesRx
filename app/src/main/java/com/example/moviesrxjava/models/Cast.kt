package com.example.moviesrxjava.models

import java.io.Serializable

data class Cast(
     var character: String? = null,
     var name: String? = null,
     var profile_path: String? = null,
     val id: Int? = null
)
