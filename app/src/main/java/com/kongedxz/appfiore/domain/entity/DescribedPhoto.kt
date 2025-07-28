package com.kongedxz.appfiore.domain.entity

import com.kongedxz.appfiore.data.local.photos.PhotoData

data class DescribedPhoto (
    val photoData: PhotoData,
    val desc: String
)