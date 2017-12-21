package de.knox.jp.utilities.inventories;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import de.knox.jp.events.JumpPadsSettingListener;
import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.Language;
import de.knox.jp.utilities.SortedMap;

public class SoundItems {

	public static final SortedMap<String, ItemStack> list;

	static {
		list = new SortedMap<>();
		load();
	}

	public static void load() {
		list.clear();

		list.put("NONE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BARRIER, Language.get("sounditem.none")), "sound", "NONE"));
		list.put("WITHER_SHOOT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SKULL_ITEM, Language.get("sounditem.wither"), 1, (short) 1), "sound", "WITHER_SHOOT"));
		list.put("ARROW_HIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.ARROW, Language.get("sounditem.arrow")), "sound", "ARROW_HIT"));
		list.put("BAT_DEATH", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.MONSTER_EGG, Language.get("sounditem.bat"), 1, (short) 65), "sound", "BAT_DEATH"));
		list.put("BURP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.APPLE, Language.get("sounditem.burp")), "sound", "BURP"));
		list.put("CHICKEN_EGG_POP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.EGG, Language.get("sounditem.egg")), "sound", "CHICKEN_EGG_POP"));
		list.put("CLICK", ItemUtils.setNBTDataTag(ItemUtils.setDisplayName(JumpPadsSettingListener.getRawExclamation(), Language.get("sounditem.click")), "sound", "CLICK"));
		list.put("CREEPER_HISS", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.MONSTER_EGG, Language.get("sounditem.creeper"), 1, (short) 50), "sound", "CREEPER_HISS"));
		list.put("DRINK", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.POTION, Language.get("sounditem.drink")), "sound", "DRINK"));
		list.put("EAT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BREAD, Language.get("sounditem.eat")), "sound", "EAT"));
		list.put("ENDERDRAGON_HIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.DRAGON_EGG, Language.get("sounditem.enderdragon")), "sound", "ENDERDRAGON_HIT"));
		list.put("ENDERDRAGON_WINGS", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.PAPER, Language.get("sounditem.wings")), "sound", "ENDERDRAGON_WINGS"));
		list.put("ENDERMAN_TELEPORT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.ENDER_PEARL, Language.get("sounditem.teleport")), "sound", "ENDERMAN_TELEPORT"));
		list.put("EXPLODE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.TNT, Language.get("sounditem.explode")), "sound", "EXPLODE"));
		list.put("FIREWORK_BLAST", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREWORK, Language.get("sounditem.firework")), "sound", "FIREWORK_BLAST"));
		list.put("FIZZ", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREBALL, Language.get("sounditem.fizz")), "sound", "FIZZ"));
		list.put("MAGMACUBE_JUMP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SLIME_BALL, Language.get("sounditem.slime")), "sound", "MAGMACUBE_JUMP"));
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
