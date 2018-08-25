package de.darkztv.engineers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.minecraft.server.v1_7_R4.EntityPlayer;

public class Main extends JavaPlugin implements Listener{
	public static String prefix = "§5§l•●Minecraft - Engineers●•";
	public static String online = "§8[§a§l+§8] §8";
	public static String offline = "§8[§c§l-§8] §8";
	public static String Mitglied = "§5§l";
	public static String welt = "world";
	
	public static boolean wartung = false;
	public static boolean pvp = false;
	public static boolean rec = false;
	

	@EventHandler
	@Override
	public void onEnable() {
		saveConfig();
		wartung = getConfig().get("wartung.Status") != null ? getConfig().getBoolean("wartung.Status") : false;
		pvp = getConfig().get("pvp.Status") != null ? getConfig().getBoolean("pvp.Status") : false;
		rec = getConfig().get("rec.Status") != null ? getConfig().getBoolean("rec.Status") : false;
		
		this.getServer().getPluginManager().registerEvents(this, this);
		/*for(Player players : Bukkit.getOnlinePlayers()){
            updateScoreboard(players);
    }*/
		if(pvp = true) {
			Bukkit.getWorld(welt).setPVP(false);
		}else {
			Bukkit.getWorld(welt).setPVP(true);
		}
		
		System.out.println(prefix + "§aDas Plugin wurde aktiviert!") ;
		System.out.println("================================");
		System.out.println("-----------Engineers------------");
		System.out.println("----------Version: 1.0----------");
		System.out.println("-------Author:DarkZelotes-------");
		System.out.println("------Hosted by DarkZTV.de------");
		System.out.println("================================");
		}
	
   /* public void updateScoreboard(Player player){
        int Kills = 0;
        int Deaths = 0;
       
        sendScoreboard(player, Kills, Deaths);
}*/
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(wartung) {
			if(!e.getPlayer().isOp()) {
				e.disallow(Result.KICK_OTHER, "§5§l•●Minecraft Engineers●•\n\n    §4•●Wir sind in Wartung●•");
			}
			
		}
		
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		if(wartung) {
			e.setMaxPlayers(0);
			e.setMotd("§5§l•●Minecraft - Engineers●•   §7[§4Wartungsmodus§7]\n§cGrund: §eBugfixes und Updates");
		} else {
			e.setMaxPlayers(30);
			e.setMotd("§5§l•●Minecraft - Engineers●•   §7[§aWhitelist§7]\n§9Hosted by DarkZNetwork");
		}		
	}
	

	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player p = (Player)sender;
		if(command.getName().equalsIgnoreCase("wartung")) {
			
			if(sender.isOp()) {
				
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("on")) {
						wartung = true;
						sender.sendMessage(prefix + "§aDer Wartungsmodus wurde aktiviert.");
					}
						
					}
					if(args[0].equalsIgnoreCase("off")) {
						wartung = false;
						sender.sendMessage(prefix + "§cDer Wartungsmodus wurde deaktiviert.");
				}
					getConfig().set("wartung.Status", wartung);
					saveConfig();
				return false;
			}else {
				sender.sendMessage(prefix + "§cDu hast keine Rechte um diesen Befehl auszuführen.");
			}
			return false;
		}
		
		if(command.getName().equalsIgnoreCase("pvp")) {
			
			if(sender.isOp()) {
				
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("on")) {
						pvp = true;
						Bukkit.getWorld(welt).setPVP(true);
						sender.sendMessage(prefix + "§aDer PvP-Modus wurde aktiviert.");
					}
						
					}
					if(args[0].equalsIgnoreCase("off")) {
						pvp = false;
						Bukkit.getWorld(welt).setPVP(false);
						sender.sendMessage(prefix + "§cDer PvP-Modus wurde deaktiviert.");
				}
					getConfig().set("pvp.Status", pvp);
					saveConfig();
				return false;
			}else {
				sender.sendMessage(prefix + "§cDu hast keine Rechte um diesen Befehl auszuführen.");
			}
			return false;
		}
		
		if(command.getName().equalsIgnoreCase("status")) {
			sender.sendMessage("§5=§d=§5=§d=" + prefix + "§d=§5=§d=§5=");
			sender.sendMessage("");
			sender.sendMessage("§dAufnahmepflicht: §7" + rec);
			sender.sendMessage("");
			sender.sendMessage("§5PvP: §8" + pvp);
			sender.sendMessage("");
			sender.sendMessage("§dWartung: §7" + wartung);
			sender.sendMessage("");
			sender.sendMessage("§5Dein Ping: §8" + getPing(p));
			sender.sendMessage("");
			sender.sendMessage("§dHosted by DarkZNetwork");
			sender.sendMessage("");
			sender.sendMessage("§5=§d=§5=§d=" + prefix + "§d=§5=§d=§5=");
		}
		
		return false;
	}
	
	public double getPing(Player p) {
		CraftPlayer pingc = (CraftPlayer) p;
		EntityPlayer pinge = pingc.getHandle();
		return pinge.ping;
}


	
   /* public void sendScoreboard(Player player,int Kills,int Deaths){
    	ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard board = sm.getNewScoreboard();
        Objective score = board.registerNewObjective("aaa", "bbb");
       
        score.setDisplayName("§5§l•●Engineers●•");
        score.setDisplaySlot(DisplaySlot.SIDEBAR);
       
        score.getScore("§4§lRec-Pflicht").setScore(8);
        score.getScore(rec + "  ").setScore(7);
        score.getScore("§7 ").setScore(6);
        score.getScore("§6§lPvP ").setScore(5);
        score.getScore(pvp + "  ").setScore(4);
        score.getScore("§6 ").setScore(3);
        
        player.setScoreboard(board);
}*/
	
	@EventHandler
	public void onJoin(PlayerJoinEvent join) {
		/*Player player = join.getPlayer(); updateScoreboard(player);*/
		Player p = join.getPlayer();
		join.setJoinMessage(online + Mitglied + p.getName());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent quit) {
		Player p = quit.getPlayer();
		quit.setQuitMessage(offline + Mitglied + p.getName());
	}
	

}
