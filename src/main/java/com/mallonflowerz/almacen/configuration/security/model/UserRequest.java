package com.mallonflowerz.almacen.configuration.security.model;

import lombok.*;

@Builder
@Data
public class UserRequest {
    
    private String email;
    private String password;
}
