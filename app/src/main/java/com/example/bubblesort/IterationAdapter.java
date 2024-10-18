

package com.example.bubblesort;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IterationAdapter extends RecyclerView.Adapter<IterationAdapter.IterationViewHolder> {

    private List<CharSequence> iterations;

    public IterationAdapter(List<CharSequence> iterations) {
        this.iterations = iterations;
    }

    @NonNull
    @Override
    public IterationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new IterationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IterationViewHolder holder, int position) {
        holder.iterationTextView.setText(iterations.get(position)); // Now handles CharSequence
    }

    @Override
    public int getItemCount() {
        return iterations.size();
    }

    public static class IterationViewHolder extends RecyclerView.ViewHolder {
        public TextView iterationTextView;

        public IterationViewHolder(View itemView) {
            super(itemView);
            iterationTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
