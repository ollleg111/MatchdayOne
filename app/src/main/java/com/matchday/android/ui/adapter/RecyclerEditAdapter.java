package com.matchday.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.matchday.android.R;
import com.matchday.android.model.EditFilter;
import com.matchday.android.ui.widget.LatoBlackTextView;
import com.matchday.android.util.GPUImageFilterTools;
import com.matchday.android.util.GPUImageFilterToolsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marina on 15.09.16.
 */
public class RecyclerEditAdapter extends RecyclerView.Adapter<RecyclerEditAdapter.ViewHolder> {
    private GPUImageFilterToolsUtils.FilterList mFilterList;

    public RecyclerEditAdapter(GPUImageFilterToolsUtils.FilterList mFilterList) {
        this.mFilterList = mFilterList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.imageFilter.setImageDrawable(mFilterList.get(position).getImageFilter());
//        holder.textFilter.setText((CharSequence) mFilterList.get(position).getTextFilter());
        holder.imageFilter.setImageDrawable(mFilterList.images.get(position));
        holder.textFilter.setText(mFilterList.names.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilterList.names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_filter)
        ImageView imageFilter;
        @BindView(R.id.txt_filter)
        LatoBlackTextView textFilter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
