package com.olson1998.authservice.domain.model.exception.data;

import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class DataNotFoundException extends NoSuchElementException {

    private final Class<?> entityClass;

    @Override
    public String getMessage() {
        return String.format("data not found for entity: %s", entityClass.getName());
    }
}
