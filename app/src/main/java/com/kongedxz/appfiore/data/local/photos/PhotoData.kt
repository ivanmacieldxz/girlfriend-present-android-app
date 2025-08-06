package com.kongedxz.appfiore.data.local.photos

import com.kongedxz.appfiore.domain.entity.Photo

data class PhotoData (
    val name: String,
    val assetsUri: String,
) {
    fun toDomainPhoto(desc: String, category: List<String>): Photo {
        return Photo(
            photoData = this,
            desc = desc,
            category = category
        )
    }
}
