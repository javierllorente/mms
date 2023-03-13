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
package com.javierllorente.mms;

import com.javierllorente.mms.model.Entry;
import com.javierllorente.mms.model.Tag;
import com.javierllorente.mms.service.EntryService;
import com.javierllorente.mms.service.TagService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "tagBean")
@ViewScoped
public class TagBean implements Serializable {
    
    @Inject
    private TagService tagService;
    
    @Inject EntryService entryService;

    private String tagName;
    private String justAddedTag;
    private List<Tag> tags;
    private List<Tag> selectedTags;
    private List<Entry> entries;
    /**
     * Creates a new instance of TagBean
     */
    public TagBean() {
    }

    public void onLoad() {
        if (tagName != null && !tagName.isBlank()) {
            entries = entryService.findByTag(tagName);
            
            if (entries == null) {
                addMessage("No entries found with tag \"" + tagName);
            }
        }
    }
    
    @PostConstruct
    public void loadTags() {
        tags = tagService.findAll();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    
    public List<Tag> getTags() {
        return tags;
    }

    public List<Tag> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<Tag> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public List<Entry> getEntries() {
        return entries;
    } 

    public void add() {
        String message;
        if (tagName == null || tagName.isBlank()) {
            message = "Empty tag";
        } else if (tagService.find(tagName) == null) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tagService.add(tag);
            tags.add(tag);
            justAddedTag = tagName;
            tagName = "";
            message = "Tag \"" + tag.getName() + "\" has been added successfuly";
        } else {
            message = "Tag for \"" + tagName + "\" already exists in the database";
        }        
        addMessage(message);
    }
    
    public void delete() {
        String deletedTags = "";  
        for (Iterator<Tag> iterator = selectedTags.iterator(); iterator.hasNext();) {
            Tag selectedTag = iterator.next();
            // * Keep entities in sync *
            // Make sure a just-added tag is synchronized with DB if user adds it to
            // an entry and afterwards deletes the tag, so that entry is not left with
            // a reference to an existent tag. Needed if Web page is not reloaded
            if (justAddedTag != null) {
                selectedTag = tagService.find(selectedTag.getName());
            }            
            tagService.remove(selectedTag);
            deletedTags += selectedTag.getName();
            if (iterator.hasNext()) {
                deletedTags += ", ";
            }
            iterator.remove();
            tags.remove(selectedTag);
        }
        addMessage("Deleted tag(s): " + deletedTags);
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage("tagForm", new FacesMessage(message));
    }
    
}
