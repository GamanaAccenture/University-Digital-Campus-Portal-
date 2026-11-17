package com.udcp.core.models.impl;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.Courses;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {Courses.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CoursesImpl implements Courses{

    @ValueMapValue 
    String courseName;
    @ValueMapValue 
    String description;
    @ValueMapValue 
    String duration;
    @ValueMapValue 
    String fees;
    @Override
    public String getCourseName() {
        // TODO Auto-generated method stub
        return courseName;
    }
    @Override
    public String getCourseDescription() {
        // TODO Auto-generated method stub
        return description;
    }
    @Override
    public String getCourseDuration() {
        // TODO Auto-generated method stub
        return duration;
    }
    @Override
    public String getFees() {
        // TODO Auto-generated method stub
        return fees;
    }

}
