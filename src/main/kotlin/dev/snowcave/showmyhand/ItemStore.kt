package dev.snowcave.showmyhand

import org.bukkit.inventory.ItemStack
import java.util.*

class ItemStore {

    private val itemStore = HashMap<String, ItemStack>()
    private val keyOrder = LinkedList<String>()

    fun storeItem(item: ItemStack): String {
        val key = UUID.randomUUID().toString()
        itemStore[key] = item.clone()
        keyOrder.push(key)
        if(itemStore.size > 100){
            val oldest = keyOrder.poll()
            itemStore.remove(oldest)
        }
        return key
    }

    fun hasItem(key: String): Boolean {
        return itemStore.contains(key)
    }

    fun getItem(key: String): ItemStack? {
        return itemStore[key]
    }
}