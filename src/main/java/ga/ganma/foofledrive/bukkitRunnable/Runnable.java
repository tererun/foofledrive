package ga.ganma.foofledrive.bukkitRunnable;

import ga.ganma.foofledrive.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public class Runnable extends BukkitRunnable {
	private Plugin plugin;
	public Runnable(Plugin pl){
		plugin = pl;
	}
	@Override
	public void run() {
		String FS = File.separator;
		File folder = new File(plugin.getDataFolder() + FS + "playerdata");
		if(folder.exists()) {
			File[] list = folder.listFiles();
			for (File f : list){
				if(f != null){
					String name = f.getName();
					name = name.substring(0,name.lastIndexOf('.'));
					UUID id = UUID.fromString(name);
					OfflinePlayer pl = Bukkit.getOfflinePlayer(id);
					Economy.paymoney(pl);
				}
			}

		}
	}
}
