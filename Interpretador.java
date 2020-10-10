import java.util.Scanner;
import java.io.File;
import java.util.Stack;

public class Interpretador {

    private final int TAMANHO_MEMORIA = 1000;

    private int[] memoria;
    private int ponteiroDados;
    private int ponteiroPrograma;
    private Scanner arquivoIn;

    public Interpretador() throws Exception {
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

    private void leArquivoIN() throws Exception {
        try {
            File arquivo = new File("./IN.txt");
            this.arquivoIn = new Scanner(arquivo);
        } catch (Exception e) {
            //TODO: handle exception
            throw new Exception(e);
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
                    Stack<String> pilha = new Stack();
                    pilha.push("[");
                    if (this.memoria[this.ponteiroDados] == 0) {
                        while (!pilha.isEmpty()) {
                            if (linha.charAt(i) == '[') {
                                pilha.push("[");
                            } else if (linha.charAt(i) == ']') {
                                pilha.pop();
                            }
                            i++;
                        }
                    }
                    break;
                case ']':
                    // Stack<String> pilhaVolta = new Stack();
                    // pilhaVolta.push("]");
                    if (this.memoria[this.ponteiroDados] != 0) {
                        // int j = i;
                        // while (linha.charAt(j) != '[') {
                        //     j--;
                        // }
                        // i = --j;

                        int count = 1;
                        i--;
                        while (count > 0) {
                            if (linha.charAt(i) == ']') {
                                count++;
                            } else if (linha.charAt(i) == '[') {
                                count--;
                            }
                            i--;
                        }

                        // System.out.println(i);
                        // while (!pilhaVolta.isEmpty()) {
                        //     if (i < linha.length()) {
                        //         if (linha.charAt(i) == ']') {
                        //             pilhaVolta.push("]");
                        //         } else if (linha.charAt(i) == '[') {
                        //             pilhaVolta.pop();
                        //         }
                        //         i++;    
                        //     } else {
                        //         break;
                        //     }    
                        // }
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