package com.example.delta_task3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypePoke extends AppCompatActivity {
    Retrofit retrofit;
    RecyclerView recyclerView;
    String url;
    int id;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_poke);
        progressBar = (ProgressBar) findViewById(R.id.prog);
        Intent intent = getIntent();
        if (intent.getStringExtra("check").equals("type")) {
            url = "https://pokeapi.co/api/v2/type/" + intent.getIntExtra("id", 1) + "/";
        } else if (intent.getStringExtra("check").equals("region")) {
            url = "https://pokeapi.co/api/v2/region/" + (intent.getIntExtra("id", 1)) + "/";
        } else if (intent.getStringExtra("check").equals("more")) {
            id = intent.getIntExtra("id", 1);
            String pokemon = intent.getStringExtra("pokemon_id");
            if (id == 1) {
                url=" https://pokeapi.co/api/v2/pokemon-species/"+pokemon+"/";

            } else if (id == 2) {
                url = "https://pokeapi.co/api/v2/pokemon/" + pokemon + "/";
            } else if (id == 3) {
                url = "https://pokeapi.co/api/v2/pokemon/" + pokemon + "/";

            } else if (id == 4) {
                url=" https://pokeapi.co/api/v2/pokemon-species/"+pokemon+"/";

            }
        }else if(intent.getStringExtra("check").equals("region2")){
            url=intent.getStringExtra("pokemon_id");
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recyclerView = (RecyclerView) findViewById(R.id.pokeintype);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (intent.getStringExtra("check").equals("type")) {
            progressBar.setVisibility(View.VISIBLE);
            Call<PokemonType> call = retrofit.create(PokeApi.class).getPokeType();
            call.enqueue(new Callback<PokemonType>() {
                @Override
                public void onResponse(Call<PokemonType> call, Response<PokemonType> response) {
                    if (!response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Pokemon> list = response.body().getPokemon();
                    List<String> name = new ArrayList<>();
                    List<String> ids = new ArrayList<>();
                    String unique;
                    String id;
                    for (Pokemon pokemon : list) {
                        name.add(pokemon.getPokemon().getName());
                        unique = pokemon.getPokemon().getUrl();
                        id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);
                        ids.add(id);
                    }
                    recyclerView.setAdapter(new TypeAdapter(name, TypePoke.this, "no", ids));
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<PokemonType> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (intent.getStringExtra("check").equals("region")) {
            progressBar.setVisibility(View.VISIBLE);
            Call<Pokedexes> call = retrofit.create(PokeApi.class).getPokedexes();
            call.enqueue(new Callback<Pokedexes>() {
                @Override
                public void onResponse(Call<Pokedexes> call, Response<Pokedexes> response) {
                    if (!response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Pokedex> list = response.body().getPokemonEntries();
                    List<String> list1 = new ArrayList<>();
                    List<String> list2=new ArrayList<>();
                    for (Pokedex pokedex : list) {
                        list1.add(pokedex.getName());
                        list2.add(pokedex.getUrl());
                    }
                    recyclerView.setAdapter(new TypeAdapter(list1, TypePoke.this, "region1", list2));
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Pokedexes> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                }
            });

        } else if (intent.getStringExtra("check").equals("more")) {
            if (id == 1) {
                progressBar.setVisibility(View.VISIBLE);
                Call<EvolutionChain> call=retrofit.create(PokeApi.class).getEvolutionChain();
                call.enqueue(new Callback<EvolutionChain>() {
                    @Override
                    public void onResponse(Call<EvolutionChain> call, Response<EvolutionChain> response) {
                        if (!response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String link=response.body().getEvolutionChain().getUrl();
                        retrofit = new Retrofit.Builder()
                                .baseUrl(link)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Call<Chains> chainsCall=retrofit.create(PokeApi.class).getData();
                        chainsCall.enqueue(new Callback<Chains>() {
                            @Override
                            public void onResponse(Call<Chains> call, Response<Chains> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                List<String> list=new ArrayList<>();
                                List<String> list1=new ArrayList<>();
                                String name,unique,id;
                                Chain chain=response.body().getChain();
                                List<EvolvesTo> evolvesTos=chain.getEvolvesTo();
                                for(EvolvesTo evolvesTo:evolvesTos){
                                    List<EvolvesTo_> evolvesTos1=evolvesTo.getEvolvesTo();
                                    for(EvolvesTo_ evolvesTo_:evolvesTos1){
                                         name=evolvesTo_.getSpecies().getName()+"\n↑";
                                       unique=evolvesTo_.getSpecies().getUrl();
                                        id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);
                                        list.add(name);
                                        list1.add(id);

                                    }

                                    list.add(evolvesTo.getSpecies().getName()+"\n↑");
                                    unique=evolvesTo.getSpecies().getUrl();
                                    id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);
                                    list1.add(id);


                                }
                                list.add(chain.getSpecies().getName());
                                unique=chain.getSpecies().getUrl();
                                id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);
                                list1.add(id);
                                recyclerView.setAdapter(new TypeAdapter(list, TypePoke.this, "evolution", list1));
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onFailure(Call<Chains> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<EvolutionChain> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                    }
                });

            } else if (id == 2) {
                progressBar.setVisibility(View.VISIBLE);
                Call<StatsnType> call = retrofit.create(PokeApi.class).getStatsnType();
                call.enqueue(new Callback<StatsnType>() {
                    @Override
                    public void onResponse(Call<StatsnType> call, Response<StatsnType> response) {
                        if (!response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<Stat> list = response.body().getStats();
                        List<String> list1 = new ArrayList<>();
                        for (Stat stat : list) {
                            int base = stat.getBaseStat();
                            String name = stat.getStat().getName() + " : ";
                            list1.add(name + base);
                        }
                        recyclerView.setAdapter(new TypeAdapter(list1, TypePoke.this, "stats", null));
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<StatsnType> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();


                    }
                });

            } else if (id == 3) {
                progressBar.setVisibility(View.VISIBLE);
                Call<StatsnType> call = retrofit.create(PokeApi.class).getStatsnType();
                call.enqueue(new Callback<StatsnType>() {
                    @Override
                    public void onResponse(Call<StatsnType> call, Response<StatsnType> response) {
                        if (!response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<Type> list = response.body().getTypes();
                        List<String> list1 = new ArrayList<>();
                        for (Type type : list) {

                            String name = type.getType().getName();
                            list1.add(name);
                        }
                        recyclerView.setAdapter(new TypeAdapter(list1, TypePoke.this, "stats", null));
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<StatsnType> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (id == 4) {
                progressBar.setVisibility(View.VISIBLE);
                Call<PokedexNumb> call = retrofit.create(PokeApi.class).getPokedex();
                call.enqueue(new Callback<PokedexNumb>() {
                    @Override
                    public void onResponse(Call<PokedexNumb> call, Response<PokedexNumb> response) {
                        if (!response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<String> list=new ArrayList<>();
                        List<PokedexNumber> list1=response.body().getPokedexNumbers();
                        for(PokedexNumber pokedexNumber:list1){
                            list.add(pokedexNumber.getPokedex().getName());
                        }
                        recyclerView.setAdapter(new TypeAdapter(list, TypePoke.this, "pokedex", null));
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PokedexNumb> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();


                    }
                });

            }

        }else if(intent.getStringExtra("check").equals("region2")){
            progressBar.setVisibility(View.VISIBLE);
            Call<Entries> entriesCall=retrofit.create(PokeApi.class).getRegionLoc();
            entriesCall.enqueue(new Callback<Entries>() {
                @Override
                public void onResponse(Call<Entries> call, Response<Entries> response) {
                    if (!response.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(TypePoke.this, "List not Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<PokemonEntry> list=response.body().getPokemonEntries();
                    List<String> list1=new ArrayList<>();
                    List<String> list2=new ArrayList<>();
                    String unique;
                    String id;
                    for(PokemonEntry pokemonEntry:list){
                        list1.add(pokemonEntry.getPokemonSpecies().getName());
                        unique=pokemonEntry.getPokemonSpecies().getUrl();
                        id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);
                        list2.add(id);

                    }
                    recyclerView.setAdapter(new TypeAdapter(list1,TypePoke.this,"region3",list2));
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TypePoke.this, "Data Loaded", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onFailure(Call<Entries> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(TypePoke.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
