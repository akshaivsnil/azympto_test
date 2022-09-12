package com.akshai.azymptotest.repo.mapper

import com.akshai.azymptotest.repo.domain.CategoryDomain
import com.akshai.azymptotest.repo.entity.ItemsItem

class CategoryMapper : EntityMapper<ItemsItem, CategoryDomain>{

    override fun mapFromEntity(entity: ItemsItem): CategoryDomain {
        return CategoryDomain(
            id = entity.id,
            parentId = entity.parentId,
            title = entity.title,
        )
    }

    fun fromEntityList(initial : List<ItemsItem>) : List<CategoryDomain>{
        return initial.map { mapFromEntity(it) }
    }

}