package ga.ganma.foofledrive.command;

import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.Listener.GetEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Subopen {
	Plugin pl;
	public Subopen(Plugin pl, Player p){
		this.pl = pl;
		p.openInventory(Filerelation.readFile(p).getInv());
		GetEvent.isinventoryopen.put(p,true);
	}
}
