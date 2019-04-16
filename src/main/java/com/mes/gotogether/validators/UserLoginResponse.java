package com.mes.gotogether.validators;

import com.mes.gotogether.security.domain.AuthRequest;
import lombok.Data;
import java.util.Map;

@Data
public class UserLoginResponse {

    private AuthRequest user;
    private boolean validated;
    private Map<String, String> errorMessages;

}