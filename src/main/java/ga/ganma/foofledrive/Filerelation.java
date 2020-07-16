package ga.ganma.foofledrive;

import ga.ganma.foofledrive.playerdata.Playerdata;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.logging.Level;

public class Filerelation {

	public static boolean namecheck(Player p){
		String FS = File.separator;
		File file = new File(Foofledrive.ender.getDataFolder() + FS + "playerdata" + FS + p.getUniqueId().toString() + ".dat");
		return file.exists();
	}

	public static boolean namecheck(OfflinePlayer offp){
		String FS = File.separator;
		File file = new File(Foofledrive.ender.getDataFolder() + FS + "playerdata" + FS + offp.getUniqueId().toString() + ".dat");
		return file.exists();
	}

	public static void createFile(Playerdata e){
		try {
			String FS = File.separator;
			File folder = new File(Foofledrive.ender.getDataFolder() + FS + "playerdata");
			folder.mkdir();
			File file = new File(Foofledrive.ender.getDataFolder() + FS + "playerdata" + FS + e.getMcid().toString() + ".dat");

			if(file.exists()) {
				file.delete();
			}
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(e);
				oos.close();

		}
		catch (IOException io){
			Foofledrive.ender.getLogger().log(Level.SEVERE, "ファイルの作成に失敗しました。");
		}
	}

	public static Playerdata readFile(Player p) {
		Playerdata pd = null;
		if(!namecheck(p)){
			return null;
		}
		try {
			String FS = File.separator;
			File file = new File(Foofledrive.ender.getDataFolder() + FS + "playerdata" + FS + p.getUniqueId().toString() + ".dat");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			pd =  (Playerdata) ois.readObject();
			ois.close();
		}
		catch (IOException e){
			Foofledrive.ender.getLogger().log(Level.SEVERE, "ファイルの読み取りに失敗しました。");
		}
		catch (ClassNotFoundException e){
			Foofledrive.ender.getLogger().log(Level.SEVERE, "内部エラーが発生しました。製作者に次のエラーコードを伝えてください。");
			Foofledrive.ender.getLogger().log(Level.SEVERE, "File error：01");
		}
		return pd;
	}

	public static Playerdata readFile(OfflinePlayer offp) {
		Playerdata pd = null;
		if(!namecheck(offp)){
			return null;
		}
		try {
			String FS = File.separator;
			File file = new File(Foofledrive.ender.getDataFolder() + FS + "playerdata" + FS + offp.getUniqueId().toString() + ".dat");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			pd =  (Playerdata) ois.readObject();
			ois.close();
		}
		catch (IOException e){
			Foofledrive.ender.getLogger().log(Level.SEVERE, "ファイルの読み取りに失敗しました。");
		}
		catch (ClassNotFoundException e){
			Foofledrive.ender.getLogger().log(Level.SEVERE, "内部エラーが発生しました。製作者に次のエラーコードを伝えてください。");
			Foofledrive.ender.getLogger().log(Level.SEVERE, "File error：01");
		}
		return pd;
	}
}
