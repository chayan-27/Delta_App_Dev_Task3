
package com.example.delta_task3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonEntry {

    @SerializedName("entry_number")
    @Expose
    private Integer entryNumber;
    @SerializedName("pokemon_species")
    @Expose
    private PokemonSpecies pokemonSpecies;

    public Integer getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(Integer entryNumber) {
        this.entryNumber = entryNumber;
    }

    public PokemonSpecies getPokemonSpecies() {
        return pokemonSpecies;
    }

    public void setPokemonSpecies(PokemonSpecies pokemonSpecies) {
        this.pokemonSpecies = pokemonSpecies;
    }

}
