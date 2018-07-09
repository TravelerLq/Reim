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

package com.jci.vsd.observer;

import android.support.v4.app.FragmentManager;

/**
 * Created by heisenberg on 2017/7/21.
 * heisenberg.gong@koolpos.com
 */

public interface DialogObserverHolder extends ObserverHolder {
    FragmentManager getSupportFragmentManager();

   /* void showDialog(cn.koolcloud.pos.mobile.widget.Actions4SimpleDlg actions4SimpleDlg);*/
}
