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

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Entity
@NamedQuery(name = Tag.FIND_ALL, query = "SELECT t FROM Tag t")

public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "Tag.findAll";
    
    @Id
    @NotNull
    private String name;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<Entry> entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javierllorente.mms.model.Tag[ name=" + name + " ]";
    }
    
}
