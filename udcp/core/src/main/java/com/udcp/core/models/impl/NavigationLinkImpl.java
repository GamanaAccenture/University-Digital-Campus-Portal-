package com.udcp.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.NavigationLink;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = NavigationLink.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class NavigationLinkImpl  implements NavigationLink {

    @ValueMapValue 
    String linkText;
    @ValueMapValue
    String linkURL;

     public String getLinkText() {
            return linkText;
        }

        @Override
        public String getLinkURL() {
            return linkURL;
        }

}
