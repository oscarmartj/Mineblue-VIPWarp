package com.mcmineblue.plugin.oscarmj11;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VIPWarpMain extends JavaPlugin {

	private int flag = 0;
	private World world;
	private Location location;
	//private String mineblue="&3Mine&bblue&f-&aVIP&2Warp&f";
	public void onEnable() {
		getLogger().info("Mineblue-VIPWarp desarrollado por oscarmj11 - Cargando...");
		saveDefaultConfig();
		File config = new File(getDataFolder(), "config.yml");
		if (!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveConfig();
		}		    
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3MINE&bBLUE&f-&aVIP&2WARP&f"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&0Plugin creado por &6&loscarmj11"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="));

	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}else {
			return false;
		}
		if(args.length>0) {
			sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"))) + 
					ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.incorrecto")));
			return false;
		}
		if(command.getName().equalsIgnoreCase("vip")) {
			if(!player.hasPermission("vip.tp")) {
				sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"))) + 
						ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
				return false;
			}
			this.flag = 1;
			this.setWorld(player.getWorld());;
			this.setLocation(player.getLocation());
			World world = new WorldCreator((String) getConfig().get("zone-vip.world")).createWorld();
			Location vip = new Location(
					world,
					(Double)getConfig().get("zone-vip.x-coord"),
					(Double)getConfig().get("zone-vip.y-coord"),
					(Double)getConfig().get("zone-vip.z-coord"),
					(Float)getConfig().get("zone-vip.yaw"),
					(Float)getConfig().get("zone-vip.pitch"));
			sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + 
					ChatColor.translateAlternateColorCodes('&', "&aTeletransportandote a la zona vip..." ))));
			player.teleport(vip);
		}

		if(command.getName().equalsIgnoreCase("vipback")) {
			if(!player.hasPermission("vip.back")) {
				sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"))) + 
						ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
				return false;
			}
			if(flag!=1) {
				sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + 
						ChatColor.translateAlternateColorCodes('&', "Para utilizar el comando vipback, tienes que haber ido previamente a la zona vip" ))));
				return false;
			}
			//Volver a la posicion anterior en la que estaba
			Location tpback = new Location(this.getWorld(),this.getLocation().getX(),this.getLocation().getY(),this.getLocation().getZ(),this.getLocation().getYaw(),this.getLocation().getPitch());			
			player.teleport(tpback);
			this.flag = 0;
		}

		if(command.getName().equalsIgnoreCase("setvip")) {
			if(!player.hasPermission("vip.set")) {
				sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"))) + 
						ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
				return false;
			}
			
			Location location = player.getLocation();
			FileConfiguration fc = getConfig();
			fc.set("zone-vip.world", location.getWorld().getName());
			fc.set("zone-vip.x-coord", location.getX());
			fc.set("zone-vip.y-coord", location.getY());
			fc.set("zone-vip.z-coord", location.getZ());
			fc.set("zone-vip.yaw", location.getYaw());
			fc.set("zone-vip.pitch", location.getPitch());
			saveConfig();
			sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix") + 
					ChatColor.translateAlternateColorCodes('&', "&aEstablecida zona vip en "+ location.getWorld().getName()+":"+" ("+location.getX()+", "+location.getY()+", "+location.getZ()+" )"  ))));

		}

		return true;

	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

}
