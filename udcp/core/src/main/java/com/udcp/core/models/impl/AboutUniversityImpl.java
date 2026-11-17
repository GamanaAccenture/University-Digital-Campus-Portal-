package com.udcp.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.AboutUniversityModel;


@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {AboutUniversityModel.class},
    resourceType = "udcp/components/udcpcomponents/about-univeristy",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AboutUniversityImpl implements AboutUniversityModel{


    @ValueMapValue 
    String heading;
    @ValueMapValue 
    String image;
    
    @ValueMapValue
    String buttonLink;

    @ValueMapValue
    String imageAlt;
    @ValueMapValue
    String description;
    @ValueMapValue
    String buttonText;



    public String getHeading() {
    return heading;
}

public String getImage() {
    return image;
}

public String getButtonLink() {
    return buttonLink;
}

public String getImageAlt() {
    return imageAlt;
}

public String getDescription() {
    return description;
}

public String getButtonText() {
    return buttonText;
}

}
