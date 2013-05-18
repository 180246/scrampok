package com.pokware.pokertester;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImageIconFactory {

	private final static ImageIconFactory instance = new ImageIconFactory();

	public static ImageIconFactory getInstance() {
		return instance;
	}

	private final ImageIcon[] cards = new ImageIcon[53];

	public ImageIconFactory() {
		String name = "/com/pokware/resources/cards/b1fv.png";
		URL resource = ImageIconFactory.class.getResource(name);
		if (resource == null) {
			throw new RuntimeException("can't find " + name);
		}
 		ImageIcon icon = new ImageIcon(resource);
 		cards[0] = icon;

		for (int i = 1; i <= 52; i++) {
			name = "/com/pokware/resources/cards/"+i+".png";
			resource = ImageIconFactory.class.getResource(name);
			if (resource == null) {
				throw new RuntimeException("can't find " + name);
			}
	 		icon = new ImageIcon(resource);
	 		cards[i] = icon;
		}
	}

	public ImageIcon getCardImageIcon(int index) {
		return cards[index];
	}

}
