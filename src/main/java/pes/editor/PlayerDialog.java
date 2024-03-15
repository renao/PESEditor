package pes.editor;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class PlayerDialog extends JDialog {// implements ListSelectionListener,
											// MouseListener {
	OptionFile of;

	// OptionFile of2;
	// OptionFile of2;
	// JButton cancelBut;
	// JButton importBut;
	// JLabel fileLabel;
	// JLabel oldPlLabel;
	// PlayerList plList;
	// InfoPanel2 infoPanel;
	// boolean of2Open;
	int index;

	Player player;

	GeneralAbilityPanel genPanel;

	PositionPanel posPanel;

	Ability99Panel abiPanel;

	SpecialAbilityPanel spePanel;

	JButton acceptButton;

	JButton cancelButton;

	JButton importButton;

	// int replacement;

	PlayerImportDialog plImpDia;

	public PlayerDialog(Frame owner, OptionFile opf, PlayerImportDialog pid) {
		super(owner, "Edit Player", true);
		JPanel panel = new JPanel();
		JPanel lPanel = new JPanel(new BorderLayout());
		JPanel bPanel = new JPanel();
		acceptButton = new JButton("Accept");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if (check()) {
					updateStats();
					setVisible(false);
				}
			}
		});
		CancelButton cancelButton = new CancelButton(this);
		importButton = new JButton("Import (OF2)");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent i) {
				// System.out.println(index);
				plImpDia.show(index);
				setVisible(false);
			}
		});
		of = opf;
		plImpDia = pid;
		// of2 = opf2;
		genPanel = new GeneralAbilityPanel(of);
		posPanel = new PositionPanel(of);
		abiPanel = new Ability99Panel(of);
		spePanel = new SpecialAbilityPanel(of);

		bPanel.add(acceptButton);
		bPanel.add(cancelButton);
		bPanel.add(importButton);
		lPanel.add(genPanel, BorderLayout.NORTH);
		lPanel.add(posPanel, BorderLayout.CENTER);
		lPanel.add(bPanel, BorderLayout.SOUTH);
		panel.add(lPanel);
		panel.add(abiPanel);
		panel.add(spePanel);
		getContentPane().add(panel);
		pack();
		setResizable(false);
	}

	public void show(Player p) {
		index = p.index;
		player = p;
		setTitle("Edit Player - " + String.valueOf(index) + " - " + p.name);
		if (plImpDia.of2Open) {
			importButton.setVisible(true);
		} else {
			importButton.setVisible(false);
		}
		genPanel.load(index);
		posPanel.load(index);
		abiPanel.load(index);
		spePanel.load(index);
		setVisible(true);
	}

	private boolean check() {
		boolean ok = true;
		int v;
		for (int i = 0; i < abiPanel.ability99.length; i++) {
			try {
				v = Integer.parseInt(abiPanel.field[i].getText());
				if (v < 1 || v > 99) {
					ok = false;
				}
			} catch (NumberFormatException nfe) {
				ok = false;
			}
		}
		try {
			v = Integer.parseInt(genPanel.heightField.getText());
			if (v < 148 || v > 211) {
				ok = false;
			}
		} catch (NumberFormatException nfe) {
			ok = false;
		}
		try {
			v = Integer.parseInt(genPanel.weightField.getText());
			if (v < 1 || v > 127) {
				ok = false;
			}
		} catch (NumberFormatException nfe) {
			ok = false;
		}
		try {
			v = Integer.parseInt(genPanel.ageField.getText());
			if (v < 15 || v > 46) {
				ok = false;
			}
		} catch (NumberFormatException nfe) {
			ok = false;
		}
		return ok;
	}

	private void updateStats() {
		for (int i = 0; i < PlayerAttributes.roles.length; i++) {
			if (i != 1) {
				PlayerAttributes.setValue(of, index, PlayerAttributes.roles[i], boToInt(posPanel.checkBox[i].isSelected()));
			}
		}
		int v = 0;
		for (int i = 0; i < PlayerAttributes.roles.length; i++) {
			if (((String) (posPanel.regBox.getSelectedItem()))
					.equals(PlayerAttributes.roles[i].name)) {
				v = i;
			}
		}
		PlayerAttributes.setValue(of, index, PlayerAttributes.regPos, v);
		
		PlayerAttributes.setValue(of, index, PlayerAttributes.height, genPanel.heightField.getText());

		int item = genPanel.footBox.getSelectedIndex();
		int foot = item / 3;
		int side = item - (foot * 3);
		PlayerAttributes.setValue(of, index, PlayerAttributes.foot, foot);
		PlayerAttributes.setValue(of, index, PlayerAttributes.favSide, side);
		PlayerAttributes.setValue(of, index, PlayerAttributes.wfa, (String) (genPanel.wfaBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.wff, (String) (genPanel.wffBox.getSelectedItem()));
		
		for (int i = 0; i < PlayerAttributes.ability99.length; i++) {
			PlayerAttributes.setValue(of, index, PlayerAttributes.ability99[i], abiPanel.field[i].getText());
		}

		PlayerAttributes.setValue(of, index, PlayerAttributes.consistency, (String) (genPanel.consBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.condition, (String) (genPanel.condBox.getSelectedItem()));

		for (int i = 0; i < PlayerAttributes.abilitySpecial.length; i++) {
			PlayerAttributes.setValue(of, index, PlayerAttributes.abilitySpecial[i], boToInt(spePanel.checkBox[i].isSelected()));
		}
		
		PlayerAttributes.setValue(of, index, PlayerAttributes.injury, (String) (genPanel.injuryBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.freekick, (String) (genPanel.fkBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.pkStyle, (String) (genPanel.pkBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.age, genPanel.ageField.getText());
		PlayerAttributes.setValue(of, index, PlayerAttributes.weight, genPanel.weightField.getText());
		PlayerAttributes.setValue(of, index, PlayerAttributes.nationality, (String) (genPanel.nationBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.dribSty, (String) (genPanel.dribBox.getSelectedItem()));
		PlayerAttributes.setValue(of, index, PlayerAttributes.dkSty, (String) (genPanel.dkBox.getSelectedItem()));

		PlayerAttributes.setValue(of, index, PlayerAttributes.abilityEdited, 1);
	}

	private int boToInt(boolean b) {
		int i = 0;
		if (b) {
			i = 1;
		}
		return i;
	}

}
