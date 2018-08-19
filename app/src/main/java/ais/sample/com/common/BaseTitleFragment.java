package ais.sample.com.common;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by YaTomat on 09.07.2017.
 */

public abstract class BaseTitleFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        setScreenTitle(getActivity(), provideTitle());
    }

    private void setScreenTitle(Context context, CharSequence title) {
        if (context instanceof TitleSetter) {
            ((TitleSetter) context).setScreenTitle(title);
        } else {
            Log.w(this.getClass().getSimpleName(),
                    "Given context doesn't implement " + TitleSetter.class.getName() + " interface.");
        }
    }

    @Nullable
    protected abstract CharSequence provideTitle();

    public interface TitleSetter {
        void setScreenTitle(CharSequence charSequence);
    }
}
