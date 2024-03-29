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

import com.javierllorente.mms.model.Entry;
import com.javierllorente.mms.model.HtmlData;
import com.javierllorente.mms.model.User;
import com.javierllorente.mms.service.EntryService;
import com.javierllorente.mms.service.UserService;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "entryBean")
@ViewScoped
public class EntryBean implements Serializable {
    
    @Inject
    private EntryService entryService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private CurrentUserBean currentUserBean;

    private Entry entry;
    private String term;
    private List<Entry> entries;
    /**
     * Creates a new instance of EntryBean
     */
    public EntryBean() {
    }
    
    public void init() {
        User user = userService.find(currentUserBean.getUsername());
        entry = new Entry();
        entry.setUser(user);
        entry.setHtmlData(new HtmlData());
    }
    
    public void onLoad() {
        if (term != null && !term.isBlank()) {
            entry = entryService.find(term);
            
            if (entry == null) {
                addMessage("Entry \"" + term + "\" not found");
            }
        }
    }
    
    public void loadEntries() {
        entries = entryService.findAll();
    }
    
    public Entry getEntry() {
        return entry;
    }

    public List<Entry> getEntries() {
        return entries;
    }
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void add() {
        String message;

        if (entryService.find(entry.getTerm()) == null) {
            entryService.add(entry);
            message = "Entry \"" + entry.getTerm() + "\" added successfuly";
        } else {
            message = "Entry for \"" + entry.getTerm() + "\" already exists in the database";
        }
        
        addMessage(message);
    }
    
    public void update() {
        // By default, if entry is not found, it is addded!
        entryService.update(entry);
        addMessage("Entry \"" + entry.getTerm() + "\" updated");
    }
    
    public void delete() {
        entryService.remove(entry);
        addMessage("Entry \"" + entry.getTerm() + "\" deleted");
        entry.setTerm(null);
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage("entryForm", new FacesMessage(message));
    }
    
}
