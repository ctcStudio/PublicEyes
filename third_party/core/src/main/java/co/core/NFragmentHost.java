package co.core;

import co.core.fragments.NavigationManager;
import co.core.imageloader.NImageLoader;

/**
 * @author TUNGDX
 */
public interface NFragmentHost {
    NImageLoader getImageLoader();

    NavigationManager getNavigationManager();
}
