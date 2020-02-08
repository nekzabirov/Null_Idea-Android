/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.model

data class BaseResponseModel <T> (
    val status: String,
    val error: String,
    val data: T
) {
    val isSuccess: Boolean = status == "OK"
}