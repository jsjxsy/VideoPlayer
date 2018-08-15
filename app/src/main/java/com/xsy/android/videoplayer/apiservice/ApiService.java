package com.xsy.android.videoplayer.apiservice;

import com.goldplusgold.networklib.NetworkListener;
import com.goldplusgold.networklib.NetworkRequestsUtil;

/**
 * Created by admin on 2017/8/30.
 */

public class ApiService {
    private static class SingleTonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    public static ApiService getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private ApiService() {

    }

    public void getMovies(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.MOVIE_LIST, null, networkListener);
    }


    public void getDetail(String url, NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(url, null, networkListener);
    }

    public void getTeleplay(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.TELEPLAY_LIST, null, networkListener);
    }

    public void getComic(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.COMIC_LIST, null, networkListener);
    }

    public void getVariety(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.VARIETY_LIST, null, networkListener);
    }

    public void getLastedMovie(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.LASTED_MOVIE_LIST, null, networkListener);
    }

    public void getClassicMovie(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.CLASSIC_MOVIE_LIST, null, networkListener);
    }

    public void getImages(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.IMAGE_LIST, null, networkListener);
    }

    public void checkVersion(NetworkListener networkListener) {
        NetworkRequestsUtil.getInstance().getNetworkRequests(APIConfig.APP_UPDATE, null, networkListener);
    }
}



