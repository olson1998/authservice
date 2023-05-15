package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.requesting.utils.PersistTypeName;
import com.olson1998.authservice.domain.port.request.data.PersistCommand;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode

@NoArgsConstructor
@AllArgsConstructor
public class PersistCommandForm implements PersistCommand {

    @JsonProperty(value = "type", required = true)
    private PersistTypeName persistType;

    @JsonProperty(value = "arg", required = true)
    private String argument;

}
