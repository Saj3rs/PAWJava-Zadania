package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class CalcBB {
	private String amm;
	private String years;
	private Double full;
	private String interest;
	private Double monthly;
	
	

	
	
	@Inject
	FacesContext ctx;

	public String getAmm() {
		return amm;
	}

	public void setAmm(String amm) {
		this.amm = amm;
	}
	
	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}
	
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Double getFull() {
		return full;
	}

	public void setFull(Double full) {
		this.full = full;
	}

	public Double getMonthly() {
		return monthly;
	}

	public void setMonthly(Double monthly) {
		this.monthly = monthly;
	}

	public boolean doTheMath() {
		try {
			
			double amm = Double.parseDouble(this.amm);
			double years = Double.parseDouble(this.years);
			double interest = Double.parseDouble(this.interest)/100;
			double mYear=years;
			full=amm;
			while(years>0){
	            full +=interest*full;
	            years--;
	        }
	        //obliczenie kwoty miesięcznej
			
	        setMonthly(full/(12*mYear));
			

			//ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów ", null));
			return false;
		}
	}
//stara wersja bez uzycia wbudowanego ajaxa
	// Go to "showresult" if ok 
	//public String calc() {
	//	if (doTheMath()) {
	//		return "showresult";
	//	}
	//	return null;
	//}

	// Put result in messages on AJAX call
	public String calc() {
		if (doTheMath()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pelna suma do splacenia: " + full, null));
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Miesieczna oplata: " + monthly, null));
		}
		return null;
	}

	public String info() {
		return "info";
	}
}
