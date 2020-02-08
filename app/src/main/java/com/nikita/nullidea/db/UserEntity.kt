/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserEntity(
    @SerializedName("u_id")
    @PrimaryKey
    val id: Int,

    @SerializedName("username")
    val userName: String,

    val email: String,

    val password: String,

    val name: String,

    val about: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("register_date")
    val registerDate: String
)