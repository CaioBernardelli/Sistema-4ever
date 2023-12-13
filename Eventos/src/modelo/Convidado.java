package modelo;



public class Convidado extends Participante {
	@Override
	public String toString() {
		return "Convidado [empresa=" + empresa + "]";
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	private String empresa;

	public Convidado(String cpf, String nascimento, String empresa) {
		super(cpf, nascimento);
		this.empresa = empresa;
		
	}
	

	
	
	

}
