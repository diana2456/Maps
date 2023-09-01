package com.example.maps.data;

import android.os.AsyncTask;

import java.sql.*;

public class InsertData extends AsyncTask<Void, Void, Void> {

   public String text, errorText;
    String records = "", error = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sattarvj_demo200","sattarvj_demo200","Aaa123");
            Statement statement = connection.createStatement();
            ResultSet resultSet =statement.executeQuery("SELECT * FROM `StopBike`");

            while (resultSet.next()){
                records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            error = e.toString();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        text = records;
        if (error != "")
            errorText = error;
        super.onPostExecute(unused);
    }
}
