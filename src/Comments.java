import java.io.*;

/**
 * Created by Richard on 03/03/2017.
 *
 */
public class Comments {

    public static void main(String[] args) throws IOException {
        // Leemos un archivo de texto para guardar el contenido en una variable
        FileReader fr = new FileReader("src\\Input.txt");
        BufferedReader br = new BufferedReader(fr);
        // Guardamos ese contenido en la variable
        String linea = "";
        for (String line; (line = br.readLine()) != null; ) {
                linea += line;
                linea += "\n";
        }

        int estado = 1;
        int cont_inicial = 0;
        int contador = 0;
        boolean band = true;
        int lineaActual = 1;

        while (contador < linea.length()){
            char caracter = linea.charAt(contador);
            if(caracter == '\n'){
                lineaActual++;
                contador++;
                continue;
            }

            //INICIO DE AUTÓMATA
            if (estado == 1){
                if(caracter == '/'){
                    estado = 2;
                }else if(caracter == ' ' || caracter == '\t' || caracter == '\n'){
                    estado = 1;
                }else{
                    estado = 0; //Estado de error
                }
            }else if (estado == 2){
                if(caracter == '*'){
                    estado = 3;
                }else if(caracter == '/'){
                    estado = 1; // Forma de terminar
                    while (contador < linea.length() && linea.charAt(contador) != '\n')
                        contador++;
                    lineaActual++;
                    System.out.println("Comment inline detected: " + linea.substring(cont_inicial+1, contador));
                    cont_inicial = contador;
                }else{
                    estado = 0;
                }
            }else if(estado == 3){
                if(caracter == '*'){
                    estado = 4;
                }
            }else if(estado == 4){
                if (caracter == '/'){
                    estado = 1;     //Terminó, volvemos al estado inicial
                    System.out.println("Comment detected: " + linea.substring(cont_inicial, contador+1));
                    cont_inicial = contador;
                    cont_inicial++;
                }else if(caracter == '*'){
                    estado = 4;
                }else{
                    estado = 3;
                }
            }
            if(estado == 0){    //Estado de Error
                band = false;
                break;
            }
            contador++;
        }   //FIN DE AUTÓMATA
        // SI está en estado de error O Si quedo en un estado NO TERMINANTE
        if (estado == 0 || (estado != 4 && estado != 1)){
            System.out.println("Error en linea " + lineaActual);
            band = false;
        }
        //Si todo salió bien
        if(band)
            System.out.println("Without problems.");
    }
}
