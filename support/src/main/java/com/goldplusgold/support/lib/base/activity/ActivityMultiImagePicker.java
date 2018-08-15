//package com.goldplusgold.support.lib.base.activity;
//
//import android.Manifest;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import com.goldplusgold.support.lib.R;
//import com.goldplusgold.support.lib.base.fragment.FragmentMultiAlbum;
////import com.goldplusgold.support.lib.base.fragment.FragmentMultiImagePicker;
//
//
///**
// * Created by kevin on 15/8/24.
// */
//public class ActivityMultiImagePicker extends BaseActivity
//        implements FragmentMultiAlbum.OnAlbumPickListener {
//
//    private FragmentMultiAlbum mFragmentMultiAlbum;
//    private FragmentMultiImagePicker mFragmentMultiImagePicker;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.anim_window_slide_bottom_in, R.anim.anim_window_slide_bottom_out);
//    }
//
//    @Override
//    public String getActivityNavTextRight() {
//        return getString(R.string.common_cancel);
//    }
//
//    @Override
//    public String getActivityNavTitle() {
//        return getString(R.string.common_album);
//    }
//
//    @Override
//    public boolean isNeedNavTextRight() {
//        return true;
//    }
//
//    @Override
//    protected int getLayoutContent() {
//        return R.layout.activity_multi_image_picker;
//    }
//
//    @Override
//    public boolean isNeedToolbarCustomView() {
//        return !isMaterialDesign();
//    }
//
//    @Override
//    protected void initBodyControl() {
//        super.initBodyControl();
//        checkPermission(new OnPermissionResultListener() {
//            @Override
//            public void onSucceed(String[] permission) {
//                mFragmentMultiImagePicker = FragmentMultiImagePicker.newInstance("",
//                        getIntent().getIntExtra(ToolConstants._NAME_IMAGE_PICKED_MAX_SIZE, 5),
//                        getIntent().getStringArrayListExtra(ToolConstants._NAME_IMAGE_PICKED));
//                getSupportFragmentManager().beginTransaction().add(R.id.content, mFragmentMultiImagePicker)
//                        .commitAllowingStateLoss();
//            }
//
//            @Override
//            public void onFailed(String[] permission) {
//                finish();
//            }
//
//            @Override
//            public void onNotAskAgain(String[] permission) {
//
//            }
//        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//    }
//
//    @Override
//    public void onNavIconLeftClickAction() {
//        if (mFragmentMultiAlbum != null && mFragmentMultiAlbum.isVisible()) {
//            super.onNavIconLeftClickAction();
//            return;
//        }
//        if (mFragmentMultiAlbum == null) {
//            mFragmentMultiAlbum = FragmentMultiAlbum.newInstance();
//            mFragmentMultiAlbum.setOnAlbumPickListener(this);
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.content, mFragmentMultiAlbum)
//                    .hide(mFragmentMultiImagePicker)
////                    .setCustomAnimations(R.anim.anim_window_in, R.anim.anim_window_out)
//                    .commitAllowingStateLoss();
//        } else {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .hide(mFragmentMultiImagePicker)
////                    .setCustomAnimations(R.anim.anim_window_in, R.anim.anim_window_out)
//                    .show(mFragmentMultiAlbum)
//                    .commitAllowingStateLoss();
//        }
//    }
//
//    @Override
//    public void onNavIconRightClickAction() {
//        super.onNavIconRightClickAction();
//        finishActivity();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (isMaterialDesign()) {
//            getMenuInflater().inflate(R.menu.menu_cancel, menu);
//        }
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_cancel) {
//            finishActivity();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onAlbumPicked(FragmentMultiAlbum.GalleryData data) {
//        if (mFragmentMultiImagePicker != null) {
//            if (data == null) {
//                return;
//            }
//            getSupportFragmentManager().beginTransaction().hide(mFragmentMultiAlbum)
//                    .show(mFragmentMultiImagePicker).commitAllowingStateLoss();
//            if (data.getBucketName() != null) {
//                mFragmentMultiImagePicker.setBucketId(data.getBucketId());
//                setActivityNavTitle(data.getBucketName());
//            } else {
//                mFragmentMultiImagePicker.setBucketId("");
//                setActivityNavTitle(getString(R.string.common_album));
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        try {
//            super.onBackPressed();
//        } catch (Exception e) {
//            finishActivity();
//        }
//    }
//}
