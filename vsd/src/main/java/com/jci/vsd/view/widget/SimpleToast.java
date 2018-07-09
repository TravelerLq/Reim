/*
 * Copyright (c) 2017 李虎
 * Copyright (c) 2017 李世界
 * Copyright (c) 2017 朱璟
 * Copyright (c) 2017 heisenberg.gong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jci.vsd.view.widget;

import android.annotation.SuppressLint;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;

@SuppressLint("InflateParams")
public class SimpleToast {
	private static SimpleToast simpleToast;

	private SimpleToast() {
	}

	public static SimpleToast getInatance() {
		if (simpleToast == null) {
			simpleToast = new SimpleToast();
		}
		return simpleToast;
	}

	/**
	 * 显示Toast
	 * 
	 */
	
	public static void ToastMessage(@StringRes int tvStringSrc) {
		 String tvString = VsdApplication.getInstance().getString(tvStringSrc);
		 ToastMessage(tvString);
	}

	public static void ToastMessage(String tvString) {
		ToastMessage(tvString, false);
	}

	public static void ToastMessage(String tvString, boolean error) {
		toastMessage(tvString, 1,error);
	}
	public static void ToastMessageShort(String tvString) {
		toastMessage(tvString, Toast.LENGTH_SHORT);
	}
	public void ToastMessage(String tvString, int length) {
		if(TextUtils.isEmpty(tvString)) return; // 空信息不显示
		try {
			View layout = LayoutInflater.from(VsdApplication.getInstance()).inflate(R.layout.toast_xml, null);
			TextView text = (TextView) layout.findViewById(R.id.text);
			Toast toast = null;
			text.setText(tvString);
			toast = new Toast(VsdApplication.getInstance());
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(length);
			toast.setView(layout);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void ToastMessage(int tvStringSrc, int length) {
		String tvString = VsdApplication.getInstance().getString(tvStringSrc);
		ToastMessage(tvString, length);
	}
	
	public static void toastMessage(String tvString, int length) {
		toastMessage(tvString,length,false);
	}
	private static void toastMessage(String tvString, int length, boolean error) {
		if(TextUtils.isEmpty(tvString)) return; // 空信息不显示
		try {
			View layout = LayoutInflater.from(VsdApplication.getInstance()).inflate(R.layout.toast_xml, null);
			TextView text = (TextView) layout.findViewById(R.id.text);
			if (error){
				text.setTextColor(VsdApplication.getInstance().getResources().getColor(R.color.red));
			}
			text.setText(tvString);
			Toast toast   = new Toast(VsdApplication.getInstance());
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(length);
			toast.setView(layout);
			toast.show();
		} catch (Exception e) {
		}
	}
}
