package com.hiepkhach9x.publiceyes.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.hiepkhach9x.base.api.errors.Error;
import com.hiepkhach9x.publiceyes.R;

/**
 * Created by hungh on 5/1/2017.
 */

public class AppAlertDialog {

    public static android.support.v7.app.AlertDialog ErrorApiAlertDialogOk(Context context, Error error,
                                                                           boolean hasOKButton, Dialog.OnClickListener okClick) {
        if (TextUtils.isEmpty(error.getMessage())){
            return AlertDialogOkAndCancel(context, context.getString(R.string.error), error.getErrorDescription(), hasOKButton, okClick, false, null);
        }else {
            Spanned spanned = Html.fromHtml(error.getMessage());
            String massage = spanned.toString();
            return AlertDialogOkAndCancel(context, context.getString(R.string.error),
                    massage, hasOKButton, okClick, false, null);
        }
    }

    public static android.support.v7.app.AlertDialog AlertDialogCancel(Context context, String title, String message,
                                                                       boolean hasCancelButton, Dialog.OnClickListener cancelClick) {
        return AlertDialogOkAndCancel(context, title, message, false, null, hasCancelButton, cancelClick);
    }

    public static android.support.v7.app.AlertDialog AlertDialogOk(Context context, String title, String message,
                                                                   boolean hasOKButton, Dialog.OnClickListener okClick) {
        return AlertDialogOkAndCancel(context, title, message, hasOKButton, okClick, false, null);
    }

    public static android.support.v7.app.AlertDialog AlertDialogOkAndCancel(Context context, String title, String message,
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
