import java.util.Scanner;
import java.io.File;

public class Interpretador {

    private final int TAMANHO_MEMORIA = 1000;

    private int[] memoria;
    private int ponteiroDados;
    private int ponteiroPrograma;
    private Scanner arquivoIn;

    public Interpretador() {
        this.memoria = new int[this.TAMANHO_MEMORIA];
        this.ponteiroDados = 0;
        this.ponteiroPrograma = 0;
        limpaMemoria();
        leArquivoIN();
    }

    private void limpaMemoria() {
        for(int i = 0; i < this.TAMANHO_MEMORIA; i++) {
            this.memoria[i] = 0;
        }
    }

    private void leArquivoIN() {
        try {
            File arquivo = new File("./IN.txt");
            this.arquivoIn = new Scanner(arquivo);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private void encerrar() {
        this.arquivoIn.close();
        printMemoria();
    }

    private void printMemoria() {
        for (int i = 0; i < this.TAMANHO_MEMORIA; i++) {
            System.out.print( this.memoria[i] + "\t");
            if (i != 0 && i % 8 == 0) System.out.println();
        }
    }

    public boolean compilaLinha(String linha) {
        for (int i = 0; i < linha.length(); i++) {
            switch (linha.charAt(i)) {
                case '>':
                    this.ponteiroDados++;
                    break;
                case '<':
                    this.ponteiroDados--;
                    break;
                case '+':
                    this.memoria[this.ponteiroDados] += 1;
                    break;
                case '-':
                    this.memoria[this.ponteiroDados] -= 1;
                    break;
                case '[':
                    break;
                case ']':
                    break;
                case ',':
                    this.memoria[this.ponteiroDados] = this.arquivoIn.nextInt();
                    break;
                case '.':
                    
                    break;
                case '$':
                    encerrar();
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}