package team.rainfall.finality.api.ui;

import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.MenuManager;
import team.rainfall.finality.FinalityLogger;
import team.rainfall.finality.api.interaction.Interaction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/** Registers and switches dynamically-created AoH2DE views. */
public final class MenuProxy {
    public static final MenuProxy PROXY = new MenuProxy();
    public final HashMap<String, Integer> viewIDs = new HashMap<String, Integer>();
    public final HashMap<String, Interaction<ArrayList<Menu>>> views = new HashMap<String, Interaction<ArrayList<Menu>>>();

    private Method register;
    private Method replace;

    private MenuProxy() {
    }

    /** Registers a callback that returns the menu pages for a custom view. */
    public void addView(String id, Interaction<ArrayList<Menu>> callback) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("view id cannot be empty");
        if (callback == null) throw new IllegalArgumentException("view callback cannot be null");
        views.put(id, callback);
    }

    public int getViewID(String id) {
        Interaction<ArrayList<Menu>> callback = views.get(id);
        if (callback == null) {
            FinalityLogger.error("Failed to find custom view " + id);
            return mainMenuId();
        }
        ArrayList<Menu> menus = callback.run(null);
        if (menus == null || menus.isEmpty()) throw new IllegalArgumentException("Custom view must contain a menu");
        try {
            if (viewIDs.containsKey(id)) {
                invoke(replace, "finality$replaceCustomView", viewIDs.get(id), menus);
                return viewIDs.get(id);
            }
            int viewID = (Integer) invoke(register, "finality$registerCustomView", menus);
            viewIDs.put(id, viewID);
            return viewID;
        } catch (Exception e) {
            FinalityLogger.error("Failed to register custom view " + id, e);
            return mainMenuId();
        }
    }

    public static void setViewID(String id) {
        invokeSet(id, false);
    }

    public static void setViewIDWithoutAnimation(String id) {
        invokeSet(id, true);
    }

    private static void invokeSet(String id, boolean withoutAnimation) {
        try {
            MenuManager manager = manager();
            Method method = manager.getClass().getMethod(withoutAnimation
                    ? "finality$setCustomViewIDWithoutAnimation"
                    : "finality$setCustomViewID", String.class);
            method.invoke(manager, id);
        } catch (Exception e) {
            FinalityLogger.error("Failed to switch to custom view " + id, e);
        }
    }

    private int mainMenuId() {
        try {
            return manager().MAIN_MENU;
        } catch (Exception e) {
            return 0;
        }
    }

    private static MenuManager manager() {
        try {
            Class<?> cfg = Class.forName("age.of.civilizations2.jakowski.lukasz.CFG");
            return (MenuManager) cfg.getField("menus").get(null);
        } catch (Exception e) {
            throw new IllegalStateException("AoH2DE menus are not initialized", e);
        }
    }

    private static Object invoke(Method method, String name, Object... args) throws Exception {
        MenuManager manager = manager();
        if (method == null) {
            if ("finality$registerCustomView".equals(name)) {
                method = manager.getClass().getMethod(name, java.util.List.class);
                PROXY.register = method;
            } else {
                method = manager.getClass().getMethod(name, int.class, java.util.List.class);
                PROXY.replace = method;
            }
        }
        return method.invoke(manager, args);
    }
}
