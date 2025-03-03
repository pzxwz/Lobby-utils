package br.pzsz.lobby.events;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

public class onPlayerQuitEvent implements Listener {

    @EventHandler
    public void playerquit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        e.setQuitMessage(null);
        inv.clear();
        p.addPotionEffect(null);
    }

}