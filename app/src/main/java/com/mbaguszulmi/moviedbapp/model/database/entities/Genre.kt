package com.mbaguszulmi.moviedbapp.model.database.entities

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)
