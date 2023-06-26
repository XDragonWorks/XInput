package top.meown.xdragon.input.chest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.meown.xdragon.core.utils.lambda.Parameter1;
import top.meown.xdragon.core.utils.lambda.Parameter2;

import java.util.HashMap;
import java.util.Optional;

public class InputListener implements Listener {
    protected static HashMap<Player,Parameter1<InventoryClickEvent>> inputMap = new HashMap<>();
    @EventHandler
    public void onInventoryClick (InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){ //check if the HumanEntity is a Player
            Player p = (Player) e.getWhoClicked(); //cast the HumanEntity as Player
            Parameter1<InventoryClickEvent> parameter1 = inputMap.get(p);
            if(parameter1 != null){
                parameter1.run(e);
            }
        }

    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        inputMap.remove(e.getPlayer());
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
            Player p = (Player) e.getPlayer();
            Parameter1<InventoryClickEvent> parameter1 = inputMap.get(p);
            if(parameter1 != null){
                parameter1.run(null);
            }
    }
}
