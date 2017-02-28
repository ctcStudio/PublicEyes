package co.mediapicker;

import java.util.List;

/**
 * @author TUNGDX
 */

/**
 * Listener for select media item.
 */
interface NMediaSelectedListener {
    void onHasNoSelected();

    void onHasSelected(List<NMediaItem> mediaSelectedList);
}
