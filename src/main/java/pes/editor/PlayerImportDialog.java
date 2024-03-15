package pes.editor;

import pes.editor.constants.PlayerConstant;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerImportDialog extends JDialog implements
		ListSelectionListener, MouseListener {
	OptionFile of;

	OptionFile of2;

	JLabel fileLabel;

	SelectByTeam plList;

	InfoPanel infoPanel;

	boolean of2Open;

	int index;

	int replacement;
	
	JRadioButton allButton;
	
	JRadioButton statsButton;

	public PlayerImportDialog(Frame owner, OptionFile opf, OptionFile opf2) {
		super(owner, "Import Player", true);
		of = opf;
		of2 = opf2;
		fileLabel = new JLabel("From:");
		plList = new SelectByTeam(of2, false);
		infoPanel = new InfoPanel(of2);
		plList.squadList.addListSelectionListener(this);
		plList.squadList.addMouseListener(this);
		CancelButton cancelButton = new CancelButton(this);
		
		allButton = new JRadioButton("Import everything (name, appearance, stats, etc.)");
		statsButton = new JRadioButton("Import only the stats editable on the 'Edit Player' dialog");
		JRadioButton exStatsButton = new JRadioButton("Import everything except stats (name, appearance, etc.)");
		ButtonGroup group = new ButtonGroup();
		group.add(allButton);
		group.add(statsButton);
		group.add(exStatsButton);
		allButton.setSelected(true);
		
		JPanel topPanel = new JPanel(new GridLayout(4,1));
		topPanel.add(fileLabel);
		topPanel.add(allButton);
		topPanel.add(statsButton);
		topPanel.add(exStatsButton);
		
		getContentPane().add(plList, BorderLayout.WEST);
		getContentPane().add(infoPanel, BorderLayout.CENTER);
		getContentPane().add(cancelButton, BorderLayout.SOUTH);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		of2Open = false;
		index = 0;
		replacement = 0;
		pack();
		setResizable(false);
	}

	public void show(int i) {
		index = i;
		setVisible(true);
	}

	public void refresh() {
		plList.refresh();
		of2Open = true;
		fileLabel.setText("  From:  " + of2.fileName);
		index = 0;
		replacement = 0;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (!plList.squadList.isSelectionEmpty()) {
				infoPanel
						.refresh(
								((Player) plList.squadList.getSelectedValue()).index,
								0);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		int clicks = e.getClickCount();
		JList list = (JList) (e.getSource());
		int pi = ((Player) list.getSelectedValue()).index;
		if (clicks == 2 && pi != 0) {
			replacement = pi;
			importPlayer();
			setVisible(false);
		}
	}

	private void importPlayer() {
		int ia = PlayerConstant.startAdr + (index * 124);
		if (index >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
			ia = PlayerConstant.startAdrE + ((index - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124);
		}
		int ra = PlayerConstant.startAdr + (replacement * 124);
		if (replacement >= PlayerConstant.INDEX_FIRST_EDIT_PLAYER) {
			ra = PlayerConstant.startAdrE
					+ ((replacement - PlayerConstant.INDEX_FIRST_EDIT_PLAYER) * 124);
		}
		if (allButton.isSelected()) {
			System.arraycopy(of2.data, ra, of.data, ia, 124);
			PlayerAttributes.setValue(of, index, PlayerAttributes.nameEdited, 1);
			PlayerAttributes.setValue(of, index, PlayerAttributes.callEdited, 1);
			PlayerAttributes.setValue(of, index, PlayerAttributes.shirtEdited, 1);
			PlayerAttributes.setValue(of, index, PlayerAttributes.abilityEdited, 1);
			if (!of.isWE() && of2.isWE()) {
				Convert.player(of, index, Convert.WE2007_PES6);
			}
			if (of.isWE() && !of2.isWE()) {
				Convert.player(of, index, Convert.PES6_WE2007);
			}
		} else if (statsButton.isSelected()){
			PlayerAttributes.setValue(of, index, PlayerAttributes.nationality, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.nationality));
			PlayerAttributes.setValue(of, index, PlayerAttributes.age, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.age));
			PlayerAttributes.setValue(of, index, PlayerAttributes.height, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.height));
			PlayerAttributes.setValue(of, index, PlayerAttributes.weight, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.weight));
			PlayerAttributes.setValue(of, index, PlayerAttributes.foot, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.foot));
			PlayerAttributes.setValue(of, index, PlayerAttributes.favSide, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.favSide));
			PlayerAttributes.setValue(of, index, PlayerAttributes.wfa, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.wfa));
			PlayerAttributes.setValue(of, index, PlayerAttributes.wff, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.wff));
			PlayerAttributes.setValue(of, index, PlayerAttributes.condition, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.condition));
			PlayerAttributes.setValue(of, index, PlayerAttributes.consistency, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.consistency));
			PlayerAttributes.setValue(of, index, PlayerAttributes.injury, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.injury));
			PlayerAttributes.setValue(of, index, PlayerAttributes.dribSty, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.dribSty));
			PlayerAttributes.setValue(of, index, PlayerAttributes.pkStyle, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.pkStyle));
			PlayerAttributes.setValue(of, index, PlayerAttributes.freekick, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.freekick));
			PlayerAttributes.setValue(of, index, PlayerAttributes.dkSty, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.dkSty));
			PlayerAttributes.setValue(of, index, PlayerAttributes.regPos, PlayerAttributes.getValue(of2, replacement, PlayerAttributes.regPos));
			
			for (int i = 0; i < PlayerAttributes.roles.length; i++) {
				PlayerAttributes.setValue(of, index, PlayerAttributes.roles[i], PlayerAttributes.getValue(of2, replacement, PlayerAttributes.roles[i]));
			}
			for (int i = 0; i < PlayerAttributes.ability99.length; i++) {
				PlayerAttributes.setValue(of, index, PlayerAttributes.ability99[i], PlayerAttributes.getValue(of2, replacement, PlayerAttributes.ability99[i]));
			}
			for (int i = 0; i < PlayerAttributes.abilitySpecial.length; i++) {
				PlayerAttributes.setValue(of, index, PlayerAttributes.abilitySpecial[i], PlayerAttributes.getValue(of2, replacement, PlayerAttributes.abilitySpecial[i]));
			}
			PlayerAttributes.setValue(of, index, PlayerAttributes.abilityEdited, 1);
		} else {
			byte[] temp = new byte[124];
			System.arraycopy(of2.data, ra, temp, 0, 124);
			
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.nationality, PlayerAttributes.getValue(of, index, PlayerAttributes.nationality));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.age, PlayerAttributes.getValue(of, index, PlayerAttributes.age));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.height, PlayerAttributes.getValue(of, index, PlayerAttributes.height));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.weight, PlayerAttributes.getValue(of, index, PlayerAttributes.weight));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.foot, PlayerAttributes.getValue(of, index, PlayerAttributes.foot));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.favSide, PlayerAttributes.getValue(of, index, PlayerAttributes.favSide));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.wfa, PlayerAttributes.getValue(of, index, PlayerAttributes.wfa));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.wff, PlayerAttributes.getValue(of, index, PlayerAttributes.wff));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.condition, PlayerAttributes.getValue(of, index, PlayerAttributes.condition));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.consistency, PlayerAttributes.getValue(of, index, PlayerAttributes.consistency));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.injury, PlayerAttributes.getValue(of, index, PlayerAttributes.injury));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.dribSty, PlayerAttributes.getValue(of, index, PlayerAttributes.dribSty));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.pkStyle, PlayerAttributes.getValue(of, index, PlayerAttributes.pkStyle));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.freekick, PlayerAttributes.getValue(of, index, PlayerAttributes.freekick));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.dkSty, PlayerAttributes.getValue(of, index, PlayerAttributes.dkSty));
			PlayerAttributes.setValue(of2, replacement, PlayerAttributes.regPos, PlayerAttributes.getValue(of, index, PlayerAttributes.regPos));
			
			for (int i = 0; i < PlayerAttributes.roles.length; i++) {
				PlayerAttributes.setValue(of2, replacement, PlayerAttributes.roles[i], PlayerAttributes.getValue(of, index, PlayerAttributes.roles[i]));
			}
			for (int i = 0; i < PlayerAttributes.ability99.length; i++) {
				PlayerAttributes.setValue(of2, replacement, PlayerAttributes.ability99[i], PlayerAttributes.getValue(of, index, PlayerAttributes.ability99[i]));
			}
			for (int i = 0; i < PlayerAttributes.abilitySpecial.length; i++) {
				PlayerAttributes.setValue(of2, replacement, PlayerAttributes.abilitySpecial[i], PlayerAttributes.getValue(of, index, PlayerAttributes.abilitySpecial[i]));
			}
			
			System.arraycopy(of2.data, ra, of.data, ia, 124);
			PlayerAttributes.setValue(of, index, PlayerAttributes.nameEdited, 1);
			PlayerAttributes.setValue(of, index, PlayerAttributes.callEdited, 1);
			PlayerAttributes.setValue(of, index, PlayerAttributes.shirtEdited, 1);
			if (!of.isWE() && of2.isWE()) {
				Convert.player(of, index, Convert.WE2007_PES6);
			}
			if (of.isWE() && !of2.isWE()) {
				Convert.player(of, index, Convert.PES6_WE2007);
			}
			
			System.arraycopy(temp, 0, of2.data, ra, 124);
		}
	}
}
