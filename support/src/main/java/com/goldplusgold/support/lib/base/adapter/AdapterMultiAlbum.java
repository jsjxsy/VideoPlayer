//package com.goldplusgold.support.lib.base.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.goldplusgold.support.lib.R;
//import com.goldplusgold.support.lib.base.fragment.FragmentMultiAlbum;
//
//import java.util.List;
//
//
///**
// * Created by kevin on 15/8/24.
// */
//public class AdapterMultiAlbum extends RecyclerView.Adapter<AdapterMultiAlbum.ViewHolder>
//        implements View.OnClickListener {
//
//    private Context mContext;
//
//    private List<FragmentMultiAlbum.GalleryData> datas;
//
//    private OnItemClickListener mOnItemClickListener;
//
//    public AdapterMultiAlbum(Context context) {
//        mContext = context;
//    }
//
//    public void setDatas(List<FragmentMultiAlbum.GalleryData> datas) {
//        this.datas = datas;
//        notifyDataSetChanged();
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        mOnItemClickListener = onItemClickListener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_album,
//                parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        view.setOnClickListener(this);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        FragmentMultiAlbum.GalleryData data = datas.get(position);
//        if (data == null) {
//            return;
//        }
//        holder.itemView.setTag(data);
//        holder.mImageView.load(data.getData());
//        if (data.getBucketName() == null) {
//            holder.mNameText.setText(mContext.getString(R.string.common_album));
//        } else {
//            holder.mNameText.setText(data.getBucketName());
//        }
//        holder.mCountText.setText(String.format("(%d)", data.getCount()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return datas == null ? 0 : datas.size();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickListener != null) {
//            mOnItemClickListener.onItemClicked(v, (FragmentMultiAlbum.GalleryData) v.getTag());
//        }
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView mImageView;
//        TextView mNameText;
//        TextView mCountText;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mImageView = (mImageView) itemView.findViewById(R.id.item_image);
//            mNameText = (TextView) itemView.findViewById(R.id.item_name);
//            mCountText = (TextView) itemView.findViewById(R.id.item_count);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClicked(View view, FragmentMultiAlbum.GalleryData data);
//    }
//
//}
