package co.ntq.xyz.utils;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;
import android.widget.NumberPicker;

/**
 * Created by HungHN on 6/3/16.
 */
public class ViewUtils {
    /**
     * Call this function within {@link DialogFragment#onStart()}
     *
     * @param dialogFragment
     * @param width
     * @param height
     */
    public static void setWidthHeightDialogFragment(DialogFragment dialogFragment, int width, int height) {
        if (dialogFragment == null) return;
        Dialog dialog = dialogFragment.getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(width, height);
        }
    }

    /**
     * change divider color of NumberPickerView
     *
     * @param numberPicker
     * @param color
     */
    public static void setNPDividerColor(NumberPicker numberPicker, int color) {
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(numberPicker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * change divider color of DatePickerView
     *
     * @param datePicker
     * @param color`
     */
    public static void setDPDividerColor(DatePicker datePicker, int color) {
        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("day", "id", "android");
        int monthId = system.getIdentifier("month", "id", "android");
        int yearId = system.getIdentifier("year", "id", "android");

        NumberPicker dayPicker = (NumberPicker) datePicker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) datePicker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) datePicker.findViewById(yearId);

        setNPDividerColor(dayPicker, color);
        setNPDividerColor(monthPicker, color);
        setNPDividerColor(yearPicker, color);
    }

    /**
     * <pre>
     * setDescendantFocusability as {@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     *
     * Set wrapSelectorWheel as false
     * </pre>
     */
    public static void wrapWheelNumberPicker(NumberPicker numberPicker, int minValue, int maxValue) {
        numberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);

        numberPicker.setWrapSelectorWheel(false);
    }

    public static void setBackgroundAsStateListDrawable(View view, StateListDrawable drawable) {
        view.setClickable(true);
        setBackground(view, drawable);
    }

    public static void setBackground(View view, Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Because webView on Android is different depend on each Android version. This method will try
     * to setting WebView to display well on all Android versions.
     */
    public static void settingWebView(WebView webView, final Context context) {
        if (webView == null) return;
        webView.setLongClickable(false);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
                if (OSUtils.hasKITKAT()) {
                    float scaleF = oldScale / newScale;
                    int scalePercent = Math.round(scaleF * 100);
                    view.getSettings().setTextZoom(scalePercent);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (TextUtils.isEmpty(url) || !url.contains("http")) {
                    return false;
                }

                if (context != null) context.startActivity(IntentUtils.openLink(url));

                return true;
            }
        });

        WebSettings settings = webView.getSettings();

        settings.setDefaultFixedFontSize(16);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        settings.setDomStorageEnabled(true);

        if (OSUtils.hasKITKAT()) {
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            // if we use LayoutAlgorithm.SINGLE_COLUMN, many CSS errors will be occur
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
    }

    /**
     * If the #body has CSS, this CSS part may run not well in some Android version without trick :D.
     * Should display HTML body by this method to avoid that error.
     *
     * @param webView
     * @param body
     */
    public static void showHTMLBodyOnWV(WebView webView, String body) {
        if (webView == null) return;
        if (body == null) return;

        if (OSUtils.hasKITKAT()) {
            webView.loadDataWithBaseURL(ServerConfig.URL, body, "text/html; charset=utf-8", "utf-8", "");
        } else {
            webView.loadDataWithBaseURL(ServerConfig.URL, body, "text/html", "UTF-8", "");
        }
    }
}
