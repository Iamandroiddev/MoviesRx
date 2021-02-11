package com.example.moviesrxjava.models

import com.google.gson.JsonObject

data class Actor(
        var birthday: String? = null,
        var name: String? = null,
        var biography: String? = null,
        var place_of_birth: String? = null,
        var profile_path: String? = null,
        var known_for_department: String? = null,
        val popularity: Number? = null,
        val id: Int? = null,
        val movie_credits: JsonObject? = null
)
