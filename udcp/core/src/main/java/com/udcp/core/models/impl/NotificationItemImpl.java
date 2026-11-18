package com.udcp.core.models.impl;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.udcp.core.models.NotificationItem;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;



@Model (

    adaptables = {SlingHttpServletRequest.class , Resource.class},
    adapters = {NotificationItem.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL

)

public class NotificationItemImpl  implements NotificationItem{

    @ValueMapValue 
    String title;
    @ValueMapValue 
    String description;
    @ValueMapValue 
    String category;
    @ValueMapValue 
    String openingDate;
    @ValueMapValue 
    String closingDate;
   

    public String getTitle(){
        return  title;

    }
    public String getDescription(){
        return  description;
    }
    public String getCategory()
    {
      return  category;
    }
    public String getOpeningDate(){
     return  openingDate;
    }
    public String getClosingDate(){
     return  closingDate;
    }
   

}
