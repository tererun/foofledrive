package ga.ganma.foofledrive.inventoryRelation;

import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.Foofledrive;
import ga.ganma.foofledrive.economy.Economy;
import ga.ganma.foofledrive.Plan;
import ga.ganma.foofledrive.playerdata.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class InventoryAPI {
	public static Inventory Inventorysizechange(Inventory oldInv, int setsize){
		Inventory inv = Bukkit.createInventory(null,setsize,"foofle drive");
		inv.setStorageContents(oldInv.getStorageContents());
		return inv;
	}

	public static boolean planchange(Player player, Plan plan){
		Inventory inv = null;
		ItemStack[] is = null;
		switch (plan){
			case FREE:
				inv = Bukkit.createInventory(null,9,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				if(is.length > 9){
					int a = is.length;
					List<ItemStack> isl = new ArrayList<>(Arrays.asList(is));
					isl.subList(9,a).clear();
					ItemStack[] array = new ItemStack[isl.size()];
					int i = 0;
					for (ItemStack ist : isl){
						array[i] = ist;
						i += 1;
					}
					is = array;
				}
				break;

			case LIGHT:
				inv = Bukkit.createInventory(null,18,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				if(is.length >= 19){
					int a = is.length;
					List<ItemStack> isl = new ArrayList<>(Arrays.asList(is));
					isl.subList(18,a).clear();
					ItemStack[] array = new ItemStack[isl.size()];
					int i = 0;
					for (ItemStack ist : isl){
						array[i] = ist;
						i += 1;
					}
					is = array;
				}
				break;
			case MIDDLE:
				inv = Bukkit.createInventory(null,27,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				if(is.length >= 28){
					int a = is.length;
					List<ItemStack> isl = new ArrayList<>(Arrays.asList(is));
					isl.subList(27,a).clear();
					ItemStack[] array = new ItemStack[isl.size()];
					int i = 0;
					for (ItemStack ist : isl){
						array[i] = ist;
						i += 1;
					}
					is = array;
				}
				break;

			case LARGE:
				inv = Bukkit.createInventory(null,54,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				break;
		}
		if(Foofledrive.econ.getBalance(player) >= Economy.getPlanPrice(plan)) {
			inv.setStorageContents(is);
			Playerdata pd = new Playerdata(player, inv, plan);
			pd.setFinish(Calendar.getInstance());
			Foofledrive.econ.withdrawPlayer(player, Economy.getPlanPrice(plan));
			player.sendMessage("[foofle drive]このプランの一週間の利用料金を払いました。");
			Filerelation.createFile(pd);
			return true;
		}
		else {
			player.sendMessage("[foofle drive]お金が足りないため、" + plan + "プランの契約ができませんでした。");
		}
		return false;
	}

	public static void planchange(OfflinePlayer player, Plan plan){
		Inventory inv = null;
		ItemStack[] is = null;
		switch (plan){
			case FREE:
				inv = Bukkit.createInventory(null,9,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				if(is.length > 9){
					int a = is.length;
					List<ItemStack> isl = new ArrayList<>(Arrays.asList(is));
					isl.subList(9,a).clear();
					ItemStack[] array = new ItemStack[isl.size()];
					int i = 0;
					for (ItemStack ist : isl){
						array[i] = ist;
						i += 1;
					}
					is = array;
				}
				break;

			case LIGHT:
				inv = Bukkit.createInventory(null,18,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				if(is.length >= 19){
					int a = is.length;
					List<ItemStack> isl = new ArrayList<>(Arrays.asList(is));
					isl.subList(18,a).clear();
					ItemStack[] array = new ItemStack[isl.size()];
					int i = 0;
					for (ItemStack ist : isl){
						array[i] = ist;
						i += 1;
					}
					is = array;
				}
				break;
			case MIDDLE:
				inv = Bukkit.createInventory(null,27,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				if(is.length >= 28){
					int a = is.length;
					List<ItemStack> isl = new ArrayList<>(Arrays.asList(is));
					isl.subList(27,a).clear();
					ItemStack[] array = new ItemStack[isl.size()];
					int i = 0;
					for (ItemStack ist : isl){
						array[i] = ist;
						i += 1;
					}
					is = array;
				}
				break;

			case LARGE:
				inv = Bukkit.createInventory(null,54,"foofle drive");
				is = Filerelation.readFile(player).getInv().getStorageContents();
				break;
		}
		inv.setStorageContents(is);
		Foofledrive.econ.withdrawPlayer(player,Economy.getPlanPrice(Filerelation.readFile(player).getPlan()));
		Playerdata pd = new Playerdata(player,inv,plan);
		pd.setFinish(Calendar.getInstance());
		Filerelation.createFile(pd);
	}
}
