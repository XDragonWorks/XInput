package top.meown.xdragon.input.chest;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.meown.xdragon.core.utils.MessageManager;
import top.meown.xdragon.core.utils.lambda.Parameter1;
import top.meown.xdragon.input.utils.InventoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Sound.ENTITY_VILLAGER_NO;
import static top.meown.xdragon.input.chest.InputListener.inputMap;

public class IntegerInput {

    private static ItemStack overrideInfo(ItemStack itemStack, String name, String lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLoreComponents(MessageManager.getInstance().getBaseComponentsList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    private static ItemStack overrideInfo(ItemStack itemStack, String name){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void get(Player player, Parameter1<Integer> lam, String title, int defaultValue,int minValue,int maxValue ){
        final int[] value = {defaultValue};
        Inventory inv = Bukkit.createInventory(player,9,title);
        ItemStack left = overrideInfo(CustomStack.getInstance("mcicons:icon_left_blue").getItemStack(), "§6-1", "input.integer.left");
        ItemStack reset = overrideInfo(CustomStack.getInstance("mcicons:icon_refresh").getItemStack(),"§6重置","input.integer.refresh");
        ItemStack right = overrideInfo(CustomStack.getInstance("mcicons:icon_right_blue").getItemStack(), "§6+1", "input.integer.right");
        ItemStack cancel = overrideInfo(CustomStack.getInstance("mcicons:icon_cancel").getItemStack(),"§4取消","input.integer.cancel");
        ItemStack confirm = overrideInfo(CustomStack.getInstance("mcicons:icon_confirm").getItemStack(),"§a保存","input.integer.confirm");
        ItemStack number  = overrideInfo(CustomStack.getInstance("iasurvival:knowledge_fragment").getItemStack(),"§3值: §b§l" + defaultValue,"input.integer.number");
        number.setAmount(defaultValue);
        inv.setItem(1,number);
        inv.setItem(2,left);
        inv.setItem(3,reset);
        inv.setItem(4,right);
        inv.setItem(6,confirm);
        inv.setItem(7,cancel);
        inputMap.put(player,(InventoryClickEvent e)->{
            //End of input
            if (e == null){
                inputMap.remove(player);
                return;
            }

            e.setCancelled(true);
            switch (e.getSlot()){
                //inputs
                case 2: if(value[0]>minValue){
                    value[0]-=1;
                    overrideInfo(e.getInventory().getItem(1), "§3值: §b§l" + value[0]).setAmount(value[0]);
                }else{
                    player.playSound(player.getLocation(),Sound.ENTITY_VILLAGER_NO,1F,1F);
                }
                break;
                case 3: value[0]=defaultValue;
                    overrideInfo(e.getInventory().getItem(1), "§3值: §b§l" + value[0]).setAmount(defaultValue);
                    break;
                case 4: if(value[0]<maxValue){
                    value[0]+=1;
                    overrideInfo(e.getInventory().getItem(1), "§3值: §b§l" + value[0]).setAmount(value[0]);
                }else{
                    player.playSound(player.getLocation(),Sound.ENTITY_VILLAGER_NO,1F,1F);
                }
                    break;

                //End of input
                case 7:e.getInventory().close();
                        break;
                case 6:
                    lam.run(value[0]);
                    inputMap.remove(player);
                    e.getInventory().close();
                    break;
            }

        });

        player.openInventory(inv);

    }
    }

