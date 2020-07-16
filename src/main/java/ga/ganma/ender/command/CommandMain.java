package ga.ganma.ender.command;

import ga.ganma.ender.Filerelation;
import ga.ganma.ender.Plan;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

public class CommandMain implements CommandExecutor {
	private Plugin pl;

	public CommandMain(Plugin pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			pl.getLogger().log(Level.FINE, "このコマンドはコンソールからではなくプレイヤーが入力するものです。");
			return false;
		}
		if(!label.equalsIgnoreCase("ec")){
			//not ec command
			return false;
		}

		Player p = (Player) sender;
		if(args.length == 0){
			p.sendMessage("[foofle drive] /ec open でfoofle driveを開くことができます。");
			p.sendMessage("[foofle drive] /ec plan で好きなプランに加入することができます。");
			return false;
		}

		if (args[0].equalsIgnoreCase("open")) {
			new Subopen(this.pl, (Player) sender);
		} else if (args[0].equalsIgnoreCase("plan")) {
			if(args.length == 1){	//入力不足
				p.sendMessage("[foofle drive]プラン名を入力してください。");
				p.sendMessage("[foofle drive]" + Plan.getPlanNameList());
				return false;
			}

			Plan inputPlan = Plan.getPlan(args[1]);
			if(inputPlan == null){	//入力値無効
				p.sendMessage("[foofle drive]「"+ args[1] +"」は無効なプラン名です。");
				p.sendMessage("[foofle drive] 有効なプラン名を入力してください。" + Plan.getPlanNameList());
			}

			new Subplan(this.pl, p, inputPlan);
			if(inputPlan == Plan.LIGHT){
				Filerelation.readFile(p).setFinish(Calendar.getInstance());
			}
		}
		return false;
	}
}
