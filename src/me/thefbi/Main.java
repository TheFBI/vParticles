package me.thefbi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.thefbi.particles.Particles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public static Main INSTANCE;
	
	public static int I_RUNNABLE;
		
	public static List<UUID> toggled = new ArrayList<UUID>();
		
	public static HashMap<UUID, Effect> hm = new HashMap<UUID, Effect>();
	
	private String[] commands = {"/particles set <type>", "/particles toggle", "/particles list", "/particles set <player> <effect>", "/particles toggle <player>"};
	
	private String[] particleList = {"FLAMES", "VILLAGER", "HEARTS"};
	
	public String prefix = ChatColor.GREEN + "Venom " + ChatColor.DARK_GRAY + "// " + ChatColor.GRAY;
	
	public void onEnable()
	{
		INSTANCE = this;
		//Particles.delayTicks = getConfig().getInt("particles-per-tick");
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(!(sender instanceof Player) || sender.isOp())
		{
			return true;
		}
		
		Player player = (Player) sender;
		
		if(command.getName().equalsIgnoreCase("vparticles") && args.length == 0)
		{
			for(String cmds : commands)
			{
				player.sendMessage(prefix + ChatColor.RED + cmds);
			}
			return true;

		} else if (args.length == 1 && args[0].equalsIgnoreCase("list"))
		{
			for(String particles : particleList)
			{
				player.sendMessage(prefix + particles);
			}
			return true;

		} else if (args.length == 1 && args[0].equalsIgnoreCase("toggle"))
		{
			if(!(hm.containsKey(player.getUniqueId())))
			{
				player.sendMessage(prefix + "You don't have a particle effect set!");
				return true;
			}
			
			if(toggled.contains(player.getUniqueId()))
			{
				toggled.remove(player.getUniqueId());
				player.sendMessage(prefix + ChatColor.RED + "Disabled " + ChatColor.GRAY + "Particle Effects.");
				hm.remove(player.getUniqueId());
				return true;
			} else {
				toggled.add(player.getUniqueId());
				Particles.setParticles(player, hm.get(player.getUniqueId()), player.getName());
				player.sendMessage(prefix + ChatColor.GREEN + "Enabled " + ChatColor.GRAY + "Particle Effects.");
				I_RUNNABLE++;
				return true;
			}
		} else if (args.length == 2 && args[0].equalsIgnoreCase("set"))
		{
			Effect effect;
			
			String chosenEffect = args[1];
			
			if(chosenEffect.equalsIgnoreCase("FLAMES"))
			{
				effect = Effect.MOBSPAWNER_FLAMES;
				hm.remove(player.getUniqueId());
				hm.put(player.getUniqueId(), effect);
				
				player.sendMessage(prefix + "You have set your particles to " + ChatColor.RED + effect);
				return true;
				
			} else if(chosenEffect.equalsIgnoreCase("VILLAGER"))
			{
				Particles.delayTicks = 5;
				effect = Effect.HAPPY_VILLAGER;
				hm.remove(player.getUniqueId());
				hm.put(player.getUniqueId(), effect);
				player.sendMessage(prefix + "You have set your particles to " + ChatColor.RED + effect);
				return true;
			} else if(chosenEffect.equalsIgnoreCase("HEARTS"))
			{
				Particles.delayTicks = 15;
				effect = Effect.HEART;
				hm.remove(player.getUniqueId());
				hm.put(player.getUniqueId(), effect);
				player.sendMessage(prefix + "You have set your particles to " + ChatColor.RED + effect);
				return true;
			}
		} else if(args[0].equalsIgnoreCase("set") && args.length == 3)
		{
			if(Bukkit.getPlayer(args[1]) == null)
			{
				player.sendMessage(prefix + ChatColor.RED + args[1] + " not found!");
				return true;
			 
			}
			Effect effect = null;
			
			if(args[2].equalsIgnoreCase("MOBSPAWNER_FLAMES"))
			{
				effect = Effect.MOBSPAWNER_FLAMES;
				Particles.delayTicks = 35;
			hm.put(Bukkit.getPlayer(args[1]).getUniqueId(), effect);
			toggled.add(Bukkit.getPlayer(args[1]).getUniqueId());
				Particles.setParticles(Bukkit.getPlayer(args[1]), effect, args[1]);
			player.sendMessage(prefix + "Gave " + ChatColor.RED + args[1] + " " + effect + ChatColor.GRAY + " Particles.");
			return true;
			
		} else if(args[0].equalsIgnoreCase("toggle") && args.length == 2)
		{
			if(toggled.contains(Bukkit.getPlayer(args[1]).getUniqueId()))
			{
				toggled.remove(Bukkit.getPlayer(args[1]).getUniqueId());
				return true;
			} else {
				toggled.add(Bukkit.getPlayer(args[1]).getUniqueId());
				return true;
			}

		} else if(command.getName().equalsIgnoreCase("vparticles") && args[0].equalsIgnoreCase("toggle") && args.length == 2)
		{
			Player p = Bukkit.getPlayer(args[1]);
			if(p == null)
			{
				player.sendMessage(prefix + "player not found!");
				return true;
			}
			
		}
		}
		return false;
		
	}
	
	public static boolean isToggled(Player name)
	{
		if(toggled.contains(name.getUniqueId()))
		{
			return true;
		}
		return false;
	}
	
}
