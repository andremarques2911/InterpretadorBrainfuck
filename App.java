import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            Interpretador interpretador = new Interpretador();
            File arquivo = new File("./SOURCE.txt");
            Scanner sc = new Scanner(arquivo);
            String program = "";
            while (sc.hasNext()) {
                String linha = sc.nextLine();
                if (linha.charAt(0) != '%') {
                    //System.out.print(linha);
                    program += linha;
                }
            }
            interpretador.compilar(program);
            sc.close();
            pause();
        } catch (Exception e) {
            //TODO: handle exception
            throw new Exception(e);
        }
    }

    private static void pause() throws IOException {
        System.out.println();
        System.out.println("Pressione Enter para continuar...");
        System.in.read();
        System.out.println("Fim.");
    }
}