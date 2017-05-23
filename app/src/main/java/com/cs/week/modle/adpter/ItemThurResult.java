package com.cs.week.modle.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cs.week.R;
import com.cs.week.entry.DateSqlListResult;
import com.cs.week.views.GlideCircleTransform;

import java.util.List;

/**
 * Created by chenshuai on 2017/4/27.
 */

public class ItemThurResult extends RecyclerView.Adapter<ItemThurResult.MyViewHolder> {
    private Context context;
    private List<DateSqlListResult> mList;
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
        boolean onLongItemClick(View view, int position);
    }
    public OnItemClickLitener mOnItemClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener){
        this.mOnItemClickLitener=onItemClickLitener;
    }

    public ItemThurResult(Context context, List<DateSqlListResult> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ItemThurResult.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_thur, parent, false);
        return new ItemThurResult.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mContent.setText(mList.get(position).getContent());
        holder.mTime.setText(mList.get(position).getTime());
        holder.mTimeStart.setText(mList.get(position).getCreattime());

        switch (mList.get(position).getColor()){
            case 0:
                holder.mImagColor.setImageResource(R.drawable.ic_priority_blue);
                break;
            case 1:
                holder.mImagColor.setImageResource(R.drawable.ic_priority_darkblue);
                break;
            case 2:
                holder.mImagColor.setImageResource(R.drawable.ic_priority_green);
                break;
            case 3:
                holder.mImagColor.setImageResource(R.drawable.ic_priority_origin);
                break;
            case 4:
                holder.mImagColor.setImageResource(R.drawable.ic_priority_red);
                break;

        }

        if (!TextUtils.isEmpty(mList.get(position).getImagepath())) {
           // Glide.with(context).load(mList.get(position).getPath().get(0)).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.mImagView);
            Glide.with(context)
                    .load(mList.get(position).getImagepath())
                    .transform(new GlideCircleTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mImagView);

        }else {

        }
        if (mOnItemClickLitener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position1 = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,position1);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position1 = holder.getLayoutPosition();
                 return mOnItemClickLitener.onLongItemClick(holder.itemView,position1);

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        ImageView mImagView;
        LinearLayout mTask;
        private final ImageView mImagColor;
        private final TextView mTimeStart;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle= (TextView) itemView.findViewById(R.id.tv_item_thur_title);
            mContent= (TextView) itemView.findViewById(R.id.tv_item_thur_content);
            mTime= (TextView) itemView.findViewById(R.id.tv_item_thur_time);
            mImagView= (ImageView) itemView.findViewById(R.id.iv_item_thur_icon);
            mTask= (LinearLayout) itemView.findViewById(R.id.ll_task_thur_mask);
            mImagColor = (ImageView) itemView.findViewById(R.id.iv_item_thur_priority);
            mTimeStart = (TextView) itemView.findViewById(R.id.tv_item_thur_timestart);



        }
    }

}


