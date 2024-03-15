package pes.editor;

import pes.editor.constants.PlayerConstant;

import java.util.Collections;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class NationalityList extends JList {
	private OptionFile of;


	public NationalityList(OptionFile opf) {
		super();
		of = opf;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayoutOrientation(JList.VERTICAL);
		setVisibleRowCount(32);
	}

	public void refresh(int nation, boolean alpha) {
		int a;
		int index;
		Vector model = new Vector();

		if (nation == PlayerAttributes.nation.length + 5) {
			for (int p = 1; p < PlayerConstant.AMOUNT_PLAYERS; p++) {
				model.addElement(new Player(of, p, 0));
			}
			for (int p = PlayerConstant.INDEX_FIRST_EDIT_PLAYER; p < 32952; p++) {
				model.addElement(new Player(of, p, 0));
			}
		} else if (nation == PlayerAttributes.nation.length + 4) {
			boolean free;

			for (int p = 1; p < PlayerConstant.INDEX_FIRST_CLASSIC_PLAYER; p++) {
				free = true;
				a = Squads.slot32 - 2;
				do {
					a = a + 2;
					index = (of.toInt(of.data[a + 1]) << 8)
							| of.toInt(of.data[a]);
					if (index == p) {
						free = false;
					}
				} while (a < Squads.slot32 + (Clubs.total * 64) - 2 && index != p);
				if (free) {
					model.addElement(new Player(of, p, 0));
				}
			}

			for (int p = PlayerConstant.INDEX_FIRST_CLUB_PLAYER; p < PlayerConstant.INDEX_FIRST_MASTER_LEAGUE_PLAYER; p++) {
				free = true;
				a = Squads.slot32 - 2;
				do {
					a = a + 2;
					index = (of.toInt(of.data[a + 1]) << 8)
							| of.toInt(of.data[a]);
					if (index == p) {
						free = false;
					}
				} while (a < Squads.slot32 + (Clubs.total * 64) - 2 && index != p);
				if (free) {
					model.addElement(new Player(of, p, 0));
				}
			}
		} else if (nation == PlayerAttributes.nation.length + 3) {
			for (int p = PlayerConstant.INDEX_FIRST_CLUB_PLAYER; p < PlayerConstant.INDEX_FIRST_PES_UNITED_PLAYER; p++) {
				int dupe = getDupe(p);
				if (dupe != -1) {
					model.addElement(new Player(of, p, 0));
					model.addElement(new Player(of, dupe, 0));
				}
			}
		} else if (nation == PlayerAttributes.nation.length + 2) {
			for (int p = PlayerConstant.INDEX_FIRST_MASTER_LEAGUE_YOUNG_PLAYER; p < PlayerConstant.INDEX_FIRST_MASTER_LEAGUE_OLD_PLAYER; p++) {
				model.addElement(new Player(of, p, 0));
			}
		} else if (nation == PlayerAttributes.nation.length + 1) {
			for (int p = PlayerConstant.INDEX_FIRST_MASTER_LEAGUE_OLD_PLAYER; p < PlayerConstant.INDEX_FIRST_UNUSED_PLAYER; p++) {
				model.addElement(new Player(of, p, 0));
			}
		} else if (nation == PlayerAttributes.nation.length) {
			for (int p = PlayerConstant.INDEX_FIRST_UNUSED_PLAYER; p < PlayerConstant.AMOUNT_PLAYERS; p++) {
				model.addElement(new Player(of, p, 0));
			}
		} else {
			for (int p = 1; p < PlayerConstant.AMOUNT_PLAYERS; p++) {
				if (PlayerAttributes.getValue(of, p, PlayerAttributes.nationality) == nation) {
					model.addElement(new Player(of, p, 0));
				}
			}
			for (int p = PlayerConstant.INDEX_FIRST_EDIT_PLAYER; p < 32952; p++) {
				if (PlayerAttributes.getValue(of, p, PlayerAttributes.nationality) == nation) {
					model.addElement(new Player(of, p, 0));
				}
			}
		}
		if (nation != PlayerAttributes.nation.length + 3 && alpha) {
			Collections.sort(model);
		}
		model.trimToSize();
		setListData(model);
	}
	
	private int getDupe(int p) {
		for (int i = 1; i < PlayerConstant.INDEX_FIRST_CLASSIC_PLAYER; i++) {
			boolean isDupe = true;
				if (PlayerAttributes.getValue(of, p, PlayerAttributes.nationality) != PlayerAttributes.getValue(of, i, PlayerAttributes.nationality)) {
					isDupe = false;
				} else {
					int score = 0;
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.age) == PlayerAttributes.getValue(of, i, PlayerAttributes.age)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.height) == PlayerAttributes.getValue(of, i, PlayerAttributes.height)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.weight) == PlayerAttributes.getValue(of, i, PlayerAttributes.weight)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.foot) == PlayerAttributes.getValue(of, i, PlayerAttributes.foot)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.favSide) == PlayerAttributes.getValue(of, i, PlayerAttributes.favSide)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.regPos) == PlayerAttributes.getValue(of, i, PlayerAttributes.regPos)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.attack) == PlayerAttributes.getValue(of, i, PlayerAttributes.attack)) {
						score++;
					}
					if (PlayerAttributes.getValue(of, p, PlayerAttributes.accel) == PlayerAttributes.getValue(of, i, PlayerAttributes.accel)) {
						score++;
					}
					if (score < 7) { 
						isDupe = false;
					}
				}
			if (isDupe) {
				return i;
			}
		}
		return -1;
	}

}
