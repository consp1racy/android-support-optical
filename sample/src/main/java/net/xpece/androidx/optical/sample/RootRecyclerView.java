package net.xpece.androidx.optical.sample;

import android.content.Context;
import android.graphics.Insets;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import net.xpece.androidx.optical.InsetsCompat;

public class RootRecyclerView extends RecyclerView {
    public RootRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RootRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyle) {
        super(context, attrs, defStyle);
    }

    //@Override
    @NonNull
    @RequiresApi(16)
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    Insets getOpticalInsets() {
        return InsetsCompat.NONE;
    }
}
