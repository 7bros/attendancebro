package com.example.attendencebro.takeAttendace;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.attendencebro.R;
import com.example.attendencebro.dashBoard.HomeViewModel;
import com.example.attendencebro.databinding.FragmentHomeBinding;

public class takeAttendanceFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView tvDate;
    private Button btndate;
    Button btnGo;
    View rootview;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootview = binding.getRoot();
        init();
        registerListeners();
        navigate();
        return rootview;
    }

    private void init() {
        tvDate = binding.tvDate;
        btndate = binding.btndate;
        btnGo = binding.btnGo;

    }


    private void registerListeners() {
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int date, int month, int year) {
                        tvDate.setText(String.valueOf(date) + "." + String.valueOf(month + 1) + "." + String.valueOf(year));
                    }
                }, 2024, 02, 1);
                dialog.show();
            }
        });

    }

    private void navigate() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nav_home_to_nav_member);

            }
        });


    }
}



