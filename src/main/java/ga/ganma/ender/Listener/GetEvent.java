package ga.ganma.ender.Listener;

import ga.ganma.ender.Filerelation;
import ga.ganma.ender.plan;
import ga.ganma.ender.playerdata.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class GetEvent implements Listener {

	public GetEvent(Plugin pl){
		Bukkit.getPluginManager().registerEvents(this,pl);
	}
	@EventHandler
	public void getplayerloginEvent(PlayerJoinEvent e) throws IOException {
		Player p = e.getPlayer();
		if(Filerelation.namecheck(p)){
			return;
		}
		Playerdata pd = new Playerdata(p, Bukkit.getServer().createInventory(null, 18, "foofle Drive"), plan.FREE);
		Filerelation.createFile(pd);

	}
}
