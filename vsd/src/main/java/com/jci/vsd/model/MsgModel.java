package com.jci.vsd.model;

import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.bean.notice.NoticeRequestBean;
import com.jci.vsd.bean.notice.UnreadNoticeBean;
import com.jci.vsd.network.control.NoticeCenterControl;
import com.jci.vsd.observer.DefaultObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;

import java.util.List;

import io.reactivex.Observable;

/**
 * Date:2018/2/5
 * Author:Edward
 * Description:
 */

public class MsgModel {

    private OnUnReadMsgListener unReadMsgListener;

    /**
     * 未读消息
     */
    public void getUnreadMsg(OnUnReadMsgListener unReadMsgListener) {
        this.unReadMsgListener = unReadMsgListener;
        msgCenterData();
        alertData();
    }

    /**
     * 请求参数
     */
    private NoticeRequestBean getParams() {
        LoginResponseBean loginResponseBean = VsdApplication.getInstance().getLoginResponseBean();
        if (loginResponseBean == null) {
            return null;
        }
        NoticeRequestBean noticeRequestBean = new NoticeRequestBean(loginResponseBean.getId(), "1000", 1 + "");
        if (VsdApplication.getInstance().getLoginResponseBean() != null) {
            noticeRequestBean.setReceiver(VsdApplication.getInstance().getLoginResponseBean().getId());
        }
        return noticeRequestBean;
    }

    /**
     * 消息中心未读消息
     */
    private void msgCenterData() {
        NoticeRequestBean params = getParams();
        if (params == null) {
            return;
        }

        Observable<List<UnreadNoticeBean>> observable = new NoticeCenterControl().getPushNotice(params);
        DefaultObserver<List<UnreadNoticeBean>> observer = new DefaultObserver<List<UnreadNoticeBean>>() {
            @Override
            protected void showProgress() {

            }

            @Override
            protected void dismissProgress() {

            }

            @Override
            public void onNext(List<UnreadNoticeBean> unreadNoticeBeanList) {
                super.onNext(unreadNoticeBeanList);
                unReadMsgListener.onReceiveMsg("消息中心", unreadNoticeBeanList);
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }

    /**
     * 到料提醒未读消息
     */
    private void alertData() {
        NoticeRequestBean params = getParams();
        if (params == null) {
            return;
        }
        Observable<List<UnreadNoticeBean>> observable = new NoticeCenterControl().getUnreadMsg(params);
        DefaultObserver<List<UnreadNoticeBean>> observer = new DefaultObserver<List<UnreadNoticeBean>>() {
            @Override
            protected void showProgress() {

            }

            @Override
            protected void dismissProgress() {

            }

            @Override
            public void onNext(List<UnreadNoticeBean> unreadNoticeBeanList) {
                super.onNext(unreadNoticeBeanList);
                Loger.e("--unreadNoticeBeanList.size()=" + unreadNoticeBeanList.size());
                unReadMsgListener.onReceiveMsg("到料提醒", unreadNoticeBeanList);
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }

    /**
     * 回调
     */
    public interface OnUnReadMsgListener {
        void onReceiveMsg(String title, List<UnreadNoticeBean> unreadNoticeBeanList);
    }
}
