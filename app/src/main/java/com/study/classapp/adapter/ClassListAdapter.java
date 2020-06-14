package com.study.classapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.classapp.R;
import com.study.classapp.databinding.ListStudentClassItemBinding;
import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.model.StudentClass;
import com.study.classapp.util.DialogUtil;

import java.util.List;
//
public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ViewHolder> {

    private List<StudentClass> mInfoList;
    private Context mContext;

    public ClassListAdapter(List<StudentClass> mInfoList, Context mContext) {
        this.mInfoList = mInfoList;
        this.mContext = mContext;
    }

    public void setData(List<StudentClass> mInfoList) {
        this.mInfoList = mInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_student_class_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentClass studentClass = mInfoList.get(position);
        String className = studentClass.getClassName();
        String classAddress = studentClass.getClassAddress();
        String classTeacher = studentClass.getClassTeacher();
        String classWeeks = studentClass.getClassWeeks();
        holder.viewItemBinding.tvClassAddress.setText("地点：" + classAddress);
        holder.viewItemBinding.tvClassName.setText("课程：" + className);
        holder.viewItemBinding.tvClassTeacher.setText("教师：" + classTeacher);
        holder.viewItemBinding.tvClassWeeks.setText("周数：" + classWeeks);
        String index = String.valueOf(position + 1);
        holder.viewItemBinding.index.setText(index);
        holder.viewItemBinding.getRoot().setOnClickListener(view -> {
            new AlertDialog.Builder(mContext).setTitle("提示")
                    .setMessage("你想对课程做如何操作？")
                    .setNegativeButton("删除课程", (v, var) -> {
                        AppDatabase.getInstance().studentClassDao().deleteStudentClassById(studentClass.getClassId());
                        mInfoList.remove(studentClass);
                        notifyDataSetChanged();
                    })
                    .setPositiveButton("修改课程", (v, var) -> {
                        DialogUtil.updateClassDialog(mContext, "修改课程信息", studentClass, (studentClass1) -> {
                            String day = studentClass1.getClassDay();
                            mInfoList = AppDatabase.getInstance().studentClassDao().getStudentClassesByDay(day);
                            notifyDataSetChanged();
                        });
                    }).show();
        });
    }


    @Override
    public int getItemCount() {
        return mInfoList != null ? mInfoList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ListStudentClassItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = ListStudentClassItemBinding.bind(itemView);
        }
    }

}
