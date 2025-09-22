package com.example.m335.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m335.R;
import com.example.m335.model.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the RecyclerView to display modules
 */
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private List<Module> modules = new ArrayList<>();
    private OnItemClickListener listener;

    /**
     * ViewHolder class for module items
     */
    class ModuleViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewModuleNumber;
        private TextView textViewModuleTitle;
        private TextView textViewAverageGrade;

        /**
         * Constructor initializes the views
         *
         * @param itemView The item view
         */
        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewModuleNumber = itemView.findViewById(R.id.text_view_module_number);
            textViewModuleTitle = itemView.findViewById(R.id.text_view_module_title);
            textViewAverageGrade = itemView.findViewById(R.id.text_view_average_grade);

            // Set up click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(modules.get(position));
                    }
                }
            });
        }
    }

    /**
     * Creates new ViewHolder instances
     *
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new ViewHolder instance
     */
    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_item, parent, false);
        return new ModuleViewHolder(itemView);
    }

    /**
     * Binds data to the ViewHolder
     *
     * @param holder The ViewHolder to bind data to
     * @param position The position in the dataset
     */
    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module currentModule = modules.get(position);
        holder.textViewModuleNumber.setText(currentModule.getModuleNumber());
        holder.textViewModuleTitle.setText(currentModule.getModuleTitle());

        Float average = currentModule.getAverageGrade();
        if (average != null) {
            holder.textViewAverageGrade.setText(String.format("%.1f", average));
            holder.textViewAverageGrade.setVisibility(View.VISIBLE);
        } else {
            holder.textViewAverageGrade.setVisibility(View.GONE);
        }
    }

    /**
     * Gets the number of items in the adapter
     *
     * @return The number of items
     */
    @Override
    public int getItemCount() {
        return modules.size();
    }

    /**
     * Updates the adapter data
     *
     * @param modules The new list of modules
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
        notifyDataSetChanged();
    }

    /**
     * Interface for handling item clicks
     */
    public interface OnItemClickListener {
        void onItemClick(Module module);
    }

    /**
     * Sets the item click listener
     *
     * @param listener The listener to set
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}