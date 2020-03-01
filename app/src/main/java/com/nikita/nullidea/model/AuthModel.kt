/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.model

import com.google.gson.annotations.SerializedName

data class AuthModel(
    @SerializedName("client_id")
    val clientId: String = "2xsVxikXVWGYdtVM8zy37yuxlnJ2C3e9",
    @SerializedName("client_secret")
    val clientSecret: String = "UrgWDRChGIqShy4qntvI9MXCEeImYEXdly9BdWtvYxyBfVET0LDw7s1jXToXDQqL",
    val audience: String = "https://nullidea.herokuapp.com/",
    @SerializedName("grant_type")
    val grantType: String = "client_credentials"
)