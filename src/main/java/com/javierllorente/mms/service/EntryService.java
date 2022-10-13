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
package com.javierllorente.mms.service;

import com.javierllorente.mms.model.Entry;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Stateless
public class EntryService {
    
    @PersistenceContext(unitName = "production-unit")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void add(Entry entry) {
        em.persist(entry);
    }
    
    public void update(Entry entry) {
        em.merge(entry);
    }
    
    public void remove(Entry entry) {
        em.remove(entry);
    }
    
    public Entry search(String term) {
        return em.find(Entry.class, term);
    }
    
}
