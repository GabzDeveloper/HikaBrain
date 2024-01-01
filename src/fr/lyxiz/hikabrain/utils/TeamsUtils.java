package fr.lyxiz.hikabrain.utils;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamsUtils implements Listener {

    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    private int maxplayer;
    private String prefix;
    private boolean friendlyfire;
    private String chatprefix;
    private boolean allowteamchat;
    private String teamname;

    public Team team;

    public TeamsUtils(String teamname, int maxplayer, String prefix, boolean friendlyfire,
                      @Nullable String chatprefix, boolean allowteamchat) {

        this.teamname = teamname;
        this.maxplayer = maxplayer;
        this.prefix = prefix;
        this.friendlyfire = friendlyfire;
        this.chatprefix = chatprefix;
        this.allowteamchat = allowteamchat;

    }



    public void build() {

        this.team = scoreboard.registerNewTeam(teamname);
        this.team.setAllowFriendlyFire(friendlyfire);
        this.team.setCanSeeFriendlyInvisibles(true);
        this.team.setPrefix(prefix);

    }

    public void addPlayer(Player player) {

        if (this.team.hasEntry(player.getDisplayName()))
            return;
        if (!(this.team.getSize() < this.maxplayer))
            return;

        this.team.addEntry(player.getDisplayName());

    }

    public boolean contain(Player player) {
        return this.team.hasEntry(player.getDisplayName());
    }

    public void removePlayer(Player player) {

        if (!this.team.hasEntry(player.getDisplayName()))
            return;

        this.team.removeEntry(player.getDisplayName());

    }

    public int getSize() {
        return team.getSize();
    }

    /*
     * Ne pas oublier de resgister
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (this.allowteamchat) {
            if (!message.startsWith("!")) {
                event.setMessage(null);
                if (this.team.hasEntry(player.getDisplayName())) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (this.team.hasEntry(players.getDisplayName())) {
                            players.sendMessage(
                                    this.chatprefix + " (Team) " + player.getDisplayName() + ": " + message);
                        }
                    }
                }
            } else {
                event.setMessage(
                        this.chatprefix + " " + player.getDisplayName() + ": " + message.replaceFirst("!", ""));
            }
        }

    }
    /*
     * Getter and Setter
     */

    public String getChatPrefix() {
        return this.chatprefix;
    }

    public String getTeamName() {
        return this.teamname;
    }

    public String getTeamPrefix() {
        return this.prefix;
    }

    public int getMaxPlayer() {
        return this.maxplayer;
    }

    public boolean getAllowFriendlyFire() {
        return this.friendlyfire;
    }

    public boolean getAllowChatTeam() {
        return this.allowteamchat;
    }

    public void setChatPrefix(String chatprefix) {
        this.chatprefix = chatprefix;
    }

    public void setTeamName(String teamname) {
        this.teamname = teamname;
    }

    public void setTeamPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setMaxPlayer(int maxplayer) {
        this.maxplayer = maxplayer;
    }

    public void setFriendlyFire(boolean friendlyfire) {
        this.friendlyfire = friendlyfire;
    }

    public void setAllowTeamChat(boolean allowteamchat) {
        this.allowteamchat = allowteamchat;
    }

}

