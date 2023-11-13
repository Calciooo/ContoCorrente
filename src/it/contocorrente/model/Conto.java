package it.contocorrente.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Conto {
	protected String utente;
	protected LocalDate data;
	protected double saldo;
	protected List<Movimento> storico;
	protected String filePath ="./EstrattoConto/";
	
	public Conto(String utente, LocalDate data) {
		super();
		this.utente = utente;
		this.data = data;
		saldo = 1000;
		this.storico = new ArrayList<Movimento>();
		Movimento apertura = new Movimento(data, "Apertura", 0, saldo);
		storico.add(apertura);
	}
	
	public void preleva(LocalDate data, double ammount) {
		if(ammount>saldo)
			System.out.println("Impossibile prelevare");
		else {
			saldo -= ammount;
			this.storico.add(new Movimento(data,"Prelievo",ammount,saldo));
		}
	}
	public void versa(LocalDate data, double ammount) {
		saldo += ammount;
		this.storico.add(new Movimento(data,"Versamento",ammount,saldo));
	}
	
	
	public double generaIteressi(double tassoAnnuo,int year) {
		LocalDate endOfYear = LocalDate.of(year, 12, 31);
		LocalDate endOfPrevYear = LocalDate.of(year-1, 12, 31);

		this.storico.add(new Movimento(endOfYear,"Fine Anno",0,0));
		
		Comparator<Movimento> cmp = (Movimento t1, Movimento t2) -> t1.compareTo(t2);
		storico.sort(cmp);			
		
		double interesse = 0;
//		System.out.println("Storico "+storico.toString());
		for (int i = 0; i<storico.size(); i++) {
			Movimento mov = storico.get(i);
			if (mov.getData().isEqual(endOfYear)) {
				mov.setSaldo_post(storico.get(i-1).getSaldo_post());
//				System.out.println(mov);
			}
			if (mov.getData().isBefore(endOfPrevYear))
				continue;
			if (mov.getData().isBefore(endOfYear)) {
				double saldoPeriodo = storico.get(i).getSaldo_post();
//				System.out.println("Current mov "+mov.toString());
				int periodo = (int) (storico.get(i+1).getData().toEpochDay() - storico.get(i).getData().toEpochDay());
				double interesseGiornaliero = (saldoPeriodo * tassoAnnuo) / 365.0;
				interesse += interesseGiornaliero * periodo;
//				System.out.println("Periodo: "+periodo
//						+" Int gioranliero:"+interesseGiornaliero
//						+" Partial interest "+interesse);
			}
		}
        DecimalFormat df = new DecimalFormat("0.00");
//      System.out.println("Total interest "+df.format(interesse));
		return Double.valueOf(df.format(interesse));
	}
	
	
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "Conto [utente=" + utente + ", data=" + data + ", saldo=" + saldo + "]";
	}
	
	public void printPDF(double tassoAnnuo, int year ) throws FileNotFoundException, DocumentException {
		
		Font font = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
		DecimalFormat df = new DecimalFormat("0.00");
		Document document = new Document();
		OutputStream outputStream = new FileOutputStream(new File(filePath+"EC_"+utente+"_"+LocalDate.now()+".pdf"));
		PdfWriter.getInstance(document, outputStream);
		document.open();
		
		document.add(new Paragraph("Andrea Calciolari - 31/12/"+year));
		
		document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------"));		
		String header = " DATA        | TIPO OPERAZIONE  | IMPORTO   | SALDO FINALE";
		Paragraph headerParagrafo = new Paragraph();
		headerParagrafo.setFont(font);
		headerParagrafo.add(header);
		document.add(headerParagrafo);

		double saldoFineAnno = 0;
		
		for (Movimento movimento : storico) {
			if(movimento.getData().isBefore(LocalDate.of(year, 12, 31))) {
				Paragraph paragrafo = new Paragraph();
	    		paragrafo.setFont(font);
	    		paragrafo.add(movimento.toString());
				document.add(paragrafo);
				saldoFineAnno = movimento.getSaldo_post();
			}
		}
		document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------\n"));		
		
		double interessiLordi = generaIteressi(tassoAnnuo, year);
		double interessiNetti = generaIteressi(tassoAnnuo, year)*0.74;
		
		document.add(new Paragraph("Saldo dopo le operazioni: "+saldoFineAnno));
		document.add(new Paragraph("Interessi maturati al 31/"+year+" lordi: "+interessiLordi));
		document.add(new Paragraph("Interessi maturati al 31/"+year+" netti: "+df.format(interessiNetti)));
		document.add(new Paragraph("Saldo finale "+df.format(interessiNetti+saldoFineAnno)));
		
		
		
		
		document.close();
	}
	
	
	
	
	
//	public void generaPDF(LocalDate fineAnno, double interessiLordi, double interessiNetti, double saldoDopoOperazioni, DecimalFormat decimalFormat) {
//		String nomeDocumento;
//		PdfDocument pdf;
//		
//		try {
//			nomeDocumento = "./EC_" + titolare + "_" + fineAnno + ".pdf";
//			pdf = new PdfDocument(new PdfWriter(nomeDocumento));
//			Document document = new Document(pdf);
//			
//			String riga;
//			
//			riga = "|-----------------------------------------------------------------------------------|";
//			document.add(new Paragraph(riga));
//			
//			riga = "| Data: " + fineAnno + " " + titolare;
//			document.add(new Paragraph(riga));
//			
//			riga = "| Data            | Tipo Operazione    | Quantita' | Saldo parziale";
//			document.add(new Paragraph(riga));
//			
//			Movimento movimento;
//			for(int i = 0; i < movimenti.size(); i++)
//			{
//				movimento = movimenti.get(i);
//				String stringaDenaro = Double.toString(movimento.getDenaro());
//				if(stringaDenaro.length() < 9)
//				{
//					int lunghezza = stringaDenaro.length();
//					for(int k = 0; k < 9 - lunghezza; k++)
//					{
//						stringaDenaro += " ";
//					}
//				}
//				
//				String stringaTipo = movimento.getTipo();
//				if(stringaTipo.length() < 15)
//				{
//					int lunghezza = stringaTipo.length();
//					for(int k = 0; k < 15 - lunghezza; k++)
//					{
//						stringaTipo += "  ";
//					}
//					if(movimento.getTipo().equals("Apertura"))
//					{
//						stringaTipo += "  ";
//					} else if(movimento.getTipo().equals("Prelievo"))
//					{
//						stringaTipo += "   ";
//					}
//				}
//				riga = "| " + movimento.getData() + " | " + stringaTipo + " | " + stringaDenaro + " | " + movimento.getSaldoFinale();
//				document.add(new Paragraph(riga));
//			}
//			riga = "|";
//			document.add(new Paragraph(riga));
//			
//			riga = "| Saldo dopo le operazioni: " + saldoDopoOperazioni;
//			document.add(new Paragraph(riga));
//			
//			riga = "|";
//			document.add(new Paragraph(riga));
//			
//			riga = "| Interessi maturati al 31/12 lordi: " + decimalFormat.format(interessiLordi);
//			document.add(new Paragraph(riga));
//			
//			riga = "| Interessi maturati al 31/12 netti: " + decimalFormat.format(interessiNetti);
//			document.add(new Paragraph(riga));
//			
//			riga = "|";
//			document.add(new Paragraph(riga));
//			
//			riga = "| Saldo finale: " + decimalFormat.format(saldo);
//			document.add(new Paragraph(riga));
//			
//			riga = "|-----------------------------------------------------------------------------------|";
//			document.add(new Paragraph(riga));
//			
//			document.close();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
}
