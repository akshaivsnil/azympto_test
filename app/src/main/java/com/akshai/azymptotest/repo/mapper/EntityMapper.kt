package com.akshai.azymptotest.repo.mapper

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

}