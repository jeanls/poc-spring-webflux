package com.jean.store.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInputDto {

    @NotNull
    @NotBlank
    @JsonProperty(value = "name")
    private String name;

    @NotNull
    @NotBlank
    @JsonProperty(value = "description")
    private String description;

    @NotNull
    @JsonProperty(value = "price")
    private BigDecimal price;

}
