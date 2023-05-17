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
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', InventorySaving.getInstance().getConfig().getString("messages.err.only_materials")));
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
                /*ItemStack air = new ItemStack(Material.AIR);
                ItemStack c[];
                for(int i = 0; i < e.getInventory().getSize(); i++){
                    if(e.getInventory().getContents()[i] == null){
                        e.getInventory().getContents()[i] = air;
                    }
                }*/
                InventorySaving.getInstance().getSaves().set(TownyAPI.getInstance().getResident(p).getTown().getName(), e.getInventory().getContents());
                InventorySaving.getInstance().saveSaves();
            } catch (NotRegisteredException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @EventHandler
    public void onInventoryMoveEvent(InventoryInteractEvent e){
        /*if(!(e.getItem() == null)){
            Bukkit.getLogger().info("1 condition");
            if (!e.getInitiator().getViewers().isEmpty()) {
                Bukkit.getLogger().info("2 condition");
                if (e.getInitiator().getViewers().get(0).getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', InventorySaving.getInstance().getConfig().getString("messages.inventory.name")))) {
                    Bukkit.getLogger().info("3 condition");
                    if (e.getItem().hasItemMeta()) {
                        e.setCancelled(true);
                        Bukkit.getLogger().info("4 condition");
                    }
                }
            }
        }*/
    }
}
