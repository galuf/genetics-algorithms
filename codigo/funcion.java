import java.util.Scanner;

public class funcion {

    public static void main(String[] args) {

        System.out.println ("Algoritmos Geneticos");

        System.out.println ("Elija funcion a optimizar:");
        System.out.println (" > 1. Simple (5-x^2-y^2)");
        System.out.println (" > 2. Compleja");
        int entradaTeclado;

        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        entradaTeclado = entradaEscaner.nextInt (); //Invocamos un método sobre un objeto Scanner

        int poblacion;
        int epocas;

        System.out.println ("Ingrese el tamaño de la poblacion : ");
        Scanner entradaPoblacion = new Scanner (System.in); //Creación de un objeto Scanner
        poblacion = entradaPoblacion.nextInt (); //Invocamos un método sobre un objeto Scanner

        System.out.println ("Ingrese la cantidad de epocas : ");
        Scanner entradaEpocas = new Scanner (System.in); //Creación de un objeto Scanner
        epocas = entradaEpocas.nextInt (); //Invocamos un método sobre un objeto Scanner

        // Algoritmo Genetico
        genetic g = new genetic(poblacion, 5,entradaTeclado); // poblacion 30 (poner multiplos de 3) y tomamos 5 decimales
        g.init();

        System.out.println("\n Poblacion Inicial \n");

        for(int i = 0; i < g.xb.length ; i++){
            System.out.println("elemento X" + i + " " +g.x[i] +" : " + g.xb[i] + " -- "+"elemento Y" + i + " " + g.y[i] +" : " + g.yb[i]);
        }
        
        for(int i = 0 ; i < epocas ; i++){
            g.sgtPoblacion();
        }

        System.out.println("\n **********Transcurso de Epocas*********** \n");

        System.out.println("\n Poblacion Final Evolucionada \n");
        for(int i = 0; i < g.xb.length ; i++){
            System.out.println("elemento X" + i + " " +g.x[i] +" : " + g.xb[i] + " -- "+"elemento Y" + i + " " + g.y[i] +" : " + g.yb[i]);
        }
        if(entradaTeclado == 1){
            System.out.println("\nValor obtenido : "+fun2(g.x[0],g.y[0]));
        }else{
            System.out.println("\nValor obtenido : "+fun(g.x[0],g.y[0]));
        }

    }
    public static double fun(double x,double y){
        double a = 0.4/(1 + 0.02*((x+20)*(x+20) + (y+20)*(y+20)));
        double b = 0.2/(1 + 0.5*((x+5)*(x+5) + (y+25)*(y+25)));
        double c = 0.7/(1 + 0.01*((x)*(x) + (y-30)*(y-30)));
        double d = 1/(1 + 2*((x+30)*(x+30) + (y)*(y)));
        double e = 0.05/(1 + 0.1*((x-30)*(x-30) + (y+30)*(y+30)));
        return 1.23*Math.sin((x*x + y*y)/1.0563)*(a+b+c+d+e);
    }

    public static double fun2(double x, double y){
        return 5 -x*x - y*y;
    }
    
    public static String convierte(double a){

        return "hola";
    }

    public static String IntAbinString(double valor, int decimales){
        int numBits = 5;
        int reval = (int)valor;
        double decimal = valor - reval;

        StringBuffer bitString = new StringBuffer(numBits);
        for(int i = numBits-1; i >=0; --i ){
            if( reval - (Math.pow(2, i)) >= 0 ){
                bitString.append("1");
                reval = (int) (reval - Math.pow(2, i));
            }
            else{
                bitString.append("0");
            }
        }
        bitString.append(",");

        for(int i = 0 ; i < decimales; i++){
            if((decimal - (int)decimal) == 0.0){
                bitString.append("0");
            }else{
                decimal = decimal*2;
                if((int)decimal == 0){
                    bitString.append("0");    
                }else{
                    bitString.append("1");
                }
            }
        }
        return bitString.toString();
    }

    public static double binStringDouble(String sval){
        double j=0;
        for(int i=0;i<sval.length();i++){
            if(sval.charAt(i)== '1'){
                j=j+ Math.pow(2,sval.length()-4-1-i);
            }
            if(sval.charAt(i)== ','){
                i++;
                int m = -1;
                for(int k=0;k<3;k++){
                    if(sval.charAt(i)== '1'){
                        j = j+Math.pow(2, m);
                    }
                    m=m-1;
                    i++;
                }
            }
        }
        return j;
    }

    public static void sort(int arr[]){ 
        int n = arr.length; 
        for (int i=1; i<n; ++i){ 
            int key = arr[i]; 
            int j = i-1; 
            while (j>=0 && arr[j] < key){ 
                arr[j+1] = arr[j]; 
                j = j-1; 
            } 
            arr[j+1] = key; 
        } 
    }

    public static void printArray(double arr[]) 
    { 
        int n = arr.length; 
        for (int i=0; i<n; ++i) 
            System.out.print(arr[i] + " "); 
        System.out.println(); 
    } 
}



//1.23*sin((x*x+y*y)/1.0563)*((0.4/(1+0.02*((x+20)*(x+20)+(y+20)*(y+20)))) + (0.2/(1+0.5*((x+5)*(x+5)+(y+25)*(y+25)))) + (0.7/(1+0.01*((x)*(x)+(y-30)*(y-30)))) + (1/(1+2*((x+30)*(x+30)+(y)*(y)))) + (0.05/(1+0.1*((x-30)*(x-30)+(y+30)*(y+30)))))