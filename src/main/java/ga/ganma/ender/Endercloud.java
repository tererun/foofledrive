package ga.ganma.ender;

import ga.ganma.ender.Listener.GetEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Endercloud extends JavaPlugin {

	public static Plugin ender;

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		ender = this;
		new GetEvent(this);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
