package modelo;

import java.util.ArrayList;

public class Evento {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}
	public void setIngressos(ArrayList<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	public Evento(int id,String data, String descricao, int capacidade, double preco) {
		super();
		this.id = id;
		this.data = data;
		this.descricao = descricao;
		this.capacidade = capacidade;
		this.preco = preco;
		
	}
	public double getPreco() {
		return preco;
	}


	public void setPreco(double preco) {
		this.preco = preco;
	}
	private int id;
	private String data;
	private String descricao;
	private int capacidade;
	private double preco;
	private ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();
	
	
	public int quantidadeIngressos() {
		return ingressos.size();
	
	}
	
	
	public double totalArrecadado() {
		 return ingressos.size() * getPreco();
		
	}
	public void adicionar(Ingresso i) {
		ingressos.add(i);
		i.setEvento(this);
		
		
	}
	
	public void remover(Ingresso i) {
		ingressos.remove(i);
		i.setEvento(null);
		
		
	}
	
	
	//o método retornará true quando o número de ingressos for maior ou igual à capacidade do evento, indicando que o evento está lotado. Caso contrário, retornará false.
	public boolean lotado() {
		 return this.getIngressos().size() >= this.capacidade;
			
			
	
		}
		
		
		
		
	}




