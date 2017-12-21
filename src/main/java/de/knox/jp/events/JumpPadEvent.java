package de.knox.jp.events;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import de.knox.jp.json.JsonJumpPads.JumpPadMetadata;
import lombok.Getter;
import lombok.Setter;

public final class JumpPadEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	@Getter
	private Player player;
	@Getter
	private Location location;
	@Getter
	private Sound sound;
	@Getter
	private Effect particle;
	@Getter
	private Vector vector;
	@Getter
	private boolean vectorState;
	@Getter
	private JumpPadMetadata metadata;

	public JumpPadEvent(Player player, Location location, Sound sound, Effect particle, Vector vector,
			boolean vectorState, JumpPadMetadata metadata) {
		this.player = player;
		this.location = location;
		this.sound = sound;
		this.particle = particle;
		this.vector = vector;
		this.vectorState = vectorState;
		this.metadata = metadata;
	}

	@Getter
	@Setter
	private boolean cancelled;

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}