package dev.snowcave.showmyhand.commands

import dev.snowcave.showmyhand.ItemStore
import dev.snowcave.showmyhand.utils.Chatter
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import java.util.*


class ViewHandCommandHandler(private val itemStore: ItemStore) : CommandExecutor, Listener {

    object Settings {
        const val VIEWHAND_COMMAND = "viewhand"
    }

    private val viewers = HashSet<UUID>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == Settings.VIEWHAND_COMMAND) {
            if (sender !is Player) {
                Chatter.error(sender, "Only players can use this command.")
                return true
            }
            if(args.isEmpty()){
                Chatter.error(sender, "Missing argument - no item code provided.")
                return true
            }
            val itemKey = args[0]
            if(!itemStore.hasItem(itemKey)){
                Chatter.error(sender, "No item is registered with that code.")
                return true
            }
            val item = itemStore.getItem(itemKey)

            val inv = Bukkit.createInventory(null, 9, Component.text("Item Display"))
            inv.setItem(4, item)
            sender.openInventory(inv)
            viewers.add(sender.uniqueId)
        }
        return false
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(viewers.contains(event.whoClicked.uniqueId)){
            event.isCancelled = true;
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        viewers.remove(event.player.uniqueId)
    }

}