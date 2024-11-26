package dev.snowcave.showmyhand

import dev.snowcave.showmyhand.commands.ShowOffCommandHandler
import dev.snowcave.showmyhand.commands.ViewHandCommandHandler
import dev.snowcave.showmyhand.utils.Chatter
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.plugin.java.JavaPlugin

class ShowMyHand : JavaPlugin() {

    object Settings {
        const val BSTATS_ID = 24005
    }

    override fun onEnable() {
        super.onEnable()
        Metrics(this, Settings.BSTATS_ID)
        val store = ItemStore()
        this.getCommand(ShowOffCommandHandler.Settings.SHOWOFF_COMMAND)?.setExecutor(ShowOffCommandHandler(store))
        val viewCommandHandler = ViewHandCommandHandler(store);
        this.getCommand(ViewHandCommandHandler.Settings.VIEWHAND_COMMAND)?.setExecutor(viewCommandHandler)
        Bukkit.getServer().pluginManager.registerEvents(viewCommandHandler, this)
    }

}