package com.example.delta_task3;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Information extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information);
        int s=getIntent().getIntExtra("id",1);
        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + s+ ".png").into((ImageView)findViewById(R.id.given));

        List<String> list=new ArrayList<>();
        list.add("EVOLUTION CHAIN");
        list.add("STATS");
        list.add("TYPE");
        list.add("REGION");
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.inform);
        recyclerView.setLayoutManager(new LinearLayoutManager(Information.this));
        recyclerView.setAdapter(new TypeAdapter(list,this,"more"+s,null));



     /*  FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framee,new PokeInform()).addToBackStack(null).commit();*/
    }

}
