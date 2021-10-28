package com.example.mykjpoetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mykjpoetryapp.Apis.ApiClient;
import com.example.mykjpoetryapp.Apis.ApiInterface;
import com.example.mykjpoetryapp.Responses.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {
    Toolbar toolbarPoetry;
    EditText poetryData, poetName;
    AppCompatButton btnSubmit;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);

        intialization();
        setUpToolbar();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString = poetryData.getText().toString();
                String poetNameString = poetName.getText().toString();

                if(poetryDataString.equals("")){
                    poetryData.setError("Field is empty");
                }
                else {
                    if(poetNameString.equals("")){
                        poetName.setError("Field is empty");
                    }
                    else{
                          CallApi(poetryDataString,poetNameString);
                    }
                }
            }
        });

    }

    private void intialization(){
        toolbarPoetry = findViewById(R.id.toolbar_add);
        poetryData = findViewById(R.id.etPoetry);
        poetName = findViewById(R.id.etName);
        btnSubmit = findViewById(R.id.btnSubmit);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    private void setUpToolbar()
    {
        setSupportActionBar(toolbarPoetry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarPoetry.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPoetry.this, "Clicked back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void CallApi(String poetryDataString, String poetNameString){
        apiInterface.addPoetry(poetryDataString,poetNameString).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {

                    if(response.body().getStatus().equals("1")){

                        Toast.makeText(AddPoetry.this,"Added  Successfully",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(AddPoetry.this,"Added not Successfully",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.e("exp",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {

                Log.e("Failure",t.getLocalizedMessage());
            }
        });
    }

}