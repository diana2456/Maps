package com.example.maps.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.maps.R;
import com.example.maps.databinding.FragmentProfileBinding;
import com.example.maps.databinding.FragmentRegistrationBinding;
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProfileFragment extends Fragment {

    private MaterialButton button;
    private MaterialButton buttonMaps;
    private LinearLayout layout;

    private FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        main();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.btn_trans);
        buttonMaps = view.findViewById(R.id.btn_marsh);
        layout = view.findViewById(R.id.ll_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment newFragment = new MyTransportFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, newFragment).commit();
            }
        });

        buttonMaps.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Fragment newFragment = new MapsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, newFragment).commit();
            }
        });


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new RegisFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, newFragment).commit();
            }
        });
    }

    public void main() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/sattarvj_demo200";
        String username = "<sattarvj_demo200>";
        String password = "<Aaa123>";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            String selectQuery = "SELECT * FROM users";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                String photo = resultSet.getString("username");
                String name = resultSet.getString("Img");

                binding.tvName.setText(photo);
                Glide.with(binding.ivPhoto).load(name).into(binding.ivPhoto);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}