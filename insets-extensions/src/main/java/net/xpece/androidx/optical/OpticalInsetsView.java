package net.xpece.androidx.optical;

import android.graphics.Insets;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface OpticalInsetsView {

    @NonNull
    Insets getOpticalInsets();

    void setOpticalInsets(@NonNull Insets insets);
}
