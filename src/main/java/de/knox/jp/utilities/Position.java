package de.knox.jp.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Position {

	@Getter
	@Setter
	private double x;
	@Getter
	@Setter
	private double y;
	@Getter
	@Setter
	private double z;
}
