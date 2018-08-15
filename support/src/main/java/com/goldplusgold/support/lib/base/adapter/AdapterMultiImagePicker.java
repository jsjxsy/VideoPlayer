//package com.goldplusgold.support.lib.base.adapter;
//
//import android.alibaba.support.R;
//import android.content.Context;
//import android.database.Cursor;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.Toast;
//
////import com.alibaba.intl.android.picture.widget.LoadableImageView;
//
//import java.util.ArrayList;
//
///**
// * Created by kevin on 15/8/24.
// */
//public class AdapterMultiImagePicker extends RecyclerView.Adapter<AdapterMultiImagePicker.ViewHolder>
//        implements View.OnClickListener {
//
//    public static final int VIEW_TYPE_CAMERA = 1;
//    public static final int VIEW_TYPE_IMAGE = 2;
//
//    private Context mContext;
//
//    private Cursor mCursor;
//
//    private int mMax;
//
//    private ArrayList<String> mCheckedPath;
//
//    private OnImageCheckChangeListener mOnImageCheckChangeListener;
//
//    private OnItemClickListener mOnItemClickListener;
//
//    public AdapterMultiImagePicker(Context context) {
//        mContext = context;
//        mCheckedPath = new ArrayList<String>();
//    }
//
//    public void setCursor(Cursor cursor) {
//        if (mCursor != null && !mCursor.isClosed()) {
//            mCursor.close();
//            mCursor = null;
//        }
//        mCursor = cursor;
//        notifyDataSetChanged();
//    }
//
//    public void setMax(int max) {
//        mMax = max;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        mOnItemClickListener = onItemClickListener;
//    }
//
//    public void setOnImageCheckChangeListener(OnImageCheckChangeListener onImageCheckChangeListener) {
//        mOnImageCheckChangeListener = onImageCheckChangeListener;
//    }
//
//    public ArrayList<String> getCheckedPath() {
//        return mCheckedPath;
//    }
//
//    public void setCheckedPath(ArrayList<String> checkedPath) {
//        mCheckedPath = checkedPath;
//        if (mCheckedPath == null) {
//            mCheckedPath = new ArrayList<String>();
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_CAMERA) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_image_camera,
//                    parent, false);
//            ViewHolder holder = new ViewHolder(view);
//            view.setOnClickListener(this);
//            return holder;
//        } else if (viewType == VIEW_TYPE_IMAGE) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_image_picker,
//                    parent, false);
//            ViewHolder holder = new ViewHolder(view);
//            view.setOnClickListener(this);
//            holder.mCheckView.setOnClickListener(this);
//            return holder;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (holder == null) {
//            return;
//        }
//        if (mCursor == null) {
//            return;
//        }
//
//        holder.itemView.setTag(position);
//
//        if (getItemViewType(position) == VIEW_TYPE_IMAGE) {
//            mCursor.moveToPosition(position - 1);
//            String path = mCursor.getString(1);
//            holder.mImageView.setTag(path);
//            holder.mCheckView.setTag(path);
//            holder.mImageView.load(path);
//            if (mCheckedPath.contains(path)) {
//                holder.mCheckView.setImageResource(R.drawable.ic_photo_small_checked);
//            } else {
//                holder.mCheckView.setImageResource(R.drawable.ic_photo_small_unchecked);
//            }
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return VIEW_TYPE_CAMERA;
//        }
//        return VIEW_TYPE_IMAGE;
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mCursor == null) {
//            return 1;
//        }
//        if (mCursor.isClosed()) {
//            return 1;
//        }
//        return mCursor.getCount() + 1;
//    }
//
//    public void destroy() {
//        if (mCursor != null && !mCursor.isClosed()) {
//            mCursor.close();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.id_checked_item_image_picker) {
//            String path = (String) v.getTag();
//            if (mCheckedPath.contains(path)) {
//                ((ImageView) v).setImageResource(R.drawable.ic_photo_small_unchecked);
//                mCheckedPath.remove(path);
//            } else {
//                if (mCheckedPath.size() >= mMax) {
//                    Toast.makeText(mContext, mContext.getString(R.string.common_selectfiveimages).replace("5", String.valueOf(mMax)),
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ((ImageView) v).setImageResource(R.drawable.ic_photo_small_checked);
//                mCheckedPath.add(path);
//            }
//            if (mOnImageCheckChangeListener != null) {
//                mOnImageCheckChangeListener.onImageCheckChanged(mCheckedPath.contains(path), path);
//            }
//        } else {
//            if (v.getTag() == null) {
//                return;
//            }
//            int pos = (int) v.getTag();
//            String path = null;
//            if (getItemViewType(pos) == VIEW_TYPE_IMAGE) {
//                path = (String) v.findViewById(R.id.id_thumb_item_image_picker).getTag();
//            }
//            if (mOnItemClickListener != null) {
//                mOnItemClickListener.onItemClicked(pos, path);
//            }
//        }
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView mImageView;
//        ImageView mCheckView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mImageView = (ImageView) itemView.findViewById(R.id.id_thumb_item_image_picker);
//            mCheckView = (ImageView) itemView.findViewById(R.id.id_checked_item_image_picker);
//        }
//    }
//
//    public interface OnImageCheckChangeListener {
//        void onImageCheckChanged(boolean checked, String path);
//    }
//
//    public interface OnItemClickListener {
//        void onItemClicked(int position, String path);
//    }
//}
