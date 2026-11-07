package team.rainfall.finality.api.ui;

import age.of.civilizations2.jakowski.lukasz.View;

/** View API matching the AoH3 custom-view entry points. */
public final class ViewAPI {
    private ViewAPI() {
    }

    public static void setViewID(String id) {
        MenuProxy.setViewID(id);
    }

    public static void setViewIDWithoutAnimation(String id) {
        MenuProxy.setViewIDWithoutAnimation(id);
    }

    public static void setView(View view) {
        if (view == null) throw new IllegalArgumentException("view cannot be null");
        try {
            Class<?> cfg = Class.forName("age.of.civilizations2.jakowski.lukasz.CFG");
            Object menus = cfg.getField("menus").get(null);
            menus.getClass().getMethod("setMenuID", View.class).invoke(menus, view);
        } catch (Exception e) {
            throw new IllegalStateException("AoH2DE menus are not initialized", e);
        }
    }

    public static void setViewWithoutAnimation(View view) {
        if (view == null) throw new IllegalArgumentException("view cannot be null");
        try {
            Class<?> cfg = Class.forName("age.of.civilizations2.jakowski.lukasz.CFG");
            Object menus = cfg.getField("menus").get(null);
            menus.getClass().getMethod("setMenuIDWithoutAnim", View.class).invoke(menus, view);
        } catch (Exception e) {
            throw new IllegalStateException("AoH2DE menus are not initialized", e);
        }
    }
}
