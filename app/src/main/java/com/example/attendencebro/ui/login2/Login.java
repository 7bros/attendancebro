package com.example.attendencebro.ui.login2;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

public class Login extends Fragment {
    Button button;
    EditText username, password;
    View rootview;
    TextView forgot_pass,new_user;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_login2, container, false);

        // Initialize your views here
        // Example: TextView textView = view.findViewById(R.id.text_view);
        init();

        login();

        vaalidate();
        return rootview;


    }

    private void login() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot_pass.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                forgot_pass.setTextColor(getResources().getColor(R.color.black));

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_login2_to_nav_forgot_pass);


            }
        });
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_user.setTextColor(getResources().getColor(R.color.black));
                new_user.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_login2_to_nav_signup);


            }
        });
    }

    private void register() {
        if (vaalidate()) {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = Master.MAIN_URL+"login.php?" +
                    "userid=" + username.getText() +
                    "&pass=" + password.getText();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject J=new JSONObject (response);
                                if (J.getString("verify_status").equals("Valid")) {
                                    Toast.makeText(getContext(),"Welcome"+username.getText(),Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(rootview).navigate(R.id.action_nav_login2_to_nav_dashBoard);
                                } else {
                                    Toast.makeText(getContext(),"Invalid User id or Password.",Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(),"Error Occurred.\n"+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                            //    txt1.setText("Response is: " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),"Request failed. Error is : " + error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        }
    }

    private void init() {
        button = rootview.findViewById(R.id.login_btn);
        username = rootview.findViewById(R.id.Username_Input);
        password = rootview.findViewById(R.id.Password_Input);
        forgot_pass=rootview.findViewById(R.id.txt_forgot);
        new_user=rootview.findViewById(R.id.txt_newuser);

    }

    private boolean vaalidate() {
        boolean result = false;
        String input = username.getText().toString().trim();

        if (!input.isEmpty()) {
            if (input.contains("@")) {
                // Input is an email address
                if (password.getText().toString().trim().length() > 0) {
                    result = true;
                } else {
                    Toast.makeText(getContext(), "Please fill in the password field", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Input is a username
                if (password.getText().toString().trim().length() > 0) {
                    result = true;
                } else {
                    Toast.makeText(getContext(), "Please fill in the password field", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), "Please fill in the username or email field", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

}
