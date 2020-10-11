package de.schornyy.crate.crateplugin.interactableItems;

import de.schornyy.crate.crateplugin.CratePlugin;
import org.bukkit.inventory.ItemStack;

public class InteractItemsUtils {

    public static InteractItem getItemByIndex(int index) {
        if(CratePlugin.plugin.getInteractItemManager().getStoredInteractItems().isEmpty()) return null;

        for(InteractItem interactItem : CratePlugin.plugin.getInteractItemManager().getStoredInteractItems().values()) {
            if(interactItem == null)break;
            if(interactItem.getIndex() == index) {
                return interactItem;
            }
        }
        return null;
    }

    public static InteractItem getInteractItembyItemStack(ItemStack itemStack) {
        return CratePlugin.plugin.getInteractItemManager().getStoredInteractItems().get(itemStack);
    }
}
