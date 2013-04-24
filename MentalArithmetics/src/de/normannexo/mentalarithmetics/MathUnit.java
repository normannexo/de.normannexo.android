package de.normannexo.mentalarithmetics;

import java.util.Random;

public class MathUnit {
    //private Mode mode;
    private Random random;
    private int stellenZahl1;
    private int stellenZahl2;

    public MathUnit() {
	random = new Random(System.currentTimeMillis());
	stellenZahl1 = 2;
	stellenZahl2 = 2;
    }

    public Aufgabe getAufgabe(Mode mode) {
	return (new Aufgabe(Mode.Quad, 20,30));
    }
    
    public Aufgabe createAufgabe(Mode mode) {
	int zahl1 = (int) (random.nextDouble() * 9 * Math.pow(10, stellenZahl1 -1) + Math.pow(10, stellenZahl1 -1));
	int zahl2 = (int) (random.nextDouble() * 9 * Math.pow(10, stellenZahl2 -1) + Math.pow(10, stellenZahl2 -1));
	if (mode == Mode.Mix) {
	    int rand = (int) (random.nextDouble() * 4);
	    switch (rand) {
	    case 0:
		mode = Mode.Mal;
		break;
	    case 1:
		mode = Mode.Quad;
		break;
	    case 2:
		mode = Mode.Plus;
		break;
	    case 3:
		mode = Mode.Minus;
		break;
	    }
	}
	
	return new Aufgabe(mode, zahl1, zahl2);
    }
    
    public void setStellen(int zahl, int stellen) {
	if (zahl == 1) {
	    stellenZahl1 = stellen;
	} else if (zahl == 2) {
	    stellenZahl2 = stellen;
	}
    }
}
