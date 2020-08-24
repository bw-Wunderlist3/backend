package com.wunderlist.backend.services;

import com.wunderlist.backend.handler.HelperFunctions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "helpFunctions")
public class HelperFunctionsImpl implements HelperFuction
{
    @Override
    public boolean isAuthorized(String username)
    {


        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName()
                .toLowerCase()) || authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("USER")))
        {
            return true;
        } else
        {

            throw new EntityNotFoundException(authentication.getName() + " not authorized to make change");
        }
    }
}