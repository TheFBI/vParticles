package me.thefbi.particles;

import me.thefbi.Main;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class Particles {
	
	public static int delayTicks = 35;
		
	@SuppressWarnings("deprecation")
	public static void setParticles(final Player player, final Effect effect, final String name)
	{
		@SuppressWarnings("unused")
		int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, new Runnable(){

			
			@Override
			public void run() {
				
								
				if(Main.toggled.isEmpty())
				{
					return;
				}
				
				if(Main.toggled.contains(player.getUniqueId()))
				{
					
					Location loc = Bukkit.getPlayer(name).getLocation();
					
					if(effect == Effect.HAPPY_VILLAGER)
					{
						loc = Bukkit.getPlayer(name).getEyeLocation().add(new Location(player.getWorld(), 0, 0.5, 0));
						
						for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
							p.playEffect(loc, effect, 10);
							
						}			
						
						return;	
					} 
					
					if( effect == Effect.MOBSPAWNER_FLAMES)
					{
						loc = Bukkit.getPlayer(name).getLocation();
						
						for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
							p.playEffect(loc, effect, 10);
							
						}		
						
						return;
						
					} 
					
					if(effect == Effect.HEART)
					{
						loc = Bukkit.getPlayer(name).getEyeLocation().add(new Location(player.getWorld(), 0, 0.1, 0));
						
						for(Player p : Bukkit.getServer().getOnlinePlayers())
						{
							p.playEffect(loc, effect, 5);
							
						}
						
						return;
					}
						else {
					
					return;
				}
			}
				
			}
			
		}, 1, delayTicks);
	}
}
