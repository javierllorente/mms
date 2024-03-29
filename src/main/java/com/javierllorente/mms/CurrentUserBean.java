/*
 * Copyright 2022-2023 Javier Llorente <javier@opensuse.org>.
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

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.security.Principal;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "currentUserBean")
@RequestScoped
public class CurrentUserBean {
    
    /**
     * Creates a new instance of CurrentUserBean
     */
    
    private String username;
    
    public CurrentUserBean() {
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Principal principal = context.getExternalContext().getUserPrincipal();
        username = (principal == null) ? "" : principal.getName();
    }
    
    public String getUsername() {
        return username;
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    
}
