

package com.abbaqus.reddit.base;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abbaqus.reddit.R;
import com.abbaqus.reddit.utils.exception.AppException;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
        subscribeToLiveData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mViewDataBinding == null)
        {
            Log.d("BaseFragment","BaseFragment OnCreateView is not getting called");
            return;
        }
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        // clear the stuff
        mViewModel.onCleared();
        super.onDestroy();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }


    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }


    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    private void subscribeToLiveData()
    {
        if(mViewModel == null)
            return;
       mViewModel.getAppExceptionEvent().observe(this, new Observer<AppException>() {
            @Override
            public void onChanged(@Nullable AppException e) {
                showSnackBar(e.getErrorMessage(),false);
            }
        });

    }

    protected void showSnackBar(String message, boolean success) {
        Snackbar snackbar = getSnackBar(message, success);
        snackbar.show();
    }


    private Snackbar getSnackBar(String message, boolean success) {
        Snackbar snackbar = Snackbar.make(getViewDataBinding().getRoot(), message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        int color = getResources().getColor(R.color.colorPrimary);
        if (!success)
            color = getResources().getColor(R.color.colorAccent);
        snackBarView.setBackgroundColor(color);
        return snackbar;
    }

}
