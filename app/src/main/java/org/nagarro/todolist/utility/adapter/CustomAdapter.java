package org.nagarro.todolist.utility.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.nagarro.todolist.R;
import org.nagarro.todolist.activity.TodoDetailActivity;
import org.nagarro.todolist.model.Todos;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private final List<Todos> dataList;
    private final Context context;

    public CustomAdapter(Context context, List<Todos> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        TextView status;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            status = mView.findViewById(R.id.status);
        }
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Todos temp = dataList.get(position);
        String statusText = "Status : ";
        holder.txtTitle.setText(dataList.get(position).getTitle());
        if(dataList.get(position).getCompleted()){
            statusText += "Completed";
        }else{
            statusText += "Not Completed";
        }
        holder.status.setText(statusText);
        holder.mView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TodoDetailActivity.class);
            intent.putExtra("title",temp.getTitle());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
