package com.generic.ecom.web;

import com.generic.ecom.service.impl.RoleServiceImpl;
import com.mysql.jdbc.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationStart {
    @Autowired
    RoleServiceImpl roleService;

    @EventListener
    public void onApplicationStart(ApplicationReadyEvent applicationReadyEvent){
        this.createRoles();
    }
    private void createRoles(){
        this.roleService.createPrimaryRoles();
        this.roleService.createPrimaryUser();
    }
}
