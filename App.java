import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            Interpretador interpretador = new Interpretador();
            File arquivo = new File("./SOURCE.txt");
            Scanner sc = new Scanner(arquivo);
            while (sc.hasNext()) {
                String linha = sc.nextLine();
                if (linha.charAt(0) != '%') {
                    //System.out.print(linha);
                    interpretador.compilaLinha(linha);
                }
            }
            sc.close();
            pause();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private static void pause() throws IOException {
        System.out.println();
        System.out.println("Pressione Enter para continuar...");
        System.in.read();
        System.out.println("Fim.");
    }
}