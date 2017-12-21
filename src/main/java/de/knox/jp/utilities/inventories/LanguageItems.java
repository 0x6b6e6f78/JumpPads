package de.knox.jp.utilities.inventories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.Language;
import de.knox.jp.utilities.SortedMap;

public class LanguageItems {

	public static final SortedMap<String, ItemStack> list;

	static {
		list = new SortedMap<>();
		load();
	}

	public static void load() {
		list.clear();

		list.put("ENGLISH", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.PAPER, Language.ENGLISH.name()), "languages", "ENGLISH"));
		list.put("DEUTSCH", ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.PAPER, Language.DEUTSCH.name()), "languages", "DEUTSCH"));
	}

	public static ItemStack getItem(Language language) {
		ItemStack itemStack = null;
		try {
			itemStack = list.get(language.name());
		} catch (Exception e) {
			return list.get("NONE");
		}
		return itemStack;
	}
}
