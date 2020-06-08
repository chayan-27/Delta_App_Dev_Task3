
package com.example.delta_task3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {

    @SerializedName("pokemon")
    @Expose
    private Pokemon_ pokemon;
    @SerializedName("slot")
    @Expose
    private Integer slot;

    public Pokemon_ getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon_ pokemon) {
        this.pokemon = pokemon;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

}
