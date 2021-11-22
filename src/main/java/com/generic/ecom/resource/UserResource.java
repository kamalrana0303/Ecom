package com.generic.ecom.resource;

import com.generic.ecom.dto.UserDto;
import com.generic.ecom.entity.User;
import com.generic.ecom.request.RoleToUserRequest;
import com.generic.ecom.request.UserRequest;
import com.generic.ecom.service.UserService;
import com.generic.ecom.utility.UtilsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final UtilsService utilsService;


    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers(){
        return  ResponseEntity.ok().body(new ModelMapper().map(userService.getUsers(),new TypeToken<List<UserDto>>(){}.getType()));
    }

    @PostMapping("add-user")
    public ResponseEntity<?> saveUser(@RequestBody UserRequest user){
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("add-role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserRequest roleToUserRM){
        userService.addRoleToUser(roleToUserRM.getUsername(), roleToUserRM.getRolename());
        return ResponseEntity.ok().build();
    }
    @GetMapping("refresh")
    public void getTokenRefreshed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        utilsService.refreshToken(request,response);
    }

}
