package team.rainfall.finality.api.mixin;

import age.of.civilizations2.jakowski.lukasz.Menu;
import team.rainfall.finality.api.ui.MenuProxy;
import team.rainfall.lumipatch.Dummy;
import team.rainfall.lumipatch.Mixin;

import java.util.ArrayList;
import java.util.List;

/** Adds the custom-view bridge directly to AoH2DE's MenuManager. */
@Mixin(target = "age.of.civilizations2.jakowski.lukasz.MenuManager")
public class MixinMenuManager {
    @Dummy private List<List<Menu>> menus;
    @Dummy private int viewID;
    @Dummy private int fromViewID;
    @Dummy private int toViewID;
    @Dummy private int addMenu(Menu menu) { return 0; }
    @Dummy private void resetHoverActive() { }
    @Dummy private void resetChangeViewMode() { }
    @Dummy private void updateViewID() { }

    public int finality$registerCustomView(List<Menu> pages) {
        if (pages == null || pages.isEmpty()) throw new IllegalArgumentException("Custom view must contain a menu");
        int id = addMenu(pages.get(0));
        for (int i = 1; i < pages.size(); i++) addNewMenuToViewID(id, pages.get(i));
        return id;
    }

    public void finality$replaceCustomView(int id, List<Menu> pages) {
        List<Menu> current = menus.get(id);
        while (current.size() > pages.size()) current.remove(current.size() - 1);
        for (int i = 0; i < pages.size(); i++) {
            if (i < current.size()) current.set(i, pages.get(i));
            else addNewMenuToViewID(id, pages.get(i));
        }
    }

    public void finality$setCustomViewID(String id) {
        switchCustomView(id, false);
    }

    public void finality$setCustomViewIDWithoutAnimation(String id) {
        switchCustomView(id, true);
    }

    private void switchCustomView(String id, boolean withoutAnimation) {
        resetHoverActive();
        if (!withoutAnimation) fromViewID = viewID;
        viewID = MenuProxy.PROXY.getViewID(id);
        toViewID = viewID;
        if (withoutAnimation) resetChangeViewMode();
        updateViewID();
    }

    @Dummy
    private int addNewMenuToViewID(int id, Menu menu) {
        return 0;
    }
}
