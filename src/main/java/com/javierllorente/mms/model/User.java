/*
 * Copyright 2022 Javier Llorente <javier@opensuse.org>.
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
package com.javierllorente.mms.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
//@Entity(name = "mms_user")
@Entity
@Table(name = "MMS_USER")
@NamedQuery(name = User.FIND_ALL_USERS, query = "SELECT u from User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL_USERS = "findAllUsers";
    public static final String FIND_USER = "findUser";
    
    @Id
    @NotNull
    private String username;
    
    @NotNull(message = "Password cannot be empty")
    private String password;

//    @CollectionTable()
//    @ElementCollection()
//    @CollectionTable(name = "MMS_GROUP", joinColumns = @JoinColumn(name = "USERNAME"))
//    @CollectionTable(name = "MMS_GROUP", joinColumns = @JoinColumn(name = "GROUPS", referencedColumnName = "USERNAME"))
//    @CollectionTable(name = "MMS_GROUP")
//    private final List<String> groups = new ArrayList<>();
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "user")
    private final List<Group> groups = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Group> getGroups() {
        return groups;
    }

//    public Group getGroup() {
//        return group;
//    }

//    public void setGroup(Group group) {
//        this.group = group;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javierllorente.mms.model.User[ username=" + username + " ]";
    }
    
}
