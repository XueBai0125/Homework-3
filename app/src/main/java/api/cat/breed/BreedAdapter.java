package api.cat.breed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class BreedAdapter extends RecyclerView.Adapter<BreedAdapter.CustomerViewHolder> {
    private Context context;
    private List<Breed> breedList;
    public BreedAdapter(Context context, List<Breed> breedList) {
        this.context = context;
        this.breedList = breedList;
    }

    public interface OnClick {
        void setOnItemClickListener(int position);
    }

    private OnClick onClickListener;
    public void setOnClickListener(OnClick listener ){
        this.onClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return breedList.size();
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.breed_item, parent,false);
        return  new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, final int position) {
        Breed cat = breedList.get(position);
        holder.nameCat.setText(cat.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener !=null){
                    onClickListener.setOnItemClickListener(position);
                }
            }
        });

    }
    class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView nameCat;
        public CustomerViewHolder(View itemView) {
            super(itemView);
            nameCat = itemView.findViewById(R.id.catname);
        }
    }

}
