package ga.ganma.foofledrive.command;

import ga.ganma.foofledrive.Filerelation;
import ga.ganma.foofledrive.Foofledrive;
import ga.ganma.foofledrive.economy.Economy;
import ga.ganma.foofledrive.Plan;
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

	private void sendFoofleDriveMessage(Player p, String msg){
		p.sendMessage("[foofle drive]" + msg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			pl.getLogger().log(Level.INFO, "このコマンドはコンソールからではなくプレイヤーが入力するものです。");
			return false;
		}
		if(!(label.equalsIgnoreCase("fl") || label.equalsIgnoreCase("foofledrive"))){
			//対象外コマンド
			return false;
		}

		Player p = (Player) sender;
		if(args.length == 0){
			sendFoofleDriveMessage(p, " /fl open でfoofle driveを開くことができます。");
			sendFoofleDriveMessage(p, " /fl plan で好きなプランに加入することができます。");
			return false;
		}

		if (args[0].equalsIgnoreCase("open")) {
			new Subopen(this.pl, (Player) sender);
			return false;
		}
		if (args[0].equalsIgnoreCase("plan")) {
			if(args.length == 1){
				//プラン選択GUI表示
				showPlanSelect(p);
			} else {
				Plan plan = Plan.getPlan(args[1]);
				if(plan == null){
					sendFoofleDriveMessage(p, " 「"+ args[1] +"」は無効なプラン名です。");
					sendFoofleDriveMessage(p, "有効なプラン名を入力してください。" + Plan.getPlanNameList());
					return false;
				}

				new Subplan(this.pl, p, plan);
				if(plan == Plan.LIGHT){
					Filerelation.readFile(p).setFinish(Calendar.getInstance());
				}
			}
			return false;
		}
		if(args[0].equalsIgnoreCase("reload")) {
			if (p.isOp()) {
				pl.reloadConfig();
				Foofledrive.configamout[0] = pl.getConfig().getInt("amout.FREE");
				Foofledrive.configamout[1] = pl.getConfig().getInt("amout.LIGHT");
				Foofledrive.configamout[2] = pl.getConfig().getInt("amout.MIDDLE");
				Foofledrive.configamout[3] = pl.getConfig().getInt("amout.LARGE");
				Foofledrive.unit = pl.getConfig().getString("unit");
				sendFoofleDriveMessage(p, "コンフィグをリロードしました。");
			}
			else {
				sendFoofleDriveMessage(p, "このコマンドは管理者専用です。");
			}
			return false;
		}
		if(args[0].equalsIgnoreCase("help")){
			sendFoofleDriveMessage(p, "/fl open でfoofle driveを開くことができます。");
			sendFoofleDriveMessage(p, "/fl plan で好きなプランに加入することができます。");
			return false;
		}

		return false;
	}

	private void showPlanSelect(Player p){
		Inventory inv = Bukkit.createInventory(null,27,"プラン選択画面");

		ItemStack is = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE,1);
		ItemMeta im0 = is.getItemMeta();
		im0.setDisplayName(" ");
		is.setItemMeta(im0);

		ItemStack free = new ItemStack(Material.PAPER,1);
		ItemMeta im1 = free.getItemMeta();
		im1.setDisplayName("FREEプラン");
		List<String> freePrice = new ArrayList<>();
		freePrice.add(Economy.getPlanPrice(Plan.FREE) + Foofledrive.unit);
		im1.setLore(freePrice);
		free.setItemMeta(im1);

		ItemStack light = new ItemStack(Material.IRON_INGOT,1);
		ItemMeta im2 = light.getItemMeta();
		im2.setDisplayName("LIGHTプラン");
		List<String> lightPrice = new ArrayList<>();
		lightPrice.add(Economy.getPlanPrice(Plan.LIGHT) + Foofledrive.unit);
		im2.setLore(lightPrice);
		light.setItemMeta(im2);

		ItemStack middle = new ItemStack(Material.GOLD_INGOT,1);
		ItemMeta im3 = middle.getItemMeta();
		im3.setDisplayName("MIDDLEプラン");
		List<String> middlePrice = new ArrayList<>();
		middlePrice.add(Economy.getPlanPrice(Plan.MIDDLE) + Foofledrive.unit);
		im3.setLore(middlePrice);
		middle.setItemMeta(im3);

		ItemStack large = new ItemStack(Material.DIAMOND,1);
		ItemMeta im4 = middle.getItemMeta();
		im4.setDisplayName("LARGEプラン");
		List<String> largePrice = new ArrayList<>();
		largePrice.add(Economy.getPlanPrice(Plan.LARGE) + Foofledrive.unit);
		im4.setLore(largePrice);
		large.setItemMeta(im4);

		for(int a = 0;a <= 26;a++) {
			if(a == 10){
				inv.setItem(a,free);
				continue;
			}
			if (a == 12){
				inv.setItem(a,light);
				continue;
			}
			if(a == 14){
				inv.setItem(a,middle);
				continue;
			}
			if(a == 16){
				inv.setItem(a,large);
				continue;
			}
			inv.setItem(a,is);
		}
		isopenInventory.put(p,true);
		p.openInventory(inv);
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
			   args[0].equals(firstCommandList.get(2)) ||   //reload
               args[0].equals(firstCommandList.get(3)) ){   //help
				//追加引数なし
				return new ArrayList<String>();
			}
			if(args[0].equals(firstCommandList.get(1))){
				//「/fl plan xx」状態
				List<String> resultList = new ArrayList<String>();
				for (Plan pln : Plan.values()) {	//planはenum
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
