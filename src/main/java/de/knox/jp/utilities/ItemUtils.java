package de.knox.jp.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.knox.jp.JumpPads;

public class ItemUtils {

	public static final ItemStack FILL_GLASS;

	static {
		FILL_GLASS = getItem(Material.STAINED_GLASS_PANE, "§0", 1, (short) 15);
	}

	public static ItemStack getItem(Material material, int amount) {
		return new ItemStack(material, amount);
	}

	public static ItemStack getItem(Material material, short damage) {
		return new ItemStack(material, 1, damage);
	}

	public static ItemStack getItem(Material material, String displayName) {
		return setDisplayName(new ItemStack(material), displayName);
	}

	public static ItemStack getItem(Material material, String displayName, int amount) {
		return setDisplayName(getItem(material, amount), displayName);
	}

	public static ItemStack getItem(Material material, String displayName, int amount, short damage) {
		return setDamage(setDisplayName(getItem(material, amount), displayName), damage);
	}

	public static ItemStack getItem(Material material, String displayName, int amount, Ench... enchantments) {
		return addEnchantments(setDisplayName(getItem(material, amount), displayName), enchantments);
	}

	public static ItemStack getItem(Material material, String displayName, int amount, short damage,
			Ench... enchantments) {
		return addEnchantments(setDamage(setDisplayName(getItem(material, amount), displayName), damage), enchantments);
	}

	public static ItemStack getItem(Material material, String displayName, String... lore) {
		return setLore(getItem(material, displayName), lore);
	}

	public static ItemStack setType(ItemStack itemStack, Material material) {
		ItemStack itemStack2 = itemStack.clone();
		itemStack2.setType(material);
		return itemStack2;
	}

	public static ItemStack setDisplayName(ItemStack itemStack, String displayName) {
		ItemStack itemStack2 = itemStack.clone();
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemStack2.setItemMeta(itemMeta);
		return itemStack2;
	}

	public static ItemStack setAmount(ItemStack itemStack, int amount) {
		ItemStack itemStack2 = itemStack.clone();
		itemStack2.setAmount(amount);
		return itemStack2;
	}

	public static ItemStack setDamage(ItemStack itemStack, int damage) {
		ItemStack itemStack2 = itemStack.clone();
		itemStack2.setDurability((short) damage);
		return itemStack2;
	}

	public static ItemStack addEnchantments(ItemStack itemStack, Ench... parameters) {
		ItemStack itemStack2 = itemStack.clone();
		ItemMeta itemMeta = itemStack.getItemMeta();

		for (int i = 0; i < parameters.length; i++)
			itemMeta.addEnchant(parameters[i].getEnchantment(), parameters[i].getLevel(), parameters[i].isVisible());

		itemStack2.setItemMeta(itemMeta);
		return itemStack2;
	}

	public static ItemStack setLore(ItemStack itemStack, String... parameters) {
		ItemStack itemStack2 = itemStack.clone();
		ItemMeta itemMeta = itemStack.getItemMeta();

		List<String> list = new ArrayList<>();
		for (String string : parameters)
			list.add(string);

		itemMeta.setLore(list);
		itemStack2.setItemMeta(itemMeta);
		return itemStack2;
	}

	public static ItemStack glow(ItemStack itemStack) {
		ItemStack itemStack2 = itemStack.clone();
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (itemStack2.getType() == Material.POTION) {
			itemStack2.setDurability((short) 8230);
			itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		} else {
			itemMeta.addEnchant(Enchantment.WATER_WORKER, 0, false);
		}
		itemStack2.setItemMeta(itemMeta);
		return itemStack2;
	}

