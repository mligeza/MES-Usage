import java.util.ArrayList;
import java.util.List;

public class Grid {
    private  Node nodes[];
    private  Element elements[];
    private GlobalData globalData;
    private UniversalElement universalElement;
    public UniversalElement getUniversalElement(){ return universalElement;}
    private SquareMatrix matrixH;
    private SquareMatrix matrixC;
    private SquareMatrix matrixS;
    private SquareMatrix matrixHC;
    private Vector vectorP;
    int proces;

    public Grid(int process){

        globalData=new GlobalData(process);
        nodes=new Node[globalData.getNumberOfN()];
        elements= new Element[globalData.getNumberOfE()];
        universalElement=new UniversalElement();
        matrixC= new SquareMatrix(globalData.getNumberOfN());
        matrixH= new SquareMatrix(globalData.getNumberOfN());
        matrixS= new SquareMatrix(globalData.getNumberOfN());
        matrixHC= new SquareMatrix(globalData.getNumberOfN());
        vectorP= new Vector(globalData.getNumberOfN());
        matrixC.clear();
        matrixS.clear();
        matrixH.clear();
        matrixHC.clear();
        vectorP.clear();
        this.proces=process;
    }


    public void fillGrid(){
        double[] igreki= {0, 0.0005, 0.001, 0.0015, 0.002, 0.0025, 0.003};
        double[][] iksy= new double[11][7];
        iksy[0][0]=0.015;
        iksy[0][1]=0.01;
        iksy[0][2]=0.005;
        iksy[0][3]=0.0;
        iksy[0][4]=0.005;
        iksy[0][5]=0.01;
        iksy[0][6]=0.015;

        iksy[1][0]=0.017;
        iksy[1][1]=0.013;
        iksy[1][2]=0.009;
        iksy[1][3]=0.005;
        iksy[1][4]=0.009;
        iksy[1][5]=0.013;
        iksy[1][6]=0.017;

        iksy[2][0]=0.019;
        iksy[2][1]=0.016;
        iksy[2][2]=0.013;
        iksy[2][3]=0.01;
        iksy[2][4]=0.013;
        iksy[2][5]=0.016;
        iksy[2][6]=0.019;

        iksy[3][0]=0.021;
        iksy[3][1]=0.019;
        iksy[3][2]=0.017;
        iksy[3][3]=0.015;
        iksy[3][4]=0.017;
        iksy[3][5]=0.019;
        iksy[3][6]=0.021;

        iksy[4][0]=0.023;
        iksy[4][1]=0.022;
        iksy[4][2]=0.021;
        iksy[4][3]=0.02;
        iksy[4][4]=0.021;
        iksy[4][5]=0.022;
        iksy[4][6]=0.023;

        iksy[5][0]=0.025;
        iksy[5][1]=0.025;
        iksy[5][2]=0.025;
        iksy[5][3]=0.025;
        iksy[5][4]=0.025;
        iksy[5][5]=0.025;
        iksy[5][6]=0.025;

        iksy[6][0]=0.027;
        iksy[6][1]=0.028;
        iksy[6][2]=0.029;
        iksy[6][3]=0.03;
        iksy[6][4]=0.029;
        iksy[6][5]=0.028;
        iksy[6][6]=0.027;

        iksy[7][0]=0.029;
        iksy[7][1]=0.031;
        iksy[7][2]=0.033;
        iksy[7][3]=0.035;
        iksy[7][4]=0.033;
        iksy[7][5]=0.031;
        iksy[7][6]=0.029;

        iksy[8][0]=0.031;
        iksy[8][1]=0.034;
        iksy[8][2]=0.037;
        iksy[8][3]=0.04;
        iksy[8][4]=0.037;
        iksy[8][5]=0.034;
        iksy[8][6]=0.031;

        iksy[9][0]=0.033;
        iksy[9][1]=0.037;
        iksy[9][2]=0.041;
        iksy[9][3]=0.045;
        iksy[9][4]=0.041;
        iksy[9][5]=0.037;
        iksy[9][6]=0.033;

        iksy[10][0]=0.035;
        iksy[10][1]=0.04;
        iksy[10][2]=0.045;
        iksy[10][3]=0.05;
        iksy[10][4]=0.045;
        iksy[10][5]=0.04;
        iksy[10][6]=0.035;



        //Węzły
        for (int i = 0; i < globalData.getnB(); i++) //id�c po szeroko�ci
        {

            for (int j = 0; j < globalData.getnH(); j++) //id�c po d�ugo�ci
            {
                System.out.print(iksy[i][j]+" ");
            }
            System.out.print("\n");
        }

        for (int i = 0; i < globalData.getnB(); i++) //id�c po szeroko�ci
        {
            for (int j = 0; j < globalData.getnH(); j++) //id�c po d�ugo�ci
            {
                int n = i*globalData.getnH() + j; //tworzymy element numer n
                boolean wall=false;
                if (i==0 || i==globalData.getnB()-1 || j==0 || j==globalData.getnH()-1)  //sprawdzamy czy na �cianie
                {
                    wall=true;
                }
                nodes[n]=new Node(iksy[i][j],igreki[j],globalData.getInitialT(),wall,n,this);
                nodes[n].show();

            }
            System.out.print(i+" \n");
        }

        //Elementy
        for (int i = 0; i < globalData.getnB()-1; i++)//idziemy po szeroko�ci -1 (tyle element�w si� mie�ci na szeroko��)
        {
            for (int j = 0; j < globalData.getnH()-1; j++)//idziemy po d�ugo�ci -1 (tyle element�w si� mie�ci na d�ugo��)
            {
                int n = i*(globalData.getnH()-1) + j; //tworzymy element numer N
                elements[n] = new Element(this,n);
                //ustawiamy odpowiednie w�z�y - najlepiej narysowa� sobie na kartce jak powinno to i��
                elements[n].setNode(0, n + i);
                elements[n].setNode(3, n + i + 1);
                elements[n].setNode(1, n + i + globalData.getnH());
                elements[n].setNode(2, n + i + globalData.getnH() + 1);
                if(nodes[n+i].isWall() &&nodes[n+i+1].isWall() ){//ściana 3
                    elements[n].setWall(0,true);
                }else{
                    elements[n].setWall(0,false);}
                if(nodes[n+i].isWall() &&nodes[n + i + globalData.getnH()].isWall() ){//ściana 0
                    elements[n].setWall(1,true);
                }else {
                    elements[n].setWall(1,false);}
                if(nodes[n + i + globalData.getnH()].isWall()&&nodes[n + i + globalData.getnH()+1].isWall() ){
                    elements[n].setWall(2,true);
                }else{
                    elements[n].setWall(2,false);}
                if(nodes[n+i+1].isWall() &&nodes[n + i + globalData.getnH()+1].isWall() ){//ściana 2
                    elements[n].setWall(3,true);
                }else{
                    elements[n].setWall(3,false);}
                elements[n].show();
            }
        }
    }
    public void showNodes(){
        for(int i=0;i<globalData.getNumberOfN();i++){
            nodes[i].show();
        }
    }
    public void showElements(){
        for(int i=0;i<globalData.getNumberOfE();i++){
            elements[i].show();
        }
    }
    public void drawGrid(){
        for(int i=0;i<globalData.getNumberOfE();i++){
            elements[i].draw();
        }
    }

