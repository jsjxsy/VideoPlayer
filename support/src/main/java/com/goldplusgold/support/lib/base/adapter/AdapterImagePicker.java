//package com.goldplusgold.support.lib.base.adapter;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.provider.MediaStore.Images;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ResourceCursorAdapter;
//
//
//import com.goldplusgold.support.lib.R;
//
//import java.util.HashMap;
//import java.util.Iterator;
//
///**
// * @author alibaba-guangming
// */
//public class AdapterImagePicker extends ResourceCursorAdapter {
//
//    private HashMap<String, String> mHashChecked;
//    private int mMaxSize = 4;
//
//    public AdapterImagePicker(Context context) {
//        super(context, R.layout.view_list_item_image_pick, null, false);
//        mHashChecked = new HashMap<String, String>();
//    }
//
//    public void setMaxSize(int maxSize) {
//        mMaxSize = maxSize;
//    }
//
//    public void addHashPicked(String url) {
//        mHashChecked.put(url, url);
//    }
//
//    public int onImagePickedChange(String pageName, View view, int position) {
//        if (!getCursor().moveToPosition(position)) {
//            return mHashChecked.size();
//        }
//        String url = getCursor().getString(getCursor().getColumnIndex(Images.ImageColumns.DATA));
//        Log.d("AdapterImagePicker", "url:" + url);
//        if (mHashChecked.containsKey(url)) {
//            mHashChecked.remove(url);
//        } else if (mHashChecked.size() < mMaxSize) {
//            mHashChecked.put(url, url);
//        } else {
//            return mHashChecked.size() + 1;
//        }
//        ImageView sImageCheck = (ImageView) view.findViewById(R.id.id_checked_item_image_picker);
//        if (url != null && mHashChecked.containsKey(url)) {
//            sImageCheck.setImageResource(R.drawable.ic_checked);
//        } else {
//            sImageCheck.setImageResource(R.drawable.ic_uncheck);
//        }
//        return mHashChecked.size();
//    }
//
//    public String[] getImagePicked() {
//        if (mHashChecked.size() <= 0) {
//            return null;
//        }
//        Iterator<String> it = mHashChecked.keySet().iterator();
//        String[] imagesPicked = new String[mHashChecked.size()];
//        int position = 0;
//        while (it.hasNext()) {
//            String url = (String) it.next();
//            imagesPicked[position] = url;
//
//            position++;
//        }
//
//        return imagesPicked;
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        View v = super.newView(context, cursor, parent);
//
//        ItemViewHolder holder = new ItemViewHolder();
//        holder.sImageThumb = (ImageView) v.findViewById(R.id.id_thumb_item_image_picker);
//        holder.sImageCheck = (ImageView) v.findViewById(R.id.id_checked_item_image_picker);
//
//        v.setTag(holder);
//        return v;
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        ItemViewHolder holder = (ItemViewHolder) view.getTag();
//
//        String url = cursor.getString(cursor.getColumnIndex(Images.ImageColumns.DATA));
//
//        holder.sImageThumb.load(url);
//        if (url != null && mHashChecked.containsKey(url)) {
//            holder.sImageCheck.setImageResource(R.drawable.ic_checked);
//        } else {
//            holder.sImageCheck.setImageResource(R.drawable.ic_uncheck);
//        }
//    }
//
//    public static class ItemViewHolder {
//        public ImageView sImageThumb;
//        public ImageView sImageCheck;
//    }
//}
