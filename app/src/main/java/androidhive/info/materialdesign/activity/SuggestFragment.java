package androidhive.info.materialdesign.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;

import androidhive.info.materialdesign.R;


public class SuggestFragment extends AppCompatActivity {


    public SuggestFragment() {
        // Required empty public constructor
    }

    private Toolbar mToolbar;
    private TextView mTitle;
    private Drawable[] mDrawables = new Drawable[2];
    private ProgressView pv_circular_colors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sugest);
        pv_circular_colors = (ProgressView)findViewById(R.id.progress_pv_circular_colors);

        Spinner spn_label = (Spinner)findViewById(R.id.spinner_label);
        String[] items = new String[20];
        for(int i = 0; i < items.length; i++)
            items[i] = "Category " + String.valueOf(i + 1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spn_label.setAdapter(adapter);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("Add My Location");

        final FloatingActionButton fab_image = (FloatingActionButton)findViewById(R.id.fab_image);
        mDrawables[0] = getResources().getDrawable(R.drawable.abc_ic_menu_share_mtrl_alpha);
        mDrawables[1] = getResources().getDrawable(R.drawable.abc_ic_clear_mtrl_alpha);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pv_circular_colors.applyStyle(R.style.CircularProgressDrawableStyle);
                fab_image.setBackgroundColor(getResources().getColor(R.color.white));
                pv_circular_colors.start();
                fab_image.setEnabled(false);
                fab_image.setIcon(mDrawables[0], true);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        pv_circular_colors.stop();
//                        fab_image.setIcon(mDrawables[0], true);
                        fab_image.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        fab_image.setEnabled(true);
                    }
                },5000);
            }
        });

    }

}
