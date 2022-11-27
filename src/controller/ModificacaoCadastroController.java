package controller;

import model.Cliente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import br.com.leandrocolevati.Lista.Lista;

public class ModificacaoCadastroController {
	
	public ModificacaoCadastroController() {
		super();
	}
	
	private void novoArquivo(Lista l, String caminho, String nomeArquivo) throws Exception {
		
		File dir = new File(caminho);
		
		if (dir.exists() && dir.isDirectory()) {
			
//			Cria um novo arquivo
			File arq = new File(caminho, nomeArquivo);
			
//			Instancia um StringBuffer para preencher o arquivo com o conteúdo da lista
			StringBuffer buffer = new StringBuffer();
			int contador = l.size();
			String linha = "";
			for (int i = 0; i < contador; i++) {
				linha = l.get(i).toString();
				buffer.append(linha + "\r\n");
			}
			
//			Escreve o conteúdo pego no StringBuffer no arquivo novo criado
			FileWriter abreArquivo = new FileWriter(arq);
			PrintWriter escreveArq = new PrintWriter(abreArquivo);
			escreveArq.write(buffer.toString());
			escreveArq.flush();
			escreveArq.close();
			abreArquivo.close();
		} else {
			throw new IOException("Diretório inválido");
		}
		
	}
	
	public void novoCadastro(String caminho, String arquivo, int idadeMin, int idadeMax, double limiteCredito) throws Exception {
		Lista lClient = new Lista();
		File arq = new File(caminho, arquivo);
		
		if (arq.exists() && arq.isFile()) {
			FileInputStream fluxo = new FileInputStream(arq);
			InputStreamReader leFluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leFluxo);
			String linha = buffer.readLine();
			while (linha != null) {
				if (linha.contains(";")) {
					String[] vetLinha = linha.split(";");
					Cliente cliente = new Cliente();
					cliente.cpf = vetLinha[0];
					cliente.nome = vetLinha[1];
					cliente.idade = Integer.parseInt(vetLinha[2]);
					cliente.limiteCredito = Double.parseDouble(vetLinha[3].replace(",", "."));
					if (cliente.idade >= idadeMin && cliente.idade <= idadeMax && cliente.limiteCredito >= limiteCredito) {
						if (lClient.isEmpty()) {
							lClient.addFirst(cliente);
						} else {
							lClient.addLast(cliente);
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
		String nomeArquivo = "Idade" + idadeMin + "_Limite" + (int)limiteCredito + ".csv";
		novoArquivo(lClient, caminho, nomeArquivo);
	}

}
