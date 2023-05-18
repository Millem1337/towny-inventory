package me.ferrari.tests.inventorysaving;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.AddonCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OpenInventoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!TownyAPI.getInstance().getResident((Player) sender).hasTown()){
            sender.sendMessage(Other.convert( InventorySaving.getInstance().getConfig().getString("messages.err.not_in_town")));
            return true;
        }
        Player p = ((Player) sender).getPlayer();
        Inventory inv = Bukkit.createInventory(null, 9*5, ChatColor.translateAlternateColorCodes('&',InventorySaving.getInstance().getConfig().getString("messages.inventory.name")));
        //List<ItemStack> content = null;
        if(!(TownyAPI.getInstance().getResident(p).isMayor()) && InventorySaving.getInstance().getConfig().getBoolean("inventory.only_mayor")){
            sender.sendMessage( Other.convert(InventorySaving.getInstance().getConfig().getString("messages.err.only_mayor")) );
            return true;
        }
        try {
            Bukkit.getLogger().info(TownyAPI.getInstance().getResident(p).getTown().getName());
            if(InventorySaving.getInstance().getSaves().get( TownyAPI.getInstance().getResident(p).getTown().getName() ) != null){
                InventorySaving.getInstance().loadSaves();
                List<?> itemStackList = InventorySaving.getInstance().getSaves().getList( TownyAPI.getInstance().getResident(p).getTown().getName() );
                for(int i = 0; i < inv.getSize(); i++){
                    if(itemStackList.get(i) == null){
                        //Bukkit.getLogger().info("Item is null" + i );
                        continue;
                    }
                    inv.setItem(i, (ItemStack) itemStackList.get(i));
                }
            }else{
                Bukkit.getLogger().info("N");
            }
        } catch (NotRegisteredException ex) {
            throw new RuntimeException(ex);
        }
        p.openInventory(inv);
        return true;
    }
}