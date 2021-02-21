package ga.ganma.foofledrive;

import ga.ganma.foofledrive.Listener.GUIEvent;
import ga.ganma.foofledrive.Listener.GetEvent;
import ga.ganma.foofledrive.command.CommandMain;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public final class Foofledrive extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	public static int[] configamout = new int[4];
	public static String unit;
	public static Plugin ender;
	/** flの使用を拒否するワールド名List */
	public static List<String> denyWorld;
	/** denyWorldの制限を非OPプレイヤーに限定するか否か */
	public static boolean denyWorldOnlyNonOP;
	/** flの使用を拒否するゲームモードリスト */
	public static List<GameMode> denyGameMode;
	/** denyGameModeの制限を非OPプレイヤーに限定するか否か */
	public static boolean denyGameModeOnlyNonOP;


	public Foofledrive fl;

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		ender = this;
		new GetEvent(this);
		new GUIEvent(this);
		this.getCommand("fl").setExecutor(new CommandMain(this));
		this.getCommand("foofledrive").setExecutor(new CommandMain(this));
		this.getCommand("fl").setTabCompleter(new CommandMain(this));
		this.getCommand("foofledrive").setTabCompleter(new CommandMain(this));
		saveDefaultConfig();
		configamout[0] = this.getConfig().getInt("amout.FREE");
		configamout[1] = this.getConfig().getInt("amout.LIGHT");
		configamout[2] = this.getConfig().getInt("amout.MIDDLE");
		configamout[3] = this.getConfig().getInt("amout.LARGE");
		unit = this.getConfig().getString("unit");
		denyWorld = this.getConfig().getStringList("deny.world");
		List<String> denyGameModeConfig = this.getConfig().getStringList("deny.gamemode");
		denyGameMode = new ArrayList<>();
		for(String gm : denyGameModeConfig){	//noConfig will make list NULL
			try {
				denyGameMode.add(GameMode.getByValue(Integer.parseInt(gm)));
			}catch(IllegalArgumentException e){
				this.getLogger().warning("flConfigERROR:deny.gamemode value [" + gm +"] not a GameMode int value");
				throw e;
			}
		}
		denyGameModeOnlyNonOP = this.getConfig().getBoolean("deny.gamemodeWithoutOP", true);
		denyWorldOnlyNonOP = this.getConfig().getBoolean("deny.worldWithoutOP", true);

		if(!this.setupEconomy()){
			Bukkit.getPluginManager().disablePlugin(this);
			Bukkit.getLogger().warning("[foofle drive]Vaultが存在しません！");
			return;
		}

		String FS = File.separator;
		File folder = new File(getDataFolder() + FS + "playerdata");
		if (folder.exists()) {
			File[] list = folder.listFiles();
			if (list != null) {
				for (File f : list) {
					String name = f.getName();
					name = name.substring(0, name.lastIndexOf('.'));
					UUID id = UUID.fromString(name);
					OfflinePlayer pl = Bukkit.getOfflinePlayer(id);
					ga.ganma.foofledrive.economy.Economy.paymoney(pl);
				}

			}
		}
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			econ = economyProvider.getProvider();
		}
		return (econ != null);
	}
}
