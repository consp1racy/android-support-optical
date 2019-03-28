/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.graphics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/**
 * An Insets instance holds four integer offsets which describe changes to the four
 * edges of a Rectangle. By convention, positive values move edges towards the
 * centre of the rectangle.
 * <p>
 * Insets are immutable so may be treated as values.
 */
@SuppressWarnings("unused")
public class Insets {

    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public static final Insets NONE = placeholder();

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public final int left;
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public final int top;
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public final int right;
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public final int bottom;

    private Insets(int left, int top, int right, int bottom) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    private static Insets placeholder() {
        throw new UnsupportedOperationException("Did you include Optical library proguard rules?");
    }

    // Factory methods

    /**
     * Return an Insets instance with the appropriate values.
     *
     * @param left   the left inset
     * @param top    the top inset
     * @param right  the right inset
     * @param bottom the bottom inset
     * @return Insets instance with the appropriate values
     */
    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public static Insets of(int left, int top, int right, int bottom) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return an Insets instance with the appropriate values.
     *
     * @param r the rectangle from which to take the values
     * @return an Insets instance with the appropriate values
     */
    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public static Insets of(@Nullable Rect r) {
        throw new UnsupportedOperationException();
    }

    /**
     * Two Insets instances are equal if they belong to the same class and their fields are
     * pairwise equal.
     *
     * @param o the object to compare this instance with.
     * @return true if this object is equal {@code o}
     */
    @Override
    public boolean equals(@Nullable Object o) {
        throw new UnsupportedOperationException();
    }
}
