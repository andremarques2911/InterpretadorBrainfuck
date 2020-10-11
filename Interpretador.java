import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.Random;

public class Interpretador {

    private final int TAMANHO_MEMORIA = 1000;

    private byte[] memoria;
    private int ponteiroDados;
    private int ponteiroPrograma;
    private Scanner arquivoIn;
    private FileWriter writer;

    public Interpretador() throws Exception {
        this.memoria = new byte[this.TAMANHO_MEMORIA];
        this.ponteiroDados = 0;
        this.ponteiroPrograma = 0;
        limpaMemoria();
        criaFileIN();
        criaFileOF();
    }

    private void limpaMemoria() {
        for (int i = 0; i < this.TAMANHO_MEMORIA; i++) {
            this.memoria[i] = 0;
        }
    }

    private void criaFileIN() throws Exception {
        Random rand = new Random();
        File arquivo = new File("./arquivos/IN.txt");
        if (!arquivo.exists()) {
            FileWriter fw = new FileWriter("./arquivos/IN.txt", true);
            final int NR_LINHAS = 50;
            for (int i = 0; i < NR_LINHAS; i++) {
                fw.write(String.valueOf(rand.nextInt(256)));
                if (i < NR_LINHAS - 1) {
                    fw.write("\n");
                }
            }
            fw.close();   
        }
        this.arquivoIn = new Scanner(arquivo);
    }
    
    private void criaFileOF() throws Exception {
        File file = new File("./arquivos/OF.txt");
        if (file.exists()) {
            file.delete();
        }
        this.writer = new FileWriter("./arquivos/OF.txt", true);
    }

    private void encerrar() throws IOException {

        printMemoria();
        this.arquivoIn.close();
        this.writer.close();
    }

    private void printMemoria() throws IOException {
        this.writer.write("\n");
        for (int i = 0; i < this.TAMANHO_MEMORIA; i++) {
            this.writer.write(this.memoria[i] + "\t");
            if (i != 0 && i % 10 == 0)
                this.writer.write("\n");
        }
    }

    public boolean compilar(String linha) throws IOException {
        int count = 1;
        while (linha.charAt(this.ponteiroPrograma) != '$') {
            switch (linha.charAt(this.ponteiroPrograma)) {
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
                    if (this.memoria[this.ponteiroDados] == 0) {
                        count = 1;
                        while (count > 0) {
                            this.ponteiroPrograma++;
                            if (linha.charAt(this.ponteiroPrograma) == '[') {
                                count++;
                            } else if (linha.charAt(this.ponteiroPrograma) == ']') {
                                count--;
                            }
                        }
                    }
                    break;
                case ']':
                    if (this.memoria[this.ponteiroDados] != 0) {
                        count = 1;
                        while (count > 0) {
                            this.ponteiroPrograma--;
                            if (linha.charAt(this.ponteiroPrograma) == ']') {
                                count++;
                            } else if (linha.charAt(this.ponteiroPrograma) == '[') {
                                count--;
                            }
                        }
                    }
                    break;
                case ',':
                    this.memoria[this.ponteiroDados] = (byte) this.arquivoIn.nextInt();
                    break;
                case '.':
                    this.writer.write(this.memoria[this.ponteiroDados]);
                    break;
                default:
                    break;
            }
            this.ponteiroPrograma++;
        }
        this.encerrar();
        return true;
    }
}