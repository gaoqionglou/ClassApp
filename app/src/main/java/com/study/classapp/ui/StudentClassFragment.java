package com.study.classapp.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.study.classapp.R;
import com.study.classapp.adapter.ClassListAdapter;
import com.study.classapp.databinding.FragmentStudentClassBinding;
import com.study.classapp.model.StudentClass;
import com.study.classapp.ui.viewmodel.MainViewModel;

import java.util.List;

public class StudentClassFragment extends Fragment {
    private FragmentStudentClassBinding studentClassBinding;
    private MainViewModel viewModel;
    private String day;
    private ClassListAdapter mAdapter;
    public StudentClassFragment() {
        // Required empty public constructor
    }
    public static StudentClassFragment newInstance(String day) {
        StudentClassFragment fragment = new StudentClassFragment();
        Bundle args = new Bundle();
        args.putString("day", day);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studentClassBinding = FragmentStudentClassBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        assert getArguments() != null;
        day = getArguments().getString("day");
        Log.i("app", day);
        mAdapter = new ClassListAdapter(null, getContext());
        DividerItemDecoration dec = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.divider_line_color)));
        studentClassBinding.rvClass.addItemDecoration(dec);
        studentClassBinding.rvClass.setLayoutManager(new LinearLayoutManager(getContext()));
        studentClassBinding.rvClass.setAdapter(mAdapter);
        viewModel.getStudentClasses().observe(getViewLifecycleOwner(), new Observer<List<StudentClass>>() {
            @Override
            public void onChanged(List<StudentClass> studentClasses) {
                mAdapter.setData(studentClasses);
            }
        });
        return studentClassBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.queryStudentClasses(day);
    }
}
