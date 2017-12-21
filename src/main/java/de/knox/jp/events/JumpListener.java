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
import org.bukkit.util.Vector;

import de.knox.jp.JumpPads;
import de.knox.jp.utilities.ItemUtils;
import de.knox.jp.utilities.JumpPadEvent;
import de.knox.jp.utilities.JsonJumpPads.JumpPadMetadata;
import net.minecraft.server.v1_8_R3.BlockPosition;

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
			if (ItemUtils.getNBTDataTag(inventory.getItem(8), "type").equals("jumppad"))
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
			BlockPosition blockPosition = metadata.getBlockPosition("block");
			vector = new Vector(blockPosition.getX() - location.getBlockX(),
					blockPosition.getY() - location.getBlockY(), blockPosition.getZ() - location.getBlockZ());
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
