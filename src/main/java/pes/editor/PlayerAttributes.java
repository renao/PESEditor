package pes.editor;

import pes.editor.constants.PlayerConstant;

import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.RandomAccessFile;

public class PlayerAttributes {
	
	public static final PlayerAttribute hair = new PlayerAttribute(0, 45, 0, 0x7FF, "");
	public static final PlayerAttribute faceType = new PlayerAttribute(0, 55, 0, 0x3, "");
	public static final PlayerAttribute skin = new PlayerAttribute(0, 41, 6, 0x3, "");
	public static final PlayerAttribute face = new PlayerAttribute(0, 53, 5, 0x1FF, "");
	public static final PlayerAttribute nameEdited = new PlayerAttribute(0, 3, 0, 0x1, "");
	public static final PlayerAttribute callEdited = new PlayerAttribute(0, 3, 2, 0x1, "");
	public static final PlayerAttribute shirtEdited = new PlayerAttribute(0, 3, 1, 0x1, "");
	public static final PlayerAttribute abilityEdited = new PlayerAttribute(0, 40, 4, 0x1, "");
	public static final PlayerAttribute callName = new PlayerAttribute(0, 1, 0, 0xFFFF, "");
	public static final PlayerAttribute height = new PlayerAttribute(1, 41, 0, 0x3F, "Height");
	public static final PlayerAttribute foot = new PlayerAttribute(4, 5, 0, 0x01, "Foot");
	public static final PlayerAttribute favSide = new PlayerAttribute(0, 33, 14, 0x03, "Fav side");
	public static final PlayerAttribute wfa = new PlayerAttribute(5, 33, 11, 0x07, "W Foot Acc");
	public static final PlayerAttribute wff = new PlayerAttribute(5, 33, 3, 0x07, "W Foot Freq");
	public static final PlayerAttribute injury = new PlayerAttribute(6, 33, 6, 0x03, "Injury T");
	public static final PlayerAttribute dribSty = new PlayerAttribute(5, 6, 0, 0x03, "");
	public static final PlayerAttribute freekick = new PlayerAttribute(5, 5, 1, 0x0f, "");
	public static final PlayerAttribute pkStyle = new PlayerAttribute(5, 5, 5, 0x07, "");
	public static final PlayerAttribute dkSty = new PlayerAttribute(5, 6, 2, 0x03, "");
	public static final PlayerAttribute age = new PlayerAttribute(2, 65, 9, 0x1F, "Age");
	public static final PlayerAttribute weight = new PlayerAttribute(0, 41, 8, 0x7F, "Weight");
	public static final PlayerAttribute nationality = new PlayerAttribute(3, 65, 0, 0x7F, "Nationality");
	public static final PlayerAttribute consistency = new PlayerAttribute(5, 33, 0, 0x07, "Consistency");
	public static final PlayerAttribute condition = new PlayerAttribute(5, 33, 8, 0x07, "Condition");
	public static final PlayerAttribute regPos = new PlayerAttribute(0, 6, 4, 0xF, "");
	public static final PlayerAttribute gk = new PlayerAttribute(0, 7, 7, 1, "GK");
	public static final PlayerAttribute cwp = new PlayerAttribute(0, 7, 15, 1, "CWP");
	public static final PlayerAttribute cbt = new PlayerAttribute(0, 9, 7, 1, "CBT");
	public static final PlayerAttribute sb = new PlayerAttribute(0, 9, 15, 1, "SB");
	public static final PlayerAttribute dm = new PlayerAttribute(0, 11, 7, 1, "DM");
	public static final PlayerAttribute wb = new PlayerAttribute(0, 11, 15, 1, "WB");
	public static final PlayerAttribute cm = new PlayerAttribute(0, 13, 7, 1, "CM");
	public static final PlayerAttribute sm = new PlayerAttribute(0, 13, 15, 1, "SM");
	public static final PlayerAttribute am = new PlayerAttribute(0, 15, 7, 1, "AM");
	public static final PlayerAttribute wg = new PlayerAttribute(0, 15, 15, 1, "WG");
	public static final PlayerAttribute ss = new PlayerAttribute(0, 17, 7, 1, "SS");
	public static final PlayerAttribute cf = new PlayerAttribute(0, 17, 15, 1, "CF");
	public static final PlayerAttribute[] roles = { gk, new PlayerAttribute(0, 7, 15, 1, "?"), cwp, cbt, sb,
			dm, wb, cm, sm, am, wg, ss, cf };
	public static final PlayerAttribute attack = new PlayerAttribute(0, 7, 0, 0x7F, "Attack");
	public static final PlayerAttribute defence = new PlayerAttribute(0, 8, 0, 0x7F, "Defense");
	public static final PlayerAttribute balance = new PlayerAttribute(0, 9, 0, 0x7F, "Balance");
	public static final PlayerAttribute stamina = new PlayerAttribute(0, 10, 0, 0x7F, "Stamina");
	public static final PlayerAttribute speed = new PlayerAttribute(0, 11, 0, 0x7F, "Speed");
	public static final PlayerAttribute accel = new PlayerAttribute(0, 12, 0, 0x7F, "Accel");
	public static final PlayerAttribute response = new PlayerAttribute(0, 13, 0, 0x7F, "Response");
	public static final PlayerAttribute agility = new PlayerAttribute(0, 14, 0, 0x7F, "Agility");
	public static final PlayerAttribute dribAcc = new PlayerAttribute(0, 15, 0, 0x7F, "Drib Acc");
	public static final PlayerAttribute dribSpe = new PlayerAttribute(0, 16, 0, 0x7F, "Drib Spe");
	public static final PlayerAttribute sPassAcc = new PlayerAttribute(0, 17, 0, 0x7F, "S Pass Acc");
	public static final PlayerAttribute sPassSpe = new PlayerAttribute(0, 18, 0, 0x7F, "S Pass Spe");
	public static final PlayerAttribute lPassAcc = new PlayerAttribute(0, 19, 0, 0x7F, "L Pass Acc");
	public static final PlayerAttribute lPassSpe = new PlayerAttribute(0, 20, 0, 0x7F, "L Pass Spe");
	public static final PlayerAttribute shotAcc = new PlayerAttribute(0, 21, 0, 0x7F, "Shot Acc");
	public static final PlayerAttribute shotPow = new PlayerAttribute(0, 22, 0, 0x7F, "Shot Power");
	public static final PlayerAttribute shotTec = new PlayerAttribute(0, 23, 0, 0x7F, "Shot Tech");
	public static final PlayerAttribute fk = new PlayerAttribute(0, 24, 0, 0x7F, "FK Acc");
	public static final PlayerAttribute curling = new PlayerAttribute(0, 25, 0, 0x7F, "Swerve");
	public static final PlayerAttribute heading = new PlayerAttribute(0, 26, 0, 0x7F, "Heading");
	public static final PlayerAttribute jump = new PlayerAttribute(0, 27, 0, 0x7F, "Jump");
	public static final PlayerAttribute tech = new PlayerAttribute(0, 29, 0, 0x7F, "Tech");
	public static final PlayerAttribute aggress = new PlayerAttribute(0, 30, 0, 0x7F, "Aggression");
	public static final PlayerAttribute mental = new PlayerAttribute(0, 31, 0, 0x7F, "Mentality");
	public static final PlayerAttribute gkAbil = new PlayerAttribute(0, 32, 0, 0x7F, "GK");
	public static final PlayerAttribute team = new PlayerAttribute(0, 28, 0, 0x7F, "Team Work");
	public static final PlayerAttribute[] ability99 = { attack, defence, balance, stamina, speed,
			accel, response, agility, dribAcc, dribSpe, sPassAcc, sPassSpe,
			lPassAcc, lPassSpe, shotAcc, shotPow, shotTec, fk, curling,
			heading, jump, tech, aggress, mental, gkAbil, team };
	public static final PlayerAttribute[] abilitySpecial = { new PlayerAttribute(0, 21, 7, 1, "Dribbling"),
			new PlayerAttribute(0, 21, 15, 1, "Tactical Drib"),
			new PlayerAttribute(0, 23, 7, 1, "Positioning"),
			new PlayerAttribute(0, 23, 15, 1, "Reaction"),
			new PlayerAttribute(0, 25, 7, 1, "Playmaking"),
			new PlayerAttribute(0, 25, 15, 1, "Passing"),
			new PlayerAttribute(0, 27, 7, 1, "Scoring"),
			new PlayerAttribute(0, 27, 15, 1, "1-1 Scoring"),
			new PlayerAttribute(0, 29, 7, 1, "Post"),
			new PlayerAttribute(0, 29, 15, 1, "Line Position"),
			new PlayerAttribute(0, 31, 7, 1, "Mid shooting"),
			new PlayerAttribute(0, 31, 15, 1, "Side"),
			new PlayerAttribute(0, 19, 15, 1, "Centre"),
			new PlayerAttribute(0, 19, 7, 1, "Penalties"),
			new PlayerAttribute(0, 35, 0, 1, "1-T Pass"),
			new PlayerAttribute(0, 35, 1, 1, "Outside"),
			new PlayerAttribute(0, 35, 2, 1, "Marking"),
			new PlayerAttribute(0, 35, 3, 1, "Sliding"),
			new PlayerAttribute(0, 35, 4, 1, "Cover"),
			new PlayerAttribute(0, 35, 5, 1, "D-L Control"),
			new PlayerAttribute(0, 35, 6, 1, "Penalty GK"),
			new PlayerAttribute(0, 35, 7, 1, "1-on-1 GK"),
			new PlayerAttribute(0, 37, 7, 1, "Long Throw") };

