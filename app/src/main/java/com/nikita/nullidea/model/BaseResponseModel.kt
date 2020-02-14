/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.model

import com.google.gson.annotations.SerializedName

data class BaseResponseModel <T> (
    @SerializedName("success")
    val isSuccess: Boolean,
    val error: String,
    val data: T?
)