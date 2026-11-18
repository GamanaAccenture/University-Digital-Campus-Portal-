package com.udcp.core.models.impl;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.AcademicOverview;
import com.udcp.core.models.AcademicOverviewItem;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {AcademicOverview.class},
    resourceType = "udcp/components/udcpcomponents/academic-overview",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AcademicOverviewImpl implements AcademicOverview {

    @ValueMapValue
    private String heading;
    @ValueMapValue
    private String subheading;
    @ValueMapValue
    private String image;
    @ValueMapValue
    private String imageAlt;
    @ValueMapValue
    private String description;
    @ValueMapValue
    private String schoolsHeading;
    @ValueMapValue
    private String schoolsDescription;
    @ChildResource(name = "schools")
    private List<AcademicOverviewItem> schools;
    
    @ValueMapValue
    private String facilitiesHeading;
    @ValueMapValue
    private String facilitiesDescription;
    @ChildResource(name = "facilities")
    private List<AcademicOverviewItem> facilities;
   
    @ValueMapValue
    private String ctaHeading;
    @ValueMapValue
    private String ctaDescription;
    @ValueMapValue
    private String ctaText;
    @ValueMapValue
    private String ctaLink;

    @Override
    public String getHeading() { return heading; }
    @Override
    public String getSubheading() { return subheading; }
    @Override
    public String getImage() { return image; }
    @Override
    public String getImageAlt() { return imageAlt; }
    @Override
    public String getDescription() { return description; }
    @Override
    public String getSchoolsHeading() { return schoolsHeading; }
    @Override
    public String getSchoolsDescription() { return schoolsDescription; }
    @Override
    public List<AcademicOverviewItem> getSchools() { return schools; }
    
    @Override
    public String getFacilitiesHeading() { return facilitiesHeading; }
    @Override
    public String getFacilitiesDescription() { return facilitiesDescription; }
    @Override
    public List<AcademicOverviewItem> getFacilities() { return facilities; }
    
    @Override
    public String getCtaHeading() { return ctaHeading; }
    @Override
    public String getCtaDescription() { return ctaDescription; }
    @Override
    public String getCtaText() { return ctaText; }
    @Override
    public String getCtaLink() { return ctaLink; }
}
