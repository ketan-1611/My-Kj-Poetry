package com.example.mykjpoetryapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykjpoetryapp.Apis.ApiClient;
import com.example.mykjpoetryapp.Apis.ApiInterface;
import com.example.mykjpoetryapp.Models.PoetryModel;
import com.example.mykjpoetryapp.R;
import com.example.mykjpoetryapp.Responses.DeletePoetryResponse;
import com.example.mykjpoetryapp.updatePoetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder>{


    List<PoetryModel> poetryModels;
    Context context ;
    ApiInterface apiInterface;

    public PoetryAdapter(List<PoetryModel> poetryModels, Context context) {
        this.poetryModels = poetryModels;
        this.context = context;
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_design,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PoetryModel model = poetryModels.get(position);
        holder.poet_name.setText(model.getPoet_name());
        holder.poet_data.setText(model.getPoetry_data());
        holder.poet_date.setText(model.getDate_time());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  deletePoetry(model.getId()+"",position);
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,model.getId()+"\n"+ model.getPoetry_data(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, updatePoetry.class);

                intent.putExtra("p_id",model.getId());
                intent.putExtra("p_data",model.getPoetry_data());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView poet_name, poet_data , poet_date;
        AppCompatButton update ,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poet_name = itemView.findViewById(R.id.tvPoet);
            poet_data = itemView.findViewById(R.id.tvPoetry);
            poet_date = itemView.findViewById(R.id.tvDate);
            update = itemView.findViewById(R.id.btnUpdate);
            delete = itemView.findViewById(R.id.btndelete);



        }
    }

    private void deletePoetry(String id,int pos){
        apiInterface.deletePoetry(id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {

                try {
                    if(response != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                        if (response.body().getStatus().equals("1")) {
                            poetryModels.remove(pos);
                            notifyDataSetChanged();
                        }
                    }
                }
                catch (Exception e){
                    Log.e("Failure", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {
                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }
}
