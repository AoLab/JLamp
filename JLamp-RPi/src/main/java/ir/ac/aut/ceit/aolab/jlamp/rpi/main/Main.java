package ir.ac.aut.ceit.aolab.jlamp.rpi.main;

import ir.ac.aut.ceit.aolab.jlamp.rpi.controller.KaaController;

public class Main {
	public static void main(String[] args) {
		KaaController.getInstance().start();
	}
}
