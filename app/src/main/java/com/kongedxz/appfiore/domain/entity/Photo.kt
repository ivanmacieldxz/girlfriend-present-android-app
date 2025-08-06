package com.kongedxz.appfiore.domain.entity

import com.kongedxz.appfiore.data.local.photos.PhotoData

data class Photo (
    val photoData: PhotoData,
    val desc: String,
    val category: List<String>
)