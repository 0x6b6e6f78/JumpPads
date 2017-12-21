package de.knox.jp.utilities;

import java.util.Map;

import com.google.common.collect.Maps;

import de.knox.jp.JumpPads;

public enum Language {

	ENGLISH(), DEUTSCH();

	private static Map<String, String> map;

	static {
		map = Maps.newHashMap();

		add(DEUTSCH, "paditem.name", "§7Es ist ein");
		add(DEUTSCH, "paditem.lore", "§eJumpPad");
		add(DEUTSCH, "plateitem.name", "§7Es ist eine");
		add(DEUTSCH, "plateitem.lore", "§6Goldene Druckplatte");
		add(DEUTSCH, "vectorsettingitem", "§7Vektoreinstellungen");
		add(DEUTSCH, "blocksettingitem", "§7Blockeinstellungen");
		add(ENGLISH, "languageitem.name", "§7Sprache:");
		add(DEUTSCH, "saveitem", "§bSpeichern");
		add(DEUTSCH, "savingitem", "§bSpeichert");
		add(DEUTSCH, "nextpage", "§7Nächste Seite");
		add(DEUTSCH, "lastpage", "§7Vorherige Seite");

		add(DEUTSCH, "effectitem.none", "§8Keine Partikel");
		add(DEUTSCH, "effectitem.smoke", "§8Rauch");
		add(DEUTSCH, "effectitem.endereye", "§5Enderauge");
		add(DEUTSCH, "effectitem.whitespark", "§fSternenfunken");
		add(DEUTSCH, "effectitem.crit", "§7Crit");
		add(DEUTSCH, "effectitem.magiccrit", "§8Magischer Crit");
		add(DEUTSCH, "effectitem.magic", "§dMagie");
		add(DEUTSCH, "effectitem.portal", "§5Portal");
		add(DEUTSCH, "effectitem.glyph", "§fHieroglyphen");
		add(DEUTSCH, "effectitem.lavapop", "§cLava Pop");
		add(DEUTSCH, "effectitem.bigexplosion", "§4Große Explosion");
		add(DEUTSCH, "effectitem.explosion", "§cExplosion");
		add(DEUTSCH, "effectitem.void", "§8Void");
		add(DEUTSCH, "effectitem.water", "§9Wasser");
		add(DEUTSCH, "effectitem.lava", "§eLava");
		add(DEUTSCH, "effectitem.slime", "§aSchleim");
		add(DEUTSCH, "effectitem.heart", "§cHerzen");
		add(DEUTSCH, "effectitem.thundercloud", "§7Blitzwolke");
		add(DEUTSCH, "effectitem.happyvillager", "§aGrüne Funken");

		add(DEUTSCH, "sounditem.none", "§8Kein Ton");
		add(DEUTSCH, "sounditem.wither", "§8Wither");
		add(DEUTSCH, "sounditem.arrow", "§7Pfeil");
		add(DEUTSCH, "sounditem.bat", "§8Fledermaus");
		add(DEUTSCH, "sounditem.burp", "§6Rülpser");
		add(DEUTSCH, "sounditem.egg", "§eEi");
		add(DEUTSCH, "sounditem.click", "§7Klick");
		add(DEUTSCH, "sounditem.creeper", "§2Creeper");
		add(DEUTSCH, "sounditem.drink", "§3Trinken");
		add(DEUTSCH, "sounditem.eat", "§6Essen");
		add(DEUTSCH, "sounditem.enderdragon", "§5Enderdrache");
		add(DEUTSCH, "sounditem.wings", "§dFlügelschlag");
		add(DEUTSCH, "sounditem.teleport", "§5Teleportieren");
		add(DEUTSCH, "sounditem.explode", "§fExplosion");
		add(DEUTSCH, "sounditem.firework", "§7Feuerwerk");
		add(DEUTSCH, "sounditem.fizz", "§cFizzzz");
		add(DEUTSCH, "sounditem.slime", "§aSlime");

		add(ENGLISH, "paditem.name", "§7It's a");
		add(ENGLISH, "paditem.lore", "  §eJumpPad");
		add(ENGLISH, "plateitem.name", "§7It's a");
		add(ENGLISH, "plateitem.lore", "  §6Golden Plate");
		add(ENGLISH, "vectorsettingitem", "§7Vectorsettings");
		add(ENGLISH, "blocksettingitem", "§7Blocksettings");
		add(ENGLISH, "languageitem.name", "§7Language:");
		add(ENGLISH, "saveitem", "§bSave");
		add(ENGLISH, "savingitem", "§bSaving");
		add(ENGLISH, "nextpage", "§7Next Page");
		add(ENGLISH, "lastpage", "§7Last Page");

		add(ENGLISH, "effectitem.none", "§8No Particles");
		add(ENGLISH, "effectitem.smoke", "§8Smoke");
		add(ENGLISH, "effectitem.endereye", "§5Endereye");
		add(ENGLISH, "effectitem.whitespark", "§fStarsparks");
		add(ENGLISH, "effectitem.crit", "§7Crit");
		add(ENGLISH, "effectitem.magiccrit", "§8Magic Crit");
		add(ENGLISH, "effectitem.magic", "§dMagic");
		add(ENGLISH, "effectitem.portal", "§5Portal");
		add(ENGLISH, "effectitem.glyph", "§fGlyph");
		add(ENGLISH, "effectitem.lavapop", "§cLava Pop");
		add(ENGLISH, "effectitem.bigexplosion", "§4Big Explosion");
		add(ENGLISH, "effectitem.explosion", "§cExplosion");
		add(ENGLISH, "effectitem.void", "§8Void");
		add(ENGLISH, "effectitem.water", "§9Water");
		add(ENGLISH, "effectitem.lava", "§eLava");
		add(ENGLISH, "effectitem.slime", "§aSlime");
		add(ENGLISH, "effectitem.heart", "§cHeart");
		add(ENGLISH, "effectitem.thundercloud", "§7Thundercloud");
		add(ENGLISH, "effectitem.happyvillager", "§aGreen Sparks");

		add(ENGLISH, "sounditem.none", "§8No Sound");
		add(ENGLISH, "sounditem.wither", "§8Wither");
		add(ENGLISH, "sounditem.arrow", "§7Arrow");
		add(ENGLISH, "sounditem.bat", "§8Bat");
		add(ENGLISH, "sounditem.burp", "§6Burp");
		add(ENGLISH, "sounditem.egg", "§eEgg");
		add(ENGLISH, "sounditem.click", "§7Click");
		add(ENGLISH, "sounditem.creeper", "§2Creeper");
		add(ENGLISH, "sounditem.drink", "§3Drink");
		add(ENGLISH, "sounditem.eat", "§6Eat");
		add(ENGLISH, "sounditem.enderdragon", "§5Enderdragon");
		add(ENGLISH, "sounditem.wings", "§dWings");
		add(ENGLISH, "sounditem.teleport", "§5Teleport");
		add(ENGLISH, "sounditem.explode", "§fExplosion");
		add(ENGLISH, "sounditem.firework", "§7Firework");
		add(ENGLISH, "sounditem.fizz", "§cFizzzz");
		add(ENGLISH, "sounditem.slime", "§aSlime");
	}

	public static void add(Language language, String key, String value) {
		map.put(language.name().toLowerCase() + "." + key, value);
	}

	public static String get(Language language, String key) {
		return map.get(language.name().toLowerCase() + "." + key);
	}

	public static String get(String key) {
		return map.get(JumpPads.getInstance().getLanguage().name().toLowerCase() + "." + key);
	}
}
