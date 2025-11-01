package io.github.xesam.android.debug.views;

import android.view.View;

public interface DebugApiView {
    String getDebugName();

    interface DebugListener {
        void onEvent(String event, DebugApiView debugApiView, View view);
    }
}
