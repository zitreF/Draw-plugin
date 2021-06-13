package me.fertiz.reward.commands; //co

import me.fertiz.reward.Main; //co
import me.fertiz.reward.utils.ChatUtil; //co
import me.fertiz.reward.utils.NumberUtils; //co
import org.bukkit.Bukkit; //co
import org.bukkit.command.Command; //co
import org.bukkit.command.CommandExecutor; //co
import org.bukkit.command.CommandSender; //co
import org.bukkit.entity.Player; //co

import java.util.*; //co
import java.util.concurrent.ThreadLocalRandom; //co
import java.util.concurrent.TimeUnit; //co
import java.util.stream.Collectors; //co

public class DrawCommand implements CommandExecutor { //co

    private final Main plugin; //co

    public DrawCommand(Main plugin) { //co
        this.plugin = plugin; //co
    } //co

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) { //co

        Player p = (Player) sender; //co

        if (args.length <= 1) { //co
            ChatUtil.sendMessage(p, "&8>> &7Poprawne uzycie&8: &e/losuj <ilosc_wygranych> <czas (w min)> <nagroda>"); //co
            return false; //co
        } //co
        if (!NumberUtils.isNumber(args[0]) || !NumberUtils.isNumber(args[1])) { //co
            ChatUtil.sendMessage(p, "&8>> &7Musisz wpisac liczbe!"); //co
            return false; //co
        } //co
        int winnersAmount = Integer.parseInt(args[0]); //co
        long time = TimeUnit.MINUTES.toSeconds(Long.parseLong(args[1])); //co
        Collection<? extends Player> players = Bukkit.getOnlinePlayers(); //co
        if (winnersAmount > players.size()) { //co
            ChatUtil.sendMessage(p, "&8>> &7Podana liczba wygranych jest wieksza niz ilosc graczy online!"); //co
            return false; //co
        } //co
        Collection<Player> winners = pickRandom(new ArrayList<>(players), winnersAmount, winnersAmount); //co
        Bukkit.getScheduler().runTaskLater(plugin, () -> onDrop(winners), time * 20); //co
        return false; //co
    } //co

    private static Collection<Player> pickRandom(List<Player> list, int n, int winnerAmount) { //co
        Random random = ThreadLocalRandom.current(); //co
        Collection<Player> winners = random.ints(n, 0, list.size()).mapToObj(list::get).collect(Collectors.toSet()); //co
        while (winners.size() != winnerAmount) { //co
            winners.add(list.get(random.nextInt(list.size()))); //co
        } //co
        return winners; //co
    } //co

    private void onDrop(Collection<Player> winners) { //co
        for (Player winner : winners) { //co
            Bukkit.broadcastMessage("Wygrany: " + winner.getName()); //co
        } //co
       
    } //co
} //co
