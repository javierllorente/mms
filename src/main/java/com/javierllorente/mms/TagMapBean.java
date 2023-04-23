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

import com.javierllorente.mms.model.Tag;
import com.javierllorente.mms.service.TagService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.io.Serializable;

/**
 *
 * @author Javier Llorente <javier@opensuse.org>
 */
@Named(value = "tagMapBean")
@RequestScoped
public class TagMapBean implements Serializable {
    
    @Inject
    private TagService tagService;

    private String tagName;
    private Tag tag;
    /**
     * Creates a new instance of TagMapBean
     */
    public TagMapBean() {
    }

    public void onLoad() {
        if (tagName != null && !tagName.isBlank()) {
            tag = tagService.find(tagName);
            if (tag == null) {
                addMessage("Tag \"" + tagName + "\" not found");
            } else if (tag.getRelatedTags().isEmpty()) {
                addMessage("No related tags found for " + tagName);
            }
        } else {
            addMessage("No tag given");
        }
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

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
    
}
