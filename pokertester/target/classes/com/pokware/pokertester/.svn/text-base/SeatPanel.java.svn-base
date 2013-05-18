package com.pokware.pokertester;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

public class SeatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton sitButton;
	private JPanel centerPanel;
	private JLabel statusLabel;

	public SeatPanel() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout());
		add(getSitButton(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
		add(getStatusLabel(), BorderLayout.SOUTH);
	}

	private JLabel getStatusLabel() {
		if (statusLabel == null) {
			statusLabel = new JLabel();
			statusLabel.setText("Status");
		}
		return statusLabel;
	}

	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
		}
		return centerPanel;
	}

	public void setSitButton(JButton sitButton) {
		this.sitButton = sitButton;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public void displayCards(int card1, int card2) {
		centerPanel.removeAll();
		centerPanel.add(new JLabel(ImageIconFactory.getInstance().getCardImageIcon(card1)));
		centerPanel.add(new JLabel(ImageIconFactory.getInstance().getCardImageIcon(card2)));
		centerPanel.revalidate();
	}

	public void drawHiddenCards() {
		centerPanel.removeAll();
		centerPanel.add(new JLabel(ImageIconFactory.getInstance().getCardImageIcon(0)));
		centerPanel.add(new JLabel(ImageIconFactory.getInstance().getCardImageIcon(0)));
		centerPanel.revalidate();
	}

	public JButton getSitButton() {
		if (sitButton == null) {
			sitButton = new JButton();
			sitButton.setText("Sit");
		}
		return sitButton;
	}
	
}
