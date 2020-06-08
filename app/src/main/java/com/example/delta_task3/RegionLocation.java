
package com.example.delta_task3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionLocation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }



}
