
package com.example.delta_task3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokedexNumb {

    @SerializedName("pokedex_numbers")
    @Expose
    private List<PokedexNumber> pokedexNumbers = null;

    public List<PokedexNumber> getPokedexNumbers() {
        return pokedexNumbers;
    }

    public void setPokedexNumbers(List<PokedexNumber> pokedexNumbers) {
        this.pokedexNumbers = pokedexNumbers;
    }

}
