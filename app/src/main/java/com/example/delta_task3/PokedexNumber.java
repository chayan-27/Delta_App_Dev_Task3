
package com.example.delta_task3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokedexNumber {

    @SerializedName("entry_number")
    @Expose
    private Integer entryNumber;
    @SerializedName("pokedex")
    @Expose
    private Pokedex pokedex;

    public Integer getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(Integer entryNumber) {
        this.entryNumber = entryNumber;
    }

    public Pokedex getPokedex() {
        return pokedex;
    }

    public void setPokedex(Pokedex pokedex) {
        this.pokedex = pokedex;
    }

}
