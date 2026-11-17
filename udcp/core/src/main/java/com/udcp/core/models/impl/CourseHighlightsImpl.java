package com.udcp.core.models.impl;


import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.udcp.core.models.CourseHighlights;
import com.udcp.core.models.Courses;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {CourseHighlights.class},
    resourceType = "udcp/components/udcpcomponents/course-higlights",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CourseHighlightsImpl implements CourseHighlights {

    @ChildResource(name = "courses")
 public List<Courses> courses;

    @Override
    public List<Courses> getCourses() {
        return courses;
    }
}
