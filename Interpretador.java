import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

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
        leArquivoIN();
        criaFileOF();
    }

    private void criaFileOF() throws Exception {
        File file = new File("./OF.txt");
        if (file.exists()) {
            file.delete();
        }
        this.writer = new FileWriter("./OF.txt", true);
    }

    private void limpaMemoria() {
        for (int i = 0; i < this.TAMANHO_MEMORIA; i++) {
            this.memoria[i] = 0;
        }
    }

    private void leArquivoIN() throws Exception {
        try {
            File arquivo = new File("./IN.txt");
            this.arquivoIn = new Scanner(arquivo);
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e);
        }
    }

    private void encerrar() throws IOException {

        printMemoria();
        this.arquivoIn.close();
        this.writer.close();
    }

    private void printMemoria() throws IOException {
        this.writer.write("\n");
        for (int i = 0; i < this.TAMANHO_MEMORIA; i++) {
            // writer.write(this.memoria[i] + "\t");
            this.writer.write(this.memoria[i] + "\t");
            //System.out.print(this.memoria[i] + "\t");
            if (i != 0 && i % 10 == 0)
                //System.out.println();
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
                    // this.memoria[this.ponteiroDados]
                    this.writer.write(this.memoria[this.ponteiroDados]);
                    break;
//                case '$':
//                    writer.close();
//                    encerrar();
//                    break;
                default:
                    break;
            }
            this.ponteiroPrograma++;
        }
        this.encerrar();
        return true;
    }
}