package io.github.xesam.android.debug.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DebugTextView extends TextView implements DebugApiView {
    private DebugViewHelper mDebugViewHelper;

    public DebugTextView(Context context) {
        super(context);
        init();
    }

    public DebugTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DebugTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DebugTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mDebugViewHelper = new DebugViewHelper(this);
        this.setOnClickListener(null);
    }

    @Override
    public String getDebugName() {
        return this.mDebugViewHelper.getDebugName();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(this.mDebugViewHelper.createDecoratedOnClickListener(l));
    }
}
