package com.udcp.core.models;

import java.util.List;

public interface UniversityNotificationModel {

    String getHeading();
    String getDescription();
    public List<NotificationItem> getNotificationItems();

}
