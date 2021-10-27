package com.generic.ecom.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class UserRequest{
    private String username;
    private String password;
    private Collection<String> roles;
}