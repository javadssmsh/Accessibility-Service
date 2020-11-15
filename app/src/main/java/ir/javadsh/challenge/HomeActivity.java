package ir.javadsh.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.javadsh.challenge.adapter.ShowLogAdapter;
import ir.javadsh.challenge.model.Log;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //
        List<Log> logs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Log log = new Log();
            log.setBrowserName("کروم" + i);
            log.setCreatedDate("" + i + ":" + i);
            log.setImgUrl("");
            log.setUrl("javadssmh@gmail.com");
            logs.add(log);
        }

        RecyclerView showLogRecyclerView = findViewById(R.id.show_log_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        showLogRecyclerView.setHasFixedSize(true);
        ShowLogAdapter adapter = new ShowLogAdapter(this, logs);
        showLogRecyclerView.setLayoutManager(linearLayoutManager);
        showLogRecyclerView.setAdapter(adapter);

    }
}