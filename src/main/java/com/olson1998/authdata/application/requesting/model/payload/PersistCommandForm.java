package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.application.requesting.utils.PersistTypeName;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.PersistCommand;
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
