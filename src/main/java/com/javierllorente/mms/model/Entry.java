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

import jakarta.persistence.Column;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Entity
@NamedNativeQuery(name = Entry.FIND_BY_FULLTEXT, 
        query = "SELECT * FROM ENTRY WHERE MATCH(TERM, DEFINITION, TRANSLATION, CONTEXT) "
                + "AGAINST( ? IN NATURAL LANGUAGE MODE)", 
        resultSetMapping = "FullTextResults")

@SqlResultSetMapping(name = "FullTextResults",
        entities = {
            @EntityResult(entityClass = Entry.class, fields = {                
                @FieldResult(name = "term", column = "TERM"),
                @FieldResult(name = "translation", column = "\"TRANSLATION\""),
                @FieldResult(name = "context", column = "CONTEXT")})
        }
)

public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_FULLTEXT = "findByFullText";
    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 1024;
  
    @Id
    @NotNull
    private String term;
    
    @NotNull
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    @Column(length = MAX_SIZE)
    private String definition;
    
    @NotNull
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    @Column(name = "\"TRANSLATION\"", length = MAX_SIZE)
    private String translation;
    
    @NotNull
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    @Column(length = MAX_SIZE)
    private String context;
    
    @Column(length = 500)
    private String expertNetwork;
    
    @Column(length = 500)
    private String resources;
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (term != null ? term.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry other = (Entry) object;
        if ((this.term == null && other.term != null) || (this.term != null && !this.term.equals(other.term))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javierllorente.mms.model.Entry[ term=" + term + " ]";
    }
    
}
