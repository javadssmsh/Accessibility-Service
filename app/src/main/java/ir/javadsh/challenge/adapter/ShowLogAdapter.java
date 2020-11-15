package ir.javadsh.challenge.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowLogAdapter extends RecyclerView.Adapter<ShowLogAdapter.LogViewHolder> {


    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LogViewHolder extends RecyclerView.ViewHolder {
        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
