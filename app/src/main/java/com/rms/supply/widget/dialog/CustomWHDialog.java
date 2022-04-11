package com.rms.supply.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CustomWHDialog extends Dialog {

    /**
     * 默认宽度为屏幕的2/3
     */
    public CustomWHDialog(Activity context, View layout, int style) {
        super(context, style);
        int screenW = getScreenHeight(context);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = (screenW) / 3;
            window.setAttributes(params);
        }
    }

    /**
     * 自定义宽度及位置
     */
    public CustomWHDialog(Activity context, View layout, int style, int widthRatio, int gravity, int xOff) {
        super(context, style);
        int screenW = getScreenWidth(context);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            params.gravity = gravity;
            params.width = ((screenW) - xOff) / widthRatio;
            window.setAttributes(params);
        }
    }

    /**
     * 自定义宽度及位置
     */
    public CustomWHDialog(Activity context, View layout, int style, int widthRatio, int heightRatio, int gravity, int xOff) {
        super(context, style);
        int screenW = getScreenWidth(context);
        int screenH = getScreenHeight(context);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            params.gravity = gravity;
            params.width = ((screenW) - xOff) / widthRatio;
            params.height = ((screenH)) / 100 * heightRatio;
            window.setAttributes(params);
        }
    }


    /**
     * ???? 此方法并未设置高度和宽度 width 和 height 设为0即可
     *
     * @param context
     * @param width
     * @param layout
     * @param style
     */
    public CustomWHDialog(Activity context, int width, View layout, int style) {
        super(context, style);
        int screenW = getScreenWidth(context);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams param;
        if (window != null) {
            param = window.getAttributes();
            param.gravity = Gravity.CENTER;
            param.width = (screenW) / 4;
            window.setAttributes(param);
        }
    }


    /**
     * 此方法设置Dialog的宽度
     *
     * @param context
     * @param layout
     * @param style
     * @param width
     */
    public CustomWHDialog(Context context, View layout, int style, int width) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = width;
            window.setAttributes(params);
        }
    }

    /**
     * 此方法设置Dialog的高度和宽度
     *
     * @param context
     * @param layout
     * @param style
     * @param width
     * @param height
     */
    public CustomWHDialog(Context context, View layout, int style, int width, int height) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = width;
            params.height = height;
            window.setAttributes(params);
        }
    }

    /**
     * 默认宽度为屏幕的2/3
     */
    public CustomWHDialog(Activity context, View layout, int style, String flag) {
        super(context, style);
        int screenW = getScreenWidth(context);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params;
        if (window != null) {
            params = window.getAttributes();
            if ("left".equals(flag)) {
                params.gravity = Gravity.LEFT;
            } else if ("center".equals(flag)) {
                params.gravity = Gravity.CENTER;
            } else {
                params.gravity = Gravity.RIGHT;
            }
            params.width = (screenW) / 3;
            window.setAttributes(params);
        }
    }

    public Display getDispaly(Activity context) {
        return context.getWindowManager().getDefaultDisplay();
    }

    public int getScreenWidth(Activity context) {
        return getDispaly(context).getWidth();
    }

    public int getScreenHeight(Activity context) {
        return getDispaly(context).getHeight();
    }

}
