package com.poly.poly_sender_android.util

abstract class EntityMapper <Entity, DomainModel>{

    abstract fun mapFromEntity(entity: Entity): DomainModel

    abstract fun mapToEntity(domainModel: DomainModel): Entity

    fun mapFromEntityList(entities: List<Entity>): List<DomainModel> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntityList(entities: List<DomainModel>): List<Entity> {
        return entities.map { mapToEntity(it) }
    }
}