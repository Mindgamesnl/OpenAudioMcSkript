package me.limeglass.openaudiomcskript;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;

public class OpenAudioMcSkript extends JavaPlugin {
	
	protected static String prefix = "&8[&9OpenAudioMcScript&8]&r &f";
	private FileConfiguration config = getConfig();
	protected static File configFile;
	private static OpenAudioMcSkript instance;
	
	public void onEnable(){
		configFile = new File(getDataFolder(), "config.yml");
		if (!Objects.equals(getDescription().getVersion(), config.getString("version", getDescription().getVersion()))) {
			if (configFile.exists()) {
				configFile.delete();
			}
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eNew update found! Updating config."));
		}
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cconfig.yml not found, generating a new config!"));
				config.set("version", getDescription().getVersion());
				config.set("debug", false);
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
		instance = this;
		Skript.registerAddon(this);
		Register.metrics(new Metrics(this));
		Register.main();
		consoleMessage("&aHas been enabled!");
	}
	
	public static OpenAudioMcSkript getInstance() {
		return instance;
	}
	
	public static void saveMainConfig() {
		try {
			instance.getConfig().save(OpenAudioMcSkript.configFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String cc(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static void debugMessage(String text) {
		if (instance.getConfig().getBoolean("debug")) {
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&e" + text));
		}
	}
	
	public static void consoleMessage(String text) {
		Bukkit.getConsoleSender().sendMessage(cc(prefix + text));
	}
}
/*

*/