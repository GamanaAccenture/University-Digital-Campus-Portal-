package com.udcp.core.models.impl;

import java.util.List;

import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.udcp.core.models.UpcomingEvent;
import com.udcp.core.models.UpcomingEventsList;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;



@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {UpcomingEventsList.class},
   resourceType = "udcp/components/udcpcomponents/upcoming-events",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class UpcomingEventListImpl implements UpcomingEventsList {
  

    @ChildResource(name = "eventsTab")
    List<UpcomingEvent> getUpcomingEvents;
    @Override
    public List<UpcomingEvent> getUpcomingEVents()
    {
        return getUpcomingEvents;
    }

   

}
