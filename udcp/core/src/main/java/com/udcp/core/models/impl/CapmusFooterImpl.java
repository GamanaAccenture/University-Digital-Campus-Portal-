package com.udcp.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.CampusFooter;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {CampusFooter.class},
    resourceType = "udcp/components/udcpcomponents/footer",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CapmusFooterImpl  implements  CampusFooter {


    @ValueMapValue
    String logo;
    @ValueMapValue 
    String aboutText;
    @ValueMapValue
    String applyButtonLink;
    @ValueMapValue
    String address;
    @ValueMapValue
    String phone;
    @ValueMapValue
    String email;
    @ValueMapValue
    String copyright;


    @Override
    public String getLogo() {
        // TODO Auto-generated method stub
       return logo;
    }

    @Override
    public String getAboutText() {
        // TODO Auto-generated method stub
        return aboutText;
    }

    @Override
    public String getApplyButtonLink() {
        // TODO Auto-generated method stub
       return applyButtonLink;
    }

    @Override
    public String getAddress() {
        // TODO Auto-generated method stub
        return address;
    }

    @Override
    public String getPhone() {
        // TODO Auto-generated method stub
        return phone;
        
    }

    @Override
    public String getEmail() {
        // TODO Auto-generated method stub
        return email;
    }

    @Override
    public String getCopyright() {
        // TODO Auto-generated method stub
        return copyright;
    }



}
