package com.jci.vsd.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MyListview extends ListView {

	public MyListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private GestureDetector mGestureDetector;
	OnTouchListener mGestureListener;

	public MyListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean a = super.onInterceptTouchEvent(ev);
		boolean b =mGestureDetector.onTouchEvent(ev);
//		Log.i("hiwhitley",String.valueOf(a)+"+"+String.valueOf(b)) ;
//		boolean result = super.onInterceptTouchEvent(ev)
//				&& mGestureDetector.onTouchEvent(ev);
//		Log.i("hiwhitley",String.valueOf(result)) ;
		return b;
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (Math.abs(distanceY) / Math.abs(distanceX)>2) {
				return true;
			}
			return false;
		}

//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//				float velocityY) {
//			float minMove = 120; // ��С��������
//			float minVelocity = 0; // ��С�����ٶ�
//			float beginX = e1.getX();
//			float endX = e2.getX();
//			float beginY = e1.getY();
//			float endY = e2.getY();
//
//			if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity) { // ��
//				return false;
//			} else if (endX - beginX > minMove
//					&& Math.abs(velocityX) > minVelocity) { // �һ�
//				return false;
//			} else if (beginY - endY > minMove
//					&& Math.abs(velocityY) > minVelocity) { // �ϻ�
//				return true;
//			} else if (endY - beginY > minMove
//					&& Math.abs(velocityY) > minVelocity) { // �»�
//				return true;
//			}
//
//			return false;
//		}
	}

}
