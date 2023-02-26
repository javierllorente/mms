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
package com.javierllorente.mms.service;

import com.javierllorente.mms.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Stateless
public class UserService {
    
    @PersistenceContext(unitName = "production-unit")
    private EntityManager entityManager;

    public void add(User user) {
        entityManager.persist(user);
    }
    
    public void update(User user) {
        entityManager.merge(user);
    }
    
    public void remove(User user) {
        if (!entityManager.contains(user)) {
            user = entityManager.merge(user);
        }
        entityManager.remove(user);
    }
    
    public User find(String username) {
        return entityManager.find(User.class, username);
    }
    
    public List<User> findAll() {
        return entityManager.createNamedQuery(User.FIND_ALL).getResultList();
    }
}
