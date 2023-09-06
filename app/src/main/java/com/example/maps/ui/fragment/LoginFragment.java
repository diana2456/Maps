package com.example.maps.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maps.R;
import com.example.maps.data.LoginResponse;
import com.example.maps.databinding.FragmentLoginBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment {

    String username , password, image, dateofbirth ,numberby,dateofissue;
    private FragmentLoginBinding binding;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        main();
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new RegistrationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, newFragment).commit();
            }
        });
        main();
    }

    private void main(){
        sharedPreferences = getContext().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("logget","false").equals("true")){
            Fragment newFragment = new ProfileFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newFragment).commit();
        }

        binding.btnCome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                String url ="http://192.168.1.195:8080/untitled/login.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("VolleyResponse", "Response: " + response);

                                Gson gson = new Gson();
                                LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);

                                if ("success".equals(loginResponse.getStatus())) {
                                    // Access the parsed data using loginResponse getters
                                     username = loginResponse.getUsername();
                                     password = loginResponse.getPassword();
                                     image = loginResponse.getImage();
                                     dateofbirth = loginResponse.getDateOfBirth();
                                     numberby = loginResponse.getNumberBy();
                                     dateofissue = loginResponse.getDateOfIssue();

                                    Fragment newFragment = new ProfileFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, newFragment).commit();

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username",username);
                                    editor.putString("password",password);
                                    editor.putString("image",image);
                                    editor.putString("dateofbirth",dateofbirth);
                                    editor.putString("numberby",numberby);
                                    editor.putString("dateofissue",dateofissue);
                                    editor.apply();
                                }
                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("username", binding.tvLogin.getText().toString());
                        paramV.put("password", binding.tvPassword.getText().toString());
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}