package ir.javadsh.challenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import ir.javadsh.challenge.R;
import ir.javadsh.challenge.model.WizardModel;

public class WizardAdapter extends PagerAdapter {

    private List<WizardModel> wizardModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public WizardAdapter(List<WizardModel> wizardModels, Context context) {
        this.wizardModels = wizardModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return wizardModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.wizard_item, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(wizardModels.get(position).getImage());
        title.setText(wizardModels.get(position).getTitle());
        desc.setText(wizardModels.get(position).getDesc());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("param", models.get(position).getTitle());
                context.startActivity(intent);*/
                // finish();
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
