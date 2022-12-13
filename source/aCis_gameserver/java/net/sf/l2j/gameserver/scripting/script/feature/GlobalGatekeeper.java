package net.sf.l2j.gameserver.scripting.script.feature;

import net.sf.l2j.gameserver.data.xml.GlobalGatekeeperData;
import net.sf.l2j.gameserver.model.actor.Npc;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.holder.teleport.LocationHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportMenuHolder;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.scripting.Quest;

import java.util.List;

public class GlobalGatekeeper extends Quest {
    public GlobalGatekeeper() {
        super(-1, "feature");
        addFirstTalkId(30059);
    }

    @Override
    public String onFirstTalk(Npc npc, Player player) {
        final NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());
        final StringBuilder sb = new StringBuilder();

        final StringBuilder men = new StringBuilder();
        List<TeleportMenuHolder> menu = GlobalGatekeeperData.getInstance().getMenu();
        for (TeleportMenuHolder menuItem : menu) {
            men.append(menuItem.getName());
        }

        TeleportHolder teleport = GlobalGatekeeperData.getInstance().getById(0);
        List<LocationHolder> locations = teleport.getLocations();
        for (LocationHolder location : locations) {
            sb
                    .append("<table width=280 bgcolor=000000>")
                    .append("<tr>")
                    .append("<td width=150>")
                    .append("<a action=\"bypass -h npc_%objectId%_teleport_list 15 1 villages\">")
                    .append(location.getName())
                    .append("</a>")
                    .append("</td>")
                    .append("<td width=100>")
                    .append("<font color=A3A0A3>18 - 25</font>")
                    .append("</td>")
                    .append("<td width=80>")
                    .append("<a action=\"bypass -h npc_%objectId%_teleport_list 15 1 villages\">")
                    .append("<font color=B09878>")
                    .append(location.getCapital())
                    .append("</font>")
                    .append("</a>")
                    .append("</td>")
                    .append("</tr>")
                    .append("</table>")
                    .append("<img src=L2UI.SquareGray width=280 height=1>");
        }
        html.setFile("data/html/mods/gk/index.htm");
        html.replace("%list%", sb.toString());
        html.replace("%menu%", men.toString());
        player.sendPacket(html);
        return null;
    }
}