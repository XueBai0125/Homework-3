package api.cat.breed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {



    private List<Breed> list;
    private RecyclerView recyclerviw;
    private BreedAdapter breedAdapter;
    EditText etKeyWord;
    private RequestQueue queue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_a, container, false);
        recyclerviw = root.findViewById(R.id.RecyclerView);
        etKeyWord = root.findViewById(R.id.et_keyword);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         queue = Volley.newRequestQueue(getActivity());
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviw.setLayoutManager(linearLayoutManager);
        breedAdapter = new BreedAdapter(getActivity(),list);
        recyclerviw.setAdapter(breedAdapter);
        breedAdapter.setOnClickListener(new BreedAdapter.OnClick() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BreedDetailActivity.class);
                intent.putExtra("catId",list.get(position).getId());
                startActivity(intent);
            }
        });
        view.findViewById(R.id.iv_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = etKeyWord.getText().toString().trim();
                loadBreeds(keyword);
            }
        });
    }

    public void loadBreeds(String key) {
        String url = "https://api.thecatapi.com/v1/breeds/search?q="+key;
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
        } };
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Breed[] breeds = gson.fromJson(response, Breed[].class);
                        List<Breed> breedList = Arrays.asList(breeds);
                        list.clear();
                        list.addAll(breedList);
                        breedAdapter.notifyDataSetChanged();
                    }
                }, errorListener);
        stringRequest.setTag("search");
        queue.add(stringRequest);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        queue.cancelAll("search");
    }
}