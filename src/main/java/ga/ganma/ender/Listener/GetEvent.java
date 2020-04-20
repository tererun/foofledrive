package ga.ganma.ender.Listener;

import ga.ganma.ender.Endercloud;
import ga.ganma.ender.Filerelation;
import ga.ganma.ender.inventoryRelation.InventoryAPI;
import ga.ganma.ender.plan;
import ga.ganma.ender.playerdata.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.HashMap;

public class GetEvent implements Listener {

	public static HashMap<Player, Boolean> isinventoryopen = new HashMap<>();

	public GetEvent(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler
	public void getplayerloginEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (Filerelation.namecheck(p)) {
			if (Filerelation.readFile(p).getPlan() == plan.FREE) {
				p.sendMessage("[foofle drive]あなたは現在" + Filerelation.readFile(p).getPlan() + "プランに加入しています。");
				return;
			}
			else {
				Playerdata pd = Filerelation.readFile(p);
				double bal = Endercloud.econ.getBalance(p);
				if (pd.getFinish() != null) {
					if (pd.getFinish().before(Calendar.getInstance())) {
						int[] amout = Endercloud.configamout;
						switch (pd.getPlan()) {
							case LIGHT:
								if (bal >= amout[1]) {
									Endercloud.econ.depositPlayer(p, -amout[1]);
									p.sendMessage("[foofle drive]LIGHTプランの料金を支払いました。");
								} else {
									InventoryAPI.planchange(p, plan.FREE);
									p.sendMessage("[foofle drive]お金が足りないため自動的にfreeプランへ移行しました。");
									p.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
								break;
							case MIDDLE:
								if (bal >= amout[2]) {
									Endercloud.econ.depositPlayer(p, -amout[2]);
									p.sendMessage("[foofle drive]MIDDLEプランの料金を支払いました。");
								} else {
									InventoryAPI.planchange(p, plan.FREE);
									p.sendMessage("[foofle drive]お金が足りないため自動的にfreeプランへ移行しました。");
									p.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
								break;
							case LARGE:
								if (bal >= amout[3]) {
									Endercloud.econ.depositPlayer(p, -amout[3]);
									p.sendMessage("[foofle drive]LARGEプランの料金を支払いました。");
								} else {
									InventoryAPI.planchange(p, plan.FREE);
									p.sendMessage("[foofle drive]お金が足りないため自動的にfreeプランへ移行しました。");
									p.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
								break;
						}
						return;
					}
					else {
						long diffTime = pd.getFinish().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();//[3]
						int diffDayMillis = 1000 * 60 * 60 * 24;//[4]
						int diffDays = (int) (diffTime / diffDayMillis);
						p.sendMessage("[foofle drive]支払日まであと" + diffDays + "日です。");//[5]
					}
				}
				else {
					pd.setFinish(Calendar.getInstance());
					Filerelation.createFile(pd);
					p.sendMessage("デバッグ：1");
				}
			}
		}
		else {
			Playerdata pd = new Playerdata(p, Bukkit.getServer().createInventory(null, 9, "foofle Drive"), plan.FREE);
			Filerelation.createFile(pd);
			p.sendMessage("[foofle drive]あなたは自動的に" + Filerelation.readFile(p).getPlan() + "プランに加入しました。");
		}
	}

	@EventHandler
	public void getPlayerInventoryCloseEvent(InventoryCloseEvent e) {
		Player pl = (Player) e.getPlayer();
		if(e.getView().getTitle().equals("foofle drive")){
				Playerdata pd = new Playerdata(pl,e.getView().getTopInventory(),Filerelation.readFile(pl).getPlan());
				Filerelation.createFile(pd);
		}
	}
}
