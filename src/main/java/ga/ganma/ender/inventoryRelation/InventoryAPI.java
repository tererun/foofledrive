package ga.ganma.ender.inventoryRelation;

import ga.ganma.ender.Filerelation;
import ga.ganma.ender.plan;
import ga.ganma.ender.playerdata.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryAPI {
	public static Inventory Inventorysizechange(Inventory oldInv, int setsize){
		Inventory inv = Bukkit.createInventory(null,setsize,"foofle drive");
		inv.setStorageContents(oldInv.getStorageContents());
		return inv;
	}

	public static void planchange(Player player, plan plan){
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
		Playerdata pd = new Playerdata(player,inv,plan);
		Filerelation.createFile(pd);
	}
}
