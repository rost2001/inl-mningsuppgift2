package inlämningsuppgift2;

import java.time.LocalDate;

/**
 * Klass som representerar en kund till gymmet
 * 
 * @author Robin
 *
 */
public class Customer {

	private String persNr = null;
	private String fullName = null;
	private boolean hasPayed = false;
	// används för att räkna ut om kunden betalat senaste året.
	private LocalDate lastPayDate = null;

	/**
	 * @param pNr personnummer
	 * @param n   namn
	 * @param p   när kunden senast betalat.
	 */
	public Customer(String pNr, String n, String p) {
		setPersNr(pNr);
		setFullName(n);
		setHasPayed(p);
	}

	/**
	 * Räknar ut om kunden betalat avgiften inom senaste året och därmed är behörig
	 * att träna.
	 * 
	 * @param p är datumet kunden senast betalat.
	 */
	private void setHasPayed(String p) {
		String[] payed = p.split("-");
		// sätter lastPayDate.
		this.lastPayDate = LocalDate.of(Integer.parseInt(payed[0].trim()), Integer.parseInt(payed[1].trim()),
				Integer.parseInt(payed[2].trim()));
		// kollar om kunden betalat under året.
		if (!(lastPayDate.plusDays(365).isBefore(LocalDate.now()))) {
			this.hasPayed = true;
		}
	}

	/**
	 * @return om kunden betalat senaste året.
	 */
	public boolean getHasPayed() {
		return hasPayed;
	}

	/**
	 * Kollar så personnummret på kunden är 10 siffror långt och att det inte
	 * innehåller annat än bara siffror.
	 * 
	 * @param pNr personnummer.
	 */
	private void setPersNr(String pNr) {
		if (pNr.length() != 10) {
			System.err.println("Personnummer ska vara 10 siffror! " + pNr);
			System.exit(-1);
		}
		for (int i = 0; i < pNr.length(); i++) {
			if (!Character.isDigit(pNr.charAt(i))) {
				System.err.println("Personnummer innehåller annat än siffror! " + pNr);
				System.exit(-1);
			}
		}
		this.persNr = pNr;
	}

	/**
	 * @return kundens personnummer.
	 */
	public String getPersNr() {
		return persNr;
	}

	/**
	 * @param persInfo som är för och efternamn.
	 */
	private void setFullName(String persInfo) {
		this.fullName = persInfo;
	}

	/**
	 * @return kundens för och efternamn.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * to string som returnerar personnummer för och efternamn samt om kunden
	 * betalat.
	 */
	@Override
	public String toString() {
		String payed = "Har betalat!";
		String notPayed = "Har inte betalat!";
		if (this.getHasPayed() != true) {
			return this.getPersNr() + ", " + this.getFullName() + ", " + notPayed;
		} else
			return this.getPersNr() + ", " + this.getFullName() + ", " + payed;
	}

	/**
	 * Används för att testa om kunden finns med i systemet genom att jämföra
	 * personnummer och namn.
	 * 
	 * @param c är personmummer och för och efternamn samt
	 * @return om kunden finns med.
	 */
	public boolean equals(String c) {
		if ((this.getPersNr().equalsIgnoreCase(c)) || (this.getFullName().equalsIgnoreCase(c))) {
			return true;
		} else
			return false;
	}
}
