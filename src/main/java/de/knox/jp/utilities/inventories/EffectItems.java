package de.knox.jp.utilities.inventories;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.SortedMap;

public class EffectItems {

	public static final SortedMap<String, ItemStack> list;

	static {
		list = new SortedMap<>();

		list.put("NONE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BARRIER, "§8Keine Partikel"), "particle", "NONE"));
		list.put("SMOKE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.INK_SACK, "§8Rauch"), "particle", "SMOKE"));
		list.put("ENDER_SIGNAL", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.EYE_OF_ENDER, "§5Enderauge"), "particle", "ENDER_SIGNAL"));
		list.put("FIREWORKS_SPARK", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREWORK, "§fSternfunke"), "particle", "FIREWORKS_SPARK"));
		list.put("CRIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.WOOD_SWORD, "§7Crit"), "particle", "CRIT"));
		list.put("MAGIC_CRIT", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.DIAMOND_SWORD, "§8Magischer Crit"), "particle", "MAGIC_CRIT"));
		list.put("WITCH_MAGIC", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.RED_ROSE, "§dMagie", 1, (short) 2), "particle", "WITCH_MAGIC"));
		list.put("PORTAL", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.OBSIDIAN, "§5Portal"), "particle", "PORTAL"));
		list.put("FLYING_GLYPH", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BOOKSHELF, "§fHieroglyphen"), "particle", "FLYING_GLYPH"));
		list.put("LAVA_POP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREBALL, "§cLava Pop"), "particle", "LAVA_POP"));
		list.put("EXPLOSION_HUGE", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.TNT, "§4Große Explosion"), "particle", "EXPLOSION_HUGE"));
		list.put("EXPLOSION", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SULPHUR, "§cExplosion"), "particle", "EXPLOSION"));
		list.put("VOID_FOG", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.BEDROCK, "§8Void"), "particle", "VOID_FOG"));
		list.put("WATERDRIP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.WATER_BUCKET, "§9Wasser"), "particle", "WATERDRIP"));
		list.put("LAVADRIP", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.LAVA_BUCKET, "§eLava"), "particle", "LAVADRIP"));
		list.put("SLIME", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SLIME_BALL, "§aSchleim"), "particle", "SLIME"));
		list.put("HEART", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.APPLE, "§cHerzen"), "particle", "HEART"));
		list.put("VILLAGER_THUNDERCLOUD", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.FIREWORK_CHARGE, "§7Blitzwolke"), "particle", "VILLAGER_THUNDERCLOUD"));
		list.put("HAPPY_VILLAGER", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SEEDS, "§aGrüne Funken"), "particle", "HAPPY_VILLAGER"));
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
