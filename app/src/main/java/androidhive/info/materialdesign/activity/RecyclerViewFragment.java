package androidhive.info.materialdesign.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.ReviewViewAdapter;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Drawable[] mDrawables = new Drawable[2];
    private int index = 0;
    private MaterialDialog mMaterialDialog;
    private ProgressView pv_circular_colors;

    private List<Object> mContentItems = new ArrayList<>();

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        pv_circular_colors = (ProgressView)view.findViewById(R.id.progress_pv_circular_colors);
        pv_circular_colors.applyStyle(R.style.CircularProgressDrawableStyle);

        for (int i = 0; i < 100; ++i)
            mContentItems.add(new Object());

        mAdapter = new RecyclerViewMaterialAdapter(new ReviewViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        final FloatingActionButton fab_image = (FloatingActionButton)view.findViewById(R.id.fab_image);
        mDrawables[0] = getResources().getDrawable(R.drawable.ic_plus);
        mDrawables[1] = getResources().getDrawable(R.drawable.abc_ic_clear_mtrl_alpha);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(getActivity());
                editText.setTextColor(getResources().getColor(R.color.greyDark));
                editText.setHint("Write is my review");
                editText.setPadding(10,20,10,20);

                mMaterialDialog = new MaterialDialog(getActivity())
                        .setTitle("This is my review")
                        .setView(editText)
                        .setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                fab_image.setBackgroundColor(Color.WHITE);
                                pv_circular_colors.start();
                                fab_image.setEnabled(false);
                                fab_image.setIcon(mDrawables[0], true);
                                new Handler().postDelayed(new Runnable() {
                                    @Override public void run() {
                                        pv_circular_colors.stop();
//                                        fab_image.setIcon(mDrawables[0], true);
                                        fab_image.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        fab_image.setEnabled(true);
                                    }
                                },5000);
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });

                mMaterialDialog.show();
            }
        });

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
