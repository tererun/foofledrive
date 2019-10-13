package ga.ganma.foofledrive.Listener;

import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.command.CommandMain;
import ga.ganma.foofledrive.inventoryRelation.InventoryAPI;
import ga.ganma.foofledrive.plan;
import ga.ganma.foofledrive.playerdata.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.HashMap;

public class GUIEvent implements Listener {

	private static HashMap<Player, ga.ganma.foofledrive.plan> ProvisionalPlan = new HashMap<>();

	public GUIEvent(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler
	public void getInventoryclickEvent(InventoryClickEvent e) {
		if (e.getWhoClicked() != null) {
			Player pl = (Player) e.getWhoClicked();
			if (CommandMain.isopenInventory.containsKey(pl)) {
				if (CommandMain.isopenInventory.get(pl)) {
					ItemStack clickedItem = e.getCurrentItem();
					Inventory clickedInventory = e.getClickedInventory();
					if (clickedItem == null || clickedInventory == null) {
						return;
					}
					if (! clickedItem.hasItemMeta()) {
						return;
					}
					if (e.getView().getType() != InventoryType.CREATIVE && e.getView().getTitle().contains("プラン選択画面")) {
						if (e.getSlot() == 10) {
							e.setCancelled(true);
							pl.openInventory(yesornoInv());
							ProvisionalPlan.put(pl, plan.FREE);
						} else if (e.getSlot() == 12) {
							e.setCancelled(true);
							pl.openInventory(yesornoInv());
							ProvisionalPlan.put(pl, plan.LIGHT);
						} else if (e.getSlot() == 14) {
							e.setCancelled(true);
							pl.openInventory(yesornoInv());
							ProvisionalPlan.put(pl, plan.MIDDLE);
						} else if (e.getSlot() == 16) {
							e.setCancelled(true);
							pl.openInventory(yesornoInv());
							ProvisionalPlan.put(pl, plan.LARGE);
						} else if (clickedItem.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
							e.setCancelled(true);
						}
					} else if (e.getView().getTitle().contains("契約してよろしいですか？")) {
						if (e.getSlot() == 15) {
							e.setCancelled(true);
							Playerdata pd;
							switch (ProvisionalPlan.get(pl)) {
								case FREE:
									InventoryAPI.planchange(pl, plan.FREE);
									pd = Filerelation.readFile(pl);
									if (pd.getFinish() == null) {
										pd.setFinish(Calendar.getInstance());
										Filerelation.createFile(pd);
									}
									pl.sendMessage("[foofle drive]プランを" + plan.FREE + "プランに変更しました。");
									break;

								case LIGHT:
									boolean is = InventoryAPI.planchange(pl, plan.LIGHT);
									pd = Filerelation.readFile(pl);
									if (pd.getFinish() == null) {
										pd.setFinish(Calendar.getInstance());
										Filerelation.createFile(pd);
									}
									if (is) {
										pl.sendMessage("[foofle drive]プランを" + plan.LIGHT + "プランに変更しました。");
									}
									break;

								case MIDDLE:
									boolean is1 = InventoryAPI.planchange(pl, plan.MIDDLE);
									pd = Filerelation.readFile(pl);
									if (pd.getFinish() == null) {
										pd.setFinish(Calendar.getInstance());
										Filerelation.createFile(pd);
									}
									if (is1) {
										pl.sendMessage("[foofle drive]プランを" + plan.MIDDLE + "プランに変更しました。");
									}
									break;

								case LARGE:
									boolean is2 = InventoryAPI.planchange(pl, plan.LARGE);
									pd = Filerelation.readFile(pl);
									if (pd.getFinish() == null) {
										pd.setFinish(Calendar.getInstance());
										Filerelation.createFile(pd);
									}
									if (is2) {
										pl.sendMessage("[foofle drive]プランを" + plan.LARGE + "プランに変更しました。");
									}
									break;
							}
							pl.closeInventory();
						} else if (e.getSlot() == 11) {
							e.setCancelled(true);
							e.getWhoClicked().closeInventory();
						} else if (clickedItem.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

	private Inventory yesornoInv(){
		Inventory inv = Bukkit.createInventory(null,27,"契約してよろしいですか？");
		ItemStack is = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE,1);
		ItemMeta im0 = is.getItemMeta();
		im0.setDisplayName(" ");
		ItemStack is1 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE,1);
		ItemMeta im1 = is1.getItemMeta();
		im1.setDisplayName("はい");
		ItemStack is2 = new ItemStack(Material.RED_STAINED_GLASS_PANE,1);
		ItemMeta im2 = is1.getItemMeta();
		im2.setDisplayName("いいえ");

		is.setItemMeta(im0);
		is1.setItemMeta(im1);
		is2.setItemMeta(im2);

		for(int a = 0 ; a<= 26 ; a++){
			if(a == 11){
				inv.setItem(a,is2);
			}
			else if(a == 15){
				inv.setItem(a,is1);
			}
			else {
				inv.setItem(a,is);
			}
		}
		return inv;
	}
}
