package com.lihonghui.vinci.common.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.lihonghui.vinci.R;

public class ContentDialogHelper {
	private View dialogContentView;
	private Activity mActivity;
	private ContentDialog contentDialog;

	private ContentDialogHelper(Activity activity) {
		this.mActivity = activity;
	}
	public static ContentDialogHelper getInstance(Activity activity){
		return new ContentDialogHelper(activity);
	}
	
	public ContentDialogHelper addDialogContentView(View dialogContentView){
		this.dialogContentView = dialogContentView;
		return this;
	}
	
	public ContentDialogHelper show(){
		contentDialog = new ContentDialog(mActivity, R.style.ContentDialogStyle);
		contentDialog.show();
		return this;
	}
	public void hide(){
		if(contentDialog != null){
			contentDialog.cancel();
		}		
	}
	class ContentDialog extends Dialog{
		/**
		 * @param context
		 * @param themeResId
		 */
		public ContentDialog(Context context, int themeResId) {
			super(context, themeResId);
			// TODO Auto-generated constructor stub
			
			init();
		}
		
		private void init(){
			Window window = this.getWindow();
			window.getDecorView().setPadding(0, 0, 0, 0);
			LayoutParams attributes = window.getAttributes();
			attributes.width = LayoutParams.MATCH_PARENT;
			attributes.height = LayoutParams.MATCH_PARENT;
			window.setAttributes(attributes);
			this.setContentView(dialogContentView);
		}
	}
}
