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
        wizardModels.add(new WizardModel(R.drawable.ic_image, "Brochure", "Brochure is an informative paper document (often also used for advertising) that can be folded into a template"));
        wizardModels.add(new WizardModel(R.drawable.ic_launcher_background, "Sticker", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side"));
        wizardModels.add(new WizardModel(R.drawable.ic_touch, "Poster", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
        wizardModels.add(new WizardModel(R.drawable.ic_touch, "Namecard", "Business cards are cards bearing business information about a company or individual."));

        wizardAdapter = new WizardAdapter(wizardModels, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(wizardAdapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color3)
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