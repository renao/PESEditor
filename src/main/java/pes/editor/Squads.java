package pes.editor;

public class Squads {
	static final int num23 = 657956;
	static final int num32 = 659635;
	static final int slot23 = 664372;
	static final int slot32 = 667730;
	
	public static void fixForm(OptionFile of, int s, boolean fixJobs) {
		// System.out.println(s);
		byte[] temp = new byte[64];
		byte[] tempNum = new byte[32];
		int t;
		int size;
		int firstAdr;
		int firstAdrNum;
		// for (int t = 0; t < 202; t++) {
		if ((s >= 0 && s < 64) || (s >= 73 && s < 213)) {
			t = s;
			if (s > 72) {
				t = t - 9;
			}
			// System.out.println(t);
			if (s < 64) {
				size = 23;
				firstAdr = slot23 + (s * size * 2);
				firstAdrNum = num23 + (s * size);
			} else {
				size = 32;
				firstAdr = slot32 + ((s - 73) * size * 2);
				firstAdrNum = num32 + ((s - 73) * size);
			}
			System.arraycopy(of.data, firstAdr, temp, 0, size * 2);
			System.arraycopy(of.data, firstAdrNum, tempNum, 0, size);
			for (int p = 0; p < size; p++) {
				System.arraycopy(temp, Formations.getSlot(of, t, p) * 2, of.data,
						firstAdr + (p * 2), 2);
				System.arraycopy(tempNum, Formations.getSlot(of, t, p), of.data,
						firstAdrNum + p, 1);
			}
			
			if (fixJobs) {
				boolean fixed;
				for (int j = 0; j < 6; j++) {
					fixed = false;
					for (byte i = 0; !fixed && i < 32; i++) {
						if (Formations.getSlot(of, t, i) == Formations.getJob(of, t, j)) {
							Formations.setJob(of, t, j, i);
			
							fixed = true;
						}
					}
				}
			}
			
			for (byte i = 0; i < 32; i++) {
				Formations.setSlot(of, t, i, i);
			}
		}
	}

	public static void fixAll(OptionFile of) {
		for (int i = 0; i < 213; i++) {
			fixForm(of, i, true);
		}
	}
	
	public static void tidy(OptionFile of, int team) {
		if ((team >= 0 && team < 64) || (team >= 73 && team < 213)) {
			//FormFixer.fixForm(of, team);
			// System.out.println(team);
			int size;
			int firstAdr;
			int firstAdrNum;
			if (team < 64) {
				size = 23;
				firstAdr = slot23 + (team * size * 2);
				firstAdrNum = num23 + (team * size);
			} else {
				size = 32;
				firstAdr = slot32 + ((team - 73) * size * 2);
				firstAdrNum = num32 + ((team - 73) * size);
			}
			byte[] tempSlot = new byte[(size - 11) * 2];
			byte[] tempNum = new byte[size - 11];
			int numAdr;
			int slotAdr;
			int tempPos = 0;
			for (int i = 11; i < size; i++) {
				slotAdr = firstAdr + (i * 2);
				numAdr = firstAdrNum + i;
				if (!(of.data[slotAdr] == 0 && of.data[slotAdr + 1] == 0)) {
					tempSlot[tempPos * 2] = of.data[slotAdr];
					tempSlot[(tempPos * 2) + 1] = of.data[slotAdr + 1];
					tempNum[tempPos] = of.data[numAdr];
					tempPos++;
				}
			}
			for (int j = tempPos; j < size - 11; j++) {
				tempNum[j] = -1;
			}
			System.arraycopy(tempSlot, 0, of.data, firstAdr + 22,
					tempSlot.length);
			System.arraycopy(tempNum, 0, of.data, firstAdrNum + 11,
					tempNum.length);
		}
	}

