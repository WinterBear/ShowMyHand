package dev.snowcave.showmyhand.commands

import dev.snowcave.showmyhand.ItemStore
import dev.snowcave.showmyhand.utils.Chatter
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull
import dev.snowcave.showmyhand.utils.capitalizeWords
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit

class ShowOffCommandHandler(private val itemStore: ItemStore) : CommandExecutor {

    object Settings {
        const val SHOWOFF_COMMAND = "showoff"
    }

    override fun onCommand(@NotNull sender: CommandSender, @NotNull command: Command, @NotNull alias: String, @NotNull args: Array<out String>): Boolean {
        if(command.name == Settings.SHOWOFF_COMMAND) {
            if (sender !is Player) {
                Chatter.error(sender, "Only players can use this command.")
                return true
            }
            var reciever: Player? = null
            if(args.isNotEmpty()){
                reciever = Bukkit.getPlayer(args[0]);
                if(reciever == null){
                    Chatter.error(sender, "No player could be found named ${args[0]}")
                    return true
                }
            }

            val heldItem = sender.inventory.itemInMainHand
            if(heldItem.type == Material.AIR){
                Chatter.error(sender, "You must be holding an item.")
                return true;
            }

            val itemKey = itemStore.storeItem(heldItem)

            val componentText = Component.text()

            componentText.append(LegacyComponentSerializer.legacyAmpersand().deserialize("&8>> &b"))
            componentText.append(sender.displayName())
            componentText.append(LegacyComponentSerializer.legacyAmpersand().deserialize(" &7shared item &8> &f["))
            val itemComponent = resolveItemName(heldItem)
                .clickEvent(ClickEvent.runCommand("/viewhand $itemKey"))
                .hoverEvent(heldItem.clone().asHoverEvent())
            componentText.append(itemComponent)
            componentText.append(LegacyComponentSerializer.legacyAmpersand().deserialize("&f]"))

            if(reciever != null){
                reciever.sendMessage(componentText.build())
            } else {
                Chatter.broadcast(componentText.build())
            }

            return true
        }
        return false
    }

    private fun resolveItemName(item: ItemStack): Component {
        val itemMeta = item.itemMeta
        if(itemMeta?.displayName() != null){
            return itemMeta.displayName() as Component
        } else {
            val materialName = item.type.name;
            return Component.text(materialName.replace("_", " ").capitalizeWords());
        }
    }
}