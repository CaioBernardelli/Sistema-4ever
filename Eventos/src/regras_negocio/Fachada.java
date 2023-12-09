package regras_negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import modelo.Convidado;
import modelo.Evento;
import modelo.Ingresso;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private Fachada() {}	
	
	public static void criarEvento(String data, String descricao, int capacidade, double preco) throws Exception{
		
		if(preco < 0)
			throw new Exception("Preço Inválido - Preço não pode ser nagativo ");
		if (data == null || descricao == null || data.isEmpty() || descricao.isEmpty()) {
	        throw new Exception("Data e descrição do evento são obrigatórias");
	    }
		if (capacidade < 2) {
	        throw new Exception("Capacidade do evento deve ser no mínimo 2 ingressos");
	    }
		
		if (repositorio.existeEventoNaData(data)) {
	        System.out.println("Aviso: Já existe um evento na mesma data.");
	    }
		
	
		
		int id = repositorio.gerarId();
		Evento e = new Evento(id, data,descricao,capacidade, preco);	
		repositorio.adicionar(e);
		repositorio.salvarObjetos();}
	
	  
	public static void criarParticipante(String cpf, String nascimento) throws Exception {
	    // Regra: CPF é obrigatório e deve ser válido
	    if (cpf == null || cpf.isEmpty()) {
	        throw new Exception("CPF é obrigatório");
	    }
	    
	    
	    if (nascimento == null || nascimento.isEmpty()) {
	        throw new Exception("data é obrigatório");
	    }
	    

	    // Outras verificações de validade do CPF podem ser adicionadas aqui
	    // ...

	    // Verificar se já existe um participante com o mesmo CPF
	    if (repositorio.existeParticipanteComCPF(cpf)) {
	        throw new Exception("Já existe um participante com o mesmo CPF");
	    }

	    // Criar o participante e adicioná-lo ao repositório
	    Participante participante = new Participante(cpf, nascimento);
	    repositorio.adicionar(participante);
	    repositorio.salvarObjetos();
	}

	
	
	   public static void criarConvidado(String cpf, String nascimento, String empresa) throws Exception {

		   if (cpf == null || cpf.isEmpty()) {
		        throw new Exception("CPF é obrigatório");
		    }
		   
		   
		   if (empresa == null || empresa.isEmpty()) {
		        throw new Exception("Empresa é um campo obrigatório");
		    }
		   
		   if (repositorio.existeParticipanteComCPF(cpf)) {
		        throw new Exception("Já existe um participante com o mesmo CPF");
		    }

		   
		   Convidado convidado = new Convidado(cpf, nascimento,empresa);
		    repositorio.adicionar(convidado);
		    repositorio.salvarObjetos();
		   
		   
	   }
	
	
	   
	   
	   
	
	   public static void criarIngresso(int id, String cpf, String telefone) throws Exception {
		    Evento ev = repositorio.localizarEvento(id);
		    if (ev == null) {
		        throw new Exception("Evento com id " + id + " não encontrado");
		    }

		    Participante p = repositorio.localizarParticipante(cpf);
		    if (p == null) {
		        throw new Exception("Participante com CPF " + cpf + " não encontrado");
		    }

		    if (telefone == null || telefone.isEmpty()) {
		        throw new Exception("Telefone é obrigatório");
		    }

		    // Verificar se a capacidade do evento não foi atingida
		    if (ev.lotado()) {
		        throw new Exception("Capacidade máxima de ingressos atingida para o evento.");
		    }

		    String codigo = id + "-" + cpf;
		    
		    // Verificar se o ingresso já existe
		    Ingresso existente = repositorio.localizarIngresso(codigo);
		    if (existente != null) {
		        throw new Exception("Ingresso duplicado com código " + codigo);
		    }

		    Ingresso ingresso = new Ingresso(codigo, telefone, ev, p);
		    repositorio.adicionar(ingresso);
		    p.adicionar(ingresso);
		    ev.adicionar(ingresso);
		    repositorio.salvarObjetos();

		    System.out.println("Ingresso criado com sucesso para o participante com CPF " + cpf + ".");
		}

	
	
	   public static void apagarEvento(int id) throws Exception {
		    Evento ev = repositorio.localizarEvento(id);

		    if (ev == null) {
		        throw new Exception("Evento com id " + id + " não encontrado");
		    }

		    // Verifica se há ingressos associados ao evento
		    if (ev.getIngressos() != null && !ev.getIngressos().isEmpty()) {
		        throw new Exception("Não é possível apagar o evento com ingressos associados.");
		    }

		    repositorio.remover(ev);
		    repositorio.salvarObjetos();
		}

 

	   
	   

	   public static void apagarParticipante(String cpf) throws Exception{
		   Participante  p = repositorio.localizarParticipante(cpf);
		   if (p == null) {
			   throw new Exception("Participante com cpf "+ cpf + " não encontrado");
		   }
		  
		   
		   int tamanho = p.getIngressos().size();
	        if (tamanho > 0) {
	            Ingresso ultimoValor = p.getIngressos().get(tamanho - 1);
	         
            Evento eventoUltimoIngresso = ultimoValor.getEvento();
            
            String dataEventoUltimoIngresso = eventoUltimoIngresso.getData();
            
            
            
            LocalDate dateEventoUltimoIngresso = LocalDate.parse(dataEventoUltimoIngresso, DateTimeFormatter.ISO_DATE);
            LocalDate dataAtual = LocalDate.now();
            
            
            
            if (dateEventoUltimoIngresso.isBefore(dataAtual)) {
	         
		       for (Ingresso ingresso : p.getIngressos()) {
	            repositorio.remover(ingresso);
	        }
            } 
			   
			   repositorio.remover(p);
			   repositorio.salvarObjetos();
			   
		   }
	   }
	   
	   
	   

	   
	   public static void apagarIngresso(String codigo) throws Exception {
		   Ingresso i = null;

		    // Itera sobre a lista de ingressos no repositório
		    for (Ingresso ingresso : repositorio.getIngressos()) {
		        if (ingresso.getCodigo().equals(codigo)) {
		            i = ingresso;
		            break; // Se encontrou, sai do loop
		        }
		    }

		    // Verifica se encontrou o ingresso
		    if (i == null) {
		        throw new Exception("Ingresso com código " + codigo + " não encontrado");
		    }

		    Evento ev = i.getEvento();
		    Participante p = i.getParticipante();

		    repositorio.remover(i);
		    p.remover(i);
		    ev.remover(i);
		    repositorio.salvarObjetos();
		}
	   
	   
	   

		public static ArrayList<Evento> listarEventos(){
			return repositorio.getEventos();
		}
		public static ArrayList<Ingresso> listarIngresos(){
			return repositorio.getIngressos();
		}

		public static ArrayList<Participante> listarParticipantes(){
			return repositorio.getParticipantes();

		
		
		
		
		}



		
}

