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
import org.bukkit.command.TabCompleter;
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

public class CommandMain implements CommandExecutor, TabCompleter {
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
	
	/**
	 * Tab補完支援
	 * @param sender コマンド入力者
	 * @param command コマンド(fl または foofledrive のはず）
	 * @param alias 使用された別名（fl のはず）
	 * @param args コマンドの引数部分<br>プレイヤー入力値「/fl plan something」の<br>plan, something が配列の[0][1]にそれぞれ順に格納される
	 * @auther graycat27（MCID:gray27）
	 * @return 補完候補のList。<code>null</code>の場合あり
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!(alias.equalsIgnoreCase("fl") || alias.equalsIgnoreCase("foofledrive"))) {
			return null;
		}
		List<String> firstCommandList = new ArrayList<String>();    //fl xxx
		firstCommandList.add("open");
		firstCommandList.add("plan");
		firstCommandList.add("reload");
		firstCommandList.add("help");

		if(args.length == 0 || args[0].equals("")){
			//全候補提供
			return firstCommandList;
		}

		if(args.length == 1){
			//「/fl xx」状態
			List<String> resultList = new ArrayList<String>();
			for(String maybe : firstCommandList){
				if(maybe.toLowerCase().startsWith(args[0].toLowerCase())){
					resultList.add(maybe);
				}
			}
			return resultList;
		}

		if(args.length == 2){
			if(args[0].equals(firstCommandList.get(0)) ||	//open
			   args[0].equals(firstCommandList.get(2)) ||	//reload
			   args[0].equals(firstCommandList.get(3))){	//help
				//追加引数なし
				return new ArrayList<String>();
			}
			if(args[0].equals(firstCommandList.get(1))){
				//「/fl plan xx」状態
				List<String> resultList = new ArrayList<String>();
				for (plan pln : plan.values()) {	//planはenum
					if(args[1].equals("") || 
					   pln.toString().toLowerCase().startsWith(args[1].toLowerCase())){
						//xx部分未入力or入力内容に合致する候補
						resultList.add(pln.toString());
					}
				}
				return resultList;
			}
		}
		return new ArrayList<String>();	//for safe
	}
}
