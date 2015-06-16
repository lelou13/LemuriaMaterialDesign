package androidhive.info.materialdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.model.POINearby;

/**
 * Created by Khusnan on 6/10/15.
 */
public class POIViewAdapter extends RecyclerView.Adapter<POIViewAdapter.ViewHolder> {

    private List<POINearby> listData;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public POIViewAdapter(List<POINearby> listData) {
        this.listData = listData;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("Jumlah POI 3 = "+listData.size());
        return listData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_header_poi, parent, false);
                ViewHolder myVH = new ViewHolder(view);
                return myVH;
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_poi, parent, false);
                ViewHolder myVH = new ViewHolder(view);
                return myVH;
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        POINearby item = listData.get(position);
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                holder.title.setText(item.getNama_poi());
                holder.like.setText(item.getRatting()+" Rattings");
                holder.review.setText(" "+item.getJumlah_review()+" Reviews");
                holder.jarak.setText(" "+item.getNama_poi()+" KM");
                holder.alamat.setText(" "+item.getNama_poi());
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, like, review, jarak, alamat;
        public ImageView card_image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            like = (TextView) itemView.findViewById(R.id.like);
            review = (TextView) itemView.findViewById(R.id.review);
//            jarak = (TextView) itemView.findViewById(R.id.jarak);
//            alamat = (TextView) itemView.findViewById(R.id.alamat);
            card_image = (ImageView) itemView.findViewById(R.id.card_image);
            System.out.println("Jumlah POI 2 = "+listData.size());
        }
    }
}