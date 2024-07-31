package com.example.attendencebro.ui.login2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * Use the {@link reset_passFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reset_passFragment extends Fragment {
    private String enteredOtp;
    View rootview;
    EditText etnewpass,etnewconfirmpass;
    Button btnconfirm;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reset_passFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reset_passFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static reset_passFragment newInstance(String param1, String param2) {
        reset_passFragment fragment = new reset_passFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            String otpValue = args.getString("otp");
            Toast.makeText(getContext(), otpValue, Toast.LENGTH_SHORT).show();
            enteredOtp = args.getString("otp");
            if (enteredOtp == null) {
                Toast.makeText(getContext(), "OTP token is null or not found", Toast.LENGTH_SHORT).show();
            } else {
                // Use the OTP token to validate the password reset request
            }
        } else {
            Toast.makeText(getContext(), "Bundle args is null", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_reset_pass, container, false);
        init();
        Validate();
        register();

        return rootview;
    }



    private void register() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetnow();

            }
        });
    }

    private void resetnow(){
        if (Validate()) {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = Master.MAIN_URL + "reset_password.php?new_pass=" + etnewconfirmpass.getText().toString() + "&otp_token=" + enteredOtp;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (response.startsWith("{") && response.endsWith("}")) {
                            // Response is a JSON object
                            JSONObject J = new JSONObject(response);
                            if (J.getString("status").equals("Password updated")) {
                                Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                            } else if (J.getString("status").equals("Error updating password")) {
                                Toast.makeText(getContext(), "Error updating password", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Invalid request", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Response is not a JSON object
                            Toast.makeText(getContext(), "Response: " + response, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error Occurred.\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Request failed. Error is : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        }
    }



    private void init() {
        etnewpass=rootview.findViewById(R.id.etnewpass);
        etnewconfirmpass=rootview.findViewById(R.id.etnewconfirmpass);
        btnconfirm=rootview.findViewById(R.id.btnconfirm);

    }

    private boolean Validate() {
        boolean result=false;
        if (etnewpass.getText().toString().trim().length()==0) {
            Toast.makeText(getContext(),"Enter your new password",Toast.LENGTH_SHORT).show();
            etnewpass.requestFocus();
        } else if (etnewconfirmpass.getText().toString().trim().length()==0) {
            Toast.makeText(getContext(), "Re Enter your password !", Toast.LENGTH_SHORT).show();
            etnewconfirmpass.requestFocus();
        } else if (!etnewpass.getText().toString().equals(etnewconfirmpass.getText().toString())) {
            Toast.makeText(getContext(), "password does not match with confirm password !", Toast.LENGTH_SHORT).show();
        } else {
                result=true;
        }
            return result;

    }
}