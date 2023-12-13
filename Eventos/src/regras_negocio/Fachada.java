package regras_negocio;

import java.util.ArrayList;
import java.util.List;

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

	   public static void apagarParticipante(String cpf) throws Exception {
			Participante p = repositorio.localizarParticipante(cpf);

			if (p == null) { // Lança exceção caso o participante não seja encontrado
				throw new Exception("Participante não encontrado");
			}
//			
			// Verifica se o último ingresso do participante está ultrapassado
			Ingresso ultimoIngresso;

			if (p.getIngressos().isEmpty()) {
				ultimoIngresso = null;
			}
			else {
				ultimoIngresso = p.getIngressos().get(p.getIngressos().size() - 1);
			}

			if (ultimoIngresso != null && ultimoIngresso.verificaIngressoUltrapassado()) {
			    throw new Exception("O último ingresso não está ultrapassado");
			}

			// Remove todos os ingressos associados ao participante
			for (Ingresso ingresso : new ArrayList<>(p.getIngressos())) {
				apagarIngresso(ingresso.getCodigo());
			}

			// Remove o participante do repositório
			repositorio.remover(p);
			repositorio.salvarObjetos();
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
	   
	   private static List<Ingresso> listaDeIngressos = new ArrayList<>();

	    public static void adicionarIngresso(Ingresso ingresso) {
	        listaDeIngressos.add(ingresso);
	    }

	    public static Ingresso buscarIngressoPorCodigo(String codigo) {
	        for (Ingresso ingresso : listaDeIngressos) {
	            if (ingresso.getCodigo().equals(codigo)) {
	                return ingresso;
	            }
	        }
	        return null;
	    }
	   
	    public static Evento localizarEvento(int id) {
	        for (Evento evento : repositorio.getEventos()) {
	            if (evento.getId() == id) {
	                return evento;
	            }
	        }
	        return null; // Retorna null se o evento não for encontrado
	    }

	    public static Participante localizarParticipante(String cpf) {
	        for (Participante participante : repositorio.getParticipantes()) {
	            if (participante.getCpf().equals(cpf)) {
	                return participante;
	            }
	        }
	        return null; // Retorna null se o participante não for encontrado
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

		public static boolean participanteExiste(String cpf) {
			// TODO Stub de método gerado automaticamente
			return false;
		}



		
}

