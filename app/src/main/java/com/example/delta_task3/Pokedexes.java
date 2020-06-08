package com.example.delta_task3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokedexes {
    @SerializedName("pokedexes")
    @Expose
    private List<Pokedex> pokemonEntries = null;

    public List<Pokedex> getPokemonEntries() {
        return pokemonEntries;
    }

    public void setPokemonEntries(List<Pokedex> pokemonEntries) {
        this.pokemonEntries = pokemonEntries;
    }
}
