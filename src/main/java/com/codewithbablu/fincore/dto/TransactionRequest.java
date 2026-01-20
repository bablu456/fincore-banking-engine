package com.codewithbablu.fincore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest (

    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be at least 1.0")
    double amount,

    @NotBlank(message = "Type is mandatory")
    String type
) {
}
