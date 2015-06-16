package androidhive.info.materialdesign.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.controller.AppController;
import androidhive.info.materialdesign.model.POINearby2;

/**
 * Created by Khusnan on 6/14/15.
 */
public class EventFragment2 extends Fragment {

    LinkedList<POINearby2> list = new LinkedList<POINearby2>();
    POINearby2 info;
    View rootView;
    private ProgressView pv_linear_color;
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_event, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        pv_linear_color = (ProgressView)rootView.findViewById(R.id.progress_pv_circular_colors);

        populateData("-7.559210500929888", "110.81881210000006"); // Fill the LinkedList here.
        createPersonDataView();
        configMyRecyclerView();

        return rootView;
    }

    private void configMyRecyclerView() {
        RecyclerView rvList = (RecyclerView) rootView.findViewById(R.id.recyclerView);

//        List kita tidak berubah-ubah ukurannya.
        rvList.setHasFixedSize(true);

        // Buat LayoutManager dari Activity ini (MainActivity)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(layoutManager);

        // Taruh data kita ke dalam data
        POIViewAdapter2 adapter= new POIViewAdapter2(list);

        // Taruh adapter ke dalam RecycleView
        rvList.setAdapter(adapter);

        // Sedikit animasi pemanis
        rvList.setItemAnimator(new DefaultItemAnimator());
    }

    private void createPersonDataView() {

    }

    private void populateData(String latitude, String longitude) {
        list.clear();
        showMaterialProgress();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://lemuria.basicitteam.com/index.php/api/poi/nearby?latitude="+latitude+"&longitude="+longitude, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("ISI ARRAY", response.toString());

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

                            info = new POINearby2(id,foto,nama,date);
                            list.add(info);
                        }

                        System.out.println("INFO = "+list);
                        System.out.println("Jumlah POI = "+poi.length());

                    }
                    Toast.makeText(getActivity(),
                            "Response: " + list,
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
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showMaterialProgress(){
        pv_linear_color.applyStyle(R.style.ColorsCircularProgressDrawableStyle);
        pv_linear_color.start();
    }

    private class POIViewAdapter2 extends RecyclerView.Adapter <POIViewAdapter2.ViewHolder> {
        private LinkedList listData;

        /**
         * Konstruktor untuk mengisi Adapter dengan untaian data yang hendak ditaruh.
         * @param listData
         */
        public POIViewAdapter2(LinkedList listData) {
            this.listData = listData;
        }

        /**
         * Fungsi yang akan ditaruh oleh LayoutManager ketika hendak membuat tampilan (View) baru.
         * @param root Fragment/Activity yang hendak ditempeli untaian (List)
         * @param viewType Tipe View yang hendak dihasilkan.
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup root, int viewType) {
            // Dapatkan View dari inflater
            View v = LayoutInflater.from(root.getContext()).inflate(R.layout.list_item_poi, null);

            // Masukkan ke dalam ViewHolder yang baru
            ViewHolder myVH = new ViewHolder(v);

            // Kembalikan ViewHolder yang sudah kita panggil.
            return myVH;
        }

        /**
         * Fungsi ini mengisi setiap data ke View.
         * @param holder Pemegang View yang hendak diperbaharui
         * @param i Posisi data yang hendak diisi dalam untaian
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int i) {
            // Mengambil satu item yang hendak ditaruh
            POINearby2 item = (POINearby2) listData.get(i);

            holder.title.setText(item.getHeadlines());
            Log.d(this.getClass().getCanonicalName(), item.getHeadlines());
        }

        /**
         * Fungsi ini mengembalikan jumlah data yang hendak ditampilkan
         * @return Jumlah data dalam untaian.
         */
        @Override
        public int getItemCount() {
            return listData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
            }
        }
    }
}
