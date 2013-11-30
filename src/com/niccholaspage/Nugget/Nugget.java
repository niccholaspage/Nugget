package com.niccholaspage.Nugget;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Nugget extends JavaPlugin {
	private Economy economy;

	public void onEnable(){
		Plugin vaultPlugin = getServer().getPluginManager().getPlugin("Vault");

		if (vaultPlugin == null){
			log("Vault not found! Disabling Nugget.");

			setEnabled(false);

			return;
		}

		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}else {
			log("No economy plugin installed! Disabling Nugget.");

			return;
		}

		getConfig().options().copyDefaults(true);

		saveConfig();

		new NuggetPlayerListener(this);
	}

	public void log(String message){
		getLogger().info(message);
	}

	public Economy getEconomy(){
		return economy;
	}
}
