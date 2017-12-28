package de.knox.jp.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.knox.jp.JumpPads;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class PacketReader {

	private Channel channel;
	private List<Player> injected;

	public PacketReader() {
		injected = new ArrayList<>();
	}

	public void inject(Player player) {
		injected.add(player);

		try {
			Class<?> p = Class.forName(
					"org.bukkit.craftbukkit." + JumpPads.getInstance().getNmsVersion() + ".entity.CraftPlayer");
			Object cPlayer = p.cast(player);

			Object handle = p.getMethod("getHandle").invoke(cPlayer);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);

			Class<?> packetClass = Class.forName("net.minecraft.server." + JumpPads.getInstance().getNmsVersion() + ".Packet");
			for (Field fields : networkManager.getClass().getFields()) {
				if (fields.getType().getName().equals("io.netty.channel.Channel"))
					channel = (Channel) fields.get(networkManager);
			}
			for (Field fields : networkManager.getClass().getDeclaredFields()) {
				if (fields.getType().getName().equals("io.netty.channel.Channel"))
					channel = (Channel) fields.get(networkManager);
			}
			channel.pipeline().addAfter("decoder", "PacketInjector_" + player.getName(),
					new MessageToMessageDecoder<Object>() {
						protected void decode(ChannelHandlerContext arg0, Object packet, List<Object> arg2)
								throws Exception {
							try {
								packetClass.cast(packet);
							} catch (Exception e) {
								return;
							}
							arg2.add(packet);
							readPacket(packet, player);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void uninject(Player player) {
		if (channel.pipeline().get("PacketInjector_" + player.getName()) != null)
			channel.pipeline().remove("PacketInjector_" + player.getName());
	}

	public void uninject() {
		for (Player player : injected) {
			if (channel.pipeline().get("PacketInjector_" + player.getName()) != null)
				channel.pipeline().remove("PacketInjector_" + player.getName());
		}
	}

	public void setValue(Object obj, String name, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
		}
	}

	public Object getValue(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
		}
		return null;
	}

	public abstract void readPacket(Object packet, Player player);
}
