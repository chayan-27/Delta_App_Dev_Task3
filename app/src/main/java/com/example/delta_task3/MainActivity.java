package com.example.delta_task3;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    PokeAdapter pokeAdapter;
    TypeAdapter typeAdapter;
    String unique;
    String id;
    Button button;
    String sharebody = "FAVOURITES\n\n";
    MenuItem search;
    int adapter = 5;
    Retrofit retrofit1;
    Retrofit retrofit2;
    PokeApi pokeApi1;
    PokeApi pokeApi2;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Retrofit retrofit;
    PokeApi pokeApi;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    FavDatabase favDatabase;
    int limit = 20, offset = 0;
    List<String> des;
    boolean isScrolling = false;
    int currentItems, totalItems;
    int scrolloutitems;
    LinearLayoutManager layoutManager;
    int i = 0;
    List<Integer> ii;
    public static List<String> back1;
    public static List<Integer> back2;
    boolean hello;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolBar();
        navigationView = (NavigationView) findViewById(R.id.meenuu);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        button = (Button) findViewById(R.id.share);
        // search.setVisible(false);
        textView = (TextView) findViewById(R.id.welcome);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pokeApi = retrofit.create(PokeApi.class);
        Call<Pokem> call = pokeApi.getPoke(0, 807);
        call.enqueue(new Callback<Pokem>() {
            @Override
            public void onResponse(Call<Pokem> call, Response<Pokem> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Result> results = response.body().getResults();
                back1 = new ArrayList<>();
                back2 = new ArrayList<>();
                for (Result result : results) {
                    back1.add(result.getName());
                    unique = result.getUrl();
                    id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);
                    back2.add(Integer.parseInt(id));

                }
            }

            @Override
            public void onFailure(Call<Pokem> call, Throwable t) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.Pokemons:

                        offset = 0;
                        textView.setVisibility(View.GONE);
                        search.setVisible(true);
                        button.setVisibility(View.GONE);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        hello = true;
                        progressBar.setVisibility(View.VISIBLE);
                        retrofit = new Retrofit.Builder()
                                .baseUrl("https://pokeapi.co/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        pokeApi = retrofit.create(PokeApi.class);
                        Call<Pokem> call = pokeApi.getPoke(offset, limit);
                        call.enqueue(new Callback<Pokem>() {
                            @Override
                            public void onResponse(Call<Pokem> call, Response<Pokem> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "List not Found", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                List<Result> results = response.body().getResults();

                                des = new ArrayList<>();
                                ii = new ArrayList<>();


                                for (Result result : results) {
                                    des.add(result.getName());
                                    unique = result.getUrl();
                                    id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);

                                    ii.add(Integer.parseInt(id));
                                    i++;

                                }


                                pokeAdapter = new PokeAdapter(des, MainActivity.this, ii);
                                recyclerView.setAdapter(pokeAdapter);
                                adapter = 0;
                                progressBar.setVisibility(View.INVISIBLE);
                                //  fetchmore(offset+20,limit);


                                Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Pokem> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                            }
                        });

                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                        break;
                    case R.id.Types:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        textView.setVisibility(View.GONE);
                        search.setVisible(true);
                        button.setVisibility(View.GONE);
                        offset = 0;
                        progressBar.setVisibility(View.VISIBLE);
                        retrofit = new Retrofit.Builder()
                                .baseUrl("https://pokeapi.co/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        pokeApi = retrofit.create(PokeApi.class);
                        Call<Types> call1 = pokeApi.getTypes();
                        call1.enqueue(new Callback<Types>() {
                            @Override
                            public void onResponse(Call<Types> call, Response<Types> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "List not Found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                List<Result> results = response.body().getResults();
                                List<String> type = new ArrayList<>();
                                for (Result result : results) {
                                    type.add(result.getName());
                                }
                                typeAdapter = new TypeAdapter(type, MainActivity.this, "type", null);
                                recyclerView.setAdapter(typeAdapter);
                                adapter = 1;
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Types> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                            }
                        });
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


                        break;
                    case R.id.regions:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        textView.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
                        search.setVisible(true);
                        offset = 0;
                        progressBar.setVisibility(View.VISIBLE);
                        retrofit = new Retrofit.Builder()
                                .baseUrl("https://pokeapi.co/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        pokeApi = retrofit.create(PokeApi.class);
                        Call<Regions> call2 = pokeApi.getRegions();
                        call2.enqueue(new Callback<Regions>() {
                            @Override
                            public void onResponse(Call<Regions> call, Response<Regions> response) {
                                if (!response.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "List not Found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                List<Result> results = response.body().getResults();
                                List<String> region = new ArrayList<>();
                                for (Result result : results) {
                                    region.add(result.getName());
                                }
                                typeAdapter = new TypeAdapter(region, MainActivity.this, "region", null);
                                recyclerView.setAdapter(typeAdapter);
                                adapter = 1;
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Regions> call, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

                            }
                        });
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                        break;

                    case R.id.favourites:
                        System.out.println("Favourites");
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        offset = 0;
                        adapter = 5;
                        favDatabase = Room.databaseBuilder(MainActivity.this, FavDatabase.class, "userdb").allowMainThreadQueries().build();

                        button.setVisibility(View.VISIBLE);
                        search.setVisible(false);

                        List<Favourites> list = favDatabase.favouritesDao().getPokes();
                        List<String> list1 = new ArrayList<>();
                        List<String> list2 = new ArrayList<>();
                        String r;
                        for (Favourites favourites : list) {
                            list1.add(favourites.getPokemon());
                            r = String.valueOf(favourites.getId());

                            list2.add(r);
                            sharebody = sharebody + favourites.getPokemon().toUpperCase() + "\n" + "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + favourites.getId() + ".png\n\n";
                        }
                        if (list1 != null && list1.size() > 0) {
                            textView.setVisibility(View.GONE);
                            recyclerView.setAdapter(new TypeAdapter(list1, MainActivity.this, "hello", list2));
                        } else {
                            // textView.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.INVISIBLE);
                            // textView.setText("No Data Available");
                            list1.add("EMPTY");
                            recyclerView.setAdapter(new TypeAdapter(list1, MainActivity.this, "no data", null));

                            button.setVisibility(View.INVISIBLE);
                        }
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                }

                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrolloutitems = layoutManager.findFirstVisibleItemPosition();
                if (isScrolling && ((currentItems + scrolloutitems) == totalItems) && adapter == 0) {
                    offset = offset + 20;
                    fetchmore(offset, limit);
                    isScrolling = false;


                }
            }
        });


    }

    private void fetchmore(int offset, int limit) {
        progressBar.setVisibility(View.VISIBLE);
        Call<Pokem> call = pokeApi.getPoke(offset, limit);
        call.enqueue(new Callback<Pokem>() {
            @Override
            public void onResponse(Call<Pokem> call, Response<Pokem> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "List not Found", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Result> results = response.body().getResults();


                for (Result result : results) {
                    des.add(result.getName());
                    unique = result.getUrl();
                    id = unique.substring(unique.lastIndexOf("/", unique.length() - 2) + 1, unique.length() - 1);

                    ii.add(Integer.parseInt(id));
                    i++;
                    pokeAdapter.notifyDataSetChanged();
                    // recyclerView.setAdapter(pokeAdapter);

                }


                progressBar.setVisibility(View.INVISIBLE);


                Toast.makeText(MainActivity.this, "More Data Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Pokem> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Failure Please Try Again", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupToolBar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));



        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.parseColor("#FFFFFF"));
        actionBarDrawerToggle.getDrawerArrowDrawable().setGapSize(20f);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        SearchView searchView = (SearchView) search.getActionView();
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        ImageView back= (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
       back.setColorFilter(Color.parseColor("#FFFFFF"));
        searchEditText.setTextColor(Color.parseColor("#FFFFFF"));
        searchEditText.setHintTextColor(Color.GRAY);
        searchEditText.setHint("Enter name or id");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (adapter == 0) {
                    pokeAdapter.getFilter().filter(s);
                    if (offset >= 20)
                        offset = 0;
                } else if (adapter == 1) {
                    typeAdapter.getFilter().filter(s);
                }
                return false;
            }
        });
        return true;
    }



    public void share(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharesubject = "FAVOURITES";
        intent.putExtra(Intent.EXTRA_SUBJECT, sharesubject);
        intent.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(intent, "Share Using"));
    }
}
