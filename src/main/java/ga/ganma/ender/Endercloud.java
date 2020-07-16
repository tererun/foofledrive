package ga.ganma.ender;

import ga.ganma.ender.Listener.GetEvent;
import ga.ganma.ender.command.CommandMain;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Endercloud extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	public static int[] configamout = new int[4];

	public static Plugin ender;

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		ender = this;
		new GetEvent(this);
		this.getCommand("ec").setExecutor(new CommandMain(this));
		saveDefaultConfig();
		configamout[0] = this.getConfig().getInt("amout.FREE");
		configamout[1] = this.getConfig().getInt("amout.LIGHT");
		configamout[2] = this.getConfig().getInt("amout.MIDDLE");
		configamout[3] = this.getConfig().getInt("amout.LARGE");
		this.setupEconomy();
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
