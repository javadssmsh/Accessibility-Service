package ir.javadsh.challenge.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import ir.javadsh.challenge.R;
import ir.javadsh.challenge.adapter.WizardAdapter;
import ir.javadsh.challenge.helper.MySharedPreferenceManager;
import ir.javadsh.challenge.model.WizardModel;

import android.content.Intent;
import android.os.Bundle;
import android.animation.ArgbEvaluator;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class WizardActivity extends AppCompatActivity {

    ViewPager viewPager;
    WizardAdapter wizardAdapter;
    List<WizardModel> wizardModels;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        Button startBtn = findViewById(R.id.btnOrder);
        startBtn.setOnClickListener(view -> {
            MySharedPreferenceManager.getInstance(this).setFirstEntranceBoolean(false);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        wizardModels = new ArrayList<>();
        wizardModels.add(new WizardModel(R.drawable.wizard1, "Permission", "Allow necessary permission."));
        wizardModels.add(new WizardModel(R.drawable.wizard2, "Dialog", "The app finds the desired word, Touch the window if you want to save it."));
        wizardModels.add(new WizardModel(R.drawable.wizard3, "Logs", "You can see the reports in this section."));

        wizardAdapter = new WizardAdapter(wizardModels, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(wizardAdapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color3),
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (wizardAdapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}