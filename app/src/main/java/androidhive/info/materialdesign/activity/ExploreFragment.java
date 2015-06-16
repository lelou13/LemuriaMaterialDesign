package androidhive.info.materialdesign.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.android.airmapview.AirMapView;

import androidhive.info.materialdesign.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    public ExploreFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private AirMapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        mapView = (AirMapView) rootView.findViewById(R.id.map_view);
        mapView.initialize(getActivity().getSupportFragmentManager());

        return rootView;
    }


}
