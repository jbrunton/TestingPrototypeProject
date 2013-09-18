package com.zipcar.testingprototype.shared;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class BaseListFragment extends ListFragment {
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).inject(this);
    }
}