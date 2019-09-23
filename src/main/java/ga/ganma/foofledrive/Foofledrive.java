package ga.ganma.foofledrive;

import ga.ganma.foofledrive.Listener.GUIEvent;
import ga.ganma.foofledrive.Listener.GetEvent;
import ga.ganma.foofledrive.bukkitRunnable.Runnable;
import ga.ganma.foofledrive.command.CommandMain;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Foofledrive extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	public static int[] configamout = new int[4];
	public static String unit;
	public static Plugin ender;

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		ender = this;
		new GetEvent(this);
		new GUIEvent(this);
		this.getCommand("fl").setExecutor(new CommandMain(this));
		this.getCommand("foofledrive").setExecutor(new CommandMain(this));
		saveDefaultConfig();
		configamout[0] = this.getConfig().getInt("amout.FREE");
		configamout[1] = this.getConfig().getInt("amout.LIGHT");
		configamout[2] = this.getConfig().getInt("amout.MIDDLE");
		configamout[3] = this.getConfig().getInt("amout.LARGE");
		unit = this.getConfig().getString("unit");
		this.setupEconomy();
		new Runnable(this).runTaskTimer(this,1,20);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
}
