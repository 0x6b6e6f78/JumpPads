package de.knox.jp.json;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.knox.jp.json.JsonJumpPads.JumpPadMetadata;
import de.knox.jp.utilities.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class JsonJumpPads extends HashMap<Location, JumpPadMetadata> {
	private static final long serialVersionUID = 6249342949571695741L;

	public String asJson() {
		StringBuilder jsonBulder = new StringBuilder().append("[");
		int il = 0;
		for (Location location : keySet()) {
			il++;
			jsonBulder.append("{\"location\":" + getStringByLocationJ(location) + ",\"metadatas\":[");
			int ik = 0;
			for (String key : get(location).keySet()) {
				ik++;
				if (testObject(location, key)) {
					Object value = "";
					if ((value = get(location).get(key)) instanceof Position)
						value = getStringByPostionJ((Position) value);
					jsonBulder.append("{\"key\":" + key + ",\"value\":" + value + "}"
							+ (ik != get(location).keySet().size() ? "," : ""));
				}
			}
			jsonBulder.append("]}" + (il != keySet().size() ? "," : ""));
		}
		jsonBulder.append("]");

		String json = jsonBulder.toString();
		try {
			json = new JsonParser().parse(json).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	public void load(String json) {
		if (json == null || json.isEmpty())
			return;
		try {
			JsonElement element = new JsonParser().parse(json);
			JsonArray array = element.getAsJsonArray();
			for (JsonElement jsonElement : array) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				Location location = getLocationByStringJ(jsonObject.get("location").getAsJsonObject());
				JsonArray metadatas = jsonObject.get("metadatas").getAsJsonArray();
				JumpPadMetadata metadata = new JumpPadMetadata(location);
				for (JsonElement jsonMetadataElement : metadatas) {
					JsonObject jsonMetadataObject = jsonMetadataElement.getAsJsonObject();
					Object value = null;
					try {
						JsonObject object = jsonMetadataObject.get("value").getAsJsonObject();
						if (object.get("type").getAsString().equals("position"))
							value = getPostionByStringJ(object);
					} catch (Exception e) {
						value = jsonMetadataObject.get("value").getAsString();
					}
					metadata.put(jsonMetadataObject.get("key").getAsString(), value);
				}
				put(location, metadata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JumpPadMetadata get(Location location) {
		JumpPadMetadata ret = super.get(location);
		if (ret == null) {
			ret = new JumpPadMetadata(location);
			put(location, ret);
		}
		return ret;
	}

	public static Location getLocationByStringJ(String json) {
		Location location = null;
		try {
			location = getLocationByStringJ(new JsonParser().parse(json).getAsJsonObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public static Location getLocationByStringJ(JsonObject jsonObject) {
		Location location;
		String world = jsonObject.get("world").getAsString();
		int x = Integer.parseInt(jsonObject.get("x").getAsString());
		int y = Integer.parseInt(jsonObject.get("y").getAsString());
		int z = Integer.parseInt(jsonObject.get("z").getAsString());
		location = new Location(Bukkit.getWorld(world), x, y, z);
		return location;
	}

	public static Position getPostionByStringJ(JsonObject jsonObject) {
		Position position;
		double x = jsonObject.get("x").getAsDouble();
		double y = jsonObject.get("y").getAsDouble();
		double z = jsonObject.get("z").getAsDouble();
		position = new Position(x, y, z);
		return position;
	}

	public static String getStringByPostionJ(Position position) {
		JsonObject o = new JsonObject();
		o.addProperty("type", "position");
		o.addProperty("x", position.getX());
		o.addProperty("y", position.getY());
		o.addProperty("z", position.getZ());
		return o.toString();
	}

	public static String getStringByLocationJ(Location location) {
		JsonObject o = new JsonObject();
		o.addProperty("world", location.getWorld().getName());
		o.addProperty("x", location.getBlockX());
		o.addProperty("y", location.getBlockY());
		o.addProperty("z", location.getBlockZ());
		return o.toString();
	}

	public static Location getLocationByString(String string) {
		String[] locDatas = string.split(";");
		Location location = new Location(Bukkit.getWorld(locDatas[0]), Integer.parseInt(locDatas[1]),
				Integer.parseInt(locDatas[2]), Integer.parseInt(locDatas[3]));
		return location;
	}

	public static String getStringByLocation(Location location) {
		String string = "";
		string += location.getWorld().getName() + ";";
		string += location.getBlockX() + ";";
		string += location.getBlockY() + ";";
		string += location.getBlockZ();
		return string;
	}

	public boolean testObject(Location location, String key) {
		Object o = get(location).get(key);
		if ((o instanceof Boolean && !((boolean) o)) || (o instanceof Double && ((double) o) == 0)
				|| (o instanceof Integer && ((int) o) == 0) || (o instanceof Long && ((long) o) == 0)
				|| (o instanceof Float && ((float) o) == 0) || (o instanceof Byte && ((byte) o) == 0)
				|| (o instanceof Short && ((short) o) == 0) || o == null)
			return false;
		return true;
	}

	@AllArgsConstructor
	public static class JumpPadMetadata extends HashMap<String, Object> {
		private static final long serialVersionUID = 4610543437192520570L;

		@Getter
		private Location location;

		{
			put("state", "vector");
		}

		public boolean getBoolean(String key) {
			Object ret = get(key);
			if (ret == null)
				return false;
			return Boolean.parseBoolean(ret.toString());
		}

		public String getString(String key) {
			Object ret = get(key);
			if (ret == null)
				return "";
			return ret.toString();
		}

		public byte getByte(String key) {
			Object ret = get(key);
			if (ret == null)
				return 0;
			return Byte.parseByte(ret.toString());
		}

		public short getShort(String key) {
			Object ret = get(key);
			if (ret == null)
				return 0;
			return Short.parseShort(ret.toString());
		}

		public int getInt(String key) {
			Object ret = get(key);
			if (ret == null)
				return 0;
			return Integer.parseInt(ret.toString());
		}

		public long getLong(String key) {
			Object ret = get(key);
			if (ret == null)
				return 0;
			return Long.parseLong(ret.toString());
		}

		public float getFloat(String key) {
			Object ret = get(key);
			if (ret == null)
				return 0;
			return Float.parseFloat(ret.toString());
		}

		public double getDouble(String key) {
			Object ret = get(key);
			if (ret == null)
				return 0;
			return Double.parseDouble(ret.toString());
		}

		public Location getLocation(String key) {
			Object ret = get(key);
			if (ret == null)
				return null;
			return (Location) ret;
		}

		public Position getPosition(String key) {
			Object ret = get(key);
			if (ret == null)
				return new Position(0, 0, 0);
			return (Position) ret;
		}

		public Sound getSound(String key) {
			Object ret = get(key);
			if (ret == null)
				return null;
			return Sound.valueOf(ret.toString());
		}

		public Effect getEffect(String key) {
			Object ret = get(key);
			if (ret == null)
				return null;
			return Effect.valueOf(ret.toString());
		}
	}
}
