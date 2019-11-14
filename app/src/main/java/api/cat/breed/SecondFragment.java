package api.cat.breed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Breed> list = new ArrayList<>();
    private BreedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_b, container, false);
        recyclerView = rootView.findViewById(R.id.RecyclerView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BreedAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new BreedAdapter.OnClick() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BreedDetailActivity.class);
                intent.putExtra("catId",list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        BreedDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), BreedDatabase.class, "database")
                .allowMainThreadQueries() .build();
        List<Breed> breeds = db.dao().getBreeds();
        list.clear();
        list.addAll(breeds);
        adapter.notifyDataSetChanged();
    }


}
