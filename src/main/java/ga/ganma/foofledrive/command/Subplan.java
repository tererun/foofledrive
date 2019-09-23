package ga.ganma.foofledrive.command;

import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.inventoryRelation.InventoryAPI;
import ga.ganma.foofledrive.plan;
import ga.ganma.foofledrive.playerdata.Playerdata;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;

public class Subplan {
	Plugin pl;
	Player p;
	public Subplan(Plugin pl, Player player, plan plan){
		this.pl = pl;
		p = player;
		boolean issuccess;
		issuccess = InventoryAPI.planchange(player,plan);
		Playerdata pd = Filerelation.readFile(p);
		if(pd.getFinish() == null) {
			pd.setFinish(Calendar.getInstance());
			Filerelation.createFile(pd);
		}

		if(issuccess) {
			p.sendMessage("[foofle drive]プランを" + plan + "プランに変更しました。");
		}
	}
}
