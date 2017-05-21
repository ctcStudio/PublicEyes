package com.hiepkhach9x.publiceyes.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.hiepkhach9x.base.ImageUtil;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.view.RectangleImageView;
import com.hiepkhach9x.publiceyes.view.RectangleLayout;

import java.util.ArrayList;

/**
 * Created by hungh on 3/5/2017.
 */

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.Holder> {
    private ArrayList<Complaint> mComplaints;
    private LayoutInflater mInflater;
    private Context context;

    public ComplaintsAdapter(Context context, ArrayList<Complaint> complaints) {
        this.context = context;
        this.mComplaints = complaints;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        VideoView videoView;
        RectangleLayout layoutVideo;
        ImageView playVideo;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            address = (TextView) itemView.findViewById(R.id.address);
            content = (TextView) itemView.findViewById(R.id.text_content);
            thumbnail = (RectangleImageView) itemView.findViewById(R.id.image_thumb);
            videoView = (VideoView) itemView.findViewById(R.id.video);
            layoutVideo = (RectangleLayout) itemView.findViewById(R.id.layout_video);
            playVideo = (ImageView) itemView.findViewById(R.id.play_video);
            playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    videoView.start();
                    playVideo.setVisibility(View.GONE);
                }
            });
        }

        public void updateView(Complaint complaint) {
            name.setText(complaint.getUserName());
            time.setText(complaint.getFormatTime());
            address.setText(complaint.getAddress());
            content.setText(complaint.getCategoryName());
            if(complaint.isVideo()) {
                thumbnail.setVisibility(View.GONE);
                initVideo(complaint.getUrl());
            } else {
                layoutVideo.setVisibility(View.GONE);
                ImageUtil.loadImage(context, complaint.getUrl(), thumbnail);
            }
        }

        private void initVideo(String url){
            MediaController mc = new MediaController(context);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            Uri video = Uri.parse(url);
            videoView.setMediaController(mc);
            videoView.setVideoURI(video);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    playVideo.setVisibility(View.VISIBLE);
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playVideo.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
