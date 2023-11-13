package it.contocorrente.model;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import com.itextpdf.text.DocumentException;

public class ContoDeposito extends Conto {
	
	private final double tassoInteresseAnnuo = 0.10;


	public ContoDeposito(String utente, LocalDate data) {
		super(utente, data);
	}
	public double generaInteressi(int year) {
		return super.generaIteressi(tassoInteresseAnnuo,year);
	}
	public void preleva(LocalDate data, double ammount) {
		if(ammount>1000)
			System.out.println("Impossibile prelevare qunatità superiori ai 1000€");
		else 
			super.preleva(data, ammount);
	}
	public void versa(LocalDate data, double ammount) {
		super.versa(data, ammount);
	}
	public void printPDF (int year) {
		try {
			super.printPDF(tassoInteresseAnnuo, year);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
}
