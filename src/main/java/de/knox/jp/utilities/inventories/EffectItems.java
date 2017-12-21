package de.knox.jp.utilities.inventories;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.Language;
import de.knox.jp.utilities.SortedMap;

public class EffectItems {

	public static final SortedMap<String, ItemStack> list;

	static {
		list = new SortedMap<>();
		load();
	}

	public static void load() {
		list.clear();

		list.put("NONE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BARRIER, Language.get("effectitem.none")), "particle", "NONE"));
		list.put("SMOKE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.INK_SACK, Language.get("effectitem.smoke")), "particle", "SMOKE"));
		list.put("ENDER_SIGNAL", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.EYE_OF_ENDER, Language.get("effectitem.endereye")), "particle", "ENDER_SIGNAL"));
		list.put("FIREWORKS_SPARK", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREWORK, Language.get("effectitem.whitespark")), "particle", "FIREWORKS_SPARK"));
		list.put("CRIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.WOOD_SWORD, Language.get("effectitem.crit")), "particle", "CRIT"));
		list.put("MAGIC_CRIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.DIAMOND_SWORD, Language.get("effectitem.magiccrit")), "particle", "MAGIC_CRIT"));
		list.put("WITCH_MAGIC", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.RED_ROSE, Language.get("effectitem.magic"), 1, (short) 2), "particle", "WITCH_MAGIC"));
		list.put("PORTAL", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.OBSIDIAN, Language.get("effectitem.portal")), "particle", "PORTAL"));
		list.put("FLYING_GLYPH", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BOOKSHELF, Language.get("effectitem.glyph")), "particle", "FLYING_GLYPH"));
		list.put("LAVA_POP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREBALL, Language.get("effectitem.lavapop")), "particle", "LAVA_POP"));
		list.put("EXPLOSION_HUGE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.TNT, Language.get("effectitem.bigexplosion")), "particle", "EXPLOSION_HUGE"));
		list.put("EXPLOSION", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SULPHUR, Language.get("effectitem.explosion")), "particle", "EXPLOSION"));
		list.put("VOID_FOG", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BEDROCK, Language.get("effectitem.void")), "particle", "VOID_FOG"));
		list.put("WATERDRIP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.WATER_BUCKET, Language.get("effectitem.water")), "particle", "WATERDRIP"));
		list.put("LAVADRIP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.LAVA_BUCKET, Language.get("effectitem.lava")), "particle", "LAVADRIP"));
		list.put("SLIME", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SLIME_BALL, Language.get("effectitem.slime")), "particle", "SLIME"));
		list.put("HEART", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.APPLE, Language.get("effectitem.heart")), "particle", "HEART"));
		list.put("VILLAGER_THUNDERCLOUD", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREWORK_CHARGE, Language.get("effectitem.thundercloud")), "particle", "VILLAGER_THUNDERCLOUD"));
		list.put("HAPPY_VILLAGER", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SEEDS, Language.get("effectitem.happyvillager")), "particle", "HAPPY_VILLAGER"));
	}

	public static ItemStack getItem(Effect effect) {
		ItemStack itemStack = null;
		try {
			itemStack = list.get(effect.name());
		} catch (Exception e) {
			return list.get("NONE");
		}
		return itemStack;
	}
}
