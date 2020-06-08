
package com.example.delta_task3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EvolvesTo_ {

    @SerializedName("evolves_to")
    @Expose
    private List<Object> evolvesTo = null;
    @SerializedName("is_baby")
    @Expose
    private Boolean isBaby;
    @SerializedName("species")
    @Expose
    private Species species;

    public List<Object> getEvolvesTo() {
        return evolvesTo;
    }

    public void setEvolvesTo(List<Object> evolvesTo) {
        this.evolvesTo = evolvesTo;
    }

    public Boolean getIsBaby() {
        return isBaby;
    }

    public void setIsBaby(Boolean isBaby) {
        this.isBaby = isBaby;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

}
