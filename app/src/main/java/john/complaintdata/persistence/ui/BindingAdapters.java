package john.complaintdata.persistence.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by John on 5/27/2017.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}