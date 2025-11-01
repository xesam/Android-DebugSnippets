package io.github.xesam.android.debug.views;

import android.view.View;

import java.lang.ref.WeakReference;

public final class DebugViews {

    private static boolean sDebug = false;

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    public static boolean isDebug() {
        return sDebug;
    }

    // 使用弱引用避免内存泄漏
    private static WeakReference<DebugApiView.DebugListener> mDebugListenerRef;

    public static void setDebugListener(DebugApiView.DebugListener listener) {
        if (listener != null) {
            mDebugListenerRef = new WeakReference<>(listener);
        } else {
            mDebugListenerRef = null;
        }
    }

    // 添加清除监听器的方法
    public static void destroy() {
        mDebugListenerRef = null;
    }

    public static void handleEvent(String event, DebugApiView debugApiView, View view) {
        DebugApiView.DebugListener listener = getDebugListener();
        if (listener != null) {
            listener.onEvent(event, debugApiView, view);
        }
    }

    // 获取监听器的辅助方法
    private static DebugApiView.DebugListener getDebugListener() {
        return mDebugListenerRef != null ? mDebugListenerRef.get() : null;
    }
}
