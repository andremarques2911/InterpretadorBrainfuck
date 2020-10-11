import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            Interpretador interpretador = new Interpretador();
            File arquivo = new File("./arquivos/SOURCE.txt");
            Scanner sc = new Scanner(arquivo);
            String program = "";
            while (sc.hasNext()) {
                String linha = sc.nextLine();
                if (!linha.equals("") && linha.charAt(0) != '%') {
                    program += linha;
                }
            }
            interpretador.compilar(program);
            sc.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}