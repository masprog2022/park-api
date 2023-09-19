package com.masprogtechs.park.api.jwt;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {

    private com.masprogtechs.park.api.entity.User user;


    public JwtUserDetails(com.masprogtechs.park.api.entity.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId() {
       return this.user.getId();
    }

    public String getRole(){
        return this.user.getRole().name();
    }
}
