package de.knox.jp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.knox.jp.events.JumpListener;
import de.knox.jp.events.JumpPadsSettingListener;
import de.knox.jp.utilities.JsonJumpPads;
import de.knox.jp.utilities.JsonJumpPads.JumpPadMetadata;
import de.knox.jp.utilities.PacketReader;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;

public class JumpPads extends JavaPlugin {

	public static String PREFIX;

	@Getter
	private static JumpPads instance;

	@Getter
	private JsonJumpPads metadatas;
	@Getter
	private PacketReader packetReader;

	static {
		PREFIX = "§7[§6JumpPad§7] ";
	}

	public void onEnable() {
		instance = this;

		metadatas = new JsonJumpPads();
		metadatas.load(getConfig().getString("jumppads"));

		packetReader = new PacketReader() {
			public void readPacket(Packet<?> packet, Player player) {
				if (packet instanceof PacketPlayInUpdateSign) {
					PacketPlayInUpdateSign updateSignPacket = (PacketPlayInUpdateSign) packet;
					BlockPosition blockPosition = updateSignPacket.a();
					JumpPadMetadata metadata = metadatas.get(new Location(player.getWorld(), blockPosition.getX(),
							blockPosition.getY(), blockPosition.getZ()));
					IChatBaseComponent[] linesAsComponent = updateSignPacket.b();
					try {
						int x = Integer.parseInt(linesAsComponent[0].getText());
						int y = Integer.parseInt(linesAsComponent[1].getText());
						int z = Integer.parseInt(linesAsComponent[2].getText());
						metadata.put("block", new BlockPosition(x, y, z));
					} catch (Exception e) {
						player.sendMessage(PREFIX + "Bitte gebe dort nur ganze Zahlen an");
					}

					Bukkit.getScheduler().runTask(instance,
							() -> player.openInventory(JumpPadsSettingListener.getInventory(metadata.getLocation())));
				}
			}
		};

		getServer().getPluginManager().registerEvents(new JumpListener(), instance);
		getServer().getPluginManager().registerEvents(new JumpPadsSettingListener(), instance);
	}

	public void onDisable() {
		getConfig().set("jumppads", metadatas.asJson());
		saveConfig();
	}

	public static String fill0(int number, int length) {
		String s = "";
		for (int i = 0; i < length; i++)
			s += "0";
		s = s.substring(0, length - ((Integer) number).toString().length());
		s += number;
		return s;
	}

	public static void saveJumpPads() {
		getInstance().getConfig().set("jumppads", getInstance().getMetadatas().asJson());
		getInstance().saveConfig();
	}
}
