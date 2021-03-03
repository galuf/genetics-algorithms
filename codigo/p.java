
public class p {
    public static void main(String[] args) {
        
        System.out.println(fun(-70.9921875,27.9921875)); //5.009150572693483
        System.out.println(fun(12.4921875,32.046875)); //5.337704237463713
        System.out.println(fun(3.9921875,29.2421875)); //5.7466908633578795
        System.out.println(fun(-1.2490234375,40.2490234375)); //5.3813825612060935
        System.out.println(fun(-0.4990234375,24.8740234375)); //5.689703498034158
        System.out.println(fun(0.0,32.0)); //5.812267589743124
    }





    public static double fun(double x,double y){
        double a = 0.4/(1 + 0.02*((x+20)*(x+20) + (y+20)*(y+20)));
        double b = 0.2/(1 + 0.5*((x+5)*(x+5) + (y+25)*(y+25)));
        double c = 0.7/(1 + 0.01*((x)*(x) + (y-30)*(y-30)));
        double d = 1/(1 + 2*((x+30)*(x+30) + (y)*(y)));
        double e = 0.05/(1 + 0.1*((x-30)*(x-30) + (y+30)*(y+30)));
        return 5 + 1.23*Math.sin((x*x + y*y)/1.0563)*(a+b+c+d+e);
    }
}