import java.util.Scanner;
import java.io.File;
import java.util.Stack;

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

    public boolean compilar(String linha) {
        Stack<String> pilha = new Stack();
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
                    pilha.push("[");
                    if (this.memoria[this.ponteiroDados] == 0) {
                        int j = i;
                        while (!pilha.isEmpty()) {
                            if (linha.charAt(j) == '[') {
                                pilha.push("[");
                            } else if (linha.charAt(j) == ']') {
                                pilha.pop();
                            }
                            j++;
                        }
                        i = j;
                    }
                    break;
                case ']':
                    pilha.pop();
                    if (this.memoria[this.ponteiroDados] != 0) {
                        int j = i;
                        while (linha.charAt(j) != '[') {
                            j--;
                        }
                        i = --j;
                    }
                    break;
                case ',':
                    this.memoria[this.ponteiroDados] = this.arquivoIn.nextInt();
                    break;
                case '.':
                    System.out.println(this.memoria[this.ponteiroDados]);
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