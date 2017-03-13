package com.wayto.android.function.deviceFuncations.deviceFuncation.addDevice;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wayto.android.BuildConfig;
import com.wayto.android.R;
import com.wayto.android.base.DataApplication;
import com.wayto.android.common.Constant;
import com.wayto.android.function.deviceFuncations.FillBaseFragment;
import com.wayto.android.function.deviceFuncations.deviceFuncation.addDevice.data.DeviceEntity;
import com.wayto.android.function.mapFuncation.MapSelectePointActivity;
import com.wayto.android.function.pollingFuncation.PollingContract;
import com.wayto.android.function.pollingFuncation.PollingManage;
import com.wayto.android.db.WorkRecordTable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations.deviceFuncation.addDevice
 * @Description:设施采集Fragment
 * @date 2017/2/20 15:19
 */

public class AddDeviceFragment extends FillBaseFragment<DeviceEntity> implements PollingContract.InsertWorkRecordView {

    @BindView(R.id.AddDeviceFragment_location_addr_textVew)
    TextView addressTextView;

    private static AddDeviceFragment fragment;

    public static AddDeviceFragment newInstance() {
        if (fragment == null) {
            fragment = new AddDeviceFragment();
        }
        return fragment;
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.fragmet_add_device, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void saveDraftAction() {
        PollingManage.getInstance().saveWorkRecord(getContext(), getWorkTable(), this);
    }

    @Override
    protected void submitAction() {
        PollingManage.getInstance().addWorkRecord(getContext(), getWorkTable());
    }

    @Override
    protected WorkRecordTable getWorkTable() {
        WorkRecordTable table = new WorkRecordTable();
        table.setId(DEFULT_WORKRECORDTABLE_ID);
        table.setWorkType(Constant.WORKRECORD_TYPE.FACILITY_COLLECTION.getValue());
        table.setUploadUrl(BuildConfig.DOMAI + "v2/danger");
        table.setAttributes(new Gson().toJson(getEntity()));
        table.setImagesUrl(getImageAccessoryString());
        table.setTime(System.currentTimeMillis());
        return table;
    }

    @Override
    protected DeviceEntity getEntity() {
        if (TextUtils.isEmpty(addressTextView.getText().toString())) {
            showToast("设施地址不能为空");
            return null;
        }

        if (TextUtils.isEmpty(getImageAccessoryString())) {
            showToast("图片不能为空");
            return null;
        }

        DeviceEntity entity = new DeviceEntity();
        entity.setAddr(addressTextView.getText().toString());
        entity.setImgs(PollingManage.DEFULT_IMAGE_FORMAT);/*特殊处理*/
        entity.setLat(DataApplication.getInstance().getMonitorService().getLocation().getLatitude());
        entity.setLng(DataApplication.getInstance().getMonitorService().getLocation().getLongitude());
        entity.setSource("城管");
        entity.setName("hezhi");
        entity.setCode("20170309");
        entity.setDangerType("案件");
        entity.setMissionLevel(1);

        return entity;
    }

    @OnClick({R.id.AddDeviceFragment_location_ibtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.AddDeviceFragment_location_ibtn:
                MapSelectePointActivity.startInternt(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MapSelectePointActivity.RESULT_CODE_VALUE) {
            addressTextView.setText(data.getStringExtra(MapSelectePointActivity.RESULT_FLAG_KEY));
        }
    }

    @Override
    public void insertWordRecordSuccess(WorkRecordTable table) {
        /*保存成功，获取数据库ID*/
        DEFULT_WORKRECORDTABLE_ID = table.getId();
    }

    @Override
    public void insertWorkRecordFailure() {

    }
}
