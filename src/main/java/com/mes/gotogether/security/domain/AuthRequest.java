package com.mes.gotogether.security.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.mes.gotogether.validators.ExtendedEmailValidator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {

    @NotNull
    @ExtendedEmailValidator
    private String username;
    @NotNull
    @Min(8)
    private String password;
}
