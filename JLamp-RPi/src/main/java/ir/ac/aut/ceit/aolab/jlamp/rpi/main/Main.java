package ir.ac.aut.ceit.aolab.jlamp.rpi.main;

import ir.ac.aut.ceit.aolab.jlamp.rpi.controller.KaaController;
import ir.ac.aut.ceit.aolab.jlamp.rpi.model.Lamp;

public class Main {
	public static void main(String[] args) {
		KaaController.getInstance().start();
        System.out.println(Lamp.getLampById("11").getLampStatus());
	}
}
