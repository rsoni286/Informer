package com.purutalapps.informer;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class Informer implements Animation.AnimationListener {

    public enum AnimType {
        FADE,
        SLIDE_UP_DOWN,
        SLIDE_LEFT_RIGHT
    }

    public enum Duration {
        SHORT,
        MEDIUM,
        LONG
    }

    private Activity activity;
    private String message;
    private Animation animIn, animOut;
    private View mView;
    private Duration duration;
    private int backGroundColor, textColor;
    private boolean isInforming;

    private Informer(Activity activity, int colorID, String message) {
        this.message = message;
        this.activity = activity;
        this.duration = Duration.MEDIUM;
        this.animIn = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        this.animOut = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
        this.textColor = ContextCompat.getColor(activity, android.R.color.white);
        this.backGroundColor = ContextCompat.getColor(activity, colorID);
        this.isInforming = false;
    }


    private Informer(Activity activity, String message, Duration duration, AnimType animType, int backGroundColor, int textColor) {
        this.message = message;
        this.duration = duration;
        this.activity = activity;
        this.backGroundColor = backGroundColor;
        this.textColor = textColor;
        setAnimation(animType);
    }

    public static void informDefault(Activity activity, String msg) {
        Informer mInformer = new Informer(activity, R.color.colorDefault, msg);
        InformerQueue.getInstance().push(mInformer);
    }

    public static void informError(Activity activity, String msg) {
        Informer mInformer = new Informer(activity, R.color.colorError, msg);
        InformerQueue.getInstance().push(mInformer);
    }

    public static void informSuccess(Activity activity, String msg) {
        Informer mInformer = new Informer(activity, R.color.colorSuccess, msg);
        InformerQueue.getInstance().push(mInformer);
    }


    void show() {
        isInforming = true;
        createView();

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        activity.getWindow().addContentView(mView, lp);

        mView.startAnimation(animIn);
        animOut.setAnimationListener(this);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.startAnimation(animOut);
            }
        }, getDuration());
    }

    private void createView() {
        mView = activity.getLayoutInflater().inflate(R.layout.layout_inform, null);
        ((TextView) mView).setText(message);
        ((TextView) mView).setTextColor(textColor);
        mView.setBackgroundColor(backGroundColor);
    }

    private long getDuration() {
        switch (this.duration) {
            case MEDIUM:
                return 2000;
            case LONG:
                return 4000;
            default:
                return 1000;
        }
    }

    private void setAnimation(AnimType animType) {
        switch (animType) {
            case FADE:
                this.animIn = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
                this.animOut = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);
                break;
            case SLIDE_UP_DOWN:
                this.animIn = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
                this.animOut = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
                break;
            case SLIDE_LEFT_RIGHT:
                this.animIn = AnimationUtils.loadAnimation(activity, R.anim.slide_right);
                this.animOut = AnimationUtils.loadAnimation(activity, R.anim.slide_left);
                break;
        }
    }

    boolean isInforming() {
        return isInforming;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animOut) {
            ((ViewGroup) mView.getParent()).removeView(mView);
            isInforming = false;
            InformerQueue.getInstance().remove(this);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public static class WithOptions {
        private AnimType animType;
        private Duration duration;
        private Activity activity;
        private int backgroundColor;
        private int textColor;

        public WithOptions(Activity activity) {
            this.animType = AnimType.FADE;
            this.duration = Duration.MEDIUM;
            this.activity = activity;
            this.backgroundColor = ContextCompat.getColor(activity, R.color.colorDefault);
            this.textColor = ContextCompat.getColor(activity, android.R.color.white);
        }

        public WithOptions setAnimation(AnimType animType) {
            this.animType = animType;
            return this;
        }

        public WithOptions setDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public WithOptions setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public WithOptions setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public void inform(String message) {
            Informer informer = new Informer(activity, message, this.duration, this.animType, this.backgroundColor, this.textColor);
            InformerQueue.getInstance().push(informer);
        }
    }
}
