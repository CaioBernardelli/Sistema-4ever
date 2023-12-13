package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ingresso {
	@Override
	public String toString() {
		return "Ingresso [codigo=" + codigo + ", telefone=" + telefone + "]";
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public Evento getEvento() {
		return evento;
	}


	public void setEvento(Evento evento) {
		this.evento = evento;
	}


	public Participante getParticipante() {
		return participante;
	}


	public void setParticipante(Participante participante) {
		this.participante = participante;
	}


	private String codigo;
	private String telefone;
	private Evento evento;
	private Participante participante;
	public Ingresso(String codigo, String telefone, Evento evento, Participante participante) {
		super();
		this.codigo = codigo;
		this.telefone = telefone;
		this.evento = evento;
		this.participante = participante;
	}

	
	public double calcularPreço() {
		 double precoEvento = evento.getPreco();
	     double desconto = 0.0;

	     int idadeParticipante = participante.calcularIdade();
		
		
		
		if(idadeParticipante<18) {
		desconto = 0.1 * precoEvento ;
		} else if (idadeParticipante >=60) {
			desconto = 0.2 * precoEvento ;
			
		}
		
		double precoFinal = precoEvento  - desconto;
		return precoFinal;
		
	}
	
	public boolean verificaIngressoUltrapassado() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataEvento = LocalDate.parse(this.getEvento().getData(), f);
        LocalDate hoje = LocalDate.now();

        return dataEvento.isBefore(hoje); // Se a data do evento for maior do que a data de hoje, retorna true, provando que a data do evento não está ultrapassada
    }
	

}
