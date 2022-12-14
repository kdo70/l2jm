package net.sf.l2j.gameserver.scripting.script.feature;

import net.sf.l2j.gameserver.data.xml.GlobalGatekeeperData;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportHolder;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.scripting.Quest;

import java.util.StringTokenizer;

public class GlobalGatekeeper extends Quest {

    public GlobalGatekeeper() {
        super(-1, "feature");
        addTalkId(30059);
        addFirstTalkId(30059);
    }

    @Override
    public String onFirstTalk(Npc npc, Player player) {
        return sendList(npc, player, 1);
    }

    @Override
    public String onAdvEvent(String event, Npc npc, Player player) {
        if (player.getTarget() != npc) {
            return actionFailed(player);
        }

        LOGGER.info(event);

        if (event.startsWith("List")) {
            return sendList(npc, player, getIndex(event));
        }

        if (event.startsWith("Locations")) {
            return sendLocations(npc, player, event);
        }

        if (event.startsWith("Teleport")) {
            teleport(npc, player, event);
        }

        return actionFailed(player);
    }

    public String sendList(Npc npc, Player player, int index) {
        final NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());

        String menu = GlobalGatekeeperData.getInstance().getMenu(0, index);
        String list = GlobalGatekeeperData.getInstance().getList(index);

        html.setFile("data/html/mods/gk/index.htm");
        html.replace("%menu%", menu);
        html.replace("%list%", list);
        player.sendPacket(replaceNpcData(html, npc));

        return null;
    }

    public String sendLocations(Npc npc, Player player, String event) {
        final StringTokenizer string = new StringTokenizer(event, " ");
        string.nextToken();
        int parentId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 1;
        int teleportId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 1;

        final NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());

        String menu = GlobalGatekeeperData.getInstance().getMenu(0, parentId);
        String locations = GlobalGatekeeperData.getInstance().getLocations(teleportId);

        html.setFile("data/html/mods/gk/locations.htm");
        html.replace("%menu%", menu);
        html.replace("%locations%", locations);
        player.sendPacket(replaceNpcData(html, npc));

        return null;
    }

    public void teleport(Npc npc, Player player, String event) {
        LOGGER.info(1);
        final StringTokenizer string = new StringTokenizer(event, " ");
        string.nextToken();
        int teleportId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 1;
        int locationId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 1;
        TeleportHolder teleport = GlobalGatekeeperData.getInstance().getById(teleportId);
        player.teleportTo(teleport.getLocations().get(locationId), 20);
    }

    public NpcHtmlMessage replaceNpcData(NpcHtmlMessage html, Npc npc) {
        html.replace("%objectId%", npc.getObjectId());
        html.replace("%npcTitle%", npc.getTitle());
        html.replace("%npcName%", npc.getName());
        return html;
    }

    public int getIndex(String command) {
        final StringTokenizer string = new StringTokenizer(command, " ");
        string.nextToken();
        return string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 1;
    }

    private String actionFailed(Player player) {
        player.sendPacket(ActionFailed.STATIC_PACKET);
        return null;
    }
}