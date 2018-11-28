package com.matchday.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.model.MenuItemTemplate;
import com.matchday.android.model.Template;
import com.matchday.android.ui.widget.LatoHeavyTextView;
import com.matchday.android.ui.widget.LatoTextView;
import com.matchday.android.util.UiUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marina on 30.08.16.
 */
public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder> {

    List<MenuItemTemplate> mListMenuItem;
    List<Template> mTemplateList;
    public static boolean mVarietyItems;
    Bitmap mBitmap;
    Context mContext;
//    int mHeightTemplate;

    public RecyclerMenuAdapter(List<MenuItemTemplate> list, List<Template> templateList,
                               Bitmap bitmap, Context context, boolean variety/*, int heightTemplate*/) {
        this.mListMenuItem = list;
        this.mTemplateList = templateList;
        this.mBitmap = bitmap;
        this.mContext = context;
        this.mVarietyItems = variety;
//        this.mHeightTemplate = heightTemplate;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_menu)
        ImageView mImageMenuItem;
        @BindView(R.id.txt_meni_item)
        LatoTextView mTextMenuItem;
        @BindView(R.id.img_preview_templates)
        ImageView mImagePreviewTemplates;
        @BindView(R.id.view_background)
        View mViewBackGround;
        @BindView(R.id.txt_back)
        LatoHeavyTextView mTextBack;
        @BindView(R.id.img_back)
        ImageView mImageBack;
        @BindView(R.id.frame_layout_template)
        FrameLayout mLayoutTemplate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerMenuAdapter.ViewHolder holder, int position) {
//        holder.mLayoutTemplate.getLayoutParams().height = mHeightTemplate;
//        holder.mLayoutTemplate.getLayoutParams().width = mHeightTemplate;
//        holder.mTextMenuItem.getLayoutParams().width = mHeightTemplate;
        holder.mImageMenuItem.setImageBitmap(mBitmap);
        if (!mVarietyItems) {
            holder.mTextMenuItem.setText(mListMenuItem.get(position).getName());
            Picasso.with(mContext).load(Const.BASE_URL +
                    UiUtils.getUriDependingDensityDpi(mListMenuItem.get(position).getPreview(), mContext)).
                    into(holder.mImagePreviewTemplates);
        } else {
            if (position == 0) {
                holder.mTextMenuItem.setText(mTemplateList.get(position).getName());
                holder.mViewBackGround.setVisibility(View.VISIBLE);
                holder.mTextBack.setVisibility(View.VISIBLE);
                holder.mImageBack.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(Const.BASE_URL +
                        UiUtils.getUriDependingDensityDpi(mTemplateList.get(position).getPreview(), mContext)).
                        into(holder.mImagePreviewTemplates);
            } else {
                holder.mTextMenuItem.setText(mTemplateList.get(position).getName());
                Picasso.with(mContext).load(Const.BASE_URL +
                        UiUtils.getUriDependingDensityDpi(mTemplateList.get(position).getPreview(), mContext)).
                        into(holder.mImagePreviewTemplates);
            }
        }
    }

    @Override
    public int getItemCount() {
        return !mVarietyItems ? mListMenuItem.size() : mTemplateList.size();
    }
}
