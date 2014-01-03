package me.jdon.simple.dyn.motd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Simpdynmotd  extends JavaPlugin implements Listener {
	
	Logger log = Logger.getLogger("Minecraft.LudusTravel");
	
	public void onEnable() {
		// register events in this class
		getServer().getPluginManager().registerEvents(this, this);
		// set the config
		List<String> motds = new ArrayList<String>();
		motds.add(" ' ' ");
		this.getConfig().addDefault("motds", motds);
		this.getConfig().addDefault("motds", motds);
		this.getConfig().addDefault("Dont touch anything", "below this line!");
		this.getConfig().options().copyDefaults(true);
		// save the config
		this.saveConfig();
		// print to the console that the plugin in enabled
		log.info("[Simple Dynamic Motd] has been Enabled!");
	}
	
	
	@EventHandler
	public void ping(ServerListPingEvent ev){
		if(this.getConfig().getString(ev.getAddress().getHostAddress().replace(".", "")) != null){
			// player is saved
			int numofmotd = this.getConfig().getStringList("motds").size();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(numofmotd);
			String motd = this.getConfig().getStringList("motds").get(randomInt);
			String name = this.getConfig().getString(ev.getAddress().getHostAddress().replace(".", ""));
			String motdwithplayer = motd.replaceAll("%name%", name);
			String motdnewline = motdwithplayer.replaceAll("%newline%", "\n");
			String motdcolourcodes = ChatColor.translateAlternateColorCodes('&', motdnewline);
			ev.setMotd(motdcolourcodes);
		}else{
			ev.setMotd(ChatColor.translateAlternateColorCodes('&', this.getServer().getMotd()));
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent ev){
		String address = ev.getPlayer().getAddress().getHostString().replace(".", "");
		this.getConfig().set(address, ev.getPlayer().getName());
		this.saveConfig();
	}
	

}
