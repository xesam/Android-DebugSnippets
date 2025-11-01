package io.github.xesam.android.debug.views;

import android.view.View;

public class DebugViewHelper implements DebugApiView {
    private final View mTarget;

    public DebugViewHelper(View target) {
        mTarget = target;
    }

    @Override
    public String getDebugName() {
        Object tag = mTarget.getTag();
        if (tag != null) {
            return tag.toString();
        }
        return mTarget.getClass().getSimpleName();
    }

    public View.OnClickListener createDecoratedOnClickListener(View.OnClickListener l) {
        if (!DebugViews.isDebug()) {
            return l;
        }
        return v -> {
            DebugViews.handleEvent("click", (DebugApiView) mTarget, mTarget);
            if (l != null) {
                l.onClick(v);
            }
        };
    }

}