	// statX = new Stat(of, 0, 27, 5, 0x7F, "StatX");
	// gkKick = new Stat(of, 0, 33, 7, 1, "GK Kick");
	// bff = new Stat(of, 0, 29, 7, 1, "B F Feint");
	// growth = new Stat(of, 5, 6, 2, 0x03, "");

	public static final String[] nation;
	public static final int nation_free;

	//PeterC10 MOD: Make DEFAULT_NATION match list of original nations in PES6

	public static final String[] DEFAULT_NATION = {
		"Austria",
		"Belgium",
		"Bulgaria",
		"Croatia",
		"Czech Republic",
		"Denmark",
		"England",
		"Finland",
		"France",
		"Germany",
		"Greece",
		"Hungary",
		"Ireland",
		"Italy",
		"Latvia",
		"Netherlands",
		"Northern Ireland",
		"Norway",
		"Poland",
		"Portugal",
		"Romania",
		"Russia",
		"Scotland",
		"Serbia and Montenegro",
		"Slovakia",
		"Slovenia",
		"Spain",
		"Sweden",
		"Switzerland",
		"Turkey",
		"Ukraine",
		"Wales",
		"Angola",
		"Cameroon",
		"Cote d'Ivoire",
		"Ghana",
		"Nigeria",
		"South Africa",
		"Togo",
		"Tunisia",
		"Costa Rica",
		"Mexico",
		"Trinidad and Tobago",
		"United States",
		"Argentina",
		"Brazil",
		"Chile",
		"Colombia",
		"Ecuador",
		"Paraguay",
		"Peru",
		"Uruguay",
		"Iran",
		"Japan",
		"Saudi Arabia",
		"South Korea",
		"Australia",
		"Bosnia and Herzegovina",
		"Estonia",
		"Israel",
		"Honduras",
		"Jamaica",
		"Panama",
		"Bolivia",
		"Venezuela",
		"China",
		"Uzbekistan",
		"Albania",
		"Cyprus",
		"Iceland",
		"Macedonia",
		"Armenia",
		"Belarus",
		"Georgia",
		"Liechtenstein",
		"Lithuania",
		"Algeria",
		"Benin",
		"Burkina Faso",
		"Cape Verde",
		"Congo",
		"DR Congo",
		"Egypt",
		"Equatorial Guinea",
		"Gabon",
		"Gambia",
		"Guinea",
		"Guinea-Bissau",
		"Kenya",
		"Liberia",
		"Libya",
		"Mali",
		"Morocco",
		"Mozambique",
		"Senegal",
		"Sierra Leone",
		"Zambia",
		"Zimbabwe",
		"Canada",
		"Grenada",
		"Guadeloupe",
		"Martinique",
		"Netherlands Antilles",
		"Oman",
		"New Zealand",
		"Free Nationality"
	};

