package com.hiepkhach9x.publiceyes.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.hiepkhach9x.base.api.errors.AuthFailureError;
import com.hiepkhach9x.base.api.errors.Error;
import com.hiepkhach9x.base.api.errors.ParserError;
import com.hiepkhach9x.base.api.errors.ServerError;
import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 5/1/2017.
 */

public class AppAlertDialog {
    public static android.support.v7.app.AlertDialog errorApiAlertDialogOk(Context context, Exception error,Dialog.OnClickListener okClick) {
        String msg;
        if (error instanceof ServerError) {
            ServerError serverError = (ServerError) error;
            if (TextUtils.isEmpty(error.getMessage())) {
                msg = serverError.getError().getErrorDescription();
            } else {
                msg = serverError.getError().getMessage();
            }
        } else if (error instanceof ParserError) {
            msg = "parser data error";
        } else if (error instanceof AuthFailureError) {
            msg = "AuthFailure error";
        } else {
            msg = "unKnow error request";
        }

        return alertDialogOkAndCancel(context, context.getString(R.string.error), msg, true, okClick, false, null);
    }

    public static android.support.v7.app.AlertDialog errorApiAlertDialogOk(Context context, Error error,
                                                                           boolean hasOKButton, Dialog.OnClickListener okClick) {
        if (TextUtils.isEmpty(error.getMessage())) {
            return alertDialogOkAndCancel(context, context.getString(R.string.error), error.getErrorDescription(), hasOKButton, okClick, false, null);
        } else {
            Spanned spanned = Html.fromHtml(error.getMessage());
            String massage = spanned.toString();
            return alertDialogOkAndCancel(context, context.getString(R.string.error),
                    massage, hasOKButton, okClick, false, null);
        }
    }

    public static android.support.v7.app.AlertDialog alertDialogCancel(Context context, String title, String message,
                                                                       boolean hasCancelButton, Dialog.OnClickListener cancelClick) {
        return alertDialogOkAndCancel(context, title, message, false, null, hasCancelButton, cancelClick);
    }

    public static android.support.v7.app.AlertDialog alertDialogOk(Context context, String title, String message,
                                                                   boolean hasOKButton, Dialog.OnClickListener okClick) {
        return alertDialogOkAndCancel(context, title, message, hasOKButton, okClick, false, null);
    }

    public static android.support.v7.app.AlertDialog alertDialogOkAndCancel(Context context, String title, String message,
                                                                            boolean hasOKButton, Dialog.OnClickListener okClick,
                                                                            boolean hasCancelButton, Dialog.OnClickListener cancelClick) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (hasOKButton) {
            builder.setNegativeButton(R.string.common_ok, okClick);
        }
        if (hasCancelButton) {
            builder.setPositiveButton(R.string.common_cancel, cancelClick);
        }
        return builder.create();
    }
}
