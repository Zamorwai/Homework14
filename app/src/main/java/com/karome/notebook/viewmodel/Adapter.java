package com.karome.notebook.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.karome.notebook.R;
import com.karome.notebook.model.Notebook;
import com.karome.notebook.view.UpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static final String TAG = "Adapter";

    private Context context;
    private Activity activity;
    private List<Notebook> notesList;
    private List<Notebook> newList;

    public Adapter(Context context, Activity activity, List<Notebook> notesList) {
        this.context = context;
        this.activity = activity;
        this.notesList = notesList;
        this.newList = new ArrayList<>(notesList);
    }

    // Данный метод возвращает объект ViewHolder(), который будет хранить данные по одному объекту Notebook
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Конвертирование layout файла во view элемент
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    // onBindViewHolder() выполняет привязку объекта ViewHolder к Notebook по определенной позиции
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called for position: " + position);

        holder.title.setText(notesList.get(position).getTitle());
        holder.description.setText(notesList.get(position).getDescription());

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("title", notesList.get(position).getTitle());
                intent.putExtra("description", notesList.get(position).getDescription());
                intent.putExtra("id", notesList.get(position).getId());

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ConstraintLayout mLayout;
        ViewHolder(@NonNull View view) {
            super(view);

            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            mLayout = view.findViewById(R.id.mLayout);
        }
    }
}
