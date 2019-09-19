package ga.ganma.ender.playerdata;

import ga.ganma.ender.InventorytoString;
import ga.ganma.ender.plan;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Playerdata implements Serializable {
	private UUID mcid;
	private plan plan;
	private String inventorySt;

	public Playerdata(Player pl, Inventory inv, plan plan){
		mcid = pl.getUniqueId();
		this.plan = plan;
		inventorySt = InventorytoString.toBase64(inv);
	}

	public ga.ganma.ender.plan getPlan() {
		return plan;
	}

	public UUID getMcid() {
		return mcid;
	}

	public Inventory getInv() throws IOException {
		return InventorytoString.fromBase64(inventorySt);
	}
}
