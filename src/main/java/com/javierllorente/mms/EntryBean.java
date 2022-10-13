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
package com.javierllorente.mms;

import com.javierllorente.mms.model.Entry;
import com.javierllorente.mms.service.EntryService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "entryBean")
@RequestScoped
public class EntryBean {
    
    @Inject
    private EntryService entryService;

    private Entry entry;
    /**
     * Creates a new instance of EntryManager
     */
    public EntryBean() {
    }
    
    @PostConstruct
    public void init() {
        entry = new Entry();
    }

    public Entry getEntry() {
        return entry;
    }
    
    public void submit() {
        String message;

        if (entryService.search(entry.getTerm()) == null) {
            entryService.add(entry);
            message = "Entry added";
        } else {
            message = "Entry already in the DB";
        }
        
        FacesContext.getCurrentInstance().addMessage("addForm", new FacesMessage(message));
    }
    
    public void update() {
        // By default, if entry is not found, it is addded!
        entryService.update(entry);
        FacesContext.getCurrentInstance().addMessage("updateForm", new FacesMessage("Entry updated"));
    }
    
}
