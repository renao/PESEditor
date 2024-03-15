package pes.editor;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GeneralAbilityPanel extends JPanel {
	OptionFile of;
	int player;

	JComboBox nationBox;

	JTextField ageField;

	JTextField heightField;

	JTextField weightField;

	JComboBox footBox;

	JComboBox wfaBox;

	JComboBox wffBox;

	JComboBox consBox;

	JComboBox condBox;

	JComboBox injuryBox;

	JComboBox fkBox;

	JComboBox pkBox;

	JComboBox dribBox;

	JComboBox dkBox;

	public GeneralAbilityPanel(OptionFile opf) {
		super(new GridLayout(0, 2));
		of = opf;
		setBorder(BorderFactory.createTitledBorder("General"));
		// stats = s;
		nationBox = new JComboBox(PlayerAttributes.nation);
		ageField = new JTextField(2);
		ageField.setInputVerifier(new VerifierAge());
		heightField = new JTextField(2);
		heightField.setInputVerifier(new VerifierHeight());
		weightField = new JTextField(2);
		weightField.setInputVerifier(new VerifierWeight());
		String[] modF = { "R foot / R side", "R foot / L side",
				"R foot / B side", "L foot / L side", "L foot / R side",
				"L foot / B side" };
		footBox = new JComboBox(modF);
		wfaBox = new JComboBox(PlayerAttributes.mod18);
		wffBox = new JComboBox(PlayerAttributes.mod18);
		consBox = new JComboBox(PlayerAttributes.mod18);
		condBox = new JComboBox(PlayerAttributes.mod18);
		injuryBox = new JComboBox(PlayerAttributes.modInjury);
		String[] mod14 = { "1", "2", "3", "4" };
		dribBox = new JComboBox(mod14);
		dkBox = new JComboBox(mod14);
		String[] mod19 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		fkBox = new JComboBox(mod19);
		String[] mod15 = { "1", "2", "3", "4", "5" };
		pkBox = new JComboBox(mod15);

		add(new JLabel("Nationality"));
		add(nationBox);
		add(new JLabel("Age"));
		add(ageField);
		add(new JLabel("Height"));
		add(heightField);
		add(new JLabel("Weight"));
		add(weightField);
		add(new JLabel("Foot / Side"));
		add(footBox);
		add(new JLabel("Weak Foot Accuracy"));
		add(wfaBox);
		add(new JLabel("Weak Foot Frequency"));
		add(wffBox);
		add(new JLabel("Consistency"));
		add(consBox);
		add(new JLabel("Condition"));
		add(condBox);
		add(new JLabel("Injury Tolerancy"));
		add(injuryBox);
		add(new JLabel("Dribble Style"));
		add(dribBox);
		add(new JLabel("Free Kick Style"));
		add(fkBox);
		add(new JLabel("Penalty Style"));
		add(pkBox);
		add(new JLabel("Drop Kick Style"));
		add(dkBox);
	}

	public void load(int p) {
		player = p;
		nationBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.nationality));
		ageField.setText(PlayerAttributes.getString(of, player, PlayerAttributes.age));
		heightField.setText(PlayerAttributes.getString(of, player, PlayerAttributes.height));
		weightField.setText(PlayerAttributes.getString(of, player, PlayerAttributes.weight));

		wfaBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.wfa));
		wffBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.wff));
		consBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.consistency));
		condBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.condition));
		injuryBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.injury));
		fkBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.freekick));
		pkBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.pkStyle));
		dribBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.dribSty));
		dkBox.setSelectedItem(PlayerAttributes.getString(of, player, PlayerAttributes.dkSty));
		
		int foot = PlayerAttributes.getValue(of, player, PlayerAttributes.foot);
		int side = PlayerAttributes.getValue(of, player, PlayerAttributes.favSide);
		int item = (foot * 3) + side;
		footBox.setSelectedIndex(item);

	}

	class VerifierHeight extends InputVerifier {
		public boolean verify(JComponent input) {
			boolean ok = false;
			JTextField tf = (JTextField) input;
			try {
				int v = Integer.parseInt(tf.getText());
				if (v >= 148 && v <= 211) {
					ok = true;
				}
			} catch (NumberFormatException nfe) {
				ok = false;
			}
			return ok;
		}
	}

	class VerifierWeight extends InputVerifier {
		public boolean verify(JComponent input) {
			boolean ok = false;
			JTextField tf = (JTextField) input;
			try {
				int v = Integer.parseInt(tf.getText());
				if (v >= 1 && v < 128) {
					ok = true;
				}
			} catch (NumberFormatException nfe) {
				ok = false;
			}
			return ok;
		}
	}

	class VerifierAge extends InputVerifier {
		public boolean verify(JComponent input) {
			boolean ok = false;
			JTextField tf = (JTextField) input;
			try {
				int v = Integer.parseInt(tf.getText());
				if (v >= 15 && v <= 46) {
					ok = true;
				}
			} catch (NumberFormatException nfe) {
				ok = false;
			}
			return ok;
		}
	}

}
