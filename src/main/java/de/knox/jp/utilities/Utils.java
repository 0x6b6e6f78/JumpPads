package de.knox.jp.utilities;

public class Utils {

	public static int maxOut(int i, int max, boolean proof0) {
		if (i < 0 && proof0)
			i = 0;
		if (i > max)
			i = max;
		return i;
	}
}
