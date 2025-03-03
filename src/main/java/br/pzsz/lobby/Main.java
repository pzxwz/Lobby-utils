package br.pzsz.lobby;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import br.pzsz.lobby.events.GeralEvents;

public class Main extends JavaPlugin implements Listener {

    public static Main m;

    @Override
    public void onLoad() {
        m = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("§aLobbyUtils habilitado com sucesso!");

    }

    @Override
    public void onDisable() {
        getLogger().info("§cLobbyUtils desabilitado com sucesso!");
        HandlerList.unregisterAll();
    }
}