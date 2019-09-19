package ga.ganma.ender;

import ga.ganma.ender.playerdata.Playerdata;
import org.bukkit.entity.Player;

import java.io.*;

public class Filerelation {

	public static boolean namecheck(Player p){
		String FS = File.separator;
		File file = new File(Endercloud.ender.getDataFolder() + FS + "playerdata" + FS + p.getUniqueId().toString() + ".data");
		return file.exists();
	}

	public static void createFile(Playerdata e) throws IOException {
			String FS = File.separator;
			File folder = new File(Endercloud.ender.getDataFolder() + FS + "playerdata");
			folder.mkdir();
			File file = new File(Endercloud.ender.getDataFolder() + FS + "playerdata" + FS + e.getMcid().toString() + ".data");

			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(e);
			Endercloud.ender.getLogger().info("ファイルの作成に成功しました。");
	}
}
