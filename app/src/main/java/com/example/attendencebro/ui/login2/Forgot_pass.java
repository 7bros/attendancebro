package com.example.attendencebro.ui.login2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendencebro.R;
import com.example.attendencebro.ui.util.Master;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Forgot_pass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forgot_pass extends Fragment {
    View rootview;
    TextInputEditText edtForgotPasswordEmail;
    Button btnreset;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Forgot_pass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forgot_pass.
     */
    // TODO: Rename and change types and number of parameters
    public static Forgot_pass newInstance(String param1, String param2) {
        Forgot_pass fragment = new Forgot_pass();
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
        rootview = inflater.inflate(R.layout.fragment_forgot_pass, container, false);
        init();
        otpsend();
        Validate();
        registerListeners();
        return rootview;
    }

    private void registerListeners() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpsend();
            }
        });
    }


    private void otpsend() {
        if (Validate()) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Sending OTP...");
            progressDialog.show();

            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = Master.MAIN_URL + "forgot_password.php?" +
                    "email=" + edtForgotPasswordEmail.getText() + "&otp=";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("Otp sent")) {
                            Toast.makeText(getContext(), "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            // Show OTP dialog
                            showOtpDialog();
                        } else if (status.equals("Error sending email")) {
                            Toast.makeText(getContext(), "Error sending email", Toast.LENGTH_SHORT).show();
                        } else if (status.equals("Email not found")) {
                            Toast.makeText(getContext(), "Email not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error Occurred.\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Request failed. Error is : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }

    private void showOtpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter OTP");
        builder.setMessage("Enter the OTP sent to your registered email");
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_otp, null);
        final EditText input = dialogView.findViewById(R.id.otpInput);
        final TextView errorText = dialogView.findViewById(R.id.errorText);
        builder.setView(dialogView);
        builder.setPositiveButton("Verify", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String enteredOtp = input.getText().toString();
                        // Send the entered OTP to the server for verification
                        verifyOtp(enteredOtp, errorText, dialog);
                    }
                });
            }
        });
        dialog.show();
    }

    private void verifyOtp(String enteredOtp, final TextView errorText, final AlertDialog dialog) {
        String verifyUrl = Master.MAIN_URL + "forgot_password.php?" +
                "email=" + edtForgotPasswordEmail.getText() + "&otp=" + enteredOtp;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest verifyRequest = new StringRequest(Request.Method.GET, verifyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("OTP verified"))
                       {
                           Bundle args = new Bundle();
                           args.putString("otp", enteredOtp);
                          // Toast.makeText(getContext(), args.getString("otp"), Toast.LENGTH_SHORT).show();
                           reset_passFragment fragment = new reset_passFragment();
                           fragment.setArguments(args);


                           // OTP is correct, navigate to Reset Password page
                        dialog.dismiss();
                        Navigation.findNavController(rootview).navigate(R.id.action_nav_forgot_pass_to_nav_resetpass,args);
                    } else if (status.equals("Invalid OTP")) {
                        // OTP is incorrect, show error message
                        errorText.setText("Invalid OTP");
                        errorText.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Occurred.\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                Toast.makeText(getContext(), "Request failed. Error is : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(verifyRequest);
    }



    private void init() {
        btnreset = rootview.findViewById(R.id.btnReset);
        edtForgotPasswordEmail = rootview.findViewById(R.id.edtForgotPasswordEmail);

    }

    public Boolean Validate() {
        Boolean result = false;
        if (edtForgotPasswordEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(), "Enter email :", Toast.LENGTH_SHORT).show();
            edtForgotPasswordEmail.requestFocus();

        } else{
            result=true;

        }
        return result;
    }
}





