package pes.editor;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import pes.editor.constants.PlayerConstant;

public class Player implements Comparable<Player>, Serializable {
	public String name;
	public int index;
	public int adr;
	private OptionFile of;

	public Player(OptionFile opf, int i, int sa) {
		of = opf;
		if (of == null)
			throw new NullPointerException();
		boolean end;
		index = i;
		adr = sa;
		if (i == 0) {
			name = "<empty>";
		} else if (i < 0 || (i >= PlayerConstant.AMOUNT_PLAYERS && i < PlayerConstant.INDEX_FIRST_EDIT_PLAYER) || i > 32951) {
			name = "<ERROR>";
			index = 0;
		} else {
			// adr = 31568 + (i * 124);
			int a = PlayerConstant.startAdr;
			int offSet = i * 124;
			if (i >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
				// adr = 8744 + (i * 124);
				a = PlayerConstant.startAdrE;
				offSet = (i - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124;
			}
			byte[] nameBytes = new byte[32];
			System.arraycopy(of.data, a + offSet, nameBytes, 0, 32);
			end = false;
			int len = 0;
			for (int j = 0; !end && j < nameBytes.length - 1; j = j + 2) {
				if (nameBytes[j] == 0 && nameBytes[j + 1] == 0) {
					end = true;
					len = j;
				}
			}

			name = new String(nameBytes, 0, len, StandardCharsets.UTF_16LE);

			if (name.isEmpty() && index >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
				// name = "<???>";
				name = "<Edited " + String.valueOf(index - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) + ">";
			} else if (name.equals("")) {
				if (index >= PlayerConstant.INDEX_FIRST_UNUSED_PLAYER) {
					name = "<Unused " + String.valueOf(index) + ">";
				} else {
					name = "<L " + String.valueOf(index) + ">";
				}
			}
		}
	}

	public String toString() {
		return name + " [" + index + "]";
	}

	public int compareTo(Player other) {
		int result = name.compareTo(other.name);
		if (result == 0) {
			var firstAge = PlayerAttributes.getValue(of, index, PlayerAttributes.age);
			var secondAge = PlayerAttributes.getValue(of, other.index, PlayerAttributes.age);
			result = Integer.compare(firstAge, secondAge);
		}
		return result;
	}

	public void setName(String newName) {
		int len = newName.length();
		if (index != 0 && len <= 15) {
			byte[] newNameBytes = new byte[32];
			byte[] t;
            t = newName.getBytes(StandardCharsets.UTF_16LE);
            if (t.length <= 30) {
				System.arraycopy(t, 0, newNameBytes, 0, t.length);
			} else {
				System.arraycopy(t, 0, newNameBytes, 0, 30);
			}
			int a = PlayerConstant.startAdr;
			int offSet = index * 124;
			if (index >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
				// adr = 8744 + (i * 124);
				a = PlayerConstant.startAdrE;
				offSet = (index - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124;
			}
			System.arraycopy(newNameBytes, 0, of.data, a + offSet, 32);
			//of.data[a + offSet + 48] = -51;
			//of.data[a + offSet + 49] = -51;
			PlayerAttributes.setValue(of, index, PlayerAttributes.callName, 0xcdcd);

			PlayerAttributes.setValue(of, index, PlayerAttributes.nameEdited, 1);
			PlayerAttributes.setValue(of, index, PlayerAttributes.callEdited, 1);
			// of.data[a + offSet + 50] = 1;
			name = newName;
		}
	}
	
	public String getShirtName() {
		String sn = "";
		int a = PlayerConstant.startAdr + 32 + (index * 124);
		if (index >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
			a = PlayerConstant.startAdrE + 32 + ((index - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124);
		}
		if (of.data[a] != 0) {
			byte[] sb = new byte[16];
			System.arraycopy(of.data, a, sb, 0, 16);
			for (int i = 0; i < 16; i++) {
				if (sb[i] == 0) {
					sb[i] = 33;
				}
			}
			sn = new String(sb);
			sn = sn.replaceAll("!", "");
		}
		return sn;
	}

	public void setShirtName(String n) {
		if (n.length() < 16 && index != 0) {
			int a = PlayerConstant.startAdr + 32 + (index * 124);
			if (index >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
				a = PlayerConstant.startAdrE + 32 + ((index - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124);
			}
			byte[] t = new byte[16];
			n = n.toUpperCase();
			byte[] nb = n.getBytes();
			for (int i = 0; i < nb.length; i++) {
				if ((nb[i] < 65 || nb[i] > 90) && nb[i] != 46
						&& nb[i] != 32 && nb[i] != 95) {
					nb[i] = 32;
				}
			}
			System.arraycopy(nb, 0, t, 0, nb.length);
			System.arraycopy(t, 0, of.data, a, 16);
			PlayerAttributes.setValue(of, index, PlayerAttributes.shirtEdited, 1);
		}
	}
	
	public void makeShirt(String n) {
		// System.out.println(n);
		StringBuilder result = new StringBuilder();
		String spaces = "";
		int len = n.length();
		if (len < 9 && len > 5) {
			spaces = " ";
		} else if (len < 6 && len > 3) {
			spaces = "  ";
		} else if (len == 3) {
			spaces = "    ";
		} else if (len == 2) {
			spaces = "        ";
		}
		n = n.toUpperCase();
		byte[] nb = n.getBytes();
		for (int i = 0; i < nb.length; i++) {
			if ((nb[i] < 65 || nb[i] > 90) && nb[i] != 46 && nb[i] != 32
					&& nb[i] != 95) {
				nb[i] = 32;
			}
		}
		n = new String(nb);
		// System.out.println(n);
		for (int i = 0; i < n.length() - 1; i++) {
			result.append(n.charAt(i)).append(spaces);
		}
		result.append(n.substring(n.length() - 1));
		setShirtName(result.toString());
	}
}
