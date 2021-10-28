package com.example.mykjpoetryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mykjpoetryapp.Adapters.PoetryAdapter;
import com.example.mykjpoetryapp.Apis.ApiClient;
import com.example.mykjpoetryapp.Apis.ApiInterface;
import com.example.mykjpoetryapp.Models.PoetryModel;
import com.example.mykjpoetryapp.Responses.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
   // List<PoetryModel>poetryModelslist = new ArrayList<>();
    ApiInterface apiInterface;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  poetryModelslist.add(new PoetryModel(1,"kvmfmadkkfmb","ketan","24-09-2021"));
     //  poetryModelslist.add(new PoetryModel(1,"kvmfmadkkfmb","ketan","24-09-2021"));
      //  poetryModelslist.add(new PoetryModel(1,"kvmfmadkkfmb","ketan","24-09-2021"));


        intialization();
        setSupportActionBar(toolbar);
        getData();

       // setAdapter(poetryModelslist);
    }

    private void intialization(){
        recyclerView = findViewById(R.id.recyclerView);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setAdapter(List<PoetryModel> poetryModels){
        poetryAdapter = new PoetryAdapter(poetryModels,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(poetryAdapter);
    }

    private void getData()
    {
        apiInterface.getPoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {
                try {

                    if(response != null)
                    {
                        if(response.body().getStatus().equals("1"))
                        {
                            setAdapter(response.body().getData());
                        }
                        else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                catch (Exception e)
                {
                    Log.e("exp",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {

                Log.e("Failure", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.addProperty:
                Intent intent = new Intent(MainActivity.this,AddPoetry.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "Clicked in Propety", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}