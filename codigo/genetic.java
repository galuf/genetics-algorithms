
public class genetic {

    public int poblacion;
    public int elite;
    public int mutacion;
    public int hibrido; 
    public int funcionOpt;

    public int numBits = 5;
    public int numMax = (int)Math.pow(2, numBits) - 1;
    public int cantDecimal;

    public double x[];
    public double y[];

    public double fitnes[];

    public String xb[];
    public String yb[];

    public genetic(int pob, int dec,int fun){
        this.poblacion = pob;
        this.cantDecimal = dec;
        this.funcionOpt = fun;

        this.elite = poblacion/3;
        this.mutacion = poblacion/3;
        this.hibrido = poblacion - this.elite - this.mutacion;

        x = new double[poblacion];
        y = new double[poblacion];

        xb = new String[poblacion];
        yb = new String[poblacion];

        fitnes = new double[poblacion];
    }

    public void init(){
        for(int i=0;i<poblacion;i++){
            int pon1 = (int)(Math.pow(-1,Math.floor(Math.random()*(2-1+1)+1)));
            int pon2 = (int)(Math.pow(-1,Math.floor(Math.random()*(2-1+1)+1)));
            double randV1 = (double)((int)((Math.random()*numMax)*Math.pow(10,cantDecimal))/Math.pow(10,cantDecimal))*pon1;
            double randV2 = (double)((int)((Math.random()*numMax)*Math.pow(10,cantDecimal))/Math.pow(10,cantDecimal))*pon2;

            x[i] = randV1;
            y[i] = randV2;
        }

        for(int i=0;i<poblacion;i++){
            xb[i] = getBit(x[i]);
            yb[i] = getBit(y[i]);
        }
    }

