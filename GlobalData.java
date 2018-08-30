public class GlobalData {
    private double initialT; //temperatura pocz�tkowa
    private double simTime; //czas trwania symulacji
    private double simStep; //krok symulacji
    private double ambientT; //temperatura otoczenia?
    private double alfa; //alfa [W/(m^2*K)]
    private int nH; //ilość węzłów w d�ugo�ci
    private int nB; //ilość węzłów w szeroko�ci
    private int numberOfN; //ilość wszystkich węzłów
    private int numberOfE; //ilość wszystkich element�w
    //private double H; //d�ugo�� (pe�ny wymiar)
    //private  double B; //szeroko�� (pe�ny wymiar)
    private double heat; //ciep�o w�a�ciwe [J/(kg*K)]
    private double cond; //przewodnictwo [W/(m*K)]
    private double density; //g�sto�� [kg/m^3]

    public GlobalData(int process){
        if(process==1)
        {
            initialT=20;
            ambientT=1000;
        }
        else if(process==2){
            initialT=815;
            ambientT=1950;
        }
        else {
            initialT=20;
            ambientT=700;
        }

        simTime=1000;
        simStep=10;

        alfa=7.9;
        ///*
       // H=0.100;
        //B=0.100;
        nH=7;
        nB=11;

        heat=460.548; //0.11 Btu/lb/ ° F
        cond=24.2 ; //
        density=7750;//0.280 lb/in3 (7750 kg/m3)
        numberOfN = nH*nB; //wszystkie w�z�y
        numberOfE = (nH - 1)*(nB - 1); // wszystkie elementy
    }


    public double getSimTime() {
        return simTime;
    }

    public double getSimStep() {
        return simStep;
    }

    public double getAmbientT() {
        return ambientT;
    }

    public double getAlfa() {
        return alfa;
    }

    public int getNumberOfN() {
        return numberOfN;
    }

    public void setNumberOfN(int numberOfN) {
        this.numberOfN = numberOfN;
    }

    public int getNumberOfE() {
        return numberOfE;
    }

    public void setNumberOfE(int numberOfE) {
        this.numberOfE = numberOfE;
    }

    /*
    public double getH() {
        return H;
    }

    public double getB() {
        return B;
    }
*/
    public double getHeat() {
        return heat;
    }

    public double getCond() {
        return cond;
    }

    public double getDensity() {
        return density;
    }
    public double getInitialT(){
        return initialT;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnB() {
        return nB;
    }

    public void setnB(int nB) {
        this.nB = nB;
    }
}
