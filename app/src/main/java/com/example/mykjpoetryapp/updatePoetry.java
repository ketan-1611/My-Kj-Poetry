package com.example.mykjpoetryapp;

import static android.os.Build.VERSION_CODES.P;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mykjpoetryapp.Apis.ApiClient;
import com.example.mykjpoetryapp.Apis.ApiInterface;
import com.example.mykjpoetryapp.Responses.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class updatePoetry extends AppCompatActivity {

    Toolbar toolbarUpdate;
    EditText PoetryUpdate;
    AppCompatButton btnSubmit;
    int p_id;
    String p_data;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);

        intailization();
        setUpToolbar();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PoetryData = PoetryUpdate.getText().toString();
                if(PoetryData.equals("")){
                    PoetryUpdate.setError("Field is empty ");

                }
                else {
                    CallApi(PoetryData,p_id+"");
                }
            }
        });
    }

    private void intailization(){
        toolbarUpdate = findViewById(R.id.toolbar_update);
        PoetryUpdate = findViewById(R.id.etPoetryUpdate);
        btnSubmit = findViewById(R.id.btnSubmit);

        p_id =  getIntent().getIntExtra("p_id",0);
        p_data = getIntent().getStringExtra("p_data");
        PoetryUpdate.setText(p_data);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    private void setUpToolbar()
    {
        setSupportActionBar(toolbarUpdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarUpdate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CallApi(String PoetryData , String p_id){
        apiInterface.updatePoetry(PoetryData,p_id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(updatePoetry.this, "Update  Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(updatePoetry.this, "Update not Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.e("Failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {


            }
        });
    }

}