	public static final String[] modFoot = { "R", "L" };
	public static final String[] modInjury = { "C", "B", "A" };
	public static final String[] modFK = { "A", "B", "C", "D", "E", "F", "G", "H" };
	public static final String[] mod18 = { "1", "2", "3", "4", "5", "6", "7", "8" };

	static {
		String[] nationArray = DEFAULT_NATION;
		try {
			List<String> nationList = new LinkedList<String>();
			RandomAccessFile in = new RandomAccessFile(new File("nationality.txt"), "r");
			while (true) {
				String value = in.readLine();
				if (value == null) {
					break;
				}
				if (!value.trim().isEmpty()) {
					nationList.add(value.trim());
				}
			}
			in.close();
			nationArray = nationList.toArray(DEFAULT_NATION);
		}
		catch (Exception exception) {
			EditorLogger.log(exception);
		}
		finally {
			nation = nationArray;
			// set free nationality
			int nation_free_guess = -1;
			for (int i=0; i<nation.length; i++) {
				if (nation[i].toLowerCase().startsWith("free ")) {
					nation_free_guess = i;
				}
			}
			nation_free = (nation_free_guess != -1) ? nation_free_guess : 0;
			// DEBUG:
			// for (int i=0; i<nation.length; i++) {
			// 	System.out.println("nationality: " + i + ": {" + nation[i] + "}");
			// }
		}
	}

