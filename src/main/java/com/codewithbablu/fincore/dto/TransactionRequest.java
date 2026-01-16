package com.codewithbablu.fincore.dto;

public record TransactionRequest (
    double amount,
    String type
) {

}
