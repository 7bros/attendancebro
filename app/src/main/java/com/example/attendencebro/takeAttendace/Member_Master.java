package com.example.attendencebro.takeAttendace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendencebro.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Member_Master#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Member_Master extends Fragment {
    View rootview;
    RecyclerView rvStudent;
    ArrayList<String> memberList;
    Button btnGo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Member_Master() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Member_Master.
     */
    // TODO: Rename and change types and number of parameters
    public static Member_Master newInstance(String param1, String param2) {
        Member_Master fragment = new Member_Master();
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
        rootview=inflater.inflate(R.layout.fragment_member__master, container, false);
        init();

        loadllist();


        return rootview;
    }

    private void loadllist() {
        memberList=new ArrayList<>();
        String[] friends = {"John", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "George", "Hannah", "Ivan"};
        for (String friend : friends) {
            memberList.add(friend);
        }



        TakeAttendanceAdapter memberListAdapter=new TakeAttendanceAdapter(memberList);
        rvStudent.setAdapter(memberListAdapter);


    }

    private void init() {
        rvStudent=rootview.findViewById(R.id.rvStudent);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvStudent.setLayoutManager(linearLayoutManager);
        rvStudent.setItemAnimator(new DefaultItemAnimator());
        btnGo=rootview.findViewById(R.id.btnGo);

    }



}