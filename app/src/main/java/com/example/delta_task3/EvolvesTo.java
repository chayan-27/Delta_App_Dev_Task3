
package com.example.delta_task3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EvolvesTo {

    @SerializedName("evolves_to")
    @Expose
    private List<EvolvesTo_> evolvesTo = null;
    @SerializedName("is_baby")
    @Expose
    private Boolean isBaby;
    @SerializedName("species")
    @Expose
    private Species_ species;

    public List<EvolvesTo_> getEvolvesTo() {
        return evolvesTo;
    }

    public void setEvolvesTo(List<EvolvesTo_> evolvesTo) {
        this.evolvesTo = evolvesTo;
    }

    public Boolean getIsBaby() {
        return isBaby;
    }

    public void setIsBaby(Boolean isBaby) {
        this.isBaby = isBaby;
    }

    public Species_ getSpecies() {
        return species;
    }

    public void setSpecies(Species_ species) {
        this.species = species;
    }

}
