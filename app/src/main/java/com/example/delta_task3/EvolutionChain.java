
package com.example.delta_task3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EvolutionChain {

    @SerializedName("evolution_chain")
    @Expose
    private EvolutionChain_ evolutionChain;

    public EvolutionChain_ getEvolutionChain() {
        return evolutionChain;
    }

    public void setEvolutionChain(EvolutionChain_ evolutionChain) {
        this.evolutionChain = evolutionChain;
    }

}
