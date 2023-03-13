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
package com.javierllorente.mms.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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
@NamedNativeQuery(name = Entry.FIND_BY_TAG, query = "SELECT ENTRY.TERM FROM ENTRY "
        + "INNER JOIN ENTRY_TAG ON ENTRY.TERM = ENTRY_TAG.TERM WHERE ENTRY_TAG.TAG = ?",
        resultSetMapping = "EntryTagResults")
@SqlResultSetMapping(name = "EntryTagResults",
        entities = {
            @EntityResult(entityClass = Entry.class, fields = {                
                @FieldResult(name = "term", column = "TERM")})
        }
)
@NamedQuery(name = Entry.FIND_ALL, query = "SELECT e FROM Entry e")

public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_FULLTEXT = "Entry.findByFullText";
    public static final String FIND_BY_TAG = "Entry.findByTag";
    public static final String FIND_ALL = "Entry.findAll";
    public static final int MIN_SIZE = 2;
    public static final int MAX_SIZE = 1024;
  
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
    
    @Embedded
    private HtmlData htmlData;
    
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User user;
    
    @Column
    private Date creationDate;
    
    @Column
    private Date lastModified;
    
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "ENTRY_TAG",
            joinColumns = @JoinColumn(name = "TERM", referencedColumnName = "TERM"),
            inverseJoinColumns = @JoinColumn(name = "TAG", referencedColumnName = "NAME"))
    private List<Tag> tags;
    
    @PrePersist
    private void prePersist() {
        creationDate = new Date();
    }
    
    @PreUpdate
    private void preUpdate() {
        lastModified = new Date();
        for (Tag tag : tags) {
            if (!tag.getEntries().contains(this)) {
                tag.getEntries().add(this);
            }
        }
    }
    
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
    
    public HtmlData getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(HtmlData htmlData) {
        this.htmlData = htmlData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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
