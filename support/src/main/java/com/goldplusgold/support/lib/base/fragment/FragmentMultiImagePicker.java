//package com.goldplusgold.support.lib.base.fragment;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
////import com.goldplusgold.support.lib.base.activity.ActivityImagePreview;
//import com.goldplusgold.support.lib.base.adapter.AdapterMultiImagePicker;
//import com.goldplusgold.support.lib.widget.AsyncTask;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static com.umeng.analytics.pro.x.R;
//
///**
// * Created by kevin on 15/8/24.
// */
//public class FragmentMultiImagePicker extends BaseFragment
//        implements View.OnClickListener, AdapterMultiImagePicker.OnImageCheckChangeListener,
//        AdapterMultiImagePicker.OnItemClickListener {
//
//    static final int _REQUEST_IMAGE_BROWSE = 1;
//    static final int _REQUEST_IMAGE_CAPTURE = 2;
//
//    private String mBucketId;
//    private int mMax;
//
//    private RecyclerView mRecyclerView;
//    private AdapterMultiImagePicker mAdapterMultiImagePicker;
//    private View mNoItemView;
//
//    private TextView mSendText;
//    private TextView mSendCountText;
//
//    private File mCurrentPhotoFile;
//    private boolean isCamera = false;
//
//    public static FragmentMultiImagePicker newInstance(String bucketId, int max, ArrayList<String> checkedPaths) {
//        FragmentMultiImagePicker fragment = new FragmentMultiImagePicker();
//        Bundle args = new Bundle();
//        args.putString(ToolConstants._NAME_IMAGE_BUCKET_ID, bucketId);
//        args.putInt(ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, max);
//        args.putStringArrayList(ToolConstants._NAME_IMAGE_PICKED, checkedPaths);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public FragmentMultiImagePicker() {
//    }
//
//    public void setBucketId(String bucketId) {
//        mBucketId = bucketId;
//        if (mBucketId == null) {
//            mBucketId = "";
//        }
//        asyncData();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ArrayList<String> checkedArrayList = new ArrayList<>();
//        mAdapterMultiImagePicker = new AdapterMultiImagePicker(getActivity());
//        Bundle args = getArguments();
//        if (args != null) {
//            mBucketId = args.getString(ToolConstants._NAME_IMAGE_BUCKET_ID);
//            mMax = args.getInt(ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, 5);
//            checkedArrayList = args.getStringArrayList(ToolConstants._NAME_IMAGE_PICKED);
//        }
//        if (TextUtils.isEmpty(mBucketId)) {
//            mBucketId = "";
//        }
//        mAdapterMultiImagePicker.setMax(mMax);
//        if (checkedArrayList != null && !checkedArrayList.isEmpty()) {
//            mAdapterMultiImagePicker.setCheckedPath(checkedArrayList);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_multi_image_picker, container, false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
//        mNoItemView = view.findViewById(R.id.no_image);
//        mSendText = (TextView) view.findViewById(R.id.send);
//        mSendCountText = (TextView) view.findViewById(R.id.send_count);
//        setUpRecyclerView();
//        mSendText.setOnClickListener(this);
//        onImageCheckChanged(false, null);
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
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(mAdapterMultiImagePicker);
//        mAdapterMultiImagePicker.setOnItemClickListener(this);
//        mAdapterMultiImagePicker.setOnImageCheckChangeListener(this);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            String cameraFilePath = savedInstanceState.getString("cameraFile");
//            if (!TextUtils.isEmpty(cameraFilePath)) {
//                mCurrentPhotoFile = new File(cameraFilePath);
//            }
//        }
//        asyncData();
//    }
//
//    private void asyncData() {
//        if (isActivityAvaiable()) {
//            new CursorTask(this, mBucketId).execute(AsyncTask.TYPE_LOCAL, getActivity().getContentResolver());
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mCurrentPhotoFile != null) {
//            outState.putString("cameraFile", mCurrentPhotoFile.getAbsolutePath());
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        mAdapterMultiImagePicker.destroy();
//        super.onDestroy();
//    }
//
//    private void enableSend(boolean enabled) {
//        if (isAvailableActivity()) {
//            if (enabled) {
//                mSendCountText.setVisibility(View.VISIBLE);
//                mSendCountText.setText(String.valueOf(mAdapterMultiImagePicker.getCheckedPath().size()));
//                mSendText.setEnabled(true);
//                mSendText.setTextColor(getResources().getColor(R.color.orange));
//            } else {
//                mSendCountText.setVisibility(View.GONE);
//                mSendText.setEnabled(false);
//                mSendText.setTextColor(getResources().getColor(R.color.color_value_9));
//            }
//        }
//    }
//
//    @Override
//    public void onImageCheckChanged(boolean checked, String path) {
//        List<String> paths = mAdapterMultiImagePicker.getCheckedPath();
//        if (paths == null || paths.isEmpty()) {
//            enableSend(false);
//        } else {
//            enableSend(true);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == _REQUEST_IMAGE_BROWSE) {
//            if (data == null) {
//                return;
//            }
//            ArrayList<String> list = data.getStringArrayListExtra(ToolConstants._NAME_IMAGE_PICKED);
//            mAdapterMultiImagePicker.setCheckedPath(list);
//            mAdapterMultiImagePicker.notifyDataSetChanged();
//            enableSend(list != null && !list.isEmpty());
//            if (resultCode == Activity.RESULT_OK) {
//                mSendText.performClick();
//            }
//        } else if (requestCode == _REQUEST_IMAGE_CAPTURE) {
//            if (resultCode == Activity.RESULT_OK) {
//                ArrayList<String> list = new ArrayList<String>();
//                list.add(mCurrentPhotoFile.getAbsolutePath());
//                mAdapterMultiImagePicker.setCheckedPath(list);
//                isCamera = true;
//                mSendText.performClick();
//            }
//        }
//    }
//
//    @Override
//    public void onItemClicked(int position, String path) {
//        if (mAdapterMultiImagePicker.getItemViewType(position) == AdapterMultiImagePicker.VIEW_TYPE_CAMERA) {
//            dispatchTakePictureIntent();
//        } else if (mAdapterMultiImagePicker.getItemViewType(position) == AdapterMultiImagePicker.VIEW_TYPE_IMAGE) {
//            Intent intent = new Intent();
//            intent.setClass(getActivity(), ActivityImagePreview.class);
//            intent.putExtra(ToolConstants._NAME_IMAGE_BUCKET_ID, mBucketId);
//            intent.putExtra(ToolConstants._NAME_INDEX, position - 1);
//            intent.putExtra(ToolConstants._NAME_IMAGE_PICKED, mAdapterMultiImagePicker.getCheckedPath());
//            intent.putExtra(ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, mMax);
//            startActivityForResult(intent, _REQUEST_IMAGE_BROWSE);
//        }
//    }
//
//    private void dispatchTakePictureIntent() {
//        checkPermission(new OnPermissionResultListener() {
//            @Override
//            public void onSucceed(String[] permissions) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // Ensure that there's a camera activity to handle the intent
//                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    // Create the File where the photo should go
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                        ex.printStackTrace();
//                    }
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(photoFile));
//                        startActivityForResult(takePictureIntent, _REQUEST_IMAGE_CAPTURE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String[] permission) {
//
//            }
//
//            @Override
//            public void onNotAskAgain(String[] permission) {
//
//            }
//        }, Manifest.permission.CAMERA);
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM);
//        if (!storageDir.exists()) {
//            storageDir.mkdirs();
//        }
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoFile = image;
//        return image;
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.send) {
//            Intent intent = new Intent();
//            intent.putStringArrayListExtra(ToolConstants._NAME_IMAGE_PICKED,
//                    mAdapterMultiImagePicker.getCheckedPath());
//            intent.putExtra(ToolConstants._NAME_IMAGE_IS_CAMERA, isCamera);
//            getActivity().setResult(Activity.RESULT_OK, intent);
//            finishActivity();
//        }
//    }
//
//    static class CursorTask extends AsyncTask<ContentResolver, String, Cursor> {
//
//        private static final String ORDER_BY = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
//
//        WeakReference<FragmentMultiImagePicker> mFragmentMultiAlbumWeakReference;
//
//        String mBucketId;
//
//        public CursorTask(FragmentMultiImagePicker fragmentMultiAlbum, String bucketId) {
//            mFragmentMultiAlbumWeakReference = new WeakReference<FragmentMultiImagePicker>(fragmentMultiAlbum);
//            mBucketId = bucketId;
//        }
//
//        @Override
//        protected Cursor doInBackground(ContentResolver... params) {
//            if (params != null && params.length > 0) {
//                Cursor cursor = runQuery(params[0]);
//                return cursor;
//            }
//            return null;
//        }
//
//        private Cursor runQuery(ContentResolver contentResolver) {
//            final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//            final String[] projections = new String[]{
//                    MediaStore.Images.ImageColumns._ID,
//                    MediaStore.Images.ImageColumns.DATA
//            };
////            final String selection = MediaStore.Images.ImageColumns.DATA + " LIKE ?";
//            final String selection = MediaStore.Images.ImageColumns.BUCKET_ID + " LIKE ?";
//            final String[] selectionArgs = new String[]{"%" + mBucketId + "%"};
////            final String[] selectionArgs = new String[]{"%" + mDir + "%"};
//            return contentResolver.query(uri, projections, selection, selectionArgs, ORDER_BY);
//        }
//
//        @Override
//        protected void onPostExecute(Cursor cursor) {
//            super.onPostExecute(cursor);
//            FragmentMultiImagePicker fragmentMultiAlbum = null;
//            if (mFragmentMultiAlbumWeakReference != null) {
//                fragmentMultiAlbum = mFragmentMultiAlbumWeakReference.get();
//            }
//            if (fragmentMultiAlbum == null || fragmentMultiAlbum.isDetached()
//                    || fragmentMultiAlbum.isRemoving() || !fragmentMultiAlbum.isAvailableActivity()) {
//                if (cursor != null && !cursor.isClosed()) {
//                    cursor.close();
//                }
//                return;
//            }
//
//            if (fragmentMultiAlbum.mAdapterMultiImagePicker != null) {
//                fragmentMultiAlbum.mAdapterMultiImagePicker.setCursor(cursor);
//            }
//            if (fragmentMultiAlbum.mNoItemView != null) {
//                if (cursor != null && cursor.getCount() > 0) {
//                    fragmentMultiAlbum.mNoItemView.setVisibility(View.GONE);
//                } else {
//                    fragmentMultiAlbum.mNoItemView.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    }
//}
