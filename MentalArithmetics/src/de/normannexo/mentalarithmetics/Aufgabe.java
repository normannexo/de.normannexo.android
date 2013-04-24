package de.normannexo.mentalarithmetics;

public class Aufgabe {
    private Mode mode;
    private int loesung;
    private int zahl1;
    private int zahl2;

    public Aufgabe(Mode mode, int zahl1, int zahl2) {
	this.mode = mode;
	this.zahl1 = zahl1;
	this.zahl2 = zahl2;

	switch (mode) {
	case Mal:
	    this.loesung = zahl1 * zahl2;
	    break;

	case Plus:
	    this.loesung = zahl1 + zahl2;
	    break;

	case Minus:
	    if (zahl2 > zahl1) {
		int iTmp = zahl1;
		this.zahl1 = zahl2;
		this.zahl2 = iTmp;
	    }
	    this.loesung = this.zahl1 - this.zahl2;
	    break;

	case Durch:
	    this.loesung = zahl1 / zahl2;
	    break;

	case Quad:
	    this.zahl2 = zahl1;
	    this.loesung = zahl1 * zahl1;
	    break;
	}

    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	switch (mode) {
	case Mal:
	    sb.append(zahl1 + " * " + zahl2);
	    break;

	case Plus:
	    sb.append(zahl1 + " + " + zahl2);
	    break;

	case Durch:
	    sb.append(zahl1 + " / " + zahl2);
	    break;

	case Quad:
	    sb.append(zahl1 + "²");
	    break;

	case Minus:
	    sb.append(zahl1 + " - " + zahl2);
	    break;
	}
	return sb.toString();
    }

    public int getLoesung() {
	return loesung;
    }

}