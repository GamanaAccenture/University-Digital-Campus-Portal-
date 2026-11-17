package com.udcp.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.FacultyProfile;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {FacultyProfile.class},
   
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FacultyProfileImpl implements FacultyProfile {

    @ValueMapValue
    private String facultyName;

    @ValueMapValue
    private String designation;

    @ValueMapValue
    private String school;

    @ValueMapValue
    private String photo;

    @ValueMapValue
    private String shortBio;

    @Override
    public String getFacultyName() {
        return facultyName;
    }

    @Override
    public String getDesignation() {
        return designation;
    }

    @Override
    public String getSchool() {
        return school;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public String getShortBio() {
        return shortBio;
    }

    
}
