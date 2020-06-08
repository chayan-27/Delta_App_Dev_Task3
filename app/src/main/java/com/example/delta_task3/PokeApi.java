package com.example.delta_task3;

import android.support.v4.os.IResultReceiver;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeApi {
    @GET("api/v2/pokemon")
    Call<Pokem> getPoke(@Query("offset")int offset,@Query("limit")int limit);

    @GET("api/v2/type")
    Call<Types> getTypes();

    @GET("api/v2/region/")
    Call<Regions> getRegions();

    @GET(".")
    Call<PokemonType> getPokeType();

    @GET(".")
    Call<Entries> getRegionLoc();

    @GET(".")
    Call<StatsnType> getStatsnType();

    @GET(".")
    Call<EvolutionChain> getEvolutionChain();

    @GET(".")
    Call<Chains> getData();

    @GET(".")
    Call<PokedexNumb> getPokedex();

    @GET(".")
    Call<Pokedexes> getPokedexes();
}
