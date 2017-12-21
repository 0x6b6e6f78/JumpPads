package de.knox.jp.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;

public abstract class PacketReader {

	private Channel channel;
	private List<Player> injected;

	public PacketReader() {
		injected = new ArrayList<>();
	}

	public void inject(Player player) {
		injected.add(player);
		CraftPlayer cPlayer = (CraftPlayer) player;
		channel = cPlayer.getHandle().playerConnection.networkManager.channel;
		channel.pipeline().addAfter("decoder", "PacketInjector_" + player.getName(),
				new MessageToMessageDecoder<Packet<?>>() {
					protected void decode(ChannelHandlerContext arg0, Packet<?> packet, List<Object> arg2)
							throws Exception {
						arg2.add(packet);
						readPacket(packet, player);
					}
				});
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

	public abstract void readPacket(Packet<?> packet, Player player);
}
