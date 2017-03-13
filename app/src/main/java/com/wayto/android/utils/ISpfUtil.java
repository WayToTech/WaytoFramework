package com.wayto.android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.wayto.android.base.DataApplication;

/**
 * SharedPreference 工具类
 * 
 * @author hezhiWu
 * 
 */
public class ISpfUtil {
	private static final String NAME = "wayTo";

	/**
	 * 设置SharePreference文件中的字段的值
	 *
	 * @param key
	 *            字段
	 * @param value
	 *            值
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("WorldWriteableFiles")
	public static boolean setValue(String key, Object value) {
		boolean status = false;
		SharedPreferences spf = null;
		try {
			spf = DataApplication.getInstance().getSharedPreferences(NAME, Context.MODE_MULTI_PROCESS);
			String type = value.getClass().getSimpleName();// 获取数据类型
			SharedPreferences.Editor editor = spf.edit();
			if (spf != null) {
				if ("String".equals(type)) {
					editor.putString(key, (String) value);
				} else if ("Integer".equals(type)) {
					editor.putInt(key, (Integer) value);
				} else if ("Boolean".equals(type)) {
					editor.putBoolean(key, (Boolean) value);
				} else if ("Long".equals(type)) {
					editor.putLong(key, (Long) value);
				} else if ("Float".equals(type)) {
					editor.putFloat(key, (Float) value);
				}
				status = editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 获得SharePreference的值
	 *
	 * @param key
	 *            字段
	 * @param defValue
	 *            默认值
	 * @return 获得对应key的值
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("WorldWriteableFiles")
	public static Object getValue(String key, Object defValue) {
		SharedPreferences spf = null;
		try {
			spf = DataApplication.getInstance().getSharedPreferences(NAME, Context.MODE_MULTI_PROCESS);
			String type = defValue.getClass().getSimpleName();// 获取数据类型
			if (spf != null) {
				if (type.equals("String")) {
					return spf.getString(key, (String) defValue);
				} else if (type.equals("Integer")) {
					return spf.getInt(key, (Integer) defValue);
				} else if (type.equals("Boolean")) {
					return spf.getBoolean(key, (Boolean) defValue);
				} else if (type.equals("Long")) {
					return spf.getLong(key, (Long) defValue);
				} else if (type.equals("Float")) {
					return spf.getFloat(key, (Float) defValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
