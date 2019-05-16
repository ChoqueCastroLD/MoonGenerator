package me.shoko.moongenerator;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class generateMoon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            WorldCreator wc = new WorldCreator("moon");

            wc.generator(new ChunkMoonGenerator());

            World w = wc.createWorld();

            w.getPopulators().add(new CraterPopulator());
            w.getPopulators().add(new OreVeinPopulator());
            w.getPopulators().add(new FloraPopulator());

            getServer().broadcastMessage("Moon generated (name: '"+w.getName()+"')");
        }


        return false;

    }
}
