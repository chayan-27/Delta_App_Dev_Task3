
package com.example.delta_task3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chain {

    @SerializedName("evolution_details")
    @Expose
    private List<Object> evolutionDetails = null;
    @SerializedName("evolves_to")
    @Expose
    private List<EvolvesTo> evolvesTo = null;
    @SerializedName("is_baby")
    @Expose
    private Boolean isBaby;
    @SerializedName("species")
    @Expose
    private Species__ species;

    public List<Object> getEvolutionDetails() {
        return evolutionDetails;
    }

    public void setEvolutionDetails(List<Object> evolutionDetails) {
        this.evolutionDetails = evolutionDetails;
    }

    public List<EvolvesTo> getEvolvesTo() {
        return evolvesTo;
    }

    public void setEvolvesTo(List<EvolvesTo> evolvesTo) {
        this.evolvesTo = evolvesTo;
    }

    public Boolean getIsBaby() {
        return isBaby;
    }

    public void setIsBaby(Boolean isBaby) {
        this.isBaby = isBaby;
    }

    public Species__ getSpecies() {
        return species;
    }

    public void setSpecies(Species__ species) {
        this.species = species;
    }

}
