package com.udcp.core.models.impl;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.UpcomingEvent;


@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {UpcomingEvent.class},

    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class UpcomingEventImpl  implements UpcomingEvent{
    
    @ValueMapValue
    String eventTitle;
    @ValueMapValue 
    String eventDate;
    @ValueMapValue 
    String eventTime;
    @ValueMapValue 
    String eventlocation;
    @ValueMapValue 
    String eventDescription;

    public String getEventTitle()
    {
        return eventTitle;
    }
    public String getEventDate()
    {
        return eventDate;
    }
    public String getEventTime()
    {
        return eventTime;
    }
    public String getEventLocation()
    {
        return eventlocation;
    }
    public String getEventDescription()
    {
        return eventDescription;
    }






}
