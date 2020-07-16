package ga.ganma.foofledrive.inventoryRelation;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class InventoryEncoder {
	public static String inventoryToString(Inventory inventory) {
		try {
			ByteArrayOutputStream str = new ByteArrayOutputStream();
			BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
			data.writeInt(inventory.getSize());
			for (int i = 0; i < inventory.getSize(); i++) {
				data.writeObject(inventory.getItem(i));
			}
			data.close();
			return Base64.getEncoder().encodeToString(str.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Inventory stringToInventory(String inventoryData) {
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
			BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
			Inventory inventory = Bukkit.createInventory(null, data.readInt(), "foofle drive");
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) data.readObject());
			}
			data.close();
			return inventory;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
