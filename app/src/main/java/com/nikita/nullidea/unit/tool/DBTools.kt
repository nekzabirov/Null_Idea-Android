/*
 * Copyright (c) 2020.
 * Nkita Knyazevkiy
 * UA
 */

package com.nikita.nullidea.unit.tool

import android.content.Context
import androidx.room.Room
import com.nikita.nullidea.unit.MyDatabase

object DBTools {

    private var context: Context? = null

    fun init(context: Context) {
        DBTools.context = context
    }

    private val myDatabase: MyDatabase by lazy {
        if (context == null)
            throw IllegalArgumentException("Please init DBTools")
        Room
            .databaseBuilder<MyDatabase>(
                context!!, MyDatabase::class.java, "null_idea_db")
            .allowMainThreadQueries()
            .build()
    }

}