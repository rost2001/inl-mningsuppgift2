package inlämningsuppgift2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Main klass som läser in kunder från fil, lägger till kunder i en array list,
 * skriver till en annan fil för den personliga tränaren. och använder en
 * dialogruta för att användas av receptionisten.
 * 
 * @author Robin
 *
 */
public class BestGymEverMain {

	private static ArrayList<Customer> customerList = new ArrayList<Customer>();

	public static void main(String[] args) {

		String fileToString = null;
		// filen inläst till text
		fileToString = readCustomersFromFile("customers.txt"); // input filen
		addCustomersToList(fileToString);

		String in = JOptionPane.showInputDialog("Kundens namn eller personnummer (10 siffror)");
		String customer = checkCustomer(in);
		// visar om kunder är behörig och betalat
		JOptionPane.showMessageDialog(null, customer);

	}

	/**
	 * Läser in kunderna från filen.
	 * 
	 * @param path till filen, relativ.
	 * @return en text sträng med alla inlästa kunder.
	 */
	private static String readCustomersFromFile(String path) {
		StringBuilder text = new StringBuilder();
		try (Scanner scan = new Scanner(new File(path), "iso8859-1")) {
			while (scan.hasNext()) {
				// lägger peronnummer, namn och datum på samma rad
				text.append(scan.nextLine() + ", " + scan.nextLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return text.toString();
	}

	/**
	 * Skriver till tränarens fil när en betalade kund kommer.
	 * 
	 * @param text kundens personnummer och namn.
	 * @param path till tränarens fil, relativ.
	 */
	private static void saveCustomerToFile(String text, String path) {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(path), true))) {
			pw.print(text + " tränade " + LocalDate.now() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lägger in alla kunder i en array list.
	 * 
	 * @param cust kundens information.
	 */
	private static void addCustomersToList(String cust) {
		// gör fil strängen till en array av kunder
		String[] customersToArray = cust.split("\n");

		for (int i = 0; i < customersToArray.length; i++) {
			// gör en array[3] av varje kunds info och skapar ett nytt Customer objekt.
			String[] customer = customersToArray[i].split(",");
			Customer c = new Customer(customer[0], customer[1].trim(), customer[2]);
			customerList.add(c);
		}
	}

	/**
	 * Kollar om kunden finns med i listan och är behörig.
	 * 
	 * @param s namnet eller personnummret på kunden.
	 * @return kundens personnummer och namn eller om den inte finns med.
	 */
	private static String checkCustomer(String s) {
		String c;
		for (int i = 0; i < customerList.size(); i++) {
			if (customerList.get(i).equals(s)) {
				c = customerList.get(i).toString();

				// kollar om kunden betalt och isf skriver till tränarens fil.
				if (customerList.get(i).getHasPayed() == true) {
					saveCustomerToFile(customerList.get(i).getPersNr() + ", " + customerList.get(i).getFullName(),
							"payingCustomers.txt");
				}
				return c;
			}
		}
		return "Personen är finns inte med i systemet!";
	}
}
