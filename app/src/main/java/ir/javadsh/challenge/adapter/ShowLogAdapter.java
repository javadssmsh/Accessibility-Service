package ir.javadsh.challenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.javadsh.challenge.R;
import ir.javadsh.challenge.model.ReportLog;

public class ShowLogAdapter extends RecyclerView.Adapter<ShowLogAdapter.LogViewHolder> {

    private Context context;
    private List<ReportLog> reportLogList;

    public ShowLogAdapter(Context context, List<ReportLog> reportLogList) {
        this.context = context;
        this.reportLogList = reportLogList;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return reportLogList.size();
    }

    public void addLogReport(ReportLog reportLog) {
        reportLogList.add(reportLog);
        notifyDataSetChanged();
    }

    public class LogViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView urlTv;
        TextView createdDateTv;
        TextView browserNameTv;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgUrl);
            urlTv = itemView.findViewById(R.id.url);
            createdDateTv = itemView.findViewById(R.id.created_date);
            browserNameTv = itemView.findViewById(R.id.browser_name);

        }
    }
}
