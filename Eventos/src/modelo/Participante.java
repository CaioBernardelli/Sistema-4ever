package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Participante {
	public Participante(String cpf, String nascimento) {
		super();
		this.cpf = cpf;
		this.nascimento = nascimento;
	}
	private String cpf;
	private String nascimento;
	private ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();

	
	
	public int calcularIdade() {
        // Converter a string da data de nascimento para LocalDate
        LocalDate dataNascimento = LocalDate.parse(nascimento, DateTimeFormatter.ISO_DATE);

        // Obter a data atual
        LocalDate dataAtual = LocalDate.now();

        // Calcular a diferença entre a data atual e a data de nascimento
        long diferencaAnos = ChronoUnit.YEARS.between(dataNascimento, dataAtual);

        // Retornar a idade do participante
        return (int) diferencaAnos;

		
		
		
		
	}



	public String getCpf() {
		return cpf;
	}



	public void setCpf(String cpf) {
		this.cpf = cpf;
	}



	public String getNascimento() {
		return nascimento;
	}



	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}



	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}



	public void setIngressos(ArrayList<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}



	public void adicionar(Ingresso i) {
		ingressos.add(i);
		i.setParticipante(this);
		
	}
	
	
	public void remover(Ingresso i) {
		ingressos.remove(i);
		i.setParticipante(null);
		
	}
	
	
}
