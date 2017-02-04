package com.lihonghui.vinci.common.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.lihonghui.vinci.R;

/**
 * �Զ��崥��������ť
 * @author lhh
 *
 */
public class StateSelectButton extends Button {
	private String normalStateText;
	private String pressStateText;
	private int normalStateTextColor;
	private int pressStateTextColor;
	private Drawable normalStateBackground;
	private Drawable pressStateBackground;

	//	private int normalStateBackgroundDrawable;
	//	private int pressStateBackgroundDrawable;
	//	private int normalStateBackgroundColor;
	//	private int pressStateBackgroundColor;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	@SuppressLint("NewApi")
	public StateSelectButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		initParameter(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public StateSelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initParameter(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public StateSelectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initParameter(context, attrs);
	}

	/**
	 * @param context
	 */
	public StateSelectButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private void initParameter(Context context, AttributeSet attrs) {
		TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.StateSelectButton);

		normalStateText = styledAttributes.getString(R.styleable.StateSelectButton_normal_state_text);
		pressStateText = styledAttributes.getString(R.styleable.StateSelectButton_press_state_text);

		normalStateTextColor = styledAttributes.getColor(R.styleable.StateSelectButton_normal_state_text_color, -1);
		pressStateTextColor = styledAttributes.getColor(R.styleable.StateSelectButton_press_state_text_color, -1);

		normalStateBackground = styledAttributes.getDrawable(R.styleable.StateSelectButton_normal_state_background);
		pressStateBackground = styledAttributes.getDrawable(R.styleable.StateSelectButton_press_state_background);

		styledAttributes.recycle();

		stateSelect(STATE_NORMAL);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			stateSelect(STATE_PRESS);
			if(mStateChangeListener != null){
				mStateChangeListener.onPress();
			}
			break;
		case MotionEvent.ACTION_UP:
			stateSelect(STATE_NORMAL);
			if(mStateChangeListener != null){
				mStateChangeListener.onUp();
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private StateChangeListener mStateChangeListener;
	public void addStateChangeListener(StateChangeListener stateChangeListener){
		this.mStateChangeListener = stateChangeListener;
	}
	public interface StateChangeListener{
		void onPress();
		void onUp();
	}

	private final int STATE_NORMAL = 0;
	private final int STATE_PRESS = 1;
	private final String EMPTY_STRING = "";

	@SuppressLint("NewApi")
	private void stateSelect(int STATE) {
		if (STATE == STATE_NORMAL) {
			if (normalStateText != null && !EMPTY_STRING.equals(normalStateText)) {
				setText(normalStateText);
			}
			if (normalStateTextColor != -1) {
				setTextColor(normalStateTextColor);
			}
			if (normalStateBackground != null) {
				setBackground(normalStateBackground);
			}
		} else {
			if (pressStateText != null && !EMPTY_STRING.equals(pressStateText)) {
				setText(pressStateText);
			}
			if (pressStateTextColor != -1) {
				setTextColor(pressStateTextColor);
			}
			if (pressStateBackground != null) {
				setBackground(pressStateBackground);
			}
		}
	}
}
