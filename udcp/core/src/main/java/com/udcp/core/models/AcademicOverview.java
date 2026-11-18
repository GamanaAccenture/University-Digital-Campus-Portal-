package com.udcp.core.models;

import java.util.List;

public interface AcademicOverview {
    String getHeading();
    String getSubheading();
    String getImage();
    String getImageAlt();
    String getDescription();
    String getSchoolsHeading();
    String getSchoolsDescription();
    List<AcademicOverviewItem> getSchools();
    String getFacilitiesHeading();
    String getFacilitiesDescription();
    List<AcademicOverviewItem> getFacilities();
    String getCtaHeading();
    String getCtaDescription();
    String getCtaText();
    String getCtaLink();
}
