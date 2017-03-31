package me.limeglass.openaudiomcskript;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class OpenAudioMcSkript extends JavaPlugin {
	
	protected static String prefix = "&8[&9OpenAudioMcScript&8]&r &f";
	private static OpenAudioMcSkript instance;
	private static SkriptAddon addonInstance;
	
	public void onEnable(){
		instance = this;
		addonInstance = Skript.registerAddon(this);
		try {
			addonInstance.loadClasses("me.limeglass.openaudiomcskript", "Elements");
		} catch (IOException e) {
			e.printStackTrace();
		}
		metrics(new Metrics(this));
		Bukkit.getConsoleSender().sendMessage(cc(prefix + "&ahas been enabled!"));
	}
	
	public static OpenAudioMcSkript getInstance() {
		return instance;
	}
	
	public static SkriptAddon getAddonInstance() {
		return addonInstance;
	}
	
	private static String cc(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	private static void metrics(Metrics metrics) {
		metrics.addCustomChart(new Metrics.SimplePie("openaudiomc_version") {
			@Override
			public String getValue() {
				return Bukkit.getPluginManager().getPlugin("OpenAudio").getDescription().getVersion();
			}
		});
	}
}
/*

*/