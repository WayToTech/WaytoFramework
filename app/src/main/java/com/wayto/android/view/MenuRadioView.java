package com.wayto.android.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.wayto.android.R;
import com.wayto.android.base.ArrayListAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.hydrant.widget
 * @Description:单选菜单样式Layout
 * @date 2016/10/18 9:06
 */

public class MenuRadioView extends LinearLayout {

    ListView menuListListView;

    private MenuSeletorAdapter adapter;

    private int selectePosition = -1;
    private String selectContent;

    public MenuRadioView(Context context) {
        super(context);
        init();
    }

    public MenuRadioView(Context context, AttributeSet attri) {
        super(context, attri);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_layout, null);
        menuListListView = ButterKnife.findById(view, R.id.menu_list_listView);

        adapter = new MenuSeletorAdapter((Activity) getContext());
        menuListListView.setAdapter(adapter);

        addView(view);
    }

    /**
     * 设置资源
     *
     * @param list
     */
    public void setDataSource(List<String> list) {
        adapter.appendToList(list);
    }

    /**
     * 设置资源
     *
     * @param data
     */
    public void setDataSource(String[] data) {
        adapter.appendToList(data);
    }

    /**
     * 返回选择的item Content
     *
     * @return
     */
    public String getSelectContent() {
        return selectContent;
    }

    /**
     * 返回选择的item position
     *
     * @return
     */
    public int getSelectePosition() {
        return selectePosition;
    }

    /**
     * 设置已选择的item
     *
     * @param content
     */
    public void setSelectContent(String content) {
        if (adapter.getList()!=null&&adapter.getList().size()>0){
            for (int i=0;i<adapter.getList().size();i++){
                if (adapter.getList().get(i).equals(content)){
                    selectePosition = i;
                    break;
                }
            }
        }
    }

    /**
     * 适配器
     */
    private class MenuSeletorAdapter extends ArrayListAdapter<String> {

        public MenuSeletorAdapter(Activity context) {
            super(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, null);
            TextView nameTv = ButterKnife.findById(convertView, R.id.menu_item_name_tv);
            CheckBox checkIv = ButterKnife.findById(convertView, R.id.menu_item_checkBox);

            nameTv.setText(mList.get(position));

            if (selectePosition == position) {
                checkIv.setChecked(true);
            } else {
                checkIv.setChecked(false);
            }
            checkIv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selectContent = getList().get(position);
                    selectePosition = position;
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
