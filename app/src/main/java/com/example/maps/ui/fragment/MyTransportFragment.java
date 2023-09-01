package com.example.maps.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.maps.R;
import com.example.maps.data.InsertData;
import com.example.maps.databinding.FragmentMyTransportBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;


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
                new RetrieveDataTask().execute();
                RetrieveDataTask re = new RetrieveDataTask();
                binding.tvNumber.setText(re.ret);
            }
        });
    }

    @SuppressLint("NewApi")
    public Connection SQLConnection()
    {
        String ip="172.12.0.1", port="1433", dbname="mytestdb", un="sa",pass="ro5165";
        StrictMode.ThreadPolicy tp= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        String ConURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConURL="jdbc:jtds:sqlserver://"+ip+ ":"+port+";"+"databasename="+dbname+";user=" +un+";password="+pass+";";
            con= DriverManager.getConnection(ConURL);
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
        }
        return con;
    }
}


class RetrieveDataTask extends AsyncTask<Void, Void, String> {

    public String ret;
    @Override
    protected String doInBackground(Void... voids) {
        String result = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/sattarvj_demo200";
            String user = "sattarvj_demo200";
            String password = "Aaa123";

            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM Stopbike";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                // Перебираем результаты запроса и формируем строку с данными
                int id = resultSet.getInt("id");
                String login = resultSet.getString("Login");
                String passwor = resultSet.getString("Password");
                String dateOfBirth = resultSet.getString("DateOfBirth");
                String numberBike = resultSet.getString("NumberBike");
                String dateOfIssue = resultSet.getString("DateOfIssue");
                String img = resultSet.getString("Img");

                result += "ID: " + id + "\n";
                result += "Login: " + login + "\n";
                result += "Password: " + passwor + "\n";
                result += "DateOfBirth: " + dateOfBirth + "\n";
                result += "NumberBike: " + numberBike + "\n";
                result += "DateOfIssue: " + dateOfIssue + "\n";
                result += "Img: " + img + "\n\n";
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = "Ошибка: " + e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
         ret = result;
    }
}
