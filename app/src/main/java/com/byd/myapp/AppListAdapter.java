package com.byd.myapp;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.byd.myapp.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    public interface OnSendToDashboardListener {
        void onSendToDashboard(AppInfo app);
        void onSendToMain(AppInfo app); // gardé pour usage futur (panel de contrôle)
    }

    private List<AppInfo> mApps = new ArrayList<>();
    private final OnSendToDashboardListener mListener;
    // Package actuellement affiché sur le cluster (indicateur vert)
    private String mCurrentPackage = null;

    public AppListAdapter(OnSendToDashboardListener listener) {
        mListener = listener;
    }

    public void setApps(List<AppInfo> apps) {
        mApps = apps;
        notifyDataSetChanged();
    }

    /** Met à jour l'indicateur de l'app actuellement sur le cluster. */
    public void setCurrentPackage(String packageName) {
        mCurrentPackage = packageName;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AppInfo app = mApps.get(position);
        holder.ivIcon.setImageDrawable(app.icon);
        holder.tvName.setText(app.appName);

        // Indicateur vert : app actuellement sur le cluster
        boolean isActive = app.packageName != null && app.packageName.equals(mCurrentPackage);
        holder.viewActiveIndicator.setVisibility(isActive ? View.VISIBLE : View.GONE);

        // Tap sur la ligne entière = envoyer sur le cluster
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSendToDashboard(app);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivIcon;
        final TextView  tvName;
        final View      viewActiveIndicator;

        ViewHolder(View itemView) {
            super(itemView);
            ivIcon              = (ImageView) itemView.findViewById(R.id.iv_app_icon);
            tvName              = (TextView)  itemView.findViewById(R.id.tv_app_name);
            viewActiveIndicator = itemView.findViewById(R.id.view_active_indicator);
        }
    }
}
