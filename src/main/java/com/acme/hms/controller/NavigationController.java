package com.acme.hms.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Coordinates navigation between UI panels displayed within the main frame.
 */
public class NavigationController {
    public static final String PROPERTY_ACTIVE_VIEW = "activeView";
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private String activeView = "dashboard";

    public void setActiveView(String view) {
        String old = this.activeView;
        this.activeView = view;
        changeSupport.firePropertyChange(PROPERTY_ACTIVE_VIEW, old, view);
    }

    public String getActiveView() {
        return activeView;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
