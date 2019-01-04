package com.sanshy.dhanawanshi.SingleItems;

import java.util.Date;

public class SingleNotificationItem {

    String Id;
    String NotificationTitle;
    String NotificationBody;
    Date NotificationDate;


    public SingleNotificationItem(String id, String notificationTitle, String notificationBody, Date notificationDate) {
        Id = id;
        NotificationTitle = notificationTitle;
        NotificationBody = notificationBody;
        NotificationDate = notificationDate;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return NotificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        NotificationBody = notificationBody;
    }

    public Date getNotificationDate() {
        return NotificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        NotificationDate = notificationDate;
    }
}
