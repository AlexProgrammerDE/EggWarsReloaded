package net.pistonmaster.eggwarsreloaded.hooks;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.api.events.GameJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PartiesHook implements Listener {
    private final EggWarsReloaded plugin;
    private final PartiesAPI api;
    private final List<UUID> noMove = new ArrayList<>();

    public PartiesHook(EggWarsReloaded plugin) {
        this.plugin = plugin;
        api = Parties.getApi();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(GameJoinEvent event) {
        PartyPlayer partyplayer = api.getPartyPlayer(event.getPlayer().getUniqueId());

        if (partyplayer.isInParty()) {
            Party party = api.getParty(Objects.requireNonNull(partyplayer.getPartyId()));

            if (partyplayer.getPlayerUUID().equals(party.getLeader())) {

            }
        }
    }
}
