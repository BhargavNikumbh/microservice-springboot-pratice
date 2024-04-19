package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account Number cannot be Empty")
    @Pattern(regexp = "($|[0-9]{10})", message = "Account Number must be of 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account Type cannot be Empty")
    private String accountType;

    @NotEmpty(message = "Branch Address cannot be null or Empty")
    private String branchAddress;
}
