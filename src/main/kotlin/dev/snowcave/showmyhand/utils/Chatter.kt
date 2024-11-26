package dev.snowcave.showmyhand.utils

import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


/**
 * Created by WinterBear on 17/11/2023.
 */
class Chatter(private val player: Player) {

    fun send(text: String?) {
        send(player, text)
    }

    fun send(text: Component) {
        send(player, text)
    }

    fun blank() {
        send(player, "")
    }

    fun sendP(text: String) {
        sendP(player, text)
    }


    fun error(text: String) {
        sendP(player, "&c$text")
    }

    companion object {
        private const val MODIFIER_UNCONVERTED = '&'

        private val PREFIX = ChatColor.of("#d4eeff").toString() + "❄" + ChatColor.of("#748acc") + " ShowMyHand &8>> &7"

        fun send(player: Player?, text: String?) {
            player?.sendMessage(ChatColor.translateAlternateColorCodes(MODIFIER_UNCONVERTED, text))
        }

        fun send(sender: CommandSender?, text: String?) {
            sender?.sendMessage(ChatColor.translateAlternateColorCodes(MODIFIER_UNCONVERTED, text))
        }

        fun send(player: Player?, text: Component) {
            player?.sendMessage(text)
        }

        fun send(sender: CommandSender?, text: Component) {
            sender?.sendMessage(text)
        }

        fun sendP(player: Player?, text: String) {
            send(player, PREFIX + text)
        }

        fun sendP(sender: CommandSender?, text: String) {
            send(sender, PREFIX + text)
        }

        fun error(player: Player?, text: String) {
            sendP(player, "&c$text")
        }

        fun error(sender: CommandSender?, text: String) {
            sendP(sender, "&c$text")
        }

        fun broadcast(text: String?) {
            val formatted =  format(text);
            if (formatted != null) {
                Bukkit.broadcastMessage(formatted)
            }
        }

        fun broadcastP(text: String) {
            val formatted =  format(PREFIX + text);
            if (formatted != null) {
                Bukkit.broadcastMessage(formatted)
            }
        }

        fun broadcast(component: Component){
            Bukkit.getOnlinePlayers().forEach { p -> p.sendMessage(component) }
        }

        fun warnConsole(message: String) {
            Bukkit.getServer().logger.warning(format(PREFIX + message))
        }

        fun infoConsole(message: String) {
            Bukkit.getServer().logger.info(format(PREFIX + message))
        }

        fun errorConsole(message: String) {
            Bukkit.getServer().logger.severe(format(PREFIX + message))
        }

        fun format(text: String?): String? {
            return ChatColor.translateAlternateColorCodes(MODIFIER_UNCONVERTED, text)
        }
    }
}
