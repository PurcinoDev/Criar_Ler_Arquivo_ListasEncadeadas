package controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import br.com.leandrocolevati.Lista.Lista;
import model.Cliente;

public class ModificacaoCadastroController {

	public ModificacaoCadastroController() {
		super();
	}

	private void novoArquivo(Lista l, String caminho, String nomeArquivo) throws Exception{
		
		File dir = new File(caminho);
		if (dir.exists() && dir.isDirectory()) {
			File arq = new File(caminho, nomeArquivo);
			boolean existe = false;
			if (arq.exists()) {
				existe = true;
			}
			
			StringBuffer buffer = new StringBuffer();
			int tamanho = l.size();
			String linha = "";
			for (int i = 0; i < tamanho; i++) {
				linha = l.get(i).toString();
				buffer.append(linha + "\r\n");
			}
			
			FileWriter abreArquivo = new FileWriter(arq, existe);
			PrintWriter escreveArq = new PrintWriter(abreArquivo);
			escreveArq.write(buffer.toString());
			escreveArq.flush();
			escreveArq.close();
			abreArquivo.close();
		} else {
			throw new IOException("Diretório inválido");
		}
		
	}
	
	public void novoCadastro (String caminho, String arquivo, int idadeMin, int idadeMax, double limiteCredito )throws Exception {
		Lista l = new Lista();
		File arq = new File(caminho, arquivo);
		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leFluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leFluxo);
			String linha = buffer.readLine();
			while (linha != null) {
				if (linha.contains(";")) {
					String[] vetLinha = linha.split(";");
					Cliente c = new Cliente();
					c.cpf = vetLinha[0];
					c.nome = vetLinha[1];
					c.idade = Integer.parseInt(vetLinha[2]);
					c.limiteCredito = Double.parseDouble(vetLinha[3]);
					if (c.idade >= idadeMin && c.idade <= idadeMax && c.limiteCredito >= limiteCredito) {
						if(l.isEmpty()) {
							l.addFirst(c);
						}else {
							l.addLast(c);
						}
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			leFluxo.close();
			fluxo.close();
		} else {
			throw new IOException("Arquivo inválido");
		}
		String nomeArquivo = "Idade" + idadeMin +"-" + idadeMax + "-Limite" + (int)limiteCredito + ".csv";
		novoArquivo(l, caminho, nomeArquivo);
	}

}
