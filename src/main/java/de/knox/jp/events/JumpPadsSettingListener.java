package de.knox.jp.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import de.knox.jp.JumpPads;
import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.JsonJumpPads;
import de.knox.jp.utilities.JsonJumpPads.JumpPadMetadata;
import de.knox.jp.utilities.Utils;
import de.knox.jp.utilities.inventories.EffectItems;
import de.knox.jp.utilities.inventories.SoundItems;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;

public class JumpPadsSettingListener implements Listener {

	@Getter
	private static final ItemStack rawUp;
	@Getter
	private static final ItemStack rawDown;
	@Getter
	private static final ItemStack rawLeft;
	@Getter
	private static final ItemStack rawRight;
	@Getter
	private static final ItemStack rawExclamation;

	@Getter
	private static boolean lightSave;

	static {
		rawUp = ItemUtils.getSkull(
				"eyJ0aW1lc3RhbXAiOjE1MTM2ODE1OTgxNjIsInByb2ZpbGVJZCI6ImZlZjAzOWVmZTZjZDQ5ODc5Yzg0MjZhM2U2MTM0Mjc3IiwicHJvZmlsZU5hbWUiOiJNSEZfQXJyb3dVcCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQ4Yjc2OGM2MjM0MzJkZmIyNTlmYjNjMzk3OGU5OGRlYzExMWY3OWRiZDZjZDg4ZjIxMTU1Mzc0YjcwYjNjIn19fQ==",
				"RB/eV17/7LJwHbQGC7fv7EXTbK0aBR21UhIz8yJ+DQBya281PXDFYWO0XG+PY2XsB7ridA9dEl6B4v4Aa10fweC7mcDIZyYHIwqBG1Anf69xrs4V58Xs6qu5QlYh3z6Y/7Y0nF5Af5Z50CeS9lQ9JahXLZmfYBwZhYlS48aPJRsQeKNd4bk+1XE/EJ88d3jV4x8o+3UcSuogiBX0tXr/Q/IWi6+H3xNSTWffyJci83cvV4Mixn+oLieI0/deA+ETW3pKcGlUemsnXvaB0cxKMeUpvFTVYS8tP5Or5UvGFswHn89Wk0tCd5T//5sbJoPov+vBrIhELcYDpTtBW0ogHqE4q/5wVSNnqn0VHPiWK0T/d6Ujh/Rqr430wWpEzgIk/uKMoSHR57t+o0xWsTkXAwQvSUOu+jzWnpWDMxaVHmTQeKKdLQg1slrUyXncY+XTIgvneFFWJSiQN032YbhyTY0AHapwfZa1DQxXK1ZDLdNsxZXtAV9alSxYav7VAfGLbuvURT50HQpgJWLfXq2gHWEPLaC4rcJwKN+3D8yxmGfE2cVRAuuGawaPlpTHSUONBOWa1FBznTftwvSiT7CA8AkmMPm23j0XimCi/Qg8fA/gFi8My6sbz6GJMkGIlPoa9R7tg2ziAhixarzcHZWDJGcRprtUN95WwVPZGu/iTPg=");
		rawDown = ItemUtils.getSkull(
				"eyJ0aW1lc3RhbXAiOjE1MTM2ODE2NzMwNjksInByb2ZpbGVJZCI6IjY4ZjU5YjliNWIwYjRiMDVhOWYyZTFkMTQwNWFhMzQ4IiwicHJvZmlsZU5hbWUiOiJNSEZfQXJyb3dEb3duIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZGFkZDc1NWQwODUzNzM1MmJmN2E5M2UzYmI3ZGQ0ZDczMzEyMWQzOWYyZmI2NzA3M2NkNDcxZjU2MTE5NGRkIn19fQ==",
				"lR9jVYq6LZOXnklaSrexE1oN7Hd1q3d2jXt921YidufVWYMvWK8WrTdRwQraLJrj3gwZH7q6wTPCsV2L0VWiWxnc1H4rImEJdtmcvXBSxx2X+ec9o3+SvL7Ts/u+Wpm0OB7FnXbb9ix6LRU042A8ZTb3mc91KGMyKjOWADTVJvgrSP756mrrdEp5lltj8vfilQkys+bQzYmoH/wbLe7eOPkmKZK1uOfHTFYduPq6ydR/fqMzS+DDfPb6bfEV4mJ4+X/sV++XoWxKcbLjoiWuC2rWEYWFx6InWKNtyPT0Wot3AGVGhP48OTsoXk6ZNZaPcyKNPQi937VkchHDURA93MG5YLXNJjm1Cm3aJfXxM4AMu7MShJATo3qWWDObTvEwY1QEa13YKP3Z6RJbq0F8vbqfEs3/Xg9IbfhAAWJu/WKhH3OxiKq68JTzHbSIpQuTsz2YM6Uxhtyb9ynuqfEtTpVFbv3be7pzl7zQs2yCwf6HrPBha9TrEJ5+hhTC2RSav6TBajXN8qGi+mFUbMKd8t72y5/E4BzNLHjEDEiuKHYGhQ2oSMukJhJBSmGms34RX0MdCLIHjOoDKrZG6nttgbNuADASGpvjqjbGC3/9u4KhuTGMuWlmMJPGpRm023o9fMrZySt1B24XRl5fKxBkxseMaoRnK6klNBQCVI6a8uM=");
		rawExclamation = ItemUtils.getSkull(
				"eyJ0aW1lc3RhbXAiOjE1MTM3OTQ2MDA1NTEsInByb2ZpbGVJZCI6ImQzYzQ3ZjZmYWUzYTQ1YzFhZDdjZTJjNzYyYjAzYWU2IiwicHJvZmlsZU5hbWUiOiJNSEZfRXhjbGFtYXRpb24iLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmE1M2JkZDE1NDU1MzFjOWViYjljNmY4OTViYzU3NjAxMmY2MTgyMGU2ZjQ4OTg4NTk4OGE3ZTg3MDlhM2Y0OCJ9fX0=",
				null);
		rawLeft = ItemUtils.getSkull(
				"eyJ0aW1lc3RhbXAiOjE1MTM3OTY5NzcyNzIsInByb2ZpbGVJZCI6ImE2OGYwYjY0OGQxNDQwMDBhOTVmNGI5YmExNGY4ZGY5IiwicHJvZmlsZU5hbWUiOiJNSEZfQXJyb3dMZWZ0IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNlYmY5MDc0OTRhOTM1ZTk1NWJmY2FkYWI4MWJlYWZiOTBmYjliZTQ5YzcwMjZiYTk3ZDc5OGQ1ZjFhMjMifX19",
				null);
		rawRight = ItemUtils.getSkull(
				"eyJ0aW1lc3RhbXAiOjE1MTM3OTcwNDE3MzUsInByb2ZpbGVJZCI6IjUwYzg1MTBiNWVhMDRkNjBiZTlhN2Q1NDJkNmNkMTU2IiwicHJvZmlsZU5hbWUiOiJNSEZfQXJyb3dSaWdodCIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xYjZmMWEyNWI2YmMxOTk5NDY0NzJhZWRiMzcwNTIyNTg0ZmY2ZjRlODMyMjFlNTk0NmJkMmU0MWI1Y2ExM2IifX19",
				null);
		lightSave = false;
	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		JumpPads.getInstance().getPacketReader().inject(event.getPlayer());
	}

	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		JumpPads.getInstance().getPacketReader().uninject(event.getPlayer());
	}

	@EventHandler
	public void blockBreakEvent(BlockBreakEvent event) {
		if (JumpPads.getInstance().getMetadatas().containsKey(event.getBlock().getLocation()))
			JumpPads.getInstance().getMetadatas().remove(event.getBlock().getLocation());
	}

	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (event.getClickedBlock().getType() != Material.GOLD_PLATE)
			return;
		Inventory inventory = getInventory(event.getClickedBlock().getLocation());
		event.getPlayer().openInventory(inventory);
	}

	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent event) {
		Location location;
		ItemStack stack;
		JumpPadMetadata metadata;
		ItemStack slot8;
		try {
			slot8 = event.getClickedInventory().getItem(8);
			String type = ItemUtils.getNBTDataTag(slot8, "type");
			location = JsonJumpPads.getLocationByString(ItemUtils.getNBTDataTag(slot8, "location"));
			if (!type.equals("jumppad"))
				return;
			if (location == null)
				return;
			if (event.getView().getTopInventory() != event.getClickedInventory())
				return;
			event.setCancelled(true);
			metadata = JumpPads.getInstance().getMetadatas().get(location);
			if (ItemUtils.isNone((stack = event.getCurrentItem()), true))
				return;
		} catch (Exception e) {
			return;
		}

		if (event.getSlot() == 0) {
			String key = ItemUtils.getNBTDataTag(stack, "key");
			metadata.put(key, !metadata.getBoolean(key));
			updateHeadLine(event.getClickedInventory(), metadata);
			return;
		}
		if (event.getSlot() == 8) {
			JumpPads.saveJumpPads();
			lightSave = true;
			updateSave(event.getClickedInventory());
			Bukkit.getScheduler().runTaskLater(JumpPads.getInstance(), () -> {
				lightSave = false;
				updateSave(event.getClickedInventory());
			}, 8);
			return;
		}
		if (event.getSlot() < 9) {
			updateLowerInventory(event.getClickedInventory(), metadata,
					ItemUtils.getNBTDataTag(event.getCurrentItem(), "state"));
			return;
		}

		if (event.getSlot() >= 9 * 2) {
			String state = ItemUtils.getNBTDataTag(slot8, "state");
			if (state.equals("vector")) {
				int row = ((int) event.getSlot() / 9);
				int column = event.getSlot() % 9;
				int add;
				if (stack.getType() == Material.SKULL_ITEM) {
					if (column < 5) {
						add = (int) (1000 * Math.pow(10, -column + 1));
						if (row == 4)
							add = -add;
						int velocity = Utils.maxOut(metadata.getInt("velocity") + add, 9999, true);
						metadata.put("velocity", velocity);
						updateVelocity(event.getClickedInventory(), metadata);
					} else {
						add = (int) (10 * Math.pow(10, -column + 6));
						if (row == 4)
							add = -add;
						int velocityY = Utils.maxOut(metadata.getInt("velocityY") + add, 99, true);
						metadata.put("velocityY", velocityY);
						updateVelocityY(event.getClickedInventory(), metadata);
					}
				}
			} else if (state.equals("block")) {
				((CraftPlayer) event.getWhoClicked()).getHandle().playerConnection
						.sendPacket(new PacketPlayOutOpenSignEditor(
								new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())));
			} else if (state.equals("sound") || state.equals("particle")) {
				int page = Integer.parseInt(ItemUtils.getNBTDataTag(slot8, "page"));
				try {
					page = Integer.parseInt(ItemUtils.getNBTDataTag(event.getCurrentItem(), "page"));
				} catch (Exception e) {
					String name = ItemUtils.getNBTDataTag(stack, state);
					if (name.equals("NONE"))
						metadata.remove(state);
					else
						metadata.put(state, name);
				}
				updateHeadLine(event.getClickedInventory(), metadata, state, page);
				if (state.equals("sound"))
					updateSound(event.getClickedInventory(), metadata, page);
				else
					updateParticle(event.getClickedInventory(), metadata, page);
			}
		}
	}

	public static Inventory getInventory(Location location) {
		Inventory inventory = Bukkit.createInventory(null, 5 * 9, "§6JumpPads");
		JumpPadMetadata metadata = JumpPads.getInstance().getMetadatas().get(location);
		updateLowerInventory(inventory, metadata, metadata.getString("state"));
		return inventory;
	}

	public static void updateBlock(Inventory inventory, JumpPadMetadata metadata) {
		BlockPosition position = metadata.getBlockPosition("block");
		ItemStack block = ItemUtils.setNBTDataTag(ItemUtils.getItem(Material.SIGN, "§7Block", "  X: " + position.getX(),
				"  Y: " + position.getY(), "  Z: " + position.getZ()));

		inventory.setItem(9 * 3 + 4, block);
	}

	public static void updateHeadLine(Inventory inventory, JumpPadMetadata metadata, String state, int page) {
		boolean isPad = metadata.getBoolean("jumppad");
		ItemStack isPadItem = ItemUtils.setNBTDataTag(ItemUtils.getItem(isPad ? Material.FIREWORK : Material.GOLD_PLATE,
				"§7Es ist ein" + (isPad ? "" : "e"), isPad ? "§eJumpPad" : "§6Goldene Druckplatte"), "key", "jumppad");

		ItemStack vector = ItemUtils.setNBTDataTag(ItemUtils.setDisplayName(rawRight, "§7Vektoreinstellungen"), "state",
				"vector");
		ItemStack block = ItemUtils.setNBTDataTag(ItemUtils.setDisplayName(rawDown, "§7Blockeinstellungen"), "state",
				"block");
		ItemStack sound = ItemUtils.setNBTDataTag(SoundItems.getItem(metadata.getSound("sound")), "state", "sound");
		ItemStack particle = ItemUtils.setNBTDataTag(EffectItems.getItem(metadata.getEffect("particle")), "state",
				"particle");

		if (state.equals("vector"))
			vector = ItemUtils.glow(vector);
		if (state.equals("block"))
			block = ItemUtils.glow(block);
		if (state.equals("sound"))
			sound = ItemUtils.glow(sound);
		if (state.equals("particle"))
			particle = ItemUtils.glow(particle);

		inventory.setItem(0, isPadItem);
		inventory.setItem(2, vector);
		inventory.setItem(3, block);
		inventory.setItem(5, sound);
		inventory.setItem(6, particle);
		updateSave(inventory, metadata, state, page);

		ItemUtils.fillAir(inventory);
	}

	public static void updateSave(Inventory inventory, JumpPadMetadata metadata, String state, int page) {
		ItemStack save = ItemUtils.setNBTDataTag(
				ItemUtils.getItem((lightSave ? Material.TRIPWIRE_HOOK : Material.REDSTONE_TORCH_ON),
						(lightSave ? "§bSpeichert" : "§bSpeichern")),
				"location", JsonJumpPads.getStringByLocation(metadata.getLocation()), "type", "jumppad", "state", state,
				"page", page);
		inventory.setItem(8, save);
	}

	public static void updateSave(Inventory inventory) {
		ItemStack save = ItemUtils.setType(
				ItemUtils.setDisplayName(inventory.getItem(8), (lightSave ? "§bSpeichert" : "§bSpeichern")),
				(lightSave ? Material.TRIPWIRE_HOOK : Material.REDSTONE_TORCH_ON));
		inventory.setItem(8, save);
	}

	public static void updateVelocityV(Inventory inventory, JumpPadMetadata metadata, int itemAmount, int add,
			String key, String name) {
		String v = JumpPads.fill0(metadata.getInt(key), itemAmount);

		for (int i = 0; i < itemAmount; i++) {
			String s = "";
			for (int j = 0; j < v.length(); j++)
				s += "§" + (i == j ? "e" : "7") + v.charAt(j);
			s = "§7" + name + ": " + s.substring(0, (itemAmount * 3) / 2) + "§7," + s.substring((itemAmount * 3) / 2);
			ItemStack up = ItemUtils.setDisplayName(rawUp, s);
			ItemStack down = ItemUtils.setDisplayName(rawDown, s);

			inventory.setItem(i + 9 * 2 + add, up);
			inventory.setItem(i + 9 * 3 + add, ItemUtils.getItem(Material.STAINED_CLAY, s,
					Integer.parseInt(("" + v.charAt(i)).toString()), (short) 5));
			inventory.setItem(i + 9 * 4 + add, down);
		}
	}

	public static void updateSetting(Inventory inventory, JumpPadMetadata metadata, int page, Class<?> clazz,
			ItemStack selected, Iterable<ItemStack> items) {
		for (int i = 0; i < 27; i++)
			inventory.setItem(i + 18, ItemUtils.FILL_GLASS);
		List<ItemStack> list = Lists.newArrayList(items);
		if (page == 0 && list.size() <= 27) {
			for (int i = 0; i < list.size(); i++) {
				ItemStack itemStack = list.get(i);
				if (itemStack.equals(selected))
					itemStack = ItemUtils.glow(itemStack);
				inventory.setItem(18 + i, itemStack);
			}
		} else {
			for (int i = 0; i < 18 && i < list.size() - page * 18; i++) {
				ItemStack itemStack = list.get(i + page * 18);
				if (itemStack.equals(selected))
					itemStack = ItemUtils.glow(itemStack);
				inventory.setItem(18 + i, itemStack);
			}
			if (page > 0)
				inventory.setItem(36, ItemUtils.setNBTDataTag(ItemUtils.setDisplayName(rawLeft, "§7Vorherige Seite"),
						"page", page - 1));
			if (list.size() - page * 18 > 18)
				inventory.setItem(44, ItemUtils.setNBTDataTag(ItemUtils.setDisplayName(rawRight, "§7Nächste Seite"),
						"page", page + 1));
		}
	}

	public static void updateHeadLine(Inventory inventory, JumpPadMetadata metadata) {
		updateHeadLine(inventory, metadata, ItemUtils.getNBTDataTag(inventory.getItem(8), "state"),
				Integer.parseInt(ItemUtils.getNBTDataTag(inventory.getItem(8), "page")));
	}

	public static void updateVelocityY(Inventory inventory, JumpPadMetadata metadata) {
		updateVelocityV(inventory, metadata, 2, 6, "velocityY", "VelocityY");
	}

	public static void updateVelocity(Inventory inventory, JumpPadMetadata metadata) {
		updateVelocityV(inventory, metadata, 4, 1, "velocity", "Velocity");
	}

	public static void updateSound(Inventory inventory, JumpPadMetadata metadata, int page) {
		updateSetting(inventory, metadata, page, SoundItems.class, SoundItems.getItem(metadata.getSound("sound")),
				SoundItems.list.values());
	}

	public static void updateParticle(Inventory inventory, JumpPadMetadata metadata, int page) {
		updateSetting(inventory, metadata, page, EffectItems.class, EffectItems.getItem(metadata.getEffect("particle")),
				EffectItems.list.values());
	}

	public static void updateLowerInventory(Inventory inventory, JumpPadMetadata metadata, String state) {
		if (state == null)
			state = metadata.getString("state");
		inventory.clear();
		if (state.equals("vector")) {
			updateVelocity(inventory, metadata);
			updateVelocityY(inventory, metadata);
			metadata.put("state", state);
		} else if (state.equals("block")) {
			updateBlock(inventory, metadata);
			metadata.put("state", state);
		} else if (state.equals("sound"))
			updateSound(inventory, metadata, 0);
		else if (state.equals("particle"))
			updateParticle(inventory, metadata, 0);

		updateHeadLine(inventory, metadata, state, 0);
	}
}
