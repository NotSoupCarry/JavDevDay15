import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;

// 1. Definiamo l'annotazione
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface InfoAutore {
    String nome();
    String data();
}

// 2. La classe che utilizza l'annotazione
class RicettaCioccolato {

    @InfoAutore(nome = "Mario Rossi", data = "2025-03-01")
    public void preparaCioccolata() {
        System.out.println("Preparazione cioccolata...");
    }
}

// 3. Classe per ispezionare l'annotazione tramite reflection
public class ProvaAnnotazioni {
    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName("RicettaCioccolato");
            for (Method method : c.getDeclaredMethods()) {
                if (method.isAnnotationPresent(InfoAutore.class)) {
                    InfoAutore info = method.getAnnotation(InfoAutore.class);
                    System.out.println("Metodo: " + method.getName());
                    System.out.println("Autore: " + info.nome());
                    System.out.println("Data:   " + info.data());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
