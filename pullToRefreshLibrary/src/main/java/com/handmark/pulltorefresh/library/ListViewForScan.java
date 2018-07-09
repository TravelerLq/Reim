package com.handmark.pulltorefresh.library;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

public class ListViewForScan extends ListView {
	private Activity activity;
	private boolean isHandelKeyEvent = false;

	public ListViewForScan(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	/**
	 * 设置Activity实例
	 * 
	 * @param activity
	 *            当前activity实例
	 * @param isHandelKeyEvent
	 *            当遇到冲突事件时，是否阻止ListView处理KeyEvent
	 */
	public void setActivity(Activity activity, boolean isHandelKeyEvent) {
		this.activity = activity;
		this.isHandelKeyEvent = isHandelKeyEvent;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("", "------ListViewForScan-----"+keyCode);
		if (keyCode == KeyEvent.KEYCODE_ENTER && this.activity != null) {
			this.activity.onKeyDown(keyCode, event);
			if (isHandelKeyEvent) {
				return true;
			}
		}
		if (keyCode == KeyEvent.KEYCODE_SPACE && this.activity != null) {
			this.activity.onKeyDown(keyCode, event);
			if (isHandelKeyEvent) {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
