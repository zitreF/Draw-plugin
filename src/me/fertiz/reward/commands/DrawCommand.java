package me.fertiz.reward.commands;

import me.fertiz.reward.Main;
import me.fertiz.reward.utils.ChatUtil;
import me.fertiz.reward.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DrawCommand implements CommandExecutor {

    private final Main plugin;

    public DrawCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;

        if (args.length <= 1) {
            ChatUtil.sendMessage(p, "&8>> &7Poprawne uzycie&8: &e/losuj <ilosc_wygranych> <czas (w min)> <nagroda>");
            return false;
        }
        if (!NumberUtils.isNumber(args[0]) || !NumberUtils.isNumber(args[1])) {
            ChatUtil.sendMessage(p, "&8>> &7Musisz wpisac liczbe!");
            return false;
        }
        int winnersAmount = Integer.parseInt(args[0]);
        long time = TimeUnit.MINUTES.toSeconds(Long.parseLong(args[1]));
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (winnersAmount > players.size()) {
            ChatUtil.sendMessage(p, "&8>> &7Podana liczba wygranych jest wieksza niz ilosc graczy online!");
            return false;
        }
        Collection<Player> winners = pickRandom(new ArrayList<>(players), winnersAmount, winnersAmount);
        Bukkit.getScheduler().runTaskLater(plugin, () -> onDrop(winners), time * 20);
        return false;
    }

    private Collection<Player> pickRandom(List<Player> list, int n, int winnerAmount) {
        Random random = ThreadLocalRandom.current();
        Collection<Player> winners = random.ints(n, 0, list.size()).mapToObj(list::get).collect(Collectors.toSet());
        while (winners.size() != winnerAmount) {
            winners.add(list.get(random.nextInt(list.size())));
        }
        return winners;
    }

    private void onDrop(Collection<Player> winners) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            ChatUtil.sendTitle(all, "&8>> &7KONKURS SIE ZAKONCZYl! &8<<");
            Bukkit.getScheduler().runTaskLater(plugin, () -> ChatUtil.sendTitle(all, "&8>> &7Wygranymi sa&8: &8<<"), 20);
            for (Player winner : winners) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> ChatUtil.sendTitle(all, "&8>> &e&l"+winner.getName()+" &8<<"), 60);
            }
        }
    }
}
