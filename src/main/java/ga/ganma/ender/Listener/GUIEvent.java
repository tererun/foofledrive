package ga.ganma.ender.Listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class GUIEvent implements Listener {
	public GUIEvent(Plugin pl){
		Bukkit.getPluginManager().registerEvents(this,pl);
	}
}
