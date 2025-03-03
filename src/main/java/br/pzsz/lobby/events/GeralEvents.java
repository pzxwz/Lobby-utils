package br.pzsz.lobby.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.inventory.PlayerInventory;

public class GeralEvents implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        e.setMaxPlayers(100);
        e.setMotd("         §c§lLEAGUEMC §7» §e[loja.leaguemc.com.br]\n                 §b§lCONHEÇA O TRAINING.");
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        e.setJoinMessage(null);
        p.setHealth(20);
        p.setFoodLevel(20);
        inv.clear();
        inv.addItem(createItem(Material.COMPASS, "§aSelecione o modo."));
        inv.setItem(7, createPoliciesBook());
        inv.setItem(1, createPlayerHead(p));
        inv.setItem(4, createItem(Material.CHEST, "§aCosméticos"));
        inv.setItem(8, createItem(Material.NETHER_STAR, "§aLobby"));
        p.sendTitle("§c§lLEAGUEMC", "§eSeja bem-vindo(a)");

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("scoreboard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§c§lLEAGUEMC");
        objective.getScore(" ").setScore(8);
        objective.getScore("§fRank: §4Admin").setScore(7);
        objective.getScore("§fClan: §7Nenhum").setScore(6);
        objective.getScore("  ").setScore(5);
        objective.getScore("§fLobby: §7#1").setScore(4);
        objective.getScore("§fJogadores: §b" + Bukkit.getOnlinePlayers().size()).setScore(3);
        objective.getScore("   ").setScore(2);
        objective.getScore("§eleaguemc.com.br").setScore(1);

        p.setScoreboard(scoreboard);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }



    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createPlayerHead(Player player) {
        ItemStack redstoneReceiver = new ItemStack(Material.REDSTONE_COMPARATOR);
        ItemMeta meta = redstoneReceiver.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§aMeu perfil");
            redstoneReceiver.setItemMeta(meta);
        }
        return redstoneReceiver;
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() != null) {
                if (p.getItemInHand().getType() == Material.COMPASS) {
                    openModeMenu(p);
                } else if (p.getItemInHand().getType() == Material.NETHER_STAR) {
                    openLobbyMenu(p);
                } else if (p.getItemInHand().getType() == Material.REDSTONE_COMPARATOR) {
                    p.sendMessage("§cEm breve... fique ligado em próximas atualizações!");
                } else if (p.getItemInHand().getType() == Material.CHEST) {
                    p.sendMessage("§cEm breve... fique ligado em próximas atualizações!");
                } else if (p.getItemInHand().getType() == Material.BOOK) {
                    if (p.getItemInHand().getItemMeta().getDisplayName().equals("§aPoliticas")) {
                        openRulesMenu(p);
                    }
                }
            }
        }
    }

    private void openModeMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, "§6Selecione o modo:");
        ItemStack trainingSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = trainingSword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cTraining");
            List<String> lore = new ArrayList<>();
            lore.add("§7Treine suas habilidades nesse novo modo desafiador.");
            lore.add("");
            lore.add("§eClique para conectar!");
            meta.setLore(lore);
            trainingSword.setItemMeta(meta);
        }
        menu.setItem(13, trainingSword);
        player.openInventory(menu);
    }

    private void openLobbyMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, "§6Selecione seu lobby:");
        ItemStack glassPanel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta glassMeta = glassPanel.getItemMeta();
        if (glassMeta != null) {
            glassMeta.setDisplayName("§cLobby #1");
            List<String> lore = new ArrayList<>();
            lore.add("§7Você está neste lobby.");
            lore.add("§7Porém, não existem outros lobbys disponíveis.");
            glassMeta.setLore(lore);
            glassPanel.setItemMeta(glassMeta);
        }
        menu.setItem(13, glassPanel);
        player.openInventory(menu);
    }

    private void openRulesMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 9 * 3, "§6Políticas do Servidor");

        ItemStack rulesBook = createRulesBook();
        menu.setItem(13, rulesBook);

        player.openInventory(menu);
    }

    private ItemStack createPoliciesBook() {
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();

        if (bookMeta != null) {
            bookMeta.setDisplayName("§aPoliticas");

            book.setItemMeta(bookMeta);
        }

        return book;
    }

    private ItemStack createRulesBook() {
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        if (bookMeta != null) {
            bookMeta.setDisplayName("§cRegras de punições");

            List<String> lore = new ArrayList<>();
            lore.add("§7- Respeite os outros jogadores, em caso de ofensas, o autor será punido;");
            lore.add("§7- Não divulgue informações de jogadores;");
            lore.add("§7- Proibida a divulgação de links fora do LeagueMC;");
            lore.add("");
            lore.add("§7- Consulte todas as regras em: §edsc.gg/leaguepvp");

            bookMeta.setLore(lore);
            book.setItemMeta(bookMeta);
        }
        return book;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null) {
            if (clickedItem.getType() == Material.DIAMOND_SWORD || clickedItem.getType() == Material.STAINED_GLASS_PANE || clickedItem.getType() == Material.BOOK) {
                event.setCancelled(true);
            } else {
                event.setCancelled(false);
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand.getType() == Material.CHEST || itemInHand.getType() == Material.REDSTONE_COMPARATOR) {
                event.setCancelled(true);
            }
        }
    }
