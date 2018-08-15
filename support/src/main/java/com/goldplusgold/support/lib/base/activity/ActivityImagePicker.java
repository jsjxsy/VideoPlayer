//package com.goldplusgold.support.lib.base.activity;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.provider.MediaStore;
//import android.provider.MediaStore.Images;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
//import android.widget.Toast;
//
//import com.goldplusgold.support.lib.R;
//import com.goldplusgold.support.lib.base.adapter.AdapterImagePicker;
//import com.goldplusgold.support.lib.utils.ToolConstants;
//import com.goldplusgold.support.lib.widget.AsyncTask;
//import com.goldplusgold.support.lib.widget.CtrlFooterActionBar;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//
//public class ActivityImagePicker extends BaseActivity implements CtrlFooterActionBar.OnFooterBarClickListener, OnItemClickListener {
//
//    private GridView mGridImages;
//    private View mEmptyView;
//    private CtrlFooterActionBar mCtrlActionBar;
//
//    private AdapterImagePicker mAdapterImagePicker;
//    private int mImageMaxSize = 4;
//
//    @Override
//    protected void initView() {
//        super.initView();
//        mImageMaxSize = getIntent().getIntExtra(
//                ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, 4);
//
//        mGridImages = (GridView) findViewById(R.id.id_grid_activity_image_picker);
//        mGridImages.setOnItemClickListener(this);
//        mAdapterImagePicker = new AdapterImagePicker(getApplicationContext());
//        mGridImages.setAdapter(mAdapterImagePicker);
//
//        mEmptyView = findViewById(R.id.id_empty_view);
//
//
//        mCtrlActionBar = (CtrlFooterActionBar) findViewById(R.id.id_footer_action_bar);
//        mCtrlActionBar = (CtrlFooterActionBar)
//                findViewById(R.id.id_footer_action_bar);
//        mCtrlActionBar.setLeftTextAndBackgroud("", -1);
//        mCtrlActionBar.setOnFooterBarClickedListener(this);
//        mCtrlActionBar.setButtonLeftVisibility(View.GONE);
//
//        int size = 0;
//        if (getIntent().hasExtra(ToolConstants._NAME_IMAGE_PICKED)) {
//            String[] imagesPicked = getIntent().getStringArrayExtra(
//                    ToolConstants._NAME_IMAGE_PICKED);
//            if (imagesPicked != null) {
//                for (int i = 0; i < imagesPicked.length; i++) {
//                    if (!TextUtils.isEmpty(imagesPicked[i]) && !"add".equals(imagesPicked[i])) {
//                        mAdapterImagePicker.addHashPicked(imagesPicked[i]);
//                        size++;
//                    }
//                }
//            }
//        }
//        mImageMaxSize = getIntent().getIntExtra(
//                ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, 4);
//        mAdapterImagePicker.setMaxSize(mImageMaxSize);
//
//        String strChoose = getString(R.string.str_image_choose, String.valueOf(size), String.valueOf(mImageMaxSize));
//        mCtrlActionBar.setRightTextAndBackgroud(strChoose, -1);
//
//        new LoadCursorAsyncTask().execute(AsyncTask.TYPE_LOCAL); // load local
//    }
//
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.layout_activity_image_picker;
//    }
//
//    public String getActivityNavTitle() {
//        if (mImageMaxSize == 1) {
//            return getString(R.string.str_title_image_picker);
//        } else {
//            return getString(R.string.str_title_images_picker);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mAdapterImagePicker == null || mAdapterImagePicker.getCursor() == null) {
//            return;
//        }
//        if (!mAdapterImagePicker.getCursor().isClosed()) {
//            mAdapterImagePicker.getCursor().close();
//        }
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        int size = mAdapterImagePicker.onImagePickedChange("", view, position);
//        if (size > mImageMaxSize) {
//            String text = getString(R.string.str_attach_picked_maximums_tips, String.valueOf(mImageMaxSize));
//            if (1 == mImageMaxSize) {
//                text = getString(R.string.str_attach_picked_maximum_tips, String.valueOf(mImageMaxSize));
//            }
//            showToastMessage(text, Toast.LENGTH_SHORT);
//            return;
//        }
//
//        String strChoose = getString(R.string.str_image_choose, String.valueOf(size), String.valueOf(mImageMaxSize));
//        mCtrlActionBar.setRightTextAndBackgroud(strChoose, -1);
//    }
//
//    @Override
//    public void onFooterActionBarClicked(String text) {
//        String[] images = mAdapterImagePicker.getImagePicked();
//        if (images == null) {
//            return;
//        }
//        Intent intent = new Intent();
//        ArrayList<String> pickedImages = new ArrayList<>();
//        Collections.addAll(pickedImages, images);
//        intent.putExtra(ToolConstants._NAME_IMAGE_PICKED, pickedImages);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    private void changeGridView() {
//        if (mAdapterImagePicker != null) {
//            if (mAdapterImagePicker.getCount() < 1) {
//                mGridImages.setVisibility(View.GONE);
//                mEmptyView.setVisibility(View.VISIBLE);
//                mCtrlActionBar.setVisibility(View.GONE);
//            } else {
//                mGridImages.setVisibility(View.VISIBLE);
//                mEmptyView.setVisibility(View.GONE);
//                mCtrlActionBar.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    private class LoadCursorAsyncTask extends AsyncTask<Void, Void, Cursor> {
//
//        private String orderBy = Images.ImageColumns.DATE_TAKEN + " DESC";
//
//        @Override
//        protected Cursor doInBackground(Void... params) {
//            Cursor cursor = getContentResolver().query(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, orderBy);
//            ;
//            return cursor;
//        }
//
//        @Override
//        protected void onPostExecute(Cursor cursor) {
//            if (isFinishing()) {
//                if (cursor != null && !cursor.isClosed()) {
//                    cursor.close();
//                }
//                return;
//            }
//            mAdapterImagePicker.changeCursor(cursor);
//            changeGridView();
//            super.onPostExecute(cursor);
//        }
//    }
//
//}