    public String getBit(double valor){
        int signo = 0;
        if(valor < 0){
            valor = valor*-1;
            signo = 1;
        }
            

        int reval = (int)valor;
        double decimal = valor - reval;

        StringBuffer bitString = new StringBuffer(numBits);
        if(signo == 1){
            bitString.append("-");
        }else{
            bitString.append("+");
        }
            
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

        for(int i = 0 ; i < cantDecimal; i++){
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

    public double getDouble(String sval){
        double j=0;
        double signo = 1.0;
        if(sval.charAt(0)== '-') signo = -1.0;
        for(int i=1;i<sval.length();i++){
            if(sval.charAt(i)== '1'){
                j=j+ Math.pow(2,sval.length()-1 - cantDecimal -1-i);
            }
            if(sval.charAt(i)== ','){
                i++;
                int m = -1;
                for(int k=0;k<cantDecimal;k++){
                    if(sval.charAt(i)== '1'){
                        j = j+Math.pow(2, m);
                    }
                    m=m-1;
                    i++;
                }
            }
        }
        return j*signo;
    }

    public static double fun2(double x, double y){
        return 5 -x*x - y*y;
    }

    public double fun(double x,double y){
        double a = 0.4/(1 + 0.02*((x+20)*(x+20) + (y+20)*(y+20)));
        double b = 0.2/(1 + 0.5*((x+5)*(x+5) + (y+25)*(y+25)));
        double c = 0.7/(1 + 0.01*((x)*(x) + (y-30)*(y-30)));
        double d = 1/(1 + 2*((x+30)*(x+30) + (y)*(y)));
        double e = 0.05/(1 + 0.1*((x-30)*(x-30) + (y+30)*(y+30)));
        return 1.23*Math.sin((x*x + y*y)/1.0563)*(a+b+c+d+e);
    }

    public void sgtPoblacion(){
        
        double eli[] = new double[elite];
        double mut[] = new double[mutacion];
        double hibri[] = new double[hibrido];
        getFitnes();
        
        double xp[] = new double[poblacion]; 
        double yp[] = new double[poblacion]; 

        xp = x;
        yp = y;
        
        int bits[] = new int[poblacion];

        for(int i = 0 ; i < poblacion ; i++){
            bits[i] = mayor();
            fitnes[bits[i]] = -500.0;
        }
        
        for(int i = 0; i<poblacion ; ){
            if(i < elite){
                x[i] = xp[bits[i]]; 
                y[i] = yp[bits[i]];
                i++;
            }else if(i < elite + mutacion){
                getMutacion(xb[bits[i%elite]], yb[bits[i%elite]], i);
                i++;
            }else{
                getCruse(xb[bits[i%(elite + mutacion)]], yb[bits[i%(elite + mutacion)]],xb[bits[i]], yb[bits[i]],i);
                i++;
            }   
        }

        for(int i=0;i<poblacion;i++){
            xb[i] = getBit(x[i]);
            yb[i] = getBit(y[i]);
        }
    }

    public void getFitnes(){
        for(int i = 0; i < fitnes.length ; i++){
            if(funcionOpt == 1){
                fitnes[i] = fun2(x[i], y[i]);
            }else{
                fitnes[i] = fun(x[i], y[i]);
            }
        }
        //sort();
    }

    public void sort(){ 
        int n = fitnes.length; 
        for (int i=1; i<n; ++i){ 
            double key = fitnes[i]; 
            int j = i-1; 
            while (j>=0 && fitnes[j] < key){ 
                fitnes[j+1] = fitnes[j]; 
                j = j-1; 
            } 
            fitnes[j+1] = key; 
        } 
    }

    public int mayor(){
        int n = fitnes.length; 
        int indice = 0;
        double mayor = -1.0;
        for (int i=0; i<n; ++i){ 
            if(mayor < fitnes[i]){
                mayor = fitnes[i];
                indice = i;
            }        
        }
        return indice;
    }

    public void getMutacion(String x1,String y1, int i){
        String cadena = x1+y1;
        //System.out.println(cadena + "---- Elite ");
        String cadena2 = cambia(cadena);
        int tam = cadena2.length();

        //System.out.println(cadena2.substring(0,tam/2));
        //System.out.println(cadena2.substring(tam/2));
        x[i] = getDouble(cadena2.substring(0,tam/2));
        y[i] = getDouble(cadena2.substring(tam/2));
    }

    public void getCruse(String x1,String y1,String x2,String y2, int i){
        String cadena1 = x1 + y1;
        String cadena2 = x2 + y2;
        String concatenacion = hibridacion(cadena1,cadena2);
        int tam = concatenacion.length();

        //System.out.println(concatenacion.substring(0,tam/2));
        //System.out.println(concatenacion.substring(tam/2));
        x[i] = getDouble(concatenacion.substring(0,tam/2));
        y[i] = getDouble(concatenacion.substring(tam/2)); 
    }

    public String cambia(String valor){
        int N = valor.length() -1;
        int M = 0;
        int valorEntero = (int) Math.floor(Math.random()*(N-M+1)+M);
        String bit = "1";
        while(valor.charAt(valorEntero)== '-' || valor.charAt(valorEntero)== '+' || valor.charAt(valorEntero)== ','){
            valorEntero = (int) Math.floor(Math.random()*(N-M+1)+M);
        }
        //System.out.println(valorEntero);
        if(valor.charAt(valorEntero)== '1'){
            bit = "0";
        }
        //System.out.println(valor.substring(0,valorEntero) +bit + valor.substring(valorEntero+1));
        return valor.substring(0,valorEntero)+bit+ valor.substring(valorEntero+1);
    }

    public String hibridacion(String valor1 , String valor2){
        int N = valor1.length() -2;
        int M = 2;
        int valorEntero = (int) Math.floor(Math.random()*(N-M+1)+M);
        //System.out.println(valorEntero);
        //System.out.println(valor1 +" cadena 1 ");
        //System.out.println(valor2 +" cadena 2 ");
        //System.out.println(valor1.substring(0,valorEntero) + valor2.substring(valorEntero));
        return valor1.substring(0,valorEntero) + valor2.substring(valorEntero);
    }
}