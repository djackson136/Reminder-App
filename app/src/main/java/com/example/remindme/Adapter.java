package com.example.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter {
        private ArrayList<Reminder> reminderData;
        private View.OnClickListener mOnItemClickListener;
        private boolean isDeleting;
        private Context parentContext;

        public class ReminderViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewName;
            public TextView textViewType;
            public TextView textViewRating;
            public Button deleteButton;

            public ReminderViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textDescription);
                textViewType = itemView.findViewById(R.id.textSubject);
                textViewRating = itemView.findViewById(R.id.textSaveDate);
                deleteButton = itemView.findViewById(R.id.buttonDeleteMeal);
                itemView.setTag(this);
                itemView.setOnClickListener(mOnItemClickListener);
            }

            public TextView getNameTextView() {
                return textViewName;
            }

            public TextView getTypeTextView() {
                return textViewType;
            }

            public TextView getRatingTextView() {
                return textViewRating;
            }

            public Button getDeleteButton() {
                return deleteButton;
            }
        }

        public Adapter(ArrayList<Reminder> arrayList, Context context) {
            reminderData = arrayList;
            parentContext = context;
        }

        public void setOnItemClickListener(View.OnClickListener itemClickListener) {
            mOnItemClickListener = itemClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);

            return new ReminderViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            ReminderViewHolder rvh = (ReminderViewHolder) holder;
            rvh.getNameTextView().setText(reminderData.get(position).getSubject());
            rvh.getTypeTextView().setText(reminderData.get(position).getDescription());
            rvh.getRatingTextView().setText(reminderData.get(position).getSaveDate());

            if (isDeleting) {
                rvh.getDeleteButton().setVisibility(View.VISIBLE);
                rvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(position);
                    }
                });
            } else {
                rvh.getDeleteButton().setVisibility(View.INVISIBLE);
            }
        }

        public void setDelete(boolean b) {
            isDeleting = b;
        }

        private void deleteItem(int position) {
            Reminder reminder = reminderData.get(position);
            DataSource ds = new DataSource(parentContext);
            try {
                ds.open();
                boolean didDelete = ds.deleteReminder(reminder.getReminderID());
                ds.close();
                if (didDelete) {
                    reminderData.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public int getItemCount() {
            return reminderData.size();
        }



}
