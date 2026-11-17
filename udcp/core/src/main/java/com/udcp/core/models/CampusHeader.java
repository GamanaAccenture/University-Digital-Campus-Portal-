package com.udcp.core.models;

import java.util.List;

public interface CampusHeader {

    String getLogo();

    List<NavigationLink> getNavigationLinks();

    String getApplyButtonText();

    String getApplyButtonLink();

    
}
