package ga.ganma.ender.command;

import ga.ganma.ender.Filerelation;
import ga.ganma.ender.Plan;
import ga.ganma.ender.inventoryRelation.InventoryAPI;
import ga.ganma.ender.playerdata.Playerdata;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;

public class Subplan {
	Plugin pl;
	Player p;
	public Subplan(Plugin pl, Player player, Plan plan){
		this.pl = pl;
		p = player;
		InventoryAPI.planchange(player,plan);
		Playerdata pd = Filerelation.readFile(p);
		pd.setFinish(Calendar.getInstance());
		Filerelation.createFile(pd);
		p.sendMessage("[foorle drive]プランを" + plan + "プランに変更しました。");
	}
}
