package com.tochka.testtask.domain.converter;

import com.tochka.testtask.domain.ResourceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class ResourceTypeConverter implements AttributeConverter<ResourceType, Integer> {

    @Override public ResourceType convertToEntityAttribute(Integer attribute) {
        if (attribute == null) {
            return null;
        }
        return ResourceType.fromId(attribute);
    }

    @Override public Integer convertToDatabaseColumn(ResourceType dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData.getId();
    }
}