	public static void tidy11(OptionFile of, int team, int freePos, int selPos) {
		if ((team >= 0 && team < 64) || (team >= 73 && team < 213)) {
			PlayerAttribute playerAttribute = PlayerAttributes.gk;
			int[] score = new int[21];
			int pos = 0;
			if ((selPos > 0 && selPos < 4) || (selPos > 5 && selPos < 8)) {
				playerAttribute = PlayerAttributes.cbt;
				pos = 1;
			}
			if (selPos == 4 || selPos == 5) {
				playerAttribute = PlayerAttributes.cwp;
				pos = 1;
			}
			if (selPos == 8) {
				playerAttribute = PlayerAttributes.sb;
				pos = 2;
			}
			if (selPos == 9) {
				playerAttribute = PlayerAttributes.sb;
				pos = 2;
			}
			if (selPos > 9 && selPos < 15) {
				playerAttribute = PlayerAttributes.dm;
				pos = 3;
			}
			if (selPos == 15) {
				playerAttribute = PlayerAttributes.wb;
				pos = 2;
			}
			if (selPos == 16) {
				playerAttribute = PlayerAttributes.wb;
				pos = 2;
			}
			if (selPos > 16 && selPos < 22) {
				playerAttribute = PlayerAttributes.cm;
				pos = 4;
			}
			if (selPos == 22) {
				playerAttribute = PlayerAttributes.sm;
				pos = 5;
			}
			if (selPos == 23) {
				playerAttribute = PlayerAttributes.sm;
				pos = 5;
			}
			if (selPos > 23 && selPos < 29) {
				playerAttribute = PlayerAttributes.am;
				pos = 6;
			}
			if (selPos > 35 && selPos < 41) {
				playerAttribute = PlayerAttributes.cf;
				pos = 7;
			}
			if (selPos > 30 && selPos < 36) {
				playerAttribute = PlayerAttributes.ss;
				pos = 6;
			}
			if (selPos == 29) {
				playerAttribute = PlayerAttributes.wg;
				pos = 8;
			}
			if (selPos == 30) {
				playerAttribute = PlayerAttributes.wg;
				pos = 8;
			}

			int size;
			int firstAdr;
			int firstAdrNum;
			if (team < 64) {
				size = 23;
				firstAdr = slot23 + (team * size * 2);
				firstAdrNum = num23 + (team * size);
			} else {
				size = 32;
				firstAdr = slot32 + ((team - 73) * size * 2);
				firstAdrNum = num32 + ((team - 73) * size);
			}

			int a;
			int c = 0;
			int pi = -1;
			int[] playerIndex = new int[21];
			for (int i = 11; pi != 0 && i < size; i++) {
				c = i - 11;
				a = firstAdr + (i * 2);
				pi = of.toInt(of.data[a + 1]) << 8 | of.toInt(of.data[a]);
				if (pi != 0) {
					playerIndex[c] = pi;
					switch (pos) {
					case 0:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.defence)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.balance)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.response)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.gkAbil)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 1:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.defence)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.balance)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.response)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.speed)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 2:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.defence)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.balance)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.response)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.stamina)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.speed)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.lPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 3:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.defence)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.balance)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.response)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.sPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 4:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.defence)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.attack)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.dribAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.sPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.tech)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 5:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.attack)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.speed)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.stamina)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.dribAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.lPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 6:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.attack)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.dribAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.sPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.tech)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.agility)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 7:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.attack)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.response)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.shotAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.shotTec)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.tech)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					case 8:
						score[c] = PlayerAttributes.getValue(of, pi, PlayerAttributes.attack)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.speed)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.dribAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.dribSpe)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.sPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.lPassAcc)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.agility)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.tech)
						+ PlayerAttributes.getValue(of, pi, PlayerAttributes.team);
						break;
					}
				}
			}

			int bestPosPlayer = 0;
			int bestPosScore = 0;
			int bestPlayer = 0;
			int bestScore = 0;
			int isPos = 0;
			for (int i = 0; i < 21; i++) {
				if (playerIndex[i] != 0) {
					isPos = PlayerAttributes.getValue(of, playerIndex[i], playerAttribute);
					if (isPos == 1 && score[i] > bestPosScore) {
						bestPosScore = score[i];
						bestPosPlayer = i;
					}
					if (isPos == 0 && score[i] > bestScore) {
						bestScore = score[i];
						bestPlayer = i;
					}
				}
			}
			// System.out.println(pos);
			// System.out.println(bestPlayer);
			// System.out.println(bestPosPlayer);
			if (bestPosScore != 0) {
				bestPlayer = bestPosPlayer;
			}
			bestPlayer = bestPlayer + 11;
			// System.out.println(bestPlayer);
			of.data[firstAdr + (2 * freePos)] = of
					.toByte(playerIndex[bestPlayer - 11] & 0x000000FF);
			of.data[firstAdr + (2 * freePos) + 1] = of
					.toByte((playerIndex[bestPlayer - 11] & 0x0000FF00) >>> 8);
			of.data[firstAdr + (2 * bestPlayer)] = 0;
			of.data[firstAdr + (2 * bestPlayer) + 1] = 0;
			of.data[firstAdrNum + freePos] = of.data[firstAdrNum + bestPlayer];
			of.data[firstAdrNum + bestPlayer] = -1;
			tidy(of, team);
		}
	}
}
