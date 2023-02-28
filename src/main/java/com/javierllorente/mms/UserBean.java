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

import com.javierllorente.mms.model.User;
import com.javierllorente.mms.service.UserService;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "userBean")
@ViewScoped
public class UserBean implements Serializable {
    
    @Inject
    private UserService userService;
    
    @Inject
    private CurrentUserBean currentUserBean;
    
    private User user;
    private String username;
    private List<User> users;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }
    
    public void init() {
        user = userService.find(currentUserBean.getUsername());
    }

    public void onLoad() {
        if (username != null && !username.isBlank()) {
            user = userService.find(username);
            if (user == null) {
                addMessage("Username \"" + username + "\" not found");
            }
        }
    }
    
    public void loadUsers() {
        users = userService.findAll();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }    

    public User getUser() {
        return user;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    private String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hash);  
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void changePassword() {        
        String message = "Password for " + user.getUsername();
        if (userService.find(user.getUsername()) != null) {
            user.setPassword(sha256(user.getPassword()));
            userService.update(user);
            message += " has been changed successfuly";
        } else {
            message = "User not found!";
        }        
        addMessage(message);        
    }
    
    public void update() {
        userService.update(user);
        addMessage("User \"" + user.getUsername() + "\" has been updated");
    }
    
    public void delete() {
        userService.remove(user);
        addMessage("User \"" + user.getUsername() + "\" has been deleted");
        user.setUsername(null);
    }
    
    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage("updateUserForm", new FacesMessage(message));
    }
    
}
