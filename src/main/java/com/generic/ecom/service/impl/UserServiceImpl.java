package com.generic.ecom.service.impl;

import com.generic.ecom.dto.UserDto;
import com.generic.ecom.entity.Role;
import com.generic.ecom.entity.User;
import com.generic.ecom.repository.RoleRepository;
import com.generic.ecom.repository.UserRepository;
import com.generic.ecom.request.UserRequest;
import com.generic.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto saveUser(UserRequest user) {
        User newUser= new ModelMapper().map(user, User.class);
        newUser=this.userRepository.save(newUser);
        if(newUser==null){
            throw new RuntimeException("invalid user");
        }
        return new ModelMapper().map(newUser, UserDto.class);
    }

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        log.info("fetching user from database");
        User user= this.userRepository.findByUsername(username);
        if(user==null){
            throw new RuntimeException("invalid user");
        }
        log.info("fetching role from database");
        Role role= this.roleRepository.findRoleByName(rolename);
        if(role==null){
            throw new RuntimeException("invalid user");
        }
        log.info("adding role to user");
        user.getRoles().add(role);
        log.info("adding user to role");
        role.getUsers().add(user);

    }

    @Override
    public UserDto getUser(String username) {
        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new RuntimeException("invalid user");
        }

        return new ModelMapper().map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> all = userRepository.findAll();
        List<UserDto> userDtos = new ModelMapper().map(all, new TypeToken<List<User>>() {
        }.getType());
        return userDtos ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("invalid user");
        }
        return user;
    }
}
