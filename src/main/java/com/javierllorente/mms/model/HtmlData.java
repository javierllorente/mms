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
package com.javierllorente.mms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Embeddable
public class HtmlData implements Serializable {

    @NotNull
    @Size(min = Entry.MIN_SIZE, max = Entry.MAX_SIZE)
    @Column(name = "DEFINITION_HTML", length = Entry.MAX_SIZE)
    private String definition;
    
    @NotNull
    @Size(min = Entry.MIN_SIZE, max = Entry.MAX_SIZE)
    @Column(name = "TRANSLATION_HTML", length = Entry.MAX_SIZE)
    private String translation;
    
    @NotNull
    @Size(min = Entry.MIN_SIZE, max = Entry.MAX_SIZE)
    @Column(name = "CONTEXT_HTML", length = Entry.MAX_SIZE)
    private String context;
    
    @Column(name = "EXPERTNETWORK_HTML", length = 500)
    private String expertNetwork;
    
    @Column(name = "RESOURCES_HTML", length = 500)
    private String resources;    

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getExpertNetwork() {
        return expertNetwork;
    }

    public void setExpertNetwork(String expertNetwork) {
        this.expertNetwork = expertNetwork;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
    
}