	public static ItemStack setNBTDataTag(ItemStack stack, String key, Object value) {
		try {
			Object nmsStack = Class.forName(
					"org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion() + ".inventory.CraftItemStack")
					.getMethod("asNMSCopy", ItemStack.class).invoke(null, stack);
			Object nbtTagCompound = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTTagCompound");
			Object compound = ((boolean) nmsStack.getClass().getMethod("hasTag").invoke(nmsStack))
					? nmsStack.getClass().getMethod("getTag").invoke(nmsStack) : nbtTagCompound;
			Class<?> nbtTabString = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTTagString");
			Class<?> nbtBase = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTBase");

			Object o = nbtTabString.newInstance();
			Field field = nbtTabString.cast(o).getClass().getDeclaredField("data");
			field.setAccessible(true);
			field.set(o, value);

			compound.getClass().getMethod("set", String.class, nbtBase).invoke(compound, key, o);

			return (ItemStack) Class
					.forName("org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion()
							+ ".inventory.CraftItemStack")
					.getMethod("asBukkitCopy", nmsStack.getClass()).invoke(null, nmsStack);
		} catch (Exception e) {
			return null;
		}
	}

	public static ItemStack setNBTDataTag(ItemStack stack, Object... keysAndValues) {
		try {
			Object nmsStack = Class.forName(
					"org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion() + ".inventory.CraftItemStack")
					.getMethod("asNMSCopy", ItemStack.class).invoke(null, stack);
			Object nbtTagCompound = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTTagCompound");
			Object compound = ((boolean) nmsStack.getClass().getMethod("hasTag").invoke(nmsStack))
					? nmsStack.getClass().getMethod("getTag").invoke(nmsStack) : nbtTagCompound;
			Class<?> nbtTabString = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTTagString");
			Class<?> nbtBase = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTBase");

			for (int i = 0; i < keysAndValues.length;) {
				String key = keysAndValues[i++].toString();
				Object o = nbtTabString.newInstance();
				Field field = nbtTabString.cast(o).getClass().getDeclaredField("data");
				field.setAccessible(true);
				field.set(o, keysAndValues[i++].toString());

				compound.getClass().getMethod("set", String.class, nbtBase).invoke(compound, key, o);
			}
			return (ItemStack) Class
					.forName("org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion()
							+ ".inventory.CraftItemStack")
					.getMethod("asBukkitCopy", nmsStack.getClass()).invoke(null, nmsStack);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getNBTDataTag(ItemStack stack, String key) {
		try {
			Object nmsStack = Class.forName(
					"org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion() + ".inventory.CraftItemStack")
					.getMethod("asNMSCopy", ItemStack.class).invoke(null, stack);
			Object nbtTagCompound = Class
					.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".NBTTagCompound");
			Object compound = ((boolean) nmsStack.getClass().getMethod("hasTag").invoke(nmsStack))
					? nmsStack.getClass().getMethod("getTag").invoke(nmsStack) : nbtTagCompound;
			return (String) compound.getClass().getMethod("getString", String.class).invoke(compound, key);
		} catch (Exception e) {
			return null;
		}
	}

	public static ItemStack getSkullByGameProfile(GameProfile profile) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta headMeta = head.getItemMeta();
		Field profileField = null;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack getUsingSkull(Player player) {
		try {
			Class<?> p = Class.forName(
					"org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion() + ".entity.CraftPlayer");
			Object cPlayer = p.cast(player);
			Object handle = p.getMethod("getHandle").invoke(cPlayer);
			return getSkullByGameProfile(((GameProfile) handle.getClass().getMethod("getProfile").invoke(handle)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ItemStack getSkull(String value, String signature) {
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", value, signature));
		return getSkullByGameProfile(profile);
	}

	public static void fillAir(ItemStack stack, Inventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack itemStack = inventory.getItem(i);
			if (itemStack == null || itemStack.getType() == Material.AIR)
				inventory.setItem(i, stack);
		}
	}

	public static void fillAir(Inventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack itemStack = inventory.getItem(i);
			if (itemStack == null || itemStack.getType() == Material.AIR)
				inventory.setItem(i, FILL_GLASS);
		}
	}

	public static boolean isNone(ItemStack itemStack, boolean fillGlass) {
		return (itemStack == null || (itemStack.equals(ItemUtils.FILL_GLASS) && fillGlass)
				|| itemStack.getType() == Material.AIR);
	}

	public static class Ench {

		private Enchantment enchantment;
		private int level;
		private boolean visible;

		public Ench(Enchantment enchantment, int level, boolean visible) {
			this.enchantment = enchantment;
			this.level = level;
			this.visible = visible;
		}

		public Enchantment getEnchantment() {
			return enchantment;
		}

		public void setEnchantment(Enchantment enchantment) {
			this.enchantment = enchantment;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}
	}
}