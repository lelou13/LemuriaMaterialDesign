package androidhive.info.materialdesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.controller.VolleySingleton;
import androidhive.info.materialdesign.model.POINearby;

/**
 * Created by Khusnan on 6/14/15.
 */
public class AdapterPoiNearby extends RecyclerView.Adapter<AdapterPoiNearby.ViewHolderNearby>{

    private ArrayList<POINearby> listNearby = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public AdapterPoiNearby(Context context){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();
    }

    public void setNearbyList(ArrayList<POINearby> listNearby){
        this.listNearby = listNearby;
        notifyItemRangeChanged(0, listNearby.size());
    }

    @Override
    public ViewHolderNearby onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_poi, parent, false);
        ViewHolderNearby viewHolder = new ViewHolderNearby(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderNearby holder, int position) {
        POINearby currentNearby = listNearby.get(position);
        holder.title.setText(currentNearby.getNama_poi());
        holder.jarak.setText(currentNearby.getJarak()+" KM");
        holder.rattings.setText(currentNearby.getRatting());
        holder.reviews.setText(currentNearby.getJumlah_review());
        String urlThumnail = currentNearby.getFoto_poi();
        if(urlThumnail!=null){
            imageLoader.get(urlThumnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.thumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
//                    holder.thumbnail.setImageDrawable(R.drawable.ic_back_map);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listNearby.size();
    }

    static  class ViewHolderNearby extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView thumbnail;
        private TextView title, rattings, reviews, jarak, alamat, category;

        public ViewHolderNearby(View view){
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.card_image);
            category = (TextView) view.findViewById(R.id.categoryColor);
            title = (TextView) view.findViewById(R.id.title);
            rattings = (TextView) view.findViewById(R.id.like);
            reviews = (TextView) view.findViewById(R.id.review);
            jarak = (TextView) view.findViewById(R.id.jarak);
//            alamat = (TextView) view.findViewById(R.id.alamat);
        }

        @Override
        public void onClick(View view) {
            Log.d("CLICK", "onClick " + getPosition() + " , " + title.getText());
        }
    }
}
