package com.jci.vsd.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.jci.vsd.R;
import com.jci.vsd.activity.NoticeCenterActivity;
import com.jci.vsd.bean.notice.UnreadNoticeBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.model.MsgModel;
import com.jci.vsd.utils.Loger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/12/10 0010.
 */

public class NoticeMsgService extends Service {
    private Timer timer;
    private static final int TIME_PERIAO = 15 * 1000;
    private Notification notification;
    private NotificationManager manager;
    private NotificationCompat.Builder mBuilder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Loger.e("service-onStartCommand");
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        endTimer();
        super.onDestroy();
    }


    private void startTimer() {
        endTimer();
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Loger.e("startTimer == ");
                getUnreadMsg();
            }
        };
        timer.schedule(timerTask, 0, TIME_PERIAO);
    }

    private void endTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;

        }
    }

    /**
     * 请求未读消息
     */
    private void getUnreadMsg() {
        new MsgModel().getUnreadMsg(new MsgModel.OnUnReadMsgListener() {
            @Override
            public void onReceiveMsg(String title, List<UnreadNoticeBean> unreadNoticeBeanList) {
                handleMsg(title, unreadNoticeBeanList);
            }
        });
    }


    /**
     * 发送广播和通知
     */
    private void handleMsg(String title, List<UnreadNoticeBean> unreadNoticeBeanList) {
        Loger.e(title + "未读消息数量：" + unreadNoticeBeanList.size());
        Intent intent = new Intent(AppConstant.NOTICE_ACTION);
        intent.putExtra("title", title);
        intent.putExtra("count", unreadNoticeBeanList.size());
        sendBroadcast(intent);

        if (unreadNoticeBeanList.size() > 0) {
            showNotification(unreadNoticeBeanList);
        }
    }

    /**
     * 发送通知
     */
    private void showNotification(List<UnreadNoticeBean> unreadNoticeBeanList) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        //跳转意图
        Intent intent = new Intent(this, NoticeCenterActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        //通知栏显示内容
        builder.setTicker(unreadNoticeBeanList.get(0).getMsgTitle());
        //通知栏消息下拉时显示的标题
        builder.setContentTitle(unreadNoticeBeanList.get(0).getMsgTitle());
        //通知消息下拉是显示的文本内容
        builder.setContentText(unreadNoticeBeanList.get(0).getMsgContent());
        //接收到通知时，按手机的默认设置进行处理，声音，震动，灯
        builder.setDefaults(Notification.DEFAULT_ALL);
        //通知栏显示图标
        builder.setSmallIcon(R.drawable.notice_42_42);
        builder.setContentIntent(pendingIntent);
        notification = builder.build();
        //点击跳转后消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(1, notification);


    }
}
