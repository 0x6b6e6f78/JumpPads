package de.knox.jp;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.knox.jp.events.JumpListener;
import de.knox.jp.events.JumpPadsSettingListener;
import de.knox.jp.json.JsonJumpPads;
import de.knox.jp.json.JsonJumpPads.JumpPadMetadata;
import de.knox.jp.utilities.Language;
import de.knox.jp.utilities.PacketReader;
import de.knox.jp.utilities.Position;
import lombok.Getter;
import lombok.Setter;

public class JumpPads extends JavaPlugin {

	public static final String PREFIX;

	@Getter
	private static JumpPads instance;

	@Getter
	private JsonJumpPads metadatas;
	@Getter
	private PacketReader packetReader;

	@Getter
	@Setter
	private Language language;

	@Getter
	private final String nmsVersion;

	static {
		PREFIX = "§7[§6JumpPad§7] ";
	}

	{
		nmsVersion = getVersion();
	}

	public void onEnable() {
		instance = this;

		metadatas = new JsonJumpPads();
		metadatas.load(getConfig().getString("jumppads"));

		getConfig().options().copyDefaults(true);
		getConfig().addDefault("language", "english");
		saveConfig();

		language = Language.valueOf(getConfig().getString("language").toUpperCase());

		packetReader = new PacketReader() {
			public void readPacket(Object packet, Player player) {
				try {
					Class<?> signPacketClass = Class
							.forName("net.minecraft.server." + nmsVersion + ".PacketPlayInUpdateSign");
					if (packet.getClass().getName().equals(signPacketClass.getName())) {
						Object updateSignPacket = signPacketClass.cast(packet);
						Class<?> blockPositionClass = Class
								.forName("net.minecraft.server." + nmsVersion + ".BlockPosition");
						Object blockPosition = signPacketClass.cast(updateSignPacket).getClass().getMethod("a")
								.invoke(updateSignPacket);
						int bx = (int) blockPositionClass.cast(blockPosition).getClass().getMethod("getX")
								.invoke(blockPosition);
						int by = (int) blockPositionClass.cast(blockPosition).getClass().getMethod("getY")
								.invoke(blockPosition);
						int bz = (int) blockPositionClass.cast(blockPosition).getClass().getMethod("getZ")
								.invoke(blockPosition);
						JumpPadMetadata metadata = metadatas.get(new Location(player.getWorld(), bx, by, bz));
						Object[] linesAsComponent = (Object[]) signPacketClass.cast(updateSignPacket).getClass()
								.getMethod("b").invoke(updateSignPacket);
						Class<?> chatCompentenClass = Class
								.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
						try {
							double x = Double.parseDouble((String) chatCompentenClass.cast(linesAsComponent[0])
									.getClass().getMethod("getText").invoke(linesAsComponent[0]));
							double y = Double.parseDouble((String) chatCompentenClass.cast(linesAsComponent[1])
									.getClass().getMethod("getText").invoke(linesAsComponent[1]));
							double z = Double.parseDouble((String) chatCompentenClass.cast(linesAsComponent[2])
									.getClass().getMethod("getText").invoke(linesAsComponent[2]));
							metadata.put("block", new Position(x, y, z));
						} catch (Exception e) {
							player.sendMessage(PREFIX + "Bitte gebe dort nur ganze Zahlen an");
						}

						Bukkit.getScheduler().runTask(instance, () -> player
								.openInventory(JumpPadsSettingListener.getInventory(metadata.getLocation())));
					}
				} catch (Exception e) {
					e.printStackTrace();
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

	public static void sendSignPacket(Player player, Location location) {
		try {
			Class<?> p = Class
					.forName("org.bukkit.craftbukkit." + getInstance().getNmsVersion() + ".entity.CraftPlayer");
			Object cPlayer = p.cast(player);
			Object handle = p.getMethod("getHandle").invoke(cPlayer);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

			Class<?> signPacketClass = Class
					.forName("net.minecraft.server." + getInstance().getNmsVersion() + ".PacketPlayOutOpenSignEditor");
			Class<?> blockPositionClass = Class
					.forName("net.minecraft.server." + getInstance().getNmsVersion() + ".BlockPosition");

			Object blockPosition = blockPositionClass.getConstructor(int.class, int.class, int.class)
					.newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());
			Object signPacket = signPacketClass.newInstance();
			Field field = signPacketClass.getDeclaredField("a");
			field.setAccessible(true);
			field.set(signPacket, blockPositionClass.cast(blockPosition));
			Class<?> packetClass = Class.forName("net.minecraft.server." + getInstance().getNmsVersion() + ".Packet");
			playerConnection.getClass().getMethod("sendPacket", packetClass).invoke(playerConnection,
					signPacketClass.cast(signPacket));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getMinecraftVersion() {
		Matcher matcher = Pattern.compile("(\\(MC: )([\\d\\.]+)(\\))").matcher(Bukkit.getVersion());
		if (matcher.find())
			return matcher.group(2);
		return null;
	}

	public static String getVersion() {
		String version = getMinecraftVersion();

		if ("1.7.2".equals(version))
			version = "v1_7_R1";
		else if ("1.7.5".equals(version))
			version = "v1_7_R2";
		else if ("1.7.8".equals(version))
			version = "v1_7_R3";
		else if ("1.7.10".equals(version))
			version = "v1_7_R4";
		else if ("1.8".equals(version))
			version = "v1_8_R1";
		else if ("1.8.1".equals(version))
			version = "v1_8_R1";
		else if ("1.8.3".equals(version))
			version = "v1_8_R2";
		else if ("1.8.4".equals(version))
			version = "v1_8_R3";
		else if ("1.8.5".equals(version))
			version = "v1_8_R3";
		else if ("1.8.6".equals(version))
			version = "v1_8_R3";
		else if ("1.8.7".equals(version))
			version = "v1_8_R3";
		else if ("1.8.8".equals(version))
			version = "v1_8_R3";
		else if ("1.9".equals(version))
			version = "v1_9_R1";
		else if ("1.9.2".equals(version))
			version = "v1_9_R1";
		else if ("1.9.4".equals(version))
			version = "v1_9_R2";
		else if ("1.10".equals(version))
			version = "v1_10_R1";
		else if ("1.10.2".equals(version))
			version = "v1_10_R1";
		else if ("1.11".equals(version))
			version = "v1_11_R1";
		else if ("1.11.2".equals(version))
			version = "v1_11_R1";
		else if ("1.12".equals(version))
			version = "v1_12_R1";
		else if ("1.12.1".equals(version))
			version = "v1_12_R1";
		else if ("1.12.2".equals(version))
			version = "v1_12_R1";
		else
			version = null;

		return version;
	}
}
