package com.udcp.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import com.udcp.core.models.HeroBanner;

@Model(adaptables = { SlingHttpServletRequest.class  , Resource.class} 
    , adapters = {HeroBanner.class },
    resourceType = "udcp/components/udcpcomponents/hero-banner",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL

)
public class HeroBannerImpl  implements  HeroBanner{

    @ValueMapValue 
    public String headline;

    @ValueMapValue 
    public String description;
    @ValueMapValue 
    public String bannerImage;
    @ValueMapValue 
    public String buttonText;
    @ValueMapValue 
    public String buttonLink;
    
    
    @Override
    public String getHeadLine() {
        // TODO Auto-generated method stub
        return headline;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return  description;
        
    }

    @Override
    public String getBannerImage() {
        // TODO Auto-generated method stub
        return  bannerImage;
        
    }

    @Override
    public String getButtonText() {
        // TODO Auto-generated method stub
        return  buttonText;
        
    }

    @Override
    public String getButtonLink() {
        // TODO Auto-generated method stub
        return  buttonLink;
        
    }

}