    public void calculateJakobian(){
        for(int i=0;i<globalData.getNumberOfE();i++)
        {
            elements[i].calculateJ(universalElement, nodes);
            elements[i].calculateN(universalElement);
        }
    }
    public void showJakobian(){
        for(int i=0;i<globalData.getNumberOfE();i++)
        {
            elements[i].showJ();
            elements[i].showRevJ();
            elements[i].showDetJ();
        }
    }

    public Vector getTasVector(){
        Vector output=new Vector(globalData.getNumberOfN());
        output.clear();
        for(int i=0;i<globalData.getNumberOfN();i++)
        {
            output.setData(i,nodes[i].getT());
        }
        return output;
    }

    public void showMatrixH(){
        System.out.println("----    Matrix H    ----");
        matrixH.show();
    }
    public void showMatrixC(){
        System.out.println("----    Matrix C    ----");
        matrixC.show();
    }
    public void showMatrixHC(){
        System.out.println("----    Matrix HC    ----");
        matrixHC.show();
    }
    public void showVectorP(){
        System.out.println("----    Vector P    ----");
        vectorP.show();
    }

    public void clear(){
        matrixHC.clear();
        matrixH.clear();
        matrixC.clear();
        matrixS.clear();
        vectorP.clear();
    }
    public void calculateByTime(){
        Calculation calculation=new Calculation();
        double time=0;
        double iterations=globalData.getSimTime()/globalData.getSimStep();
        // double iterations=1.5;
        for(int step=0;step<=iterations;step++) //pętla po czasie
        {
            clear();
            SquareMatrix localH=new SquareMatrix(4);
            SquareMatrix localSurface=new SquareMatrix(4);
            SquareMatrix localC=new SquareMatrix(4);
            Vector localP=new Vector(4);

            for(int element=0;element<globalData.getNumberOfE();element++){// pętla po elementach
                localH.clear();
                localC.clear();
                localP.clear();
                localSurface.clear();
                for(int pkt=0;pkt<4;pkt++)//po punktach całkowania
                {
                    double t0p=0;
                    for (int j = 0; j < 4; j++)  // 4 - liczba wezlow w wykorzystywanym elemencie skonczonym
                    {
                        t0p+=nodes[elements[element].getNode(j)].getT() * getUniversalElement().getN(pkt,j);//interpolacja temperatury w punkcie całkowania
                    }
                    //-------------Liczenie C lokalne - pdf Kustra: FEM 2, wzór 6.11
                    SquareMatrix tempC=calculation.vectorTimesVectorT(universalElement.getN(pkt),universalElement.getN(pkt));//NxNt
                    tempC=calculation.scalarTimesMatrix(elements[element].getDetJ(pkt),tempC);//* detJ
                    tempC=calculation.scalarTimesMatrix(globalData.getHeat(),tempC);//* c
                    tempC=calculation.scalarTimesMatrix(globalData.getDensity(),tempC);//* ro


                    SquareMatrix dNdxt=calculation.vectorTimesVectorT(elements[element].getdNdX(pkt),elements[element].getdNdX(pkt));//macierz dnxt x dndt transponowane
                    SquareMatrix dNdyt=calculation.vectorTimesVectorT(elements[element].getdNdY(pkt),elements[element].getdNdY(pkt));//macierz dnxy x dndy transponowane

                    //k* detj *(dNdx * dNdxt + dNdy * dNdyt) - pdf Kustra FEM 2, wzór 6.9 , pierwsza całka
                    SquareMatrix tempH=calculation.scalarTimesMatrix(elements[element].getDetJ(pkt),calculation.scalarTimesMatrix(globalData.getCond(),calculation.addMatrixes(dNdxt,dNdyt)));

                    //zapis do macierzy elementu
                    localH=calculation.addMatrixes(localH,tempH);
                    localC=calculation.addMatrixes(localC,tempC);

                }

                for(int sciana=0;sciana<4;sciana++)
                {
                    double detj=0;
                    if(elements[element].getWall(sciana)){
                        switch(sciana){
                            case 0:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(3)].getX()-nodes[elements[element].getNode(0)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(3)].getY()-nodes[elements[element].getNode(0)].getY(),2))/2.0;
                                break;
                            case 1:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(0)].getX()-nodes[elements[element].getNode(1)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(0)].getY()-nodes[elements[element].getNode(1)].getY(),2))/2.0;
                                break;
                            case 2:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(1)].getX()-nodes[elements[element].getNode(2)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(1)].getY()-nodes[elements[element].getNode(2)].getY(),2))/2.0;
                                break;
                            case 3:
                                detj=Math.sqrt(Math.pow(nodes[elements[element].getNode(2)].getX()-nodes[elements[element].getNode(3)].getX(),2)+
                                        Math.pow(nodes[elements[element].getNode(2)].getY()-nodes[elements[element].getNode(3)].getY(),2))/2.0;
                                break;
                        }
                        for (int p = 0; p < 2; p++)
                        {
                            for (int n = 0; n < 4; n++)
                            {
                                for (int i = 0; i < 4; i++)
                                {
                                    //H - Kustra FEM 2, wzór 6.9, całka druga
                                    double x=globalData.getAlfa() * universalElement.getPOW()[sciana].N[p][n] * universalElement.getPOW()[sciana].N[p][i] * detj;
                                    localSurface.addData(n,i,x);
                                }
                                //P - Kustra FEM 2, wzór 6.10
                                double x=globalData.getAlfa() * globalData.getAmbientT() * universalElement.getPOW()[sciana].N[p][n] * detj;
                               localP.addData(n, x);
                            }
                        }

                    }
                }
                //zapis do globalnej macierzy
                for (int a = 0; a < 4; a++) {
                    for (int b = 0; b < 4; b++) {
                        matrixH.addData(elements[element].getNode(a),elements[element].getNode(b),localH.getData(a,b));
                        matrixS.addData(elements[element].getNode(a),elements[element].getNode(b),localSurface.getData(a,b));
                        matrixC.addData(elements[element].getNode(a),elements[element].getNode(b),localC.getData(a,b));

                    }
                    vectorP.addData(elements[element].getNode(a),localP.getData(a));
                }

            }
            Vector t0=getTasVector();
            System.out.println("-------- Iteration "+(step+1)+"     Time: "+(step*globalData.getSimStep()+10)+" seconds --------");
            //[P]^ = [P] + [C]/dT * {T0}
            System.out.println("  Vector T0:");
            //t0.show();
            vectorP=calculation.addVectors(vectorP,calculation.vectorTimesMatrix(t0,calculation.scalarTimesMatrix((1/globalData.getSimStep()),matrixC)));


            //[H]^ = [H] + [C]/dT
            matrixHC=calculation.addMatrixes(calculation.scalarTimesMatrix((1/globalData.getSimStep()),matrixC),matrixH);
            //dodanie warunków brzegowych
            matrixHC=calculation.addMatrixes(matrixHC,matrixS);
            //showMatrixHC();
            //showVectorP();
            Vector t1=calculation.GaussExtermiation(vectorP,matrixHC);

            for(int q=0;q<globalData.getNumberOfN();q++){
                nodes[q].setT(t1.getData(q));
            }
            System.out.println("Vector T1:");
            //t1.show();
            if(proces==1){ checkPreheating(t1);}
            else if(proces==2){chechAustenitize(t1);}
            else{chechTemper(t1);}

            System.out.println("----------------------------------------------");
            time+=globalData.getSimStep();//kolejny krok czasowy

        }

    }
    public void checkPreheating(Vector t){
        System.out.println("Węzły:  0: "+t.getData(0)+"    3:  "+t.getData(3)+"     38: "+t.getData(38));
        for(int i=0;i<t.getSize();i++)
        {
            if(t.getData(i)<815.0){ System.out.println("Niewystarczająca temperatura dla podgrzewania, należy podgrzewać dalej");
                return;}
        }
        System.out.println("Odpowiednia temperatura podgrzewania");
        return;
    }
    public void chechAustenitize(Vector t){
        System.out.println("Węzły:  0: "+t.getData(0)+"    3:  "+t.getData(3)+"     38: "+t.getData(38));
        boolean outsideflag=false;
        for(int i=0;i<globalData.getNumberOfN();i++){
            if(nodes[i].isWall()){
                if(nodes[i].getT()>1025){outsideflag=true; }else{outsideflag=false;}
            }
        }
        if(outsideflag==true){System.out.println("Krawędzie nagrzane");}else {System.out.println("Nagrzej krawędzie!");}
        boolean flag=false;
        if(t.getData(23)<1025){flag=true;}
        if(t.getData(24)<1025){flag=true;}
        if(t.getData(25)<1025){flag=true;}
        if(t.getData(30)<1025){flag=true;}
        if(t.getData(31)<1025){flag=true;}
        if(t.getData(32)<1025){flag=true;}
        if(t.getData(37)<1025){flag=true;}
        if(t.getData(38)<1025){flag=true;}
        if(t.getData(39)<1025){flag=true;}
        if(t.getData(44)<1025){flag=true;}
        if(t.getData(45)<1025){flag=true;}
        if(t.getData(46)<1025){flag=true;}
        if(t.getData(51)<1025){flag=true;}
        if(t.getData(52)<1025){flag=true;}
        if(t.getData(53)<1025){flag=true;}
        if(flag==true){System.out.println("Nie przegrzano rdzenia");} else {System.out.println("PRZEGRZANO RDZEN!");}
    }
    public void chechTemper(Vector t){
        System.out.println("Węzły:  0: "+t.getData(0)+"    3:  "+t.getData(3)+"     38: "+t.getData(38));
        for(int i=0;i<t.getSize();i++)
        {
            if(t.getData(i)<550.0){ System.out.println("Niewystarczająca temperatura dla odpuszczania, należy podgrzewać dalej");
                return;}
        }
        System.out.println("Wystarczjąca temperatura dla odpuszczania");
        return;
    }
}
