package com.rms.supply.widget.dialog;

import android.app.Activity;
import android.app.Dialog;

/**
 * @author zgYi
 * @date 2019/6/11
 * @Description: Dialog
 */

public abstract class BaseDialog {
    private Activity context;
    private Dialog dialog;

    public BaseDialog(Activity context) {
        this.context = context;
    }

    public void onInit() {
        dialog = initDialog(context);
    }

    protected abstract Dialog initDialog(Activity context);

    public void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

}
