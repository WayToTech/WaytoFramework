package com.wayto.android.function.deviceFuncations.deviceFuncation.addDevice;

import android.app.Activity;
import android.os.Bundle;

import com.wayto.android.function.deviceFuncations.FillBaseActivity;
import com.wayto.android.function.deviceFuncations.FillBaseFragment;
import com.wayto.android.utils.ISkipActivityUtil;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations.deviceFuncation.addDevice
 * @Description:设施采集Activity
 * @date 2017/2/20 15:18
 */

public class AddDeviceActivity extends FillBaseActivity {

    public static void startAddDeviceIntent(Activity activity, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_FLAG, title);
        ISkipActivityUtil.startIntent(activity, AddDeviceActivity.class, bundle);
    }

    @Override
    public FillBaseFragment getFillingFragment(Bundle bundle) {
        FillBaseFragment fragment = AddDeviceFragment.newInstance();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getHeadTitle() {
        return getIntent().getStringExtra(TITLE_FLAG);
    }
}
