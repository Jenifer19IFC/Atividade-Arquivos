package arquivoAtividade;

import java.util.concurrent.Semaphore;

public class Main {
	
	  public static void main(String[] args) {
		  
	        String nomeProcurado = "BRuce"; 
	        Semaphore semaphore = new Semaphore(2); // Permissão para somente duas

	        for(int i = 0; i < 10; i++) {
	        	new Thread(new Busca(nomeProcurado, "/home/jenifer/eclipse-workspace/pda/src/arquivoAtividade/nomescompletos-0" + i + ".txt", semaphore)).start();
			}
	    }

}
