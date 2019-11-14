package api.cat.breed;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import java.util.Arrays;
import java.util.List;

public class BreedDetailActivity extends AppCompatActivity {
    private String catId;
    private TextView tvName,tvDes,tvOrigin, tvLifeSpan, tvWiki, tvDogFriendly,tvTemperament,tvWeight;
    private ImageView ivImg;
    private Cats.BreedsBean breed;
    private ImageView ivButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
         catId = getIntent().getStringExtra("catId");
        initView();

        initData();
    }

    private void initView() {
        ivButton = findViewById(R.id.iv_button);
        tvName = findViewById(R.id.tv_name);
        tvDes = findViewById(R.id.tv_des);
        ivImg = findViewById(R.id.iv_img);
        tvOrigin = findViewById(R.id.textview_origin);
        tvLifeSpan = findViewById(R.id.text_life_span);
        tvWiki = findViewById(R.id.text_wiki_url);
        tvDogFriendly = findViewById(R.id.tv_dog_friend);
        tvTemperament = findViewById(R.id.text_temp);
        tvWeight = findViewById(R.id.text_weight);
        ivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (breed!=null) {
                    String id= breed.getId();
                    String name= breed.getName();
                    Breed breed = new Breed();
                    breed.setId(id);
                    breed.setName(name);
                    try {
                        BreedDatabase db = Room.databaseBuilder(getApplicationContext(), BreedDatabase.class, "database")
                                .allowMainThreadQueries() .build();
                        db.dao().coll(breed);
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void initData() {
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            } };
        String url = "https://api.thecatapi.com/v1/images/search?breed_ids="+catId;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Cats[] cats = gson.fromJson(response, Cats[].class);
                        List<Cats> catList = Arrays.asList(cats);
                        Cats cat = catList.get(0);
                        breed = cat.getBreeds().get(0);
                        tvName.setText(breed.getName());
                        tvDes.setText(breed.getDescription());
                        tvOrigin.setText("Origin:"+ breed.getOrigin());
                        tvLifeSpan.setText("Life span:"+ breed.getLife_span());
                        tvWiki.setText("Wikipedia link:"+ breed.getWikipedia_url());
                        tvDogFriendly.setText("Dog Friendly:"+ breed.getDog_friendly());
                        tvTemperament.setText("Temperament:"+ breed.getTemperament());
                        tvWeight.setText("Weight:"+ breed.getWeight().getMetric());
                        Glide.with(BreedDetailActivity.this).load(cat.getUrl()).into(ivImg);

                    }
                }, errorListener);
        requestQueue.add(stringRequest);
    }




}
