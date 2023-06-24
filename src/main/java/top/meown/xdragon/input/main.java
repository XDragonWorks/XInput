package top.meown.xdragon.input;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.meown.xdragon.input.chest.InputListener;
import top.meown.xdragon.input.chest.IntegerInput;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Ciallo World.");
        // Plugin startup logic
        getCommand("testopen").setExecutor((commandSender, command, s, args)->{
            if (args.length != 2){return false;}

            Player p = (Player) commandSender;
            IntegerInput.get(p, (Integer v)-> commandSender.sendMessage("IV: " + v), args[0], Integer.parseInt(args[1]));

            return true;
        });

        Bukkit.getPluginManager().registerEvents(new InputListener(), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye World.");
        // Plugin shutdown logic
    }
}
