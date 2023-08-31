package com.example.maps.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.maps.R;
import com.example.maps.databinding.FragmentRegistrationBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private Uri selectedImageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrationBinding.inflate(inflater,container,false);
        click();
        return binding.getRoot();
    }
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            Uri uri = data.getData();
                                selectedImageUri = uri;
                                Glide.with(binding.ivPhoto).load(uri).into(binding.ivPhoto);
                        }
                        }
                    }
                }
    );

    private void click(){

        binding.btnCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction( Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                activityResultLauncher.launch(intent);
            }
        });
        binding.btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main();
            }
        });
    }
    public  void main() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/sattarvj_demo200";
        String username = "<sattarvj_demo200>";
        String password = "<Aaa123>";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            String insertQuery = "INSERT INTO users (Login, Password, Img) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, binding.tvLogin.getText().toString());
            preparedStatement.setString(2, binding.tvPassword.getText().toString());
            preparedStatement.setString(6, selectedImageUri.toString());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

            Log.i("olol", "main:соединение установлено");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("oукol", "main:соединение не установлено");

        }
    }
}