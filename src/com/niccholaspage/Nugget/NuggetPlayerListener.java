package com.niccholaspage.Nugget;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class NuggetPlayerListener implements Listener {
	private final Nugget plugin;

	public NuggetPlayerListener(Nugget plugin){
		this.plugin = plugin;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
		Player player = event.getPlayer();

		ItemStack stack = event.getItem().getItemStack();

		if (stack.getTypeId() == plugin.getConfig().getInt("material")){
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			ItemStack stack = event.getItem();

			if (stack == null){
				return;
			}

			int id = plugin.getConfig().getInt("material");

			if (stack.getTypeId() == id){
				Player player = event.getPlayer();

				double money = stack.getAmount() * plugin.getConfig().getInt("value");

				plugin.getEconomy().depositPlayer(player.getName(), money);

				player.getInventory().remove(stack);

				player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

				player.sendMessage(ChatColor.GREEN + "" + money + " coins have been deposited into your bank account.");
			}
		}
	}
}
