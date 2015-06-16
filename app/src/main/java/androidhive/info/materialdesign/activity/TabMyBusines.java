package androidhive.info.materialdesign.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.TestRecyclerViewAdapter;

/**
 * Created by Khusnan on 6/5/15.
 */
public class TabMyBusines extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Drawable[] mDrawables = new Drawable[2];
    private int index = 0;

    private List<Object> mContentItems = new ArrayList<>();

    public static TabMyBusines newInstance() {
        return new TabMyBusines();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_my_busines, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        for (int i = 0; i < 100; ++i)
            mContentItems.add(new Object());

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        final FloatingActionButton fab_image = (FloatingActionButton)view.findViewById(R.id.fab_image);
//        mDrawables[0] = view.getResources().getDrawable(R.drawable.ic_action_search);
//        mDrawables[1] = view.getResources().getDrawable(R.drawable.abc_ic_voice_search_api_mtrl_alpha);
//        fab_image.setIcon(mDrawables[index], false);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (index + 1) % 2;
                fab_image.setIcon(mDrawables[index], true);
            }
        });

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
