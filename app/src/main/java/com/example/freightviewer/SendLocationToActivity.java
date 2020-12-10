package com.example.freightviewer;

import android.location.Location;

public class SerndLocationToActivity {
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SerndLocationToActivity(Location mLocation) {
        this.location=mLocation;
    }
}
