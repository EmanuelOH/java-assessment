package com.irwi.assessment.domain.ports.service.crud;

public interface CreateService<EntityRequest, Entity> {
    public Entity create (EntityRequest request);
}
