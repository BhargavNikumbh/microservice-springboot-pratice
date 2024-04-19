package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.exception.ResourceNotFoundExcecption;
import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD controller",
        description = "ACD"
)
public class AccountsController {
    private IAccountsService accountsService;
    private ICustomerService customerService;

    @Operation(
            summary = "Create Account API",
            description = "Rest API to create account into eazy bank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount( @Valid @RequestBody CustomerDto customerDto){
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get account by mobile number API",
            description = "Rest API to get account from mobile number from eazy bank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @Parameter(
            name = "mobileNumber",
            description = "8975987068"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Resource Not found",
            content = @Content(
                    schema = @Schema(implementation = ResourceNotFoundExcecption.class)
            )
    )
    @GetMapping("/fetch")
    public ResponseEntity<Optional<CustomerDto>> customerByMobileNumber(@RequestParam("mobileNumber")
            @Pattern(regexp = "($|[0-9]{10})", message = "Mobile Number must be of 10 digits")
                                                                            String mobileNumber){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerByMobileNumber(mobileNumber));
    }

    @Operation(
            summary = "Update Account API",
            description = "Rest API to update account into eazy bank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @ApiResponse(
            responseCode = "417",
            description = "Update operation failed. Please try again or contact Dev team",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Delete account by mobile number API",
            description = "Rest API to get account from mobile number from eazy bank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Resource Not found",
            content = @Content(
                    schema = @Schema(implementation = ResourceNotFoundExcecption.class)
            )
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
            @Pattern(regexp = "($|[0-9]{10})", message = "Mobile Number must be of 10 digits")
                                                                String mobileNumber){
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.MESSAGE_417_DELETE, AccountsConstants.MESSAGE_417_DELETE));
        }
    }


}
