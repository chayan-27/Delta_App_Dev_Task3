package com.example.delta_task3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
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

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeHolder> implements Filterable {
    String[] types;
    Context context;
    String check;
    String[] ids;
    List<String> copylist;
    List<String> original;
    List<Integer> index = new ArrayList<>();
    List<Integer> index1;
    float x, y;
    float x1, y1;
    FavDatabase favDatabase;
    int clicked;

    public TypeAdapter(List<String> list, Context context, String check, List<String> list1) {
        types = new String[list.size()];
        list.toArray(types);
        this.context = context;
        this.check = check;
        if (list1 != null) {
            ids = new String[list1.size()];
            list1.toArray(ids);
        }
        original = new ArrayList<>(list);
        copylist = new ArrayList<>(list);
        favDatabase = Room.databaseBuilder(context, FavDatabase.class, "userdb").allowMainThreadQueries().build();


    }


    @NonNull
    @Override
    public TypeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.pokemo, viewGroup, false);
        return (new TypeHolder(view));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final TypeHolder typeHolder, final int i) {
        final int position = i;
        index.add(i);
        typeHolder.textView.setText(original.get(i).toUpperCase());


        typeHolder.imageView.setVisibility(View.INVISIBLE);

        if ((check.equals("type")) || check.equals("region")) {
            if ((typeHolder.imageView) != null) {
                try {
                    ((ViewGroup) typeHolder.imageView.getParent()).removeView(typeHolder.imageView);
                } catch (Exception e) {

                }

            }
            typeHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TypePoke.class);
                    intent.putExtra("id", (index.get(i) + 1));
                    intent.putExtra("check", check);
                    context.startActivity(intent);
                }
            });
        }
        if (check.equals("no") || check.equals("hello")) {
            typeHolder.imageView.setVisibility(View.VISIBLE);
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + ids[i] + ".png").into(typeHolder.imageView);
            typeHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Information.class);
                    intent.putExtra("id", Integer.parseInt(ids[i]));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context,
                                    typeHolder.imageView,
                                    ViewCompat.getTransitionName(typeHolder.imageView));
                    context.startActivity(intent, options.toBundle());
                }
            });
            if(check.equals("no")) {
                typeHolder.linearLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:
                                x = event.getX();
                                y = event.getY();
                                clicked = Integer.parseInt(ids[i]);
                                break;


                            case MotionEvent.ACTION_UP:
                                x1 = event.getX();
                                y1 = event.getY();

                                if (Math.abs(x1-x)>=50) {
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
        } else if (!(check.equals(""))) {

            if ((check.substring(0, 4).equals("more"))) {
                ((ViewGroup) typeHolder.imageView.getParent()).removeView(typeHolder.imageView);
                typeHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TypePoke.class);
                        intent.putExtra("id", (position + 1));
                        intent.putExtra("check", check.substring(0, 4));
                        intent.putExtra("pokemon_id", check.substring(4));
                        context.startActivity(intent);
                    }
                });
            } else if (check.equals("stats") || check.equals("pokedex") || check.equals("region1")) {
                try {
                    ((ViewGroup) typeHolder.imageView.getParent()).removeView(typeHolder.imageView);
                } catch (Exception e) {
                }
                if (check.equals("region1")) {
                    typeHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, TypePoke.class);
                            intent.putExtra("id", (position + 1));
                            intent.putExtra("check", "region2");
                            intent.putExtra("pokemon_id", ids[position]);
                            context.startActivity(intent);
                        }
                    });
                }
            } else if (check.equals("region3")) {
                typeHolder.imageView.setVisibility(View.VISIBLE);
                Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + ids[i] + ".png").into(typeHolder.imageView);
                typeHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Information.class);
                        intent.putExtra("id", Integer.parseInt(ids[i]));
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) context,
                                        typeHolder.imageView,
                                        ViewCompat.getTransitionName(typeHolder.imageView));
                        context.startActivity(intent, options.toBundle());
                    }
                });
                typeHolder.linearLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:
                                x = event.getX();
                                y = event.getY();
                                clicked = Integer.parseInt(ids[i]);
                                break;


                            case MotionEvent.ACTION_UP:
                                x1 = event.getX();
                                y1 = event.getY();

                                if (Math.abs(x1-x)>=50) {
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

            } else if (check.equals("evolution")) {
                typeHolder.imageView.setVisibility(View.VISIBLE);
                Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + ids[i] + ".png").into(typeHolder.imageView);

            }
        }
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
                int i = 0;
                if (number == false) {
                    for (String item : copylist) {
                        if (item.toUpperCase().contains(filterpattern)) {
                            index1.add(i);

                            list.add(item);
                        }
                        i++;
                    }
                } else {
                    try {
                        f = Integer.parseInt(filterpattern);
                        for (int i1 = 0; i1 < copylist.size(); i1++) {
                            if ((f - 1) == i1) {
                                index1.add(i1);
                                list.add(copylist.get(i1));
                            }

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
            original.clear();
            index.clear();
            original.addAll((List) results.values);
            index.addAll(index1);
            notifyDataSetChanged();
        }
    };

    public class TypeHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;

        public TypeHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.pokemi);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linee);
        }
    }
}
