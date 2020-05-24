package com.purutalapps.informer;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;


class InformerQueue {


    private List<Informer> informerQueue;
    private static InformerQueue mInstance;
    private Handler mHandler;
    private static final long waitDuration = 100;

    private InformerQueue() {
        informerQueue = new ArrayList<>();
        mHandler = new Handler();
    }

    static InformerQueue getInstance() {
        if (mInstance == null) {
            synchronized (InformerQueue.class) {
                if (mInstance == null) {
                    mInstance = new InformerQueue();
                }
            }
        }

        return mInstance;
    }

    void push(Informer informer) {
        this.informerQueue.add(informer);
        informTop();
    }

    void remove(Informer informer) {
        informerQueue.remove(informer);
        informTop();
    }

    private void informTop() {
        if (informerQueue.size() == 0) return;

        final Informer informer = informerQueue.get(0);

        // check if activity is visible or not
        // clear all informers if activity is not visible
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!isActivityShowing(informer.getActivity())) {
                    clearAllInformerWithActivity(informer.getActivity());
                    informTop();
                    return;
                }

                if (informer.isInforming()) return;

                informer.show();
            }
        }, waitDuration);
    }

    private void clearAllInformerWithActivity(Activity activity) {
        List<Informer> toBeRemovedInformers = new ArrayList<>();
        for (Informer informer : informerQueue) {
            if (informer.getActivity() == activity) {
                toBeRemovedInformers.add(informer);
            }
        }

        informerQueue.removeAll(toBeRemovedInformers);
    }

    private boolean isActivityShowing(Activity activity) {
        return activity.getWindow().getDecorView().getRootView().isShown();
    }

}
