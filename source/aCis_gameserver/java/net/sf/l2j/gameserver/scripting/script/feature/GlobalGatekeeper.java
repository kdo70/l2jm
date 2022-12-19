package net.sf.l2j.gameserver.scripting.script.feature;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.data.manager.CastleManager;
import net.sf.l2j.gameserver.data.xml.GlobalGatekeeperData;
import net.sf.l2j.gameserver.enums.TeleportType;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.entity.Castle;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportLocationHolder;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.scripting.Quest;

import java.util.StringTokenizer;

/**
 * Global gatekeeper
 */
public class GlobalGatekeeper extends Quest {

    /**
     * Menu id
     */
    protected final int menuId = 0;

    /**
     * Global gatekeeper
     */
    public GlobalGatekeeper() {
        super(-1, "feature");
        addTalkId(30059, 30080, 30878, 30256, 30320, 30899, 30848, 30177, 31275, 31964, 31320, 30006, 30146,
                30134, 30540, 30576, 30233, 30836, 31698, 31699);
        addFirstTalkId(30059, 30080, 30878, 30256, 30320, 30899, 30848, 30177, 31275, 31964, 31320, 30006,
                30146, 30134, 30540, 30576, 30233, 30836, 31698, 31699);
    }

    @Override
    public String onFirstTalk(Npc npc, Player player) {
        String event = "";
        return sendList(npc, player, event);
    }

    @Override
    public String onAdvEvent(String event, Npc npc, Player player) {
        if (player.getTarget() != npc) {
            return actionFailed(player);
        }

        if (event.startsWith("List")) {
            return sendList(npc, player, event);
        }

        if (event.startsWith("Locations")) {
            return sendLocations(npc, player, event);
        }

        if (event.startsWith("Teleport")) {
            teleport(npc, player, event);
        }

        return actionFailed(player);
    }

    /**
     * Send list string.
     *
     * @param npc    npc
     * @param player player
     * @param event  event
     * @return list string
     */
    public String sendList(Npc npc, Player player, String event) {
        final NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());
        final StringTokenizer string = getEvent(event);
        int menuItemId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 0;

        String menu = GlobalGatekeeperData.getInstance().getMenu(this.menuId, menuItemId);
        String list = GlobalGatekeeperData.getInstance().getList(player, this.menuId, menuItemId);

        html.setFile("data/html/mods/gk/index.htm");
        html.replace("%menu%", menu);
        html.replace("%list%", list);

        String pkMessage = " ";
        if (player.getKarma() > 0) {
            final StringBuilder sb = new StringBuilder();
            sb
                    .append("<img height=\"5\">")
                    .append("<font color=\"ff0000\">")
                    .append("Стоимость телепорта для вас увеличена в ")
                    .append(Config.TELEPORT_PK_MUL)
                    .append(" раз")
                    .append("</font>")
                    .append("</font>");
            pkMessage = sb.toString();
        }
        html.replace("%pk%", pkMessage);

        player.sendPacket(replaceNpcData(html, npc));

        return null;
    }

    /**
     * Send locations string.
     *
     * @param npc    npc
     * @param player player
     * @param event  event
     * @return locations string
     */
    public String sendLocations(Npc npc, Player player, String event) {
        final StringTokenizer string = getEvent(event);

        int menuId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : this.menuId;
        int menuItemId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 0;
        int areaId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 0;

        final NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());

        String menu = GlobalGatekeeperData.getInstance().getMenu(menuId, menuItemId);
        String locations = GlobalGatekeeperData.getInstance().getLocations(player, menuId, menuItemId, areaId);

        html.setFile("data/html/mods/gk/locations.htm");
        html.replace("%menu%", menu);
        html.replace("%locations%", locations);
        player.sendPacket(replaceNpcData(html, npc));

        return null;
    }

    /**
     * Teleport
     *
     * @param npc    npc
     * @param player player
     * @param event  event
     */
    public void teleport(Npc npc, Player player, String event) {
        final StringTokenizer string = getEvent(event);

        int menuId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : this.menuId;
        int menuItemId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 0;
        int areaId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 0;
        int locationId = string.hasMoreTokens() ? Integer.parseInt(string.nextToken()) : 0;

        TeleportLocationHolder location = GlobalGatekeeperData.getInstance()
                .getById(menuId)
                .getItems().get(menuItemId)
                .getAreas().get(areaId)
                .getLocations().get(locationId);

        if (location.getCastleId() > 0) {
            final Castle castle = CastleManager.getInstance().getCastleById(location.getCastleId());
            if (castle != null && castle.getSiege().isInProgress()) {
                player.sendPacket(SystemMessageId.CANNOT_PORT_VILLAGE_IN_SIEGE);
                return;
            }
        }

        if (location.getType() == TeleportType.NOBLE && !player.isNoble()) {
            return;
        }

        final int priceCount = GlobalGatekeeperData.getInstance().calculatedPriceCount(player, location);
        if (priceCount == 0 || player.destroyItemByItemId("InstantTeleport", location.getPriceId(), priceCount, npc, true)) {
            player.teleportTo(location, 20);
        }
    }

    /**
     * Replace npc data npc html message
     *
     * @param html html
     * @param npc  npc
     * @return npc html message
     */
    public NpcHtmlMessage replaceNpcData(NpcHtmlMessage html, Npc npc) {
        html.replace("%objectId%", npc.getObjectId());
        html.replace("%npcTitle%", npc.getTitle());
        html.replace("%npcName%", npc.getName());
        return html;
    }

    /**
     * Get event
     *
     * @param event event
     * @return event
     */
    public StringTokenizer getEvent(String event) {
        final StringTokenizer string = new StringTokenizer(event, " ");
        if (string.hasMoreTokens()) {
            string.nextToken();
        }
        return string;
    }


    /**
     * @param player player
     * @return actionFailed
     */
    private String actionFailed(Player player) {
        player.sendPacket(ActionFailed.STATIC_PACKET);
        return null;
    }
}