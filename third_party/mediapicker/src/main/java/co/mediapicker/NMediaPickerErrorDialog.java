package co.mediapicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * @author TUNGDX
 */

/**
 * Create dialog for media picker module. Should only use in this module.
 */
public class NMediaPickerErrorDialog extends DialogFragment {
    private String mMessage;
    private OnClickListener mOnPositionClickListener;

    public static NMediaPickerErrorDialog newInstance(String msg) {
        NMediaPickerErrorDialog dialog = new NMediaPickerErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString("msg");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(mMessage)
                .setPositiveButton(R.string.ok, mOnPositionClickListener)
                .create();
    }

    public void setOnOKClickListener(OnClickListener mOnClickListener) {
        this.mOnPositionClickListener = mOnClickListener;
    }
}
