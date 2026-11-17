package com.udcp.core.models.impl;

import java.util.List;

import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.udcp.core.models.Faculties;
import com.udcp.core.models.FacultyProfile;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {Faculties.class},
    resourceType = "udcp/components/udcpcomponents/faculty-profile",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class FacultiesImpl  implements Faculties{

   @ChildResource(name = "facultyTab")
    List<FacultyProfile> facultyprofiles;

    public List<FacultyProfile> getFacultyProfiles()
    {
        return facultyprofiles;
    }

}
