package view;

import controller.ModificacaoCadastroController;

public class Principal {

	public static void main(String[] args) throws Exception{
		
		String caminho = "C:\\TEMP";
		String arquivo = "cadastro.csv";
		ModificacaoCadastroController mod = new ModificacaoCadastroController();
		mod.novoCadastro(caminho, arquivo, 41, 60, 8000.0);
		mod.novoCadastro(caminho, arquivo, 31, 40, 5000.0);
		mod.novoCadastro(caminho, arquivo, 21, 30, 3000.0);
		
	}

}