	public static int getValue(OptionFile of, int player, PlayerAttribute playerAttribute) {

		int a = PlayerConstant.startAdr + 48 + (player * 124) + playerAttribute.offset;
		if (player >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
			a = PlayerConstant.startAdrE + 48 + ((player - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124)
					+ playerAttribute.offset;
		}
		// System.out.println(a);
		int val = (of.toInt(of.data[a]) << 8) | of.toInt(of.data[a - 1]);
		// System.out.println(val);
		val = val >>> playerAttribute.shift;
		// System.out.println(val);
		val = val & playerAttribute.mask;
		// System.out.println(val);
		return val;
	}

	public static void setValue(OptionFile of, int player, PlayerAttribute playerAttribute, int v) {
		int a = PlayerConstant.startAdr + 48 + (player * 124) + playerAttribute.offset;
		if (player >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
			a = PlayerConstant.startAdrE + 48 + ((player - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124)
					+ playerAttribute.offset;
		}
		int old = (of.toInt(of.data[a]) << 8) | of.toInt(of.data[a - 1]);
		int oldMask = 0xFFFF & (~(playerAttribute.mask << playerAttribute.shift));
		old = old & oldMask;
		v = v & playerAttribute.mask;
		v = v << playerAttribute.shift;
		v = old | v;
		of.data[a - 1] = of.toByte(v & 0xff);
		of.data[a] = of.toByte(v >>> 8);
	}

	public static void setValue(OptionFile of, int player, PlayerAttribute playerAttribute, String s) {
		int v = 0;
		try {
			if (playerAttribute.attributeType == 0) {
				v = Integer.parseInt(s);
			}
			if (playerAttribute.attributeType == 1) {
				v = Integer.parseInt(s) - 148;
			}
			if (playerAttribute.attributeType == 2) {
				v = Integer.parseInt(s) - 15;
			}
			if (playerAttribute.attributeType == 3) {
				boolean matched = false;
				for (int i = 0; i < nation.length; i++) {
					if (s.equals(nation[i])) {
						v = i;
						matched = true;
						break;
					}
				}
				if (!matched) {
					// default to Free Nationality
					v = nation_free;
				}
				// System.out.println(v);
				/*
				 * if (v == 118) { v = 291; } else { if (v > 56) { v = v + 164; } }
				 */
				// System.out.println(v);
			}
			if (playerAttribute.attributeType == 4) {
				for (int i = 0; i < modFoot.length; i++) {
					if (s.equals(modFoot[i])) {
						v = i;
					}
				}
			}
			if (playerAttribute.attributeType == 5) {
				v = Integer.parseInt(s) - 1;
			}
			if (playerAttribute.attributeType == 6) {
				for (int i = 0; i < modInjury.length; i++) {
					if (s.equals(modInjury[i])) {
						v = i;
					}
				}
			}
			if (playerAttribute.attributeType == 7) {
				for (int i = 0; i < modFK.length; i++) {
					if (s.equals(modFK[i])) {
						v = i;
					}
				}
			}
			setValue(of, player, playerAttribute, v);
		} catch (NumberFormatException exception) {
			EditorLogger.log(exception);
		}
	}

	public static String getString(OptionFile of, int player, PlayerAttribute playerAttribute) {
		String result = "";
		int val = getValue(of, player, playerAttribute);

		if (playerAttribute.attributeType == 0) {
			result = String.valueOf(val);
		}
		if (playerAttribute.attributeType == 1) {
			result = String.valueOf(val + 148);
		}
		if (playerAttribute.attributeType == 2) {
			result = String.valueOf(val + 15);
		}
		if (playerAttribute.attributeType == 3) {
			// System.out.println(val);
			if (val >= 0 && val < nation.length) {
				result = nation[val];
			} else {
				result = String.valueOf(val) + "?";
			}
		}
		if (playerAttribute.attributeType == 4) {
			result = (val == 1) ? "L" : "R";
		}
		if (playerAttribute.attributeType == 5) {
			result = String.valueOf(val + 1);
		}
		if (playerAttribute.attributeType == 6) {
			result = "A";
			if (val == 1) {
				result = "B";
			}
			if (val == 0) {
				result = "C";
			}
		}
		if (playerAttribute.attributeType == 7) {
			result = modFK[val];
		}
		return result;
	}
}
