package de.knox.jp.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.knox.jp.JumpPads;
import de.knox.jp.json.JsonJumpPads.JumpPadMetadata;
import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.Position;

public class JumpListener implements Listener {

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		if (event.getAction() != Action.PHYSICAL)
			return;
		if (event.getClickedBlock().getType() != Material.GOLD_PLATE)
			return;
		Location location = event.getClickedBlock().getLocation();
		if (!JumpPads.getInstance().getMetadatas().containsKey(location))
			return;
		Player player = event.getPlayer();

		try {
			Inventory inventory = player.getOpenInventory().getTopInventory();
			ItemStack slot8 = inventory.getItem(8).clone();
			if (inventory != null && ItemUtils.getNBTDataTag(slot8, "type").equals("jumppad"))
				return;
		} catch (Exception e) {
		}

		JumpPadMetadata metadata = JumpPads.getInstance().getMetadatas().get(location);
		boolean jump = metadata.getBoolean("jumppad");
		Sound sound = metadata.getSound("sound");
		Effect particle = metadata.getEffect("particle");
		Vector vector;
		boolean isVector = metadata.getString("state").equals("vector");

		if (isVector) {
			double velocity = metadata.getInt("velocity") * 1e-2d;
			double y = metadata.getInt("velocityY") * 1e-1d;
			vector = player.getLocation().getDirection().multiply(velocity).setY(y);
		} else {
			Position position = metadata.getPosition("block");
			vector = new Vector(position.getX() - location.getBlockX(),
					position.getY() - location.getBlockY(), position.getZ() - location.getBlockZ());
		}

		Bukkit.getScheduler().runTask(JumpPads.getInstance(), () -> {
			if (jump) {
				JumpPadEvent jumpPadEvent = new JumpPadEvent(player, location, sound, particle, vector, isVector, metadata);
				Bukkit.getServer().getPluginManager().callEvent(jumpPadEvent);
				if (jumpPadEvent.isCancelled())
					return;

				Random random = new Random();
				if (sound != null)
					player.playSound(player.getLocation(), sound, 1, 1);
				if (particle != null)
					for (int i = 0; i < 10; i++) {
						double x = random.nextInt(10) / 10D;
						double z = random.nextInt(10) / 10D;
						player.playEffect(location.clone().add(x, 0, z), particle, 10);
					}
				player.setVelocity(vector);
			}
		});
	}
}
