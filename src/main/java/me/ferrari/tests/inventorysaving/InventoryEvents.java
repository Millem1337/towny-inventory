package me.ferrari.tests.inventorysaving;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {
    @EventHandler
    public void onAddToInventory(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(!(e.getCurrentItem() == null)) {
            if (InventorySaving.getInstance().getConfig().getBoolean("inventory.only_materials") && e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', InventorySaving.getInstance().getConfig().getString("messages.inventory.name")))) {
                if (e.getCurrentItem().hasItemMeta()) {
                    p.sendMessage(Other.convert( InventorySaving.getInstance().getConfig().getString("messages.err.only_materials")));
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();

        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', InventorySaving.getInstance().getConfig().getString("messages.inventory.name")))){
            try {
                InventorySaving.getInstance().getSaves().set(TownyAPI.getInstance().getResident(p).getTown().getName(), e.getInventory().getContents());
                InventorySaving.getInstance().saveSaves();
            } catch (NotRegisteredException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
