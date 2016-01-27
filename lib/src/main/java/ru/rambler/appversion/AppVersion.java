package ru.rambler.appversion;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class AppVersion {

    public static void init(Application application, String version, int style) {
        application.registerActivityLifecycleCallbacks(new ActivityLificycleCallback(version, style));
    }

    public static void init(Application application, String version) {
        init(application, version, R.style.AppVersion_TextView);
    }

    private static class ActivityLificycleCallback implements Application.ActivityLifecycleCallbacks {
        private final String version;
        private final int style;
        private Map<Activity, View> versionViewMap = new HashMap<>();

        public ActivityLificycleCallback(String version, int style) {
            this.version = version;
            this.style = style;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            show(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            hide(activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

        private void show(Activity activity) {
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            View view = createVersionView(activity);
            windowManager.addView(view, params);
            versionViewMap.put(activity, view);
        }

        private void hide(Activity activity) {
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            View view = versionViewMap.get(activity);
            if (view != null) {
                windowManager.removeView(view);
                versionViewMap.remove(activity);
            }
        }

        private View createVersionView(Context context) {
            TextView versionView = new TextView(new ContextThemeWrapper(context, style), null, 0);
            versionView.setText(version);
            return versionView;
        }
    }
}
