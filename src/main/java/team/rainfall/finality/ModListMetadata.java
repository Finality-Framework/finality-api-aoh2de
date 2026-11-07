package team.rainfall.finality;

import java.util.ArrayList;

/**
 * Metadata of the mod list,used by the generated {@code ModList} class.
 * @author RedreamR
 */
public class ModListMetadata {
    //Whether the loader was started with a mod list (--mod-list)
    public boolean enabled;
    //Absolute paths of the mods listed in the mod list
    public ArrayList<String> mods;

    public ModListMetadata(boolean enabled, ArrayList<String> mods) {
        this.enabled = enabled;
        this.mods = mods;
    }
}
