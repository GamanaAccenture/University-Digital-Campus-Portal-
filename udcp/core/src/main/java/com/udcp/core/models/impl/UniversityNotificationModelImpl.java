package com.udcp.core.models.impl;

import java.util.List;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.NotificationItem;
import com.udcp.core.models.UniversityNotificationModel;

@Model (

    adaptables = {SlingHttpServletRequest.class , Resource.class},
    adapters = {UniversityNotificationModel.class},
    resourceType = "udcp/components/udcpcomponents/university-notifications",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL

)
public class UniversityNotificationModelImpl implements UniversityNotificationModel {

    @ValueMapValue 
    String heading;
    @ValueMapValue 
    String description;
    @ChildResource(name = "notifications")
    List<NotificationItem> notificationitems;

    public String getHeading(){
        return heading;
    }
    public String getDescription()
    {
       return  description;
    }
    public List<NotificationItem> getNotificationItems(){
       return notificationitems;
    }

}
