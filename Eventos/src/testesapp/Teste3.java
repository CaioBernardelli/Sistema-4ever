package testesapp;

import regras_negocio.Fachada;
import repositorio.Repositorio;

public class Teste3 {
	public static void main(String[] args) {
		Repositorio repositorio = new Repositorio();
	    repositorio.apagarTodosOsDados();
		try {
            // Criar alguns eventos
            Fachada.criarEvento("01/01/2024", "Evento 1", 100, 50.0);
            Fachada.criarEvento("02/02/2024", "Evento 2", 150, 40.0);

            // Criar alguns participantes
            Fachada.criarParticipante("11111111111", "01/01/1990");
            Fachada.criarConvidado("22222222222", "02/02/1995", "Empresa A");

            // Criar alguns ingressos associando eventos e participantes
            Fachada.criarIngresso(1, "11111111111", "123456789");
            Fachada.criarIngresso(2, "22222222222", "987654321");

            // Listar eventos, participantes e ingressos para verificar
            System.out.println("Eventos:");
            System.out.println(Fachada.listarEventos());

            System.out.println("\nParticipantes:");
            System.out.println(Fachada.listarParticipantes());

            System.out.println("\nIngressos:");
            System.out.println(Fachada.listarIngresos());

            // Aqui você pode realizar outras operações de teste conforme necessário
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
