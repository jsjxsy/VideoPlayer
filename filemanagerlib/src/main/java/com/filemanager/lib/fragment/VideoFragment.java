package com.filemanager.lib.fragment;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.filemanager.lib.R;
import com.filemanager.lib.adapter.ExpandableItemAdapter;
import com.filemanager.lib.bean.FileInfo;
import com.filemanager.lib.bean.SubItem;
import com.filemanager.lib.utils.FileUtil;
import com.goldplusgold.support.lib.base.fragment.BaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/9/14.
 */

public class VideoFragment extends BaseFragment {
    private List<FileInfo> fileInfos = new ArrayList<>();
    RecyclerView mRecyclerView;
    ExpandableItemAdapter mExpandableItemAdapter;
    private ArrayList<MultiItemEntity> mEntityArrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    String[] videoFormat = new String[]{"wmv", "rmvb", "avi", "mp4"};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView() {
        fileInfos.clear();
        mEntityArrayList.clear();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rlv_video);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据加载中");
        progressDialog.setCancelable(false);
        progressDialog.show();  //将进度条显示出来
        ReadVideoFile();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExpandableItemAdapter = new ExpandableItemAdapter(ExpandableItemAdapter.AUDIO_VIDEO_TYPE, mEntityArrayList, false);
        mRecyclerView.setAdapter(mExpandableItemAdapter);
    }


    private void ReadVideoFile() {
        List<File> m = new ArrayList<>();
        m.add(new File(Environment.getExternalStorageDirectory() + "/"));
        Observable.from(m)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return listFiles(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<File>() {
                            @Override
                            public void onCompleted() {
                                progressDialog.dismiss();
                                Log.e("onCompleted", "onCompleted()");
                                if (fileInfos.size() > 0) {
                                    SubItem videoItem = new SubItem("视频文件");
                                    for (int j = 0; j < fileInfos.size(); j++) {
                                        if (FileUtil.checkSuffix(fileInfos.get(j).getFilePath(), videoFormat)) {
                                            videoItem.addSubItem(fileInfos.get(j));
                                        }
                                    }

                                    mEntityArrayList.add(videoItem);
                                    mExpandableItemAdapter.setNewData(mEntityArrayList);
                                    mExpandableItemAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getActivity(), "sorry,没有读取到文件!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onNext(File file) {
                                Log.e("onNext", "onNext()");
                                FileInfo fileInfo = FileUtil.getFileInfoFromFile(file);
                                Log.e("文件路径", "文件路径：：：" + fileInfo.getFilePath());
                                fileInfos.add(fileInfo);

                            }
                        }
                );
    }

    public Observable<File> listFiles(final File f) {
        if (f.isDirectory()) {
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(file);
                }
            });
        } else {
            return Observable.just(f).filter(new Func1<File, Boolean>() {
                @Override
                public Boolean call(File file) {
                    return f.exists() && f.canRead() && FileUtil.checkSuffix(f.getAbsolutePath(), videoFormat);
                }
            });
        }
    }
}
