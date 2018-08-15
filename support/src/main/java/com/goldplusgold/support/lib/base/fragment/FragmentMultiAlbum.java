//package com.goldplusgold.support.lib.base.fragment;
//
//import android.content.ContentResolver;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.goldplusgold.support.lib.R;
//import com.goldplusgold.support.lib.base.adapter.AdapterMultiAlbum;
//import com.goldplusgold.support.lib.widget.AsyncTask;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//
///**
// * Created by kevin on 15/8/24.
// */
//public class FragmentMultiAlbum extends BaseFragment implements AdapterMultiAlbum.OnItemClickListener {
//
//    private RecyclerView mRecyclerView;
//    private AdapterMultiAlbum mAdapterMultiAlbum;
//
//    private View mNoImageView;
//
//    private OnAlbumPickListener mOnAlbumPickListener;
//
//    public static FragmentMultiAlbum newInstance() {
//        FragmentMultiAlbum fragmentMultiAlbum = new FragmentMultiAlbum();
//        return fragmentMultiAlbum;
//    }
//
//    public FragmentMultiAlbum() {
//    }
//
//    public void setOnAlbumPickListener(OnAlbumPickListener onAlbumPickListener) {
//        mOnAlbumPickListener = onAlbumPickListener;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mAdapterMultiAlbum = new AdapterMultiAlbum(getActivity());
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_multi_album, container, false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyler_view);
//        mNoImageView = view.findViewById(R.id.no_image);
//        setUpRecyclerView();
//        return view;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return 0;
//    }
//
//    private void setUpRecyclerView() {
//        mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(mAdapterMultiAlbum);
//        mAdapterMultiAlbum.setOnItemClickListener(this);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        asyncData();
//    }
//
//    protected void asyncData() {
//        if (isAvailableActivity()) {
//            new CursorTask(this).execute(AsyncTask.TYPE_LOCAL, getActivity().getContentResolver());
//        }
//    }
//
//    @Override
//    public void onItemClicked(View view, GalleryData data) {
//        if (mOnAlbumPickListener != null) {
//            mOnAlbumPickListener.onAlbumPicked(data);
//        }
//    }
//
//    static class CursorTask extends AsyncTask<ContentResolver, String, ArrayList<GalleryData>> {
//
//        private static final Uri URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        private static final String[] PROJECTIONS = new String[]{
//                MediaStore.Images.Media._ID,
//                MediaStore.Images.Media.BUCKET_ID, // 直接包含该图片文件的文件夹ID，防止在不同下的文件夹重名
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // 直接包含该图片文件的文件夹名
////                MediaStore.Images.Media.DISPLAY_NAME, // 图片文件名
//                MediaStore.Images.Media.DATA, // 图片绝对路径
////                    "count("+MediaStore.Images.Media._ID+")"//统计当前文件夹下共有多少张图片
//        };
//
//        private static final String ORDER_BY = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
//
//        WeakReference<FragmentMultiAlbum> mFragmentMultiAlbumWeakReference;
//
//        public CursorTask(FragmentMultiAlbum fragmentMultiAlbum) {
//            mFragmentMultiAlbumWeakReference = new WeakReference<FragmentMultiAlbum>(fragmentMultiAlbum);
//        }
//
//        @Override
//        protected ArrayList<GalleryData> doInBackground(ContentResolver... params) {
//            if (params != null && params.length > 0) {
//                Cursor cursor = runQuery(params[0]);
//                try {
//                    ArrayList<GalleryData> datas = cursor2Data(cursor);
//                    sortAlbums(datas);
//                    addAllGallery(datas);
//                    return datas;
//                } finally {
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                }
//            }
//            return null;
//        }
//
//        protected void sortAlbums(ArrayList<GalleryData> list) {
//            Collections.sort(list, new Comparator<GalleryData>() {
//                @Override
//                public int compare(GalleryData lhs, GalleryData rhs) {
//                    if (rhs == null || rhs.getBucketName() == null) {
//                        return 1;
//                    }
//                    if (lhs == null || lhs.getBucketName() == null) {
//                        return -1;
//                    }
//                    final String lFileName = lhs.getBucketName();
//                    final String rFileName = rhs.getBucketName();
//                    return lFileName.compareTo(rFileName);
//                }
//            });
//        }
//
//        private void addAllGallery(ArrayList<GalleryData> list) {
//            if (list == null || list.isEmpty()) {
//                return;
//            }
//            GalleryData allData = new GalleryData();
//            for (GalleryData data : list) {
//                if (allData.getData() == null) {
//                    allData.setData(data.getData());
//                }
//                allData.setCount(allData.getCount() + data.getCount());
//            }
//            list.add(0, allData);
//        }
//
//        private ArrayList<GalleryData> cursor2Data(Cursor cursor) {
//            if (cursor == null) {
//                return new ArrayList<GalleryData>();
//            }
//            HashMap<String, GalleryData> map =
//                    new HashMap<String, GalleryData>();
//            while (cursor.moveToNext()) {
//                String bucketId = cursor.getString(1);
//                GalleryData data = map.get(bucketId);
//                if (data == null) {
//                    data = new GalleryData();
//                    data.setCount(1);
//                    data.setBucketId(bucketId);
//                    data.setBucketName(cursor.getString(
//                            cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
//                    data.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
//                    map.put(bucketId, data);
//                } else {
//                    data.setCount(data.getCount() + 1);
//                }
//            }
//            return new ArrayList<GalleryData>(map.values());
//        }
//
//        private Cursor runQuery(ContentResolver contentResolver) {
//            return contentResolver.query(URI, PROJECTIONS, null, null, ORDER_BY);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<GalleryData> datas) {
//            super.onPostExecute(datas);
//            FragmentMultiAlbum fragmentMultiAlbum = mFragmentMultiAlbumWeakReference.get();
//            if (fragmentMultiAlbum == null || fragmentMultiAlbum.isDetached()
//                    || fragmentMultiAlbum.isRemoving() || !fragmentMultiAlbum.isAvailableActivity()) {
//                return;
//            }
//            fragmentMultiAlbum.mAdapterMultiAlbum.setDatas(datas);
//            if (datas == null || datas.isEmpty()) {
//                fragmentMultiAlbum.mNoImageView.setVisibility(View.VISIBLE);
//            } else {
//                fragmentMultiAlbum.mNoImageView.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    public static class GalleryData {
//        String mBucketId;
//        String mBucketName;
//        String mData;
//        int mCount;
//
//        public String getBucketId() {
//            return mBucketId;
//        }
//
//        public void setBucketId(String bucketId) {
//            mBucketId = bucketId;
//        }
//
//        public String getBucketName() {
//            return mBucketName;
//        }
//
//        public void setBucketName(String bucketName) {
//            mBucketName = bucketName;
//        }
//
//        public int getCount() {
//            return mCount;
//        }
//
//        public void setCount(int count) {
//            mCount = count;
//        }
//
//        public String getData() {
//            return mData;
//        }
//
//        public void setData(String data) {
//            mData = data;
//        }
//    }
//
//    public interface OnAlbumPickListener {
//        void onAlbumPicked(GalleryData data);
//    }
//}
