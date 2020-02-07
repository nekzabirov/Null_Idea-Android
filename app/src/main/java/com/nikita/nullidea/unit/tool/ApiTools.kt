/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit.tool

import com.nikita.nullidea.unit.rest.ApiFactory

object ApiTools {

    private val apiFactory: ApiFactory = ApiFactory()

    private const val apiUrl = ""

    fun <T> api(service: Class<T>): T {
        return apiFactory
            .buildRetrofit(apiUrl)
            .create(service)
    }
}