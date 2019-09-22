package ga.ganma.ender.command;

import ga.ganma.ender.Filerelation;
import ga.ganma.ender.plan;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;

public class CommandMain implements CommandExecutor {
	private Plugin pl;

	public CommandMain(Plugin pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("ec")) {
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
							p.sendMessage("[foofle drive]プラン名を入力してください。");
							p.sendMessage("[foofle drive]FREE,LIGHT,MIDDLE,LARGE");
						}
					}
				}
				else {
					p.sendMessage("[free drive] /ec open でfoofle driveを開くことができます。");
					p.sendMessage("[free drive] /ec plan で好きなプランに加入することができます。");
				}
			}
		}
		else {
			pl.getLogger().log(Level.FINE, "このコマンドはコンソールからではなくプレイヤーが入力するものです。");
		}
		return false;
	}
}
