package com.hiepkhach9x.publiceyes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hiepkhach9x.publiceyes.App;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.view.RectangleImageView;

import java.util.ArrayList;

import co.core.imageloader.NDisplayOptions;
import co.core.imageloader.NImageLoader;

/**
 * Created by hungh on 3/5/2017.
 */

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.Holder> {
    private ArrayList<Complaint> mComplaints;
    private LayoutInflater mInflater;
    NImageLoader mImageloader;

    public ComplaintsAdapter(Context context, ArrayList<Complaint> complaints) {
        this.mComplaints = complaints;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageloader = App.get().getImageLoader();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_complaint, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Complaint complaint = mComplaints.get(position);
        holder.updateView(complaint);
    }

    @Override
    public int getItemCount() {
        return mComplaints.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name;
        TextView time;
        TextView address;
        TextView content;
        RectangleImageView thumbnail;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            address = (TextView) itemView.findViewById(R.id.address);
            content = (TextView) itemView.findViewById(R.id.text_content);
            thumbnail = (RectangleImageView) itemView.findViewById(R.id.image_thumb);
        }

        public void updateView(Complaint complaint) {
            name.setText(complaint.getCategoryName());
            time.setText(complaint.getTime());
            address.setText(complaint.getAddress());
            content.setText(complaint.getDescription());
            if (mImageloader != null) {
                NDisplayOptions.Builder builder = new NDisplayOptions.Builder();
                builder.cacheOnDisk(true);
                builder.setImageOnLoading(R.drawable.ic_photo);
                mImageloader.display(complaint.getImageThumb(), thumbnail, builder.build());
            }
        }
    }
}
