package com.kongedxz.appfiore.data.local.photos

import com.kongedxz.appfiore.domain.entity.DescribedPhoto

data class PhotoData (
    val name: String,
    val assetsUri: String,
    val category: String
) {
    fun toDescribedPhoto(desc: String): DescribedPhoto {
        return DescribedPhoto (
            photoData = this,
            desc = desc
        )
    }
}
