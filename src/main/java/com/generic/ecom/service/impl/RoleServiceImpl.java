package com.generic.ecom.service.impl;

import com.generic.ecom.RoleEnum;
import com.generic.ecom.entity.Role;
import com.generic.ecom.entity.User;
import com.generic.ecom.repository.RoleRepository;
import com.generic.ecom.repository.UserRepository;
import com.generic.ecom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    EntityManager em;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void createPrimaryRoles(){
        if(!isRoleExist(RoleEnum.R.name())){
            saveRoles(RoleEnum.R.name());
        }
        if(!isRoleExist(RoleEnum.W.name())){
            saveRoles(RoleEnum.W.name());
        }
        if(!isRoleExist(RoleEnum.RW.name())){
            saveRoles(RoleEnum.RW.name());
        }
    }

    @Override
    @Transactional
    public void createPrimaryUser(){
        User user=userRepository.findByUsername("8376857964");
        List<Role> allRoles = getAllRoles();
        List<Role> RWRoles=allRoles.stream().filter(role->{return role.getName().equals("ROLE_"+RoleEnum.RW.name());}).collect(Collectors.toList());
        if(user==null){
            user= new User("8376857964",bCryptPasswordEncoder.encode("8376857964"),RWRoles);
            for(Role role: RWRoles){
                role.getUsers().add(user);
            }
            this.userRepository.save(user);
        }
    }

    @Override
    public void saveRoles(String rolename){

        if(!StringUtils.hasLength(rolename) ){
            throw new RuntimeException("invalid rolename");
        }
        if(rolename.isBlank()){
            throw new RuntimeException("invalid rolename");
        }

        RoleEnum roleEnum= RoleEnum.valueOf(rolename);
        Assert.isTrue(!rolename.startsWith("ROLE_"),()->{return "invalid rolename (no need to prefix ROLE_)";});
        Role role= new Role(0L,"ROLE_"+roleEnum.name(),null);
        roleRepository.save(role);
    }

    @Override
    public boolean isRoleExist(String rolename){
        Assert.isTrue(!rolename.startsWith("ROLE_"),()->{return "invalid rolename";});
        RoleEnum roleEnum= RoleEnum.valueOf(rolename);
        Role role=this.roleRepository.findRoleByName("ROLE_"+roleEnum.name());
        if(role!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<Role> getAllRoles(){
        return this.roleRepository.findAll();
    }

}

