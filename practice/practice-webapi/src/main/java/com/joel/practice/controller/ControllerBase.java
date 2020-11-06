package com.joel.practice.controller;

import com.joel.practice.common.exception.UserFriendlyException;
import com.joel.practice.service.dto.user.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Collection;

public class ControllerBase {
    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected UserDetailsDTO getSession(){
        if(getAuthentication() != null){
            return (UserDetailsDTO) getAuthentication().getPrincipal();
        }
        return null;
    }

    protected void checkError(BindingResult result){
        if(result.hasErrors()){
            ObjectError err = result.getAllErrors().get(0);
            throw new UserFriendlyException(err.getDefaultMessage());
        }
    }
}
