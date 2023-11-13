package it.contocorrente.model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Random;

import com.itextpdf.text.DocumentException;

public class ContoInvestimento extends Conto {
	
	Random random = new Random();
	double tassoInteresseAnnuo;
	
	public ContoInvestimento(String utente, LocalDate data) {
		super(utente, data);
		tassoInteresseAnnuo = generaTassoInteresseAnnuo();
	}
	public double generaInteressi(int year) {
		return super.generaIteressi(tassoInteresseAnnuo,year);
	}
	public void preleva(LocalDate data, double ammount) {
		super.preleva(data, ammount);
	}
	public void versa(LocalDate data, double ammount) {
		super.versa(data, ammount);
	}

	public static double generaTassoInteresseAnnuo() {
		Random generator = new Random();
		int tassoInteresseAnnuo = generator.nextInt(101);
		int segno = generator.nextInt(2);
		if(segno == 0)
			tassoInteresseAnnuo *= -1;
		return tassoInteresseAnnuo / 100.0;
	}
	public void printPDF (int year) {
		try {
			super.printPDF(tassoInteresseAnnuo, year);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
}
