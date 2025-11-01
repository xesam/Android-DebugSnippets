package io.github.xesam.android.debug;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import io.github.xesam.android.debug.views.DebugApiView;
import io.github.xesam.android.debug.views.DebugViews;

public class App extends Application {
    private final DebugApiView.DebugListener mDebugListener = (event, debugView, view) -> {
        if ("click".equals(event)) {
            Toast.makeText(getApplicationContext(), "click:" + debugView.getDebugName(), Toast.LENGTH_SHORT).show();
        }
        if (debugView.getDebugName().equals("button2")) {
            view.getContext().startActivity(new Intent(App.this, SecondActivity.class));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        DebugViews.setDebug(true);
        DebugViews.setDebugListener(mDebugListener);// DebugViews 内部使用 WeakReference 来引用 DebugListener，因此开发着需要确保 DebugListener 不被回收
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 在应用终止时清除监听器，避免内存泄漏
        DebugViews.destroy();
    }
}
