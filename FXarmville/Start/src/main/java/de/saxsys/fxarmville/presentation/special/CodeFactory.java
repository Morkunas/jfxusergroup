package de.saxsys.fxarmville.presentation.special;

import java.util.LinkedList;
import java.util.List;

public class CodeFactory {

	private static CodeFactory INSTANCE = new CodeFactory();

	private final List<FXAnimatedBug> bugs = new LinkedList<>();

	public static CodeFactory getInstance() {
		return INSTANCE;
	}

	public FXAnimatedBug erzeugeRealismus() {
		final FXAnimatedBug fxAnimatedBug = new FXAnimatedBug();
		bugs.add(fxAnimatedBug);
		return fxAnimatedBug;
	}

}
