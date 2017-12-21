package de.knox.jp.utilities.inventories;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import de.knox.jp.events.JumpPadsSettingListener;
import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.SortedMap;

public class SoundItems {

	public static final SortedMap<String, ItemStack> list;

	static {
		list = new SortedMap<>();

		list.put("NONE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BARRIER, "§8Kein Ton"), "sound", "NONE"));
		list.put("WITHER_SHOOT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SKULL_ITEM, "§8Wither", 1, (short) 1), "sound", "WITHER_SHOOT"));
		list.put("ARROW_HIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.ARROW, "§7Pfeil"), "sound", "ARROW_HIT"));
		list.put("BAT_DEATH", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.MONSTER_EGG, "§8Fledermaus", 1, (short) 65), "sound", "BAT_DEATH"));
		list.put("BURP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.APPLE, "§6Rülpser"), "sound", "BURP"));
		list.put("CHICKEN_EGG_POP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.EGG, "§eEi"), "sound", "CHICKEN_EGG_POP"));
		list.put("CLICK", ItemUtils.setNBTDataTag(ItemUtils.setDisplayName(JumpPadsSettingListener.getRawExclamation(), "§7Klick"), "sound", "CLICK"));
		list.put("CREEPER_HISS", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.MONSTER_EGG, "§2Creeper", 1, (short) 50), "sound", "CREEPER_HISS"));
		list.put("DRINK", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.POTION, "§3Trinken"), "sound", "DRINK"));
		list.put("EAT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BREAD, "§6Essen"), "sound", "EAT"));
		list.put("ENDERDRAGON_HIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.DRAGON_EGG, "§5Enderdrache"), "sound", "ENDERDRAGON_HIT"));
		list.put("ENDERDRAGON_WINGS", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.PAPER, "§dFlügelschlag"), "sound", "ENDERDRAGON_WINGS"));
		list.put("ENDERMAN_TELEPORT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.ENDER_PEARL, "§5Teleportieren"), "sound", "ENDERMAN_TELEPORT"));
		list.put("EXPLODE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.TNT, "§fExplosion"), "sound", "EXPLODE"));
		list.put("FIREWORK_BLAST", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREWORK, "§7Feuerwerk"), "sound", "FIREWORK_BLAST"));
		list.put("FIZZ", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREBALL, "§cFizzzz"), "sound", "FIZZ"));
		list.put("MAGMACUBE_JUMP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SLIME_BALL, "§aSlime"), "sound", "MAGMACUBE_JUMP"));
	}

	public static ItemStack getItem(Sound sound) {
		ItemStack itemStack = null;
		try {
			itemStack = list.get(sound.name());
		} catch (Exception e) {
			return list.get("NONE");
		}
		return itemStack;
	}
}
