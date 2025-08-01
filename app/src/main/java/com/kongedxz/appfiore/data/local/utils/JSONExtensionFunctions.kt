package com.kongedxz.appfiore.data.local.utils

import org.json.JSONArray

fun JSONArray.toList(): List<String> {
    val list = mutableListOf<String>()

    for (i in 0 until length()) {
        list.add(getString(i))
    }

    return list
}