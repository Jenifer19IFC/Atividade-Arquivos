package arquivoAtividade;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Busca implements Runnable {
	
    private final String nomeProcurado;
    private final String caminhoArquivo;
    private final Semaphore semaphore;

    public Busca(String nomeProcurado, String caminhoArquivo, Semaphore semaphore) {
        this.nomeProcurado = nomeProcurado;
        this.caminhoArquivo = caminhoArquivo;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); // Tentar fazer a busca
            System.out.println(Thread.currentThread().getName() + " conseguiu acesso.");
            buscaNomeNoArquivo();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
        	System.out.println(Thread.currentThread().getName() + " liberou acesso.");
            semaphore.release(); // Libera recurso
        }
    }
    
    private void buscaNomeNoArquivo() {
        try (Scanner scanner = new Scanner(new File(caminhoArquivo))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                for (int i = 0; i <= linha.length() - nomeProcurado.length(); i++) {
                    if (linha.regionMatches(true, i, nomeProcurado, 0, nomeProcurado.length())) {
                        String numArq = this.fortmataNome(caminhoArquivo);
                        System.out.println("Encontrado no arquivo " + numArq + ": " + linha);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
        }
    }
    
    private String fortmataNome(String caminhoArquivo) {
    	String numArq = "";
    	
    	Pattern pattern = Pattern.compile("-(\\d{2})\\.txt$");
        Matcher matcher = pattern.matcher(caminhoArquivo);

        if (matcher.find()) {
        	numArq = matcher.group(1);
        }
        return numArq;
    }

}

