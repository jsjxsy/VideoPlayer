package com.goldplusgold.support.lib.widget;

import java.lang.ref.WeakReference;

/**
 * Created by kevin on 16/1/4.
 */
public abstract class WeakAsyncTask<Container, Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private WeakReference<Container> mContainerRef;

    public WeakAsyncTask(Container container) {
        mContainerRef = new WeakReference<>(container);
    }

    @Override
    protected final Result doInBackground(Params... params) {
        Container container = mContainerRef.get();
        if (container == null) {
            return null;
        }
        return doInBackground(container, params);
    }

    @Override
    protected final void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        Container container = mContainerRef.get();
        if (container == null) {
            return;
        }
        onProgressUpdate(container, values);
    }

    @Override
    protected final void onPostExecute(Result result) {
        super.onPostExecute(result);
        Container container = mContainerRef.get();
        if (container == null) {
            return;
        }
        onPostExecute(container, result);
    }

    @Override
    protected final void onPreExecute() {
        super.onPreExecute();
        Container container = mContainerRef.get();
        if (container == null) {
            return;
        }
        onPreExecute(container);
    }

    protected abstract Result doInBackground(Container container, Params... params);

    protected void onProgressUpdate(Container container, Progress... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(Container container, Result result) {
        super.onPostExecute(result);
    }

    protected void onPreExecute(Container container) {
        super.onPreExecute();
    }
}
