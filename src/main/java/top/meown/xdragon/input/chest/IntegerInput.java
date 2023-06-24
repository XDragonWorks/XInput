package top.meown.xdragon.input.chest;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.meown.xdragon.core.utils.MessageManager;
import top.meown.xdragon.core.utils.lambda.Parameter1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static top.meown.xdragon.input.chest.InputListener.inputMap;

public class IntegerInput {

    private static ItemStack overrideInfo(ItemStack itemStack, String name, String lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLoreComponents(MessageManager.getInstance().getBaseComponentsList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void get(Player player, Parameter1<Integer> lam, String title, int defaultValue){
        final int[] value = {defaultValue};
        Inventory inv = Bukkit.createInventory(player,9,title+"    次数"+value[0]);
        ItemStack left = overrideInfo(CustomStack.getInstance("mcicons:icon_left_blue").getItemStack(), "-", "input.integer.left");
        ItemStack reset = overrideInfo(CustomStack.getInstance("mcicons:icon_refresh").getItemStack(),"重置","input.integer.refresh");
        ItemStack right = overrideInfo(CustomStack.getInstance("mcicons:icon_right_blue").getItemStack(), "+", "input.integer.right");
        ItemStack cancel = overrideInfo(CustomStack.getInstance("mcicons:icon_cancel").getItemStack(),"取消","input.integer.cancel");
        ItemStack confirm = overrideInfo(CustomStack.getInstance("mcicons:icon_confirm").getItemStack(),"保存","input.integer.confirm");
        inv.setItem(1,left);
        inv.setItem(2,reset);
        inv.setItem(3,right);
        inv.setItem(6,confirm);
        inv.setItem(7,cancel);
        inputMap.put(player,(InventoryClickEvent e)->{
            //End of input
            if (e == null){
                lam.run(value[0]);
                inputMap.remove(player);
                return;
            }

            e.setCancelled(true);

            switch (e.getSlot()){
                //inputs
                case 1: value[0]-=1;
                case 2: value[0]=defaultValue;
                case 3: value[0]+=1;

                //End of input
                case 6:
                case 7:
                    lam.run(value[0]);
                    inputMap.remove(player);
                    e.getInventory().close();
                    break;
            }

        });

        player.openInventory(inv);

    }
    }

