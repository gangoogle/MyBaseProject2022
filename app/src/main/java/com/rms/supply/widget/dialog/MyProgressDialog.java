package com.rms.supply.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rms.supply.R;


public class MyProgressDialog {
    Dialog dialog;
    Context context;
    boolean flags;
    TextView textView;

    OnCancelListener onCancelListener = new OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
            flags = false;
            dialog.dismiss();
        }
    };

    public MyProgressDialog(Context context) {
        this.context = context;
        flags = true;
        try {
            dialog = new Dialog(context, R.style.dialog);
            dialog.setOnCancelListener(onCancelListener);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_progress_dialog, null);
            textView = view.findViewById(R.id.show_txt);
            dialog.setContentView(view);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDialog(String str) {
        if (dialog != null) {
            if (str == null || "".equals(str)) {
                textView.setText("");
            } else {
                textView.setText(str);
            }
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    public void dissmisDialog() {
        if (dialog != null) {
            flags = false;
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public boolean isShowing() {
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }

    /**
     * Set dialog cancelable on touch outside(t/f).
     *
     * @param cancelable t/f.
     */
    public void setCanceledOnTouchOutside(boolean cancelable) {
        dialog.setCanceledOnTouchOutside(cancelable);
    }

    /**
     * set dialog cancelable.
     *
     * @param cancelable
     */
    public void setCancelable(boolean cancelable) {

        dialog.setCancelable(cancelable);
    }


    public Context getActivity() {
        return this.context;
    }

}