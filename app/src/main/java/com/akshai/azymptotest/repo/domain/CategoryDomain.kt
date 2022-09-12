package com.akshai.azymptotest.repo.domain

import com.akshai.azymptotest.repo.entity.Params

data class CategoryDomain(
    var id: String? = null,
    var title: String? = null,
    var orderLevel: String? = null,
    var parentId: String? = null,
    var child: ArrayList<CategoryDomain>? = arrayListOf(),
    var parent: CategoryDomain? = null,
    var state: String? = null,
    var params: Params? = null,
) {

    fun addChild(child: CategoryDomain?) {
        if (!this.child!!.contains(child) && child != null) this.child!!.add(child)
    }

}