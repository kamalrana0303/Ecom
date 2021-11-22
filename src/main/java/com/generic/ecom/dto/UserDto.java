package com.generic.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
    private long id;
    private String username;
    private String password;
    private Collection<RoleDto> roles;
}