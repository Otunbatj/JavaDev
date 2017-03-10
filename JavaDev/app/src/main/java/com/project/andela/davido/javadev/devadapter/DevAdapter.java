package com.project.andela.davido.javadev.devadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.andela.davido.javadev.R;
import com.project.andela.davido.javadev.devmodel.DevModel;
import com.project.andela.davido.javadev.util.CircleTransform;

import java.util.List;

/**
 * Created by DAVIDO on 3/10/2017.
 */

public class DevAdapter extends RecyclerView.Adapter<DevAdapter.DataHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<DevModel> userList;
    private Context context;
    private static DevAdapter.MyClickListener myClickListener;

    public DevAdapter(List<DevModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public static class DataHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView userImage;
        TextView userName;

        public DataHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.image);
            userName = (TextView) itemView.findViewById(R.id.name);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(DevAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DevAdapter.DataHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_item, parent, false);

        DevAdapter.DataHolder dataHolder = new DevAdapter.DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DevAdapter.DataHolder holder, int position) {
        Glide.with(context).load(userList.get(position).getImageURL())
                .thumbnail(0.5f)
                .crossFade()
                .transform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.userImage);
        holder.userImage.setColorFilter(null);
        holder.userName.setText(userList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

