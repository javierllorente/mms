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
import com.javierllorente.mms.service.TagService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "relatedTagBean")
@ViewScoped
public class RelatedTagBean implements Serializable {
    
    @Inject
    private TagService tagService;

    private String tagName;
    private Tag tag;
    private List<Tag> tags;
    private List<Tag> selectedTags;
    private List<Tag> selectedRelatedTags;
    private List<Tag> availableTags;
    private List<Entry> entries;
    /**
     * Creates a new instance of RelatedTagBean
     */
    public RelatedTagBean() {
    }

    public void onLoad() {
        if (tagName != null && !tagName.isBlank()) {
            tag = tagService.find(tagName);
            if (tag == null) {
                addMessage("Tag \"" + tagName + "\" not found");
            } else {
                availableTags.remove(tag);
                availableTags.removeAll(tag.getRelatedTags());
                if (tag.getRelatedTags().isEmpty()) {
                    addMessage("No related tags found for " + tagName);
                }
            }
        }
    }
    
    @PostConstruct
    public void loadTags() {
        tags = tagService.findAll();
        availableTags = new ArrayList<>(tags);
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Tag getTag() {
        return tag;
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

    public List<Tag> getSelectedRelatedTags() {
        return selectedRelatedTags;
    }

    public void setSelectedRelatedTags(List<Tag> selectedRelatedTags) {
        this.selectedRelatedTags = selectedRelatedTags;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<Tag> getAvailableTags() {
        return availableTags;
    }

    public void add() {
        for (Tag selectedTag : selectedTags) {
            tag.getRelatedTags().add(selectedTag);
            selectedTag.getParentTags().add(tag);            
            tagService.update(tag);
            tagService.update(selectedTag);
            availableTags.remove(selectedTag);
        }
    }
    
    public void delete() {
        for (Tag selectedTag : selectedRelatedTags) {
            tag.getRelatedTags().remove(selectedTag);
            tagService.update(tag);
            availableTags.add(selectedTag);
        }
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
    
}
