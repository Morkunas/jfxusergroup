package de.saxsys.fxarmville.model.util;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public final class FruchtBildLader {

	private static final FruchtBildLader INSTANCE = new FruchtBildLader();
	private Map<String, Image> cache = new HashMap<>();
	
	private FruchtBildLader(){
	}
	
	public static FruchtBildLader getInstance() {
		return INSTANCE;
	}
	
	public Image getBild(String bildName) {
		if (cache.containsKey(bildName)) {
			return cache.get(bildName);
		}
		Image bild = new Image(FruchtBildLader.class.getResourceAsStream("/" +bildName));
		cache.put(bildName, bild);
		return bild;
	}
	
}
