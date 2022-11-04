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
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "mindMapBean")
@RequestScoped
public class MindMapBean {

    private String term;
    private Entry entry;

    @Inject
    EntryService entryService;
    
    /**
     * Creates a new instance of MindMapBean
     */
    public MindMapBean() {
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
    
    public void onLoad() {
        if (term != null && !term.isBlank()) {
            entry = entryService.find(term);
        }
    }
    
    public String search() {
        return "mindmap?faces-redirect=true&includeViewParams=true";
    }
    
}
