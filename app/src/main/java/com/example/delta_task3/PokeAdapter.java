package com.example.delta_task3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.PokeAdapterholer> implements Filterable {
    String[] results;
    Context context;
    String[] images;
    List<String> copylist;
    List<String> original;
    List<Integer> index;
    boolean search = false;
    List<Integer> index1;
    float x, y;
    float x1, y1;
    FavDatabase favDatabase;
    int clicked;
    List<Integer> indexcopy;

    public PokeAdapter(List<String> results, Context context, List<Integer> index) {
        this.results = new String[results.size()];
        original = results;
        results.toArray(this.results);
        this.context = context;
        copylist = new ArrayList<>(results);
        favDatabase = Room.databaseBuilder(context, FavDatabase.class, "userdb").allowMainThreadQueries().build();
        this.index = index;
        indexcopy = new ArrayList<Integer>(index);
    }

    @NonNull
    @Override
    public PokeAdapterholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.pokemo, viewGroup, false);
        return (new PokeAdapterholer(view));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final PokeAdapterholer pokeAdapterholer, final int i) {

        //  index.add(i);

        final int position = i;
        pokeAdapterholer.textView.setText(original.get(i).toUpperCase());
        try {
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (index.get(i)) + ".png").into(pokeAdapterholer.imageView);
        } catch (Exception e) {

        }
        pokeAdapterholer.linearLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Information.class);
                intent.putExtra("id", (index.get(i)));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context,
                                pokeAdapterholer.imageView,
                                ViewCompat.getTransitionName(pokeAdapterholer.imageView));
                context.startActivity(intent, options.toBundle());


                //context.startActivity(intent);
            }
        });


        pokeAdapterholer.linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        clicked = index.get(i);
                        break;


                    case MotionEvent.ACTION_UP:
                        x1 = event.getX();
                        y1 = event.getY();

                        if (Math.abs(x1 - x) >= 50) {
                            try {
                                Favourites favourites = new Favourites();
                                favourites.setId(clicked);
                                favourites.setPokemon(original.get(i));
                                favDatabase.favouritesDao().addPoke(favourites);
                                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(context, "Failed to add to favourites,Try Again!", Toast.LENGTH_SHORT).show();

                            }

                        }
                        break;


                }
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return original.size();
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     *
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> list = new ArrayList<>();
            index1 = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                list.addAll(copylist);
                index1.addAll(indexcopy);
            } else {
                String filterpattern = constraint.toString().toUpperCase().trim();
                char ch = filterpattern.charAt(0);
                boolean number = false;
                int f;
                if (ch >= 'A' && ch <= 'Z') {
                    number = false;
                } else {
                    number = true;
                }
                int i = 1;
                if (number == false) {
                    for (String item : MainActivity.back1) {
                        if (item.toUpperCase().contains(filterpattern)) {

                            index1.add(i);
                            list.add(item);
                        }
                        i++;
                    }
                } else {
                    try {
                        f = Integer.parseInt(filterpattern);
                        for (Integer integer : MainActivity.back2) {
                            if (integer.equals(f)) {
                                index1.add(i);
                                list.add(MainActivity.back1.get(i - 1));
                            }
                            i++;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = list;
            //  search=true;
            //  index=index1;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //  search=true;
            original.clear();
            index.clear();

            original.addAll((List) results.values);
            index.addAll(index1);


            notifyDataSetChanged();
        }
    };


    public class PokeAdapterholer extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;
        CardView cardView;

        public PokeAdapterholer(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.pokemi);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linee);
            cardView = (CardView) itemView.findViewById(R.id.cards);
        }
    }


}
