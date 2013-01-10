package de.saxsys.fxarmville.presentation.special;

import java.util.LinkedList;
import java.util.List;

public class BugTracker {

	private static BugTracker INSTANCE = new BugTracker();

	private List<FXAnimatedBug> bugs = new LinkedList<>();

	public static BugTracker getInstance() {
		return INSTANCE;
	}

	public FXAnimatedBug erzeugeBug() {
		FXAnimatedBug fxAnimatedBug = new FXAnimatedBug();
		bugs.add(fxAnimatedBug);
		return fxAnimatedBug;
	}

	public void entferneBug(FXAnimatedBug fxAnimatedBug) {
		bugs.remove(fxAnimatedBug);
	}

	public double anzahlDerBugs() {
		return bugs.size();
	}

}
