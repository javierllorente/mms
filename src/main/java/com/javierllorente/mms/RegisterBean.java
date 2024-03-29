/*
 * Copyright 2023 Javier Llorente <javier@opensuse.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.javierllorente.mms;

import com.javierllorente.mms.model.Group;
import com.javierllorente.mms.model.UserGroup;
import com.javierllorente.mms.model.User;
import com.javierllorente.mms.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "registerBean")
@RequestScoped
public class RegisterBean {
    
    @Inject
    private UserService userService;
    
    private User user;

    /**
     * Creates a new instance of RegisterBean
     */
    public RegisterBean() {
    }
    
    @PostConstruct
    public void init() {
        user = new User();        
        Group group = new Group();
        group.setGroupname(UserGroup.USER.toString());
        user.setGroup(group);
    }

    public User getUser() {
        return user;
    }
    
    private String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void add() {        
        String message = "User " + user.getUsername();
        if (userService.find(user.getUsername()) == null) {
            user.setPassword(sha256(user.getPassword()));
            userService.add(user);
            message += " has been registered successfuly";
        } else {
            message += " is already registered";
        }        
        FacesContext.getCurrentInstance().addMessage("registerForm", new FacesMessage(message));        
    }
    
}
