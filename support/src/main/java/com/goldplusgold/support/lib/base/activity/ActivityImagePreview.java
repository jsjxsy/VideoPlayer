//package com.goldplusgold.support.lib.base.activity;
//
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.goldplusgold.support.lib.R;
//import com.goldplusgold.support.lib.utils.ToolConstants;
//import com.goldplusgold.support.lib.widget.AsyncTask;
//
//import java.io.File;
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//
///**
// * Created by kevin on 15/8/25.
// */
//public class ActivityImagePreview extends BaseActivity implements View.OnClickListener {
//
//    private Handler mHandler;
//
//    private Cursor mCursor;
//
//    private String mDir;
//    private ArrayList<String> mCheckedPaths;
//    private int mCurrentPos;
//    private int mMax;
//
//    protected ViewPager mPager;
//    protected View mTitleBar;
//    protected View mFooterBar;
//    protected TextView mSendText;
//    protected TextView mSendCount;
//    protected ImageView mCheckView;
//    protected ImagePagerAdapter mImagePagerAdapter;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(getInitLayout());
//        initRuntime();
//        initControl();
//    }
//
//    protected void initRuntime() {
//        mHandler = new Handler();
//        Intent intent = getIntent();
//        mDir = intent.getStringExtra(ToolConstants._NAME_IMAGE_BUCKET_ID);
//        mCurrentPos = intent.getIntExtra(ToolConstants._NAME_INDEX, 0);
//        mCheckedPaths = intent.getStringArrayListExtra(ToolConstants._NAME_IMAGE_PICKED);
//        mMax = intent.getIntExtra(ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, 5);
//
//        if (mCheckedPaths == null) {
//            mCheckedPaths = new ArrayList<String>();
//        }
//
//        mImagePagerAdapter = new ImagePagerAdapter();
//    }
//
//    protected int getInitLayout() {
//        return R.layout.activity_image_preview_base;
//    }
//
//    protected void initControl() {
//        mTitleBar = findViewById(R.id.id_image_title_bar);
//        mFooterBar = findViewById(R.id.id_image_footer_bar);
//        mCheckView = (ImageView) findViewById(R.id.id_check_ctrl_header_action_bar);
//        mSendText = (TextView) findViewById(R.id.send);
//        mSendCount = (TextView) findViewById(R.id.send_count);
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setAdapter(mImagePagerAdapter);
//
//        mSendText.setOnClickListener(this);
//        mSendCount.setText(Integer.toString(mCheckedPaths.size()));
//
//        mCheckView.setOnClickListener(this);
//        enableSend(!mCheckedPaths.isEmpty());
//        findViewById(R.id.id_back_ctrl_header_action_bar).setOnClickListener(this);
//
//        new CursorTask(this, mDir).execute(AsyncTask.TYPE_LOCAL, getContentResolver());
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (mPager != null) {
//            mPager.destroyDrawingCache();
//            mPager = null;
//        }
//        if (mCursor != null && !mCursor.isClosed()) {
//            mCursor.close();
//            mCursor = null;
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putStringArrayListExtra(ToolConstants._NAME_IMAGE_PICKED, mCheckedPaths);
//        setResult(RESULT_CANCELED, intent);
//        super.onBackPressed();
//    }
//
//    private void onSend() {
//        Intent intent = new Intent();
//        intent.putStringArrayListExtra(ToolConstants._NAME_IMAGE_PICKED, mCheckedPaths);
//        setResult(RESULT_OK, intent);
//        finishActivity();
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.id_back_ctrl_header_action_bar) {
//            onBackPressed();
//        } else if (id == R.id.id_check_ctrl_header_action_bar) {
//            onCheckChanged();
//        } else if (id == R.id.send) {
//            onSend();
//        }
//    }
//
//    private String getCurrent() {
//        if (mPager == null || mCursor == null || mCursor.isClosed()) {
//            return null;
//        }
//        String path = null;
//        if (mCursor.moveToPosition(mPager.getCurrentItem())) {
//            path = mCursor.getString(1);
//        }
//        return path;
//    }
//
//    private void onCheckChanged() {
//        String path = getCurrent();
//        if (TextUtils.isEmpty(path)) {
//            return;
//        }
//        if (mCheckedPaths.contains(path)) {
//            mCheckView.setImageResource(R.drawable.ic_photo_big_unchecked);
//            mCheckedPaths.remove(path);
//        } else {
//            if (mCheckedPaths.size() >= mMax) {
//                showToastMessage(getString(R.string.common_selectfiveimages).replace("5", String.valueOf(mMax)), Toast.LENGTH_SHORT);
//                return;
//            }
//            mCheckView.setImageResource(R.drawable.ic_photo_big_checked);
//            mCheckedPaths.add(path);
//        }
//        mSendCount.setText(Integer.toString(mCheckedPaths.size()));
//        enableSend(!mCheckedPaths.isEmpty());
//    }
//
//    protected void onImageClicked(LoadableGalleryImageView imageView, int position, String uri) {
//        if (mTitleBar != null) {
//            if (mTitleBar.getVisibility() != View.VISIBLE) {
//                mTitleBar.setVisibility(View.VISIBLE);
//            } else {
//                mTitleBar.setVisibility(View.GONE);
//            }
//        }
//        if (mFooterBar != null) {
//            if (mFooterBar.getVisibility() != View.VISIBLE) {
//                mFooterBar.setVisibility(View.VISIBLE);
//            } else {
//                mFooterBar.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    protected void onImageLongClicked(LoadableGalleryImageView imageView, int position, String uri) {
//    }
//
//    protected void updatePageAndSpace() {
//        String path = getCurrent();
//        if (TextUtils.isEmpty(path)) {
//            return;
//        }
//        if (mCheckedPaths.contains(path)) {
//            mCheckView.setImageResource(R.drawable.ic_photo_big_checked);
//        } else {
//            mCheckView.setImageResource(R.drawable.ic_photo_big_unchecked);
//        }
//    }
//
//    private void enableSend(boolean enabled) {
//        if (!isFinishing()) {
//            if (mSendText != null) {
//                if (enabled) {
//                    mSendText.setEnabled(true);
//                    mSendText.setTextColor(getResources().getColor(R.color.orange));
//                    mSendCount.setVisibility(View.VISIBLE);
//                } else {
//                    mSendText.setEnabled(false);
//                    mSendText.setTextColor(getResources().getColor(R.color.color_value_9));
//                    mSendCount.setVisibility(View.GONE);
//                }
//            }
//        }
//    }
//
//    private Runnable mUpdateTask = new Runnable() {
//        @Override
//        public void run() {
//            updatePageAndSpace();
//        }
//    };
//
//    protected class ImagePagerAdapter extends PagerAdapter {
//        private LayoutInflater inflater;
//        private String requestURI = "";
//
//        ImagePagerAdapter() {
//            inflater = getLayoutInflater();
//        }
//
//        @Override
//        public void destroyItem(View container, int position, Object object) {
//            ((ViewPager) container).removeView((View) object);
//        }
//
//
//        @Override
//        public void finishUpdate(View container) {
//        }
//
//        @Override
//        public int getCount() {
//            if (mCursor == null || mCursor.isClosed()) {
//                return 0;
//            }
//            return mCursor.getCount();
//        }
//
//        @Override
//        public Object instantiateItem(View view, final int position) {
//            final View imageLayout = inflater.inflate(R.layout.layout_item_pager_image, null);
//            LoadableGalleryImageView imageView = (LoadableGalleryImageView) imageLayout.findViewById(R.id.image);
//            imageView.setTag(position);
//            String path = null;
//            if (mCursor.moveToPosition(position)) {
//                path = mCursor.getString(1);
//            }
//            imageView.load(path);
//            if (TextUtils.isEmpty(path)) {
//                ((ViewPager) view).addView(imageLayout, 0);
//                return imageLayout;
//            }
//            File file = new File(path);
//            if (file != null && file.exists() /* && Long.parseLong(cacheFile.getSyncFile().getLocal_date()) == file.lastModified()*/) {
//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                opts.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
//                imageView.setMaxRequiredWidth(opts.outWidth);
//                imageView.setMaxRequiredHeight(opts.outHeight);
//            }
//            imageView.load(path);
//            final LoadableGalleryImageView iv = imageView;
//            final String uri = requestURI;
//            imageView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    onImageClicked(iv, position, uri);
//                }
//            });
//            imageView.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View v) {
//                    onImageLongClicked(iv, position, uri);
//                    return true;
//                }
//            });
//            ((ViewPager) view).addView(imageLayout, 0);
//            return imageLayout;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view.equals(object);
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//
//        @Override
//        public void finishUpdate(ViewGroup container) {
//            super.finishUpdate(container);
//            mHandler.post(mUpdateTask);
//        }
//    }
//
//    static class CursorTask extends AsyncTask<ContentResolver, String, Cursor> {
//
//        private static final String ORDER_BY = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
//
//        WeakReference<ActivityImagePreview> mActivityImagePreviewWeakReference;
//
//        String mBucketId;
//
//        public CursorTask(ActivityImagePreview fragmentMultiAlbum, String bucketId) {
//            mActivityImagePreviewWeakReference = new WeakReference<ActivityImagePreview>(fragmentMultiAlbum);
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
//            final String selection = MediaStore.Images.ImageColumns.BUCKET_ID + " LIKE ?";
//            final String[] selectionArgs = new String[]{"%" + mBucketId + "%"};
//            return contentResolver.query(uri, projections, selection, selectionArgs, ORDER_BY);
//        }
//
//        @Override
//        protected void onPostExecute(Cursor cursor) {
//            super.onPostExecute(cursor);
//            ActivityImagePreview activityImagePreview = mActivityImagePreviewWeakReference.get();
//            if (activityImagePreview == null || activityImagePreview.isFinishing()) {
//                return;
//            }
//            activityImagePreview.mCursor = cursor;
//            activityImagePreview.mImagePagerAdapter.notifyDataSetChanged();
//            activityImagePreview.mPager.setCurrentItem(activityImagePreview.mCurrentPos);
//        }
//
//    }
//}
