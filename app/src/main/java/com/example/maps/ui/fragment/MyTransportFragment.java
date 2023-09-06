package com.example.maps.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.maps.R;
import com.example.maps.databinding.FragmentMyTransportBinding;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyTransportFragment extends Fragment {

    Connection con;
    private FragmentMyTransportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyTransportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, newFragment).commit();
            }
        });

        binding.llRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //  con = c.connection();
                if (con != null){
                    try {

                        String sqlsta = "SELECT * FROM `UserStopBike`";
                        Statement sms = con.createStatement();
                        ResultSet set = sms.executeQuery(sqlsta);
                        while (set.next()) {
                            binding.tvNumber.setText(set.getString(1));
                            binding.tvPassword.setText(set.getString(2));
                            binding.tvPasswordRepeat.setText(set.getString(3));
                            binding.tvDateIssue.setText(set.getString(4));
                        }
                        con.close();
                    } catch (Exception e){
                        Log.i("Error", e.getMessage());
                    }
                }else {
                    Log.i("Erroor ", "Colection is  null");
                }
            }
        });
    }
}