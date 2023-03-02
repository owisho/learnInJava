package com.example.basic;

import javax.management.*;

public class SimpleStandard extends NotificationBroadcasterSupport implements SimpleStandardMBean {
    public String getState() {
        return this.state;
    }

    public void setState(String s) {
        state = s;
        nbChanges++;
    }

    public int getNbChanges() {
        return nbChanges;
    }

    public void reset() {
        AttributeChangeNotification acn = new AttributeChangeNotification(this, 0, 0,
                "NbChanges reset", "NbChanges", "Integer", nbChanges, 0);
        state = "initial state";
        nbChanges = 0;
        nbResets++;
        sendNotification(acn);
    }

    public int getNbResets() {
        return nbResets;
    }

    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(new String[]{AttributeChangeNotification.ATTRIBUTE_CHANGE}, AttributeChangeNotification.class.getName(),
                        "This notification is emitted when the reset() method is called.")
        };
    }

    private String state = "initial state";
    private int nbChanges = 0;
    private int nbResets = 0;
}
