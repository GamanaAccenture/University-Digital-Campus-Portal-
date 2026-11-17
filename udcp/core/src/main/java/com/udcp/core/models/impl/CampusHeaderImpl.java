package com.udcp.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.CampusHeader;
import com.udcp.core.models.NavigationLink;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = CampusHeader.class,
    resourceType = "udcp/components/udcpcomponents/header",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CampusHeaderImpl implements CampusHeader {

    @ValueMapValue
    public String logo;

    @ChildResource(name = "navigationLinks")
    public List<NavigationLink> navigationLinkResources;

    @ValueMapValue
    public String applyButtonText;

    @ValueMapValue
    public String applyButtonLink;

    @Override
    public String getLogo() {
        return logo;
    }

    @Override
    public List<NavigationLink> getNavigationLinks() {
         return navigationLinkResources;   
    }

    @Override
    public String getApplyButtonText() {
        return applyButtonText;
    }

    @Override
    public String getApplyButtonLink() {
        return applyButtonLink;
    }

   

    
}
