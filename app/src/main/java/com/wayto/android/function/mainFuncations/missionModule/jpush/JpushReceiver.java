package com.wayto.android.function.mainFuncations.missionModule.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.wayto.android.common.Constant;
import com.wayto.android.function.mainFuncations.missionModule.jpush.data.MessageEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.missionModule.jpush
 * @Description:
 * @date 2017/2/9 17:53
 */

public class JpushReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        /*接收到自定义消息*/
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            MessageEntity entity = new Gson().fromJson(message, MessageEntity.class);
            if (entity == null) {
                return;
            }
            /*处理任务类型*/
            switch (entity.getCode()) {
                case Constant.MINSSION_NEW_STATUS_VALUE:/*新任务*/
                    break;
                case Constant.MISSION_REVOKE_STATUS_VALUE:/*撤销*/
                    break;
                case Constant.MISSION_AUDITED_STATUS_VALUE:/*已审核*/
                    break;
                case Constant.MISSION_ABORED_STATUS_VALUE:/*已中止*/
                    break;
                case Constant.MISSION_REDO_STATUS_VALUE:/*重做*/
                    break;
            }
        }
    }

    /**
     * 打印所有的 intent extra 数据
     *
     * @param bundle
     */
    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
