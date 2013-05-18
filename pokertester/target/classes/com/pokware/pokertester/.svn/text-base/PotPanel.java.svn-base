package com.pokware.pokertester;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class PotPanel extends JPanel {
	/**
	 * Create the panel.
	 */
	public PotPanel() {
		setLayout(new MigLayout("", "[][][][][][][][grow,fill]", "[][][]"));

		ImageIcon cardImageIcon = ImageIconFactory.getInstance().getCardImageIcon(0);
		JLabel lblA = new JLabel(cardImageIcon);
		add(lblA, "cell 2 1");

		JLabel lblB = new JLabel(cardImageIcon);
		add(lblB, "cell 3 1");

		JLabel lblC = new JLabel(cardImageIcon);
		add(lblC, "cell 4 1");

		JLabel lblD = new JLabel(cardImageIcon);
		add(lblD, "cell 5 1");

		JLabel lblE = new JLabel(cardImageIcon);
		add(lblE, "cell 6 1");

	}
}
