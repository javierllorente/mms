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
package com.javierllorente.mms.service;

import com.javierllorente.mms.model.Tag;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Stateless
public class TagService {
    
    @PersistenceContext(unitName = "production-unit")
    private EntityManager em;
    
    public void add(Tag tag) {
        em.persist(tag);
    }
    
    public void remove(Tag tag) {
        if (!em.contains(tag)) {
            tag = em.merge(tag);
        }
        em.remove(tag);
    }
    
    public void update(Tag tag) {
        em.merge(tag);
    }
    
    public Tag find(String name) {
        return em.find(Tag.class, name);
    }
    
    public List<Tag> findAll() {
        return em.createNamedQuery(Tag.FIND_ALL, Tag.class).getResultList();
    }
    
}
