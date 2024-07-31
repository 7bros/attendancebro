package com.example.attendencebro.ui.signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendencebro.R;
import com.example.attendencebro.ui.util.Master;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup extends Fragment {
    Button signupButton;
    EditText fullNameInput, emailInput, usernameInput, passwordInput, confirmPasswordInput;
    View rootview;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_signup, container, false);
        init();
        register();
        Validate();
        return rootview;
    }

    private void register() {
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();


            }
        });

    }


    private void registration() {
        if (Validate()) {

            RequestQueue queue = Volley.newRequestQueue(getContext());


            String url = Master.MAIN_URL + "signup.php?"+"fullname="+fullNameInput.getText().toString() + "&email=" + emailInput.getText().toString() + "&username=" + usernameInput.getText().toString() + "&pass=" + passwordInput.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jo = new JSONObject(response);
                        if (jo.getString("saveMember") != null && jo.getString("saveMember").equals("Member Save Successfully")) {
                            Toast.makeText(getContext(), "Signup Success fully ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Data already exists", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error occurred.\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Request failed. Error is : " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(stringRequest);
        }
    }


    private void init() {
        signupButton = rootview.findViewById(R.id.signUpButton);
        fullNameInput = rootview.findViewById(R.id.fullNameInput);
        emailInput = rootview.findViewById(R.id.emailInput);
        usernameInput = rootview.findViewById(R.id.usernameInput);
        passwordInput = rootview.findViewById(R.id.passwordInput);
        confirmPasswordInput = rootview.findViewById(R.id.confirmPasswordInput);
    }

    private boolean Validate() {

        boolean result = false;
        if (fullNameInput.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter Name !", Toast.LENGTH_LONG).show();
            fullNameInput.requestFocus();
        } else if (emailInput.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter  Email !", Toast.LENGTH_LONG).show();
            emailInput.requestFocus();

        } else if (usernameInput.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter Username !", Toast.LENGTH_LONG).show();
            usernameInput.requestFocus();
        } else if (passwordInput.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter Password !", Toast.LENGTH_LONG).show();
            passwordInput.requestFocus();
        } else if (confirmPasswordInput.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter confirm password !", Toast.LENGTH_LONG).show();
            confirmPasswordInput.requestFocus();
        } else if (!passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
            Toast.makeText(getContext(), "password does not match with confirm password !", Toast.LENGTH_SHORT).show();


        } else {
            result = true;
        }
        return result;
    }
}