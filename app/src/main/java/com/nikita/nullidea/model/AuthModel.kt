/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.model

import com.google.gson.annotations.SerializedName

data class AuthModel(
    @SerializedName("client_id")
    val clientId: String = "PwWOqCFYyrjKt091lL0vzjGSQDUUkIxw",
    @SerializedName("client_secret")
    val clientSecret: String = "rGytL6kKhBZFhtSDdH_aWnLRz1euqpQ1SDL6o2CacwKGCaqx8qI40Q_s_3UmC9JT",
    val audience: String = "https://nullidea.herokuapp.com/",
    @SerializedName("grant_type")
    val grantType: String = "client_credentials"
)