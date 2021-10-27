package com.generic.ecom.service;

import com.generic.ecom.dto.UserDto;
import com.generic.ecom.entity.Role;
import com.generic.ecom.entity.User;
import com.generic.ecom.request.UserRequest;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserRequest user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    UserDto getUser(String username);
    List<UserDto> getUsers();
}
