package ga.ganma.foofledrive.command;

import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.Foofledrive;
import ga.ganma.foofledrive.economy.Economy;
import ga.ganma.foofledrive.plan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class CommandMain implements CommandExecutor {
	public static HashMap<Player,Boolean> isopenInventory = new HashMap<>();
	private Plugin pl;

	public CommandMain(Plugin pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("fl") || label.equalsIgnoreCase("foofledrive")) {
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("open")) {
						new Subopen(this.pl, (Player) sender);
					} else if (args[0].equalsIgnoreCase("plan")) {
						if (args.length > 1) {
							if (args[1].equalsIgnoreCase("LIGHT")) {
								new Subplan(this.pl, p, plan.LIGHT);
								Filerelation.readFile(p).setFinish(Calendar.getInstance());
							} else if (args[1].equalsIgnoreCase("FREE")) {
								new Subplan(this.pl, p, plan.FREE);
							}else if (args[1].equalsIgnoreCase("MIDDLE")) {
								new Subplan(this.pl, p, plan.MIDDLE);
							}else if (args[1].equalsIgnoreCase("LARGE")) {
								new Subplan(this.pl, p, plan.LARGE);
							}
						} else {
							Inventory inv = Bukkit.createInventory(null,27,"プラン選択画面");
							ItemStack is = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE,1);
							ItemMeta im0 = is.getItemMeta();
							im0.setDisplayName(" ");
							ItemStack free = new ItemStack(Material.PAPER,1);
							ItemMeta im1 = free.getItemMeta();
							im1.setDisplayName("FREEプラン");
							List<String> freeprice = new ArrayList<>();
							freeprice.add(Economy.getplanmoney(plan.FREE) + Foofledrive.unit);
							im1.setLore(freeprice);
							ItemStack light = new ItemStack(Material.IRON_INGOT,1);
							ItemMeta im2 = light.getItemMeta();
							im2.setDisplayName("LIGHTプラン");
							List<String> lightprice = new ArrayList<>();
							lightprice.add(Economy.getplanmoney(plan.LIGHT) + Foofledrive.unit);
							im2.setLore(lightprice);
							ItemStack middle = new ItemStack(Material.GOLD_INGOT,1);
							ItemMeta im3 = middle.getItemMeta();
							im3.setDisplayName("MIDDLEプラン");
							List<String> middleprice = new ArrayList<>();
							middleprice.add(Economy.getplanmoney(plan.MIDDLE) + Foofledrive.unit);
							im3.setLore(middleprice);
							ItemStack large = new ItemStack(Material.DIAMOND,1);
							ItemMeta im4 = middle.getItemMeta();
							im4.setDisplayName("LARGEプラン");
							List<String> largeprice = new ArrayList<>();
							largeprice.add(Economy.getplanmoney(plan.LARGE) + Foofledrive.unit);
							im4.setLore(largeprice);

							is.setItemMeta(im0);
							free.setItemMeta(im1);
							light.setItemMeta(im2);
							middle.setItemMeta(im3);
							large.setItemMeta(im4);

							for(int a = 0;a <= 26;a++) {
								if(a == 10){
									inv.setItem(a,free);
								}
								else if (a == 12){
									inv.setItem(a,light);
								}
								else if(a == 14){
									inv.setItem(a,middle);
								}
								else if(a == 16){
									inv.setItem(a,large);
								}
								else {
									inv.setItem(a,is);
								}
							}
							isopenInventory.put(p,true);
							p.openInventory(inv);
						}
					}
					else if(args[0].equalsIgnoreCase("reload")) {
						if (p.isOp()) {
							pl.reloadConfig();
							Foofledrive.configamout[0] = pl.getConfig().getInt("amout.FREE");
							Foofledrive.configamout[1] = pl.getConfig().getInt("amout.LIGHT");
							Foofledrive.configamout[2] = pl.getConfig().getInt("amout.MIDDLE");
							Foofledrive.configamout[3] = pl.getConfig().getInt("amout.LARGE");
							Foofledrive.unit = pl.getConfig().getString("unit");
							p.sendMessage("[foofle drive]コンフィグをリロードしました。");
						}
						else {
							p.sendMessage("[foofle drive]このコマンドは管理者専用です。");
						}
					}
					else if(args[0].equalsIgnoreCase("help")){
						p.sendMessage("[free drive] /fl open でfoofle driveを開くことができます。");
						p.sendMessage("[free drive] /fl plan で好きなプランに加入することができます。");
					}
				}
				else {
					p.sendMessage("[free drive] /fl open でfoofle driveを開くことができます。");
					p.sendMessage("[free drive] /fl plan で好きなプランに加入することができます。");
				}
			}
		}
		else {
			pl.getLogger().log(Level.INFO, "このコマンドはコンソールからではなくプレイヤーが入力するものです。");
		}
		return false;
	}
}
