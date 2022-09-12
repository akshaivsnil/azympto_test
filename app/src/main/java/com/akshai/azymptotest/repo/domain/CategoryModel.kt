package com.akshai.azymptotest.repo.domain

import com.akshai.azymptotest.repo.entity.Params
import com.google.gson.annotations.SerializedName

data class CategoryModel(
    var id: String? = null,
    var state: String? = null,
    var params: Params? = null,
    var title: String? = null,
    var slug: String? = null,
    var parentId: String? = null,
    var textColourTheme: String? = null,
    var image: String? = null,
    var imageName: String? = null,
    var child : List<CategoryModel>? = null
)
