package com.example.maps.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.maps.databinding.FragmentRegistrationBinding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private Bitmap selectedImageUri;
    Connection con;
    Statement stmt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
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
                            try {
                                selectedImageUri = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                                binding.ivPhoto.setImageBitmap(selectedImageUri);
                                Log.i("sdf", "onActivityResult:"+uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
    );

    private void click() {
        binding.btnCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                activityResultLauncher.launch(intent);
            }
        });

        binding.btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser registerTask = new registerUser();
                registerTask.execute("");
            }
        });
    }
    public class registerUser extends AsyncTask<String,String,String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(requireContext(), "Sending Data to Database", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageUri.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte [] bytes = byteArrayOutputStream.toByteArray();
            final String base64Image = Base64.encodeToString(bytes,Base64.DEFAULT);
            try {
                con = connectionClass("sattarvj_demo200", "Aaa123.", "sattarvj_demo200", "185.138.186.228");
                if (con == null){
                    z = "Check Your Internet Connection";
                } else {
                    String sql = "INSERT INTO UserStopBike (username, dateofbirth, password, numberby, dateofissue, image) VALUES ('"+binding.tvLogin.getText().toString()+"','"+binding.tvPassword.getText().toString()+"','"+binding.tvDate.getText().toString()+"','"+binding.tvNumber.getText().toString()+"','"+binding.tvDateIssue.getText().toString()+"','"+base64Image+"')";
                        stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                }
            } catch (Exception e) {
                isSuccess = false;
               z = e.getMessage();
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String user,String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURI =  null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURI = "jdbc:jtds:sqlserver://sattarvj.beget.tech/" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURI);
        } catch (Exception e){
            Log.i("SQL error", "connectionClass:"+e.getMessage());
        }
        return  connection;
    }
}






/* RequestQueue queue = Volley.newRequestQueue(requireContext());
                String url ="http:// 192.168.1.195/untitled/register.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("polkjhg", "onErrorResponse:"+response);
                                if (response.equals("success")){
                                    Toast.makeText(requireContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                                    Fragment newFragment = new ProfileFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, newFragment).commit();
                                } else {
                                    Toast.makeText(requireContext(),response,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        Log.i("ujmn", "onErrorResponse:"+error.getLocalizedMessage());
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("username", binding.tvLogin.getText().toString());
                        paramV.put("password", binding.tvPassword.getText().toString());
                        paramV.put("dateofbirth", binding.tvDate.getText().toString());
                        paramV.put("numberby", binding.tvNumber.getText().toString());
                        paramV.put("dateofissue", binding.tvDateIssue.getText().toString());
                        ByteArrayOutputStream byteArrayOutputStream;
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        if (selectedImageUri != null){
                            selectedImageUri.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                            byte [] bytes = byteArrayOutputStream.toByteArray();
                            final String base64Image = Base64.encodeToString(bytes,Base64.DEFAULT);
                            paramV.put("image",base64Image);
                        } else {
                            Toast.makeText(requireContext(),"фотка не отправилась",Toast.LENGTH_SHORT).show();
                        }
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }*/