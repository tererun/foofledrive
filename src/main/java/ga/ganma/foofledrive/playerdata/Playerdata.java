package ga.ganma.foofledrive.playerdata;

import ga.ganma.foofledrive.inventoryRelation.InventoryEncoder;
import ga.ganma.foofledrive.plan;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class Playerdata implements Serializable {
	private UUID mcid;
	private plan plan;
	private String inventorySt;
	private Calendar finish;

	public Playerdata(Player pl, Inventory inv, plan plan) {
		this.mcid = pl.getUniqueId();
		this.plan = plan;
		this.inventorySt = InventoryEncoder.inventoryToString(inv);
	}

	public Playerdata(OfflinePlayer pl, Inventory inv, plan plan) {
		this.mcid = pl.getUniqueId();
		this.plan = plan;
		this.inventorySt = InventoryEncoder.inventoryToString(inv);
	}

	public ga.ganma.foofledrive.plan getPlan() {
		return plan;
	}

	public UUID getMcid() {
		return mcid;
	}

	public Inventory getInv() {
		return InventoryEncoder.stringToInventory(inventorySt);
	}

	public void setFinish(Calendar cl){
		cl.add(Calendar.DAY_OF_MONTH,+7);
		finish = cl;
	}

	public Calendar getFinish(){
		return finish;
	}
}
