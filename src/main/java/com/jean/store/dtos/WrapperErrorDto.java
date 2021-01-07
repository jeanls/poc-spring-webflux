package com.jean.store.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WrapperErrorDto {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "errors")
    private Object data;
}
