package ga.ganma.foofledrive.economy;

import ga.ganma.foofledrive.Foofledrive;
import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.inventoryRelation.InventoryAPI;
import ga.ganma.foofledrive.plan;
import ga.ganma.foofledrive.playerdata.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.logging.Level;

public class Economy {
	public static void paymoney(Player p) {
		Playerdata pd = Filerelation.readFile(p);
		if (Foofledrive.econ.hasAccount(p)) {
			double bal = Foofledrive.econ.getBalance(p);
			if (pd.getFinish() != null) {
				if (pd.getFinish().before(Calendar.getInstance())) {
					switch (pd.getPlan()) {
						case LIGHT:
							if (bal >= getplanmoney(plan.LIGHT)) {
								Foofledrive.econ.withdrawPlayer(p, getplanmoney(plan.LIGHT));
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のLIGHTプランの料金を支払いました。");
								if (p.isOnline()) {
									p.sendMessage("[foofle drive]料金の支払いをしました。");
								}
								pd.setFinish(Calendar.getInstance());
								Filerelation.createFile(pd);
							} else {
								InventoryAPI.planchange(p, plan.FREE);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のお金が足りないため、自動的にfreeプランへ移行しました。");
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								if (p.isOnline()) {
									p.sendMessage("[foofle drive]お金が足りないため、自動的にfreeプランへ移行しました。");
									p.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
							}
							break;
						case MIDDLE:
							if (bal >= getplanmoney(plan.MIDDLE)) {
								Foofledrive.econ.withdrawPlayer(p, getplanmoney(plan.MIDDLE));
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のMIDDLEプランの料金を支払いました。");
								if (p.isOnline()) {
									p.sendMessage("[foofle drive]料金の支払いをしました。");
								}
								pd.setFinish(Calendar.getInstance());
								Filerelation.createFile(pd);
							} else {
								InventoryAPI.planchange(p, plan.FREE);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のお金が足りないため、自動的にfreeプランへ移行しました。");
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								if (p.isOnline()) {
									p.sendMessage("[foofle drive]お金が足りないため、自動的にfreeプランへ移行しました。");
									p.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
							}
							break;
						case LARGE:
							if (bal >= getplanmoney(plan.LARGE)) {
								Foofledrive.econ.withdrawPlayer(p, getplanmoney(plan.LARGE));
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のLARGEプランの料金を支払いました。");
								if (p.isOnline()) {
									p.sendMessage("[foofle drive]料金の支払いをしました。");
								}
								pd.setFinish(Calendar.getInstance());
								Filerelation.createFile(pd);
							} else {
								InventoryAPI.planchange(p, plan.FREE);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のお金が足りないため、自動的にfreeプランへ移行しました。");
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								if (p.isOnline()) {
									p.sendMessage("[foofle drive]お金が足りないため、自動的にfreeプランへ移行しました。");
									p.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
							}
							break;
					}
				}
			}
		}
	}

	public static void paymoney(OfflinePlayer p) {
		Playerdata pd = Filerelation.readFile(p);
		if (p != null){
			double bal = Foofledrive.econ.getBalance(p);
			if (pd.getFinish() != null) {
				if (pd.getFinish().before(Calendar.getInstance())) {
					int[] amout = Foofledrive.configamout;
					switch (pd.getPlan()) {
						case LIGHT:
							if (bal >= amout[1]) {
								Foofledrive.econ.withdrawPlayer(p, amout[1]);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のLIGHTプランの料金を支払いました。");
								if (p.isOnline()) {
									Player pl = (Player) p;
									pl.sendMessage("[foofle drive]料金の支払いをしました。");
								}
								pd.setFinish(Calendar.getInstance());
								Filerelation.createFile(pd);
							} else {
								InventoryAPI.planchange(p, plan.FREE);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のお金が足りないため、自動的にfreeプランへ移行しました。");
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								if (p.isOnline()) {
									Player pl = (Player) p;
									pl.sendMessage("[foofle drive]お金が足りないため、自動的にfreeプランへ移行しました。");
									pl.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
							}
							break;
						case MIDDLE:
							if (bal >= amout[2]) {
								Foofledrive.econ.withdrawPlayer(p, amout[2]);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のMIDDLEプランの料金を支払いました。");
								if (p.isOnline()) {
									Player pl = (Player) p;
									pl.sendMessage("[foofle drive]料金の支払いをしました。");
								}
								pd.setFinish(Calendar.getInstance());
								Filerelation.createFile(pd);
							} else {
								InventoryAPI.planchange(p, plan.FREE);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のお金が足りないため、自動的にfreeプランへ移行しました。");
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								if (p.isOnline()) {
									Player pl = (Player) p;
									pl.sendMessage("[foofle drive]お金が足りないため、自動的にfreeプランへ移行しました。");
									pl.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
							}
							break;
						case LARGE:
							if (bal >= amout[3]) {
								Foofledrive.econ.withdrawPlayer(p, amout[3]);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のLARGEプランの料金を支払いました。");
								if (p.isOnline()) {
									Player pl = (Player) p;
									pl.sendMessage("[foofle drive]料金の支払いをしました。");
								}
								pd.setFinish(Calendar.getInstance());
								Filerelation.createFile(pd);
							} else {
								InventoryAPI.planchange(p, plan.FREE);
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]" + p.getName() + "のお金が足りないため、自動的にfreeプランへ移行しました。");
								Bukkit.getLogger().log(Level.INFO, "[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								if (p.isOnline()) {
									Player pl = (Player) p;
									pl.sendMessage("[foofle drive]お金が足りないため、自動的にfreeプランへ移行しました。");
									pl.sendMessage("[foofle drive]その際、2段目以降にあるアイテムを全消去しました。");
								}
							}
							break;
					}
				}
			}
		}
	}

	public static int getplanmoney(plan plan){
		switch (plan){
			case FREE:
				return Foofledrive.configamout[0];
			case LIGHT:
				return Foofledrive.configamout[1];
			case MIDDLE:
				return Foofledrive.configamout[2];
			case LARGE:
				return Foofledrive.configamout[3];
		}

		return 0;
	}
}