package androidhive.info.materialdesign.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.AdapterPoiNearby;
import androidhive.info.materialdesign.adapter.POIViewAdapter;
import androidhive.info.materialdesign.controller.AppController;
import androidhive.info.materialdesign.controller.VolleySingleton;
import androidhive.info.materialdesign.model.POINearby;


public class EventFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private ImageLoader imageLoader;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<POINearby> listNearby = new ArrayList<>();
    private AdapterPoiNearby adapterPoiNearby;

    public EventFragment() {
        // Required empty public constructor
    }
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeLayout;
    private View rootView;
    private Drawable[] mDrawables = new Drawable[2];
    private int index = 0;
    private ProgressView pv_linear_color;
    List<POINearby> list = new ArrayList<>();
    POINearby mPOI;


    private List<Object> mContentItems = new ArrayList<>();

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    private static String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String jsonResponse;
    private String urlJsonArry = "http://lemuria.basicitteam.com/index.php/api/poi/nearby";
//    String uri = String.format(urlJsonArry,
//            "-7.559",
//            "110.818");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_event, container, false);
        pv_linear_color = (ProgressView)rootView.findViewById(R.id.progress_pv_circular_colors);
        configRecyclerView();
        swipeLayout.setRefreshing(true);
        sendJsonRequest("-7.559210500929888", "110.81881210000006");


        return rootView;
    }

    private void configRecyclerView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterPoiNearby = new AdapterPoiNearby(getActivity());
        mRecyclerView.setAdapter(adapterPoiNearby);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final FloatingActionButton fab_image = (FloatingActionButton)rootView.findViewById(R.id.fab_image);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (index + 1) % 2;
                fab_image.setIcon(mDrawables[index], true);
            }
        });

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override public void run() {
//                swipeLayout.setRefreshing(false);
//                Toast.makeText(getActivity(), "Refresh Finish", Toast.LENGTH_SHORT);
//            }
//        },5000);
        sendJsonRequest("-7.559210500929888", "110.81881210000006");
    }

    private void makeJsonObjectRequest(String latitude, String longitude) {

        showMaterialProgress();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://lemuria.basicitteam.com/index.php/api/poi/nearby?latitude="+latitude+"&longitude="+longitude, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean status = jObj.getBoolean("status");
                    if (status == true) {

                        JSONArray poi = jObj.getJSONArray("poi");
                        for (int i = 0; i < poi.length(); i++) {
                            JSONObject actor = poi.getJSONObject(i);
                            String id = actor.getString("id_poi");
                            String foto = actor.getString("foto_poi");
                            String nama = actor.getString("nama_poi");
                            String desc = actor.getString("poi_desc");
                            String date = actor.getString("date_added");
                            String ratting = actor.getString("rating");
                            String review = actor.getString("jumlah_review");
                            String id_kategori = actor.getString("id_kategori_poi");
                            String nama_kategori = actor.getString("nama_kategori");
                            String latitude = actor.getString("latitude");
                            String longitude = actor.getString("longitude");
                            String jarak = actor.getString("jarak");

//                            mPOI = new POINearby(id,foto,nama,date,ratting,review,id_kategori,nama_kategori,latitude,longitude,jarak);
//                            list.add(new POINearby(id,foto,nama,date,ratting,review,id_kategori,nama_kategori,latitude,longitude,jarak));
                            POINearby nearby = new POINearby();
                            nearby.setId_poi(id);
                            nearby.setDate_added(date);
                            nearby.setFoto_poi(foto);
                            nearby.setId_kategori_poi(id_kategori);
                            nearby.setJarak(jarak);
                            nearby.setJumlah_review(review);
                            nearby.setLatitude(latitude);
                            nearby.setLongitude(longitude);
                            nearby.setNama_kategori(nama_kategori);
                            nearby.setNama_poi(nama);
                            nearby.setRatting(ratting);
                            listNearby.add(nearby);
                        }

                        adapterPoiNearby.setNearbyList(listNearby);

                        System.out.println("INFO = "+listNearby);
                        System.out.println("Jumlah POI = "+poi.length());

                    }
                    Toast.makeText(getActivity(),
                            "Response: " + listNearby,
                            Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                pv_linear_color.stop();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                pv_linear_color.stop();
            }
        });

        // Adding request to request queue
        requestQueue.add(jsonObjReq);
    }

    private void configMyRecyclerView() {
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition =
                        (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ? 0 : mRecyclerView.getChildAt(0).getTop();
                swipeLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        for (int i = 0; i < 10; ++i)
            mContentItems.add(new Object());

        makeJsonObjectRequest("-7.559210500929888", "110.81881210000006");

        System.out.println("Jumlah INFO = "+list.size());
        mAdapter = new RecyclerViewMaterialAdapter(new POIViewAdapter(list));
        mRecyclerView.setAdapter(mAdapter);

        final FloatingActionButton fab_image = (FloatingActionButton)rootView.findViewById(R.id.fab_image);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (index + 1) % 2;
                fab_image.setIcon(mDrawables[index], true);
                Intent i = new Intent(getActivity(), SuggestFragment.class);
                startActivityForResult(i, 1);
            }
        });

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }


    private void showMaterialProgress(){
        pv_linear_color.applyStyle(R.style.ColorsCircularProgressDrawableStyle);
        pv_linear_color.start();
    }

    private ArrayList<POINearby> parseJSONResponse(JSONObject response) {
        ArrayList<POINearby> listNearby = new ArrayList<>();
        if(response != null && response.length() > 0) {
            try {
                JSONObject jObj = new JSONObject(response.toString());
                System.out.println("RESPONSE JSONObject POI : "+jObj.toString());
                boolean status = jObj.getBoolean("status");
                if (status == true) {

                    JSONArray poi = jObj.getJSONArray("poi");
                    for (int i = 0; i < poi.length(); i++) {
                        JSONObject actor = poi.getJSONObject(i);
                        String id = actor.getString("id_poi");
                        String foto = actor.getString("foto_poi");
                        String nama = actor.getString("nama_poi");
//                        String desc = actor.getString("poi_desc");
                        String date = actor.getString("date_added");
                        String ratting = actor.getString("rating");
                        String review = actor.getString("jumlah_review");
                        String id_kategori = actor.getString("id_kategori_poi");
                        String nama_kategori = actor.getString("nama_kategori");
                        String latitude = actor.getString("latitude");
                        String longitude = actor.getString("longitude");
                        String jarak = actor.getString("jarak");

//                            mPOI = new POINearby(id,foto,nama,date,ratting,review,id_kategori,nama_kategori,latitude,longitude,jarak);
//                    list.add(new POINearby(id,foto,nama,date,ratting,review,id_kategori,nama_kategori,latitude,longitude,jarak));
                        if(ratting == null || ratting.equalsIgnoreCase("null")){
                            ratting = "0";
                        }
                        if(review == null || review.equalsIgnoreCase("null")){
                            review = "0";
                        }

                        String urlThumnail = null;

                        if(foto.equalsIgnoreCase("null")||foto == null){
                            urlThumnail = "http://www.freebiesgallery.com/wp-content/uploads/2012/04/404-page.jpg";
                        }else{
                            urlThumnail = foto;
                        }

                        POINearby nearby = new POINearby();
                        nearby.setId_poi(id);
                        nearby.setDate_added(date);
                        nearby.setFoto_poi(urlThumnail);
                        nearby.setId_kategori_poi(id_kategori);
                        nearby.setJarak(jarak);
                        nearby.setJumlah_review(review);
                        nearby.setLatitude(latitude);
                        nearby.setLongitude(longitude);
                        nearby.setNama_kategori(nama_kategori);
                        nearby.setNama_poi(nama);
                        nearby.setRatting(ratting);
                        listNearby.add(nearby);
                    }

                    System.out.println("LIST NEARBY = "+listNearby.toString());
                    System.out.println("Jumlah NEARBY = "+listNearby.size());

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
            swipeLayout.setRefreshing(false);
        }
        return listNearby;
    }

    private void sendJsonRequest(String latitude, String longitude){
        String uri = "http://lemuria.basicitteam.com/index.php/api/poi/nearby?latitude="+latitude+"&longitude="+longitude;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                uri,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listNearby = parseJSONResponse(response);
                        adapterPoiNearby.setNearbyList(listNearby);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();

                swipeLayout.setRefreshing(false);
            }
        });
        requestQueue.add(request);
    }
}
