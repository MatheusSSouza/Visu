package Visu.mateface;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import javax.media.j3d.TriangleArray;

//import che.cheColaT;
//import che.cheMalha;
/**
 * Classe que possui o método main
 */
public class mfPlay {

    public static void main(String[] args) {
        String malha = "PernaNoProcedimento";
        System.out.println("MF: Malha " + malha);
        mfMalha m = new mfMalha();

        mfSoLe l = new mfSoLe();
        mfColaT c = new mfColaT();
        //mfNivelEstrela e;

        //Decide que tipo de método será usado para carregar vizinhança;
        //mfMalha.comHash = true;
        //mfMalha.posicao = new HashMap<Integer, LinkedList<mfAresta>>();
        l.leituraOFF(m, "E:/mfJava/src/off's/" + malha + ".OFF");

        int ini = 0;

        for (int fim = 0; fim < mfMalha.celula.size(); fim++) {

            int vertcont = 0;
            int arestacont = 0;
            LinkedList<Integer> vizcoord = new LinkedList<Integer>();
            LinkedList<Integer> vizcell = new LinkedList<Integer>();
            LinkedList<Integer> vizvert = new LinkedList<Integer>();

            int x = mfMalha.celula.get(ini + 1).getVertex(0);
            int y = mfMalha.celula.get(ini + 1).getVertex(1);
            int z = mfMalha.celula.get(ini + 1).getVertex(2);

            if (fim == 0) {
                x = mfMalha.celula.get(ini).getVertex(0);
                y = mfMalha.celula.get(ini).getVertex(1);
                z = mfMalha.celula.get(ini).getVertex(2);
            }
            /*
             System.out.println( x+" "+y +" "+z);
                
             for(int i =0; i< mfMalha.celula.size();i++){ 
             System.out.print(mfMalha.celula.get(i).getMate(0)+" ");
             System.out.print(mfMalha.celula.get(i).getMate(1)+" ");
             System.out.println(mfMalha.celula.get(i).getMate(2));
		        
             }
             */
            //percorre de linha em linha
            for (ini = 0; ini < fim; ini++) {
        		//System.out.println("ini: "+ini+ " fim:"+fim);

        		//System.out.println(x +" "+y+" "+z);
                for (int v1 = 0; v1 < 3; v1++) {

                    for (int v2 = 0; v2 < 3; v2++) {
                        if (m.celula.get(fim).getVertex(v1) == m.celula.get(ini).getVertex(v2)) {
                            //  System.out.println(v1 + " "+ v2 );
                            if (m.celula.get(fim).getVertex((v1 + 2) % 3) == m.celula.get(ini).getVertex((v2 + 1) % 3)) {
                                //vertcont++;
                                vizcoord.add((v1 + 1) % 3);
                                vizcoord.add((v2 + 2) % 3);
                                vizcell.add(ini);
                                vizcell.add(fim);
                                arestacont++;

                            } else if (m.celula.get(fim).getVertex((v1 + 1) % 3) == m.celula.get(ini).getVertex((v2 + 2) % 3)) {
                                //vertcont++;
                                vizcoord.add((v1 + 2) % 3);
                                vizcoord.add((v2 + 1) % 3);
                                vizcell.add(ini);
                                vizcell.add(fim);
                                arestacont++;

                            } else {
                                vertcont++;
                                vizvert.add(v1);
                                vizvert.add(v2);
                            }
                        }
                    }
                }

            }

        	//System.out.println("vert "+ vertcont);
            //System.out.println("aresta "+ arestacont);
                //for(int q=0; q<vizcoord.size();q++){
            //    System.out.print(vizcoord.get(q) + " ");
              //  }
                //System.out.println();
            //for(int q=0; q<vizvert.size();q++){
            //  System.out.print(vizvert.get(q) +  "=== ");
                //}
            // System.out.println(fim+ " ");
            if (fim == 0 || (vertcont == 0 && arestacont == 0)) {

                c.colaT(x, y, z, fim);

            } else if (arestacont == 6) {

                c.colaT(x, y, z, vizcell.get(0), vizcell.get(1), vizcell.get(4), vizcell.get(5), vizcell.get(8), vizcell.get(9), vizcoord.get(1), vizcoord.get(0), vizcoord.get(5), vizcoord.get(4), vizcoord.get(9), vizcoord.get(8), fim);

            } else if (arestacont == 4) {

                c.colaT(x, y, z, vizcell.get(0), vizcell.get(1), vizcell.get(4), vizcell.get(5), vizcoord.get(1), vizcoord.get(0), vizcoord.get(5), vizcoord.get(4), fim);

            } else if (arestacont == 2) {

                c.colaT(x, y, z, vizcell.get(0), vizcell.get(1), vizcoord.get(1), vizcoord.get(0), fim);

            } else if (vertcont == 3) {

                c.colaT(x, y, z, vizvert.get(0), vizvert.get(2), vizvert.get(4), fim);

            } else if (vertcont == 2) {

                c.colaT(x, y, z, vizvert.get(0), vizvert.get(2), fim);
            } else if (vertcont == 1) {

                c.colaT(x, y, z, vizvert.get(0), fim);
            }

            //System.out.println("=========fimdofim=========");
        }

        System.out.println("Caso0v0a " + c.Caso0v0a + "\n"
                + "Caso1v0a " + c.Caso1v0a + "\n"
                + "Caso2v0a " + c.Caso2v0a + "\n"
                + "Caso2v1a " + c.Caso2v1a + "\n"
                + "Caso3v0a " + c.Caso3v0a + "\n"
                + "Caso3v2a " + c.Caso3v2a + "\n"
                + "Caso3v3a " + c.Caso3v3a);

        System.out.println("\nCOORDS");
        for (int i = 0; i < mfMalha.i; i++) {
            for (int a = 0; a < 3; a++) {

                System.out.print(mfMalha.vertice.get(i).getCoords(a) + " ");
            }
            System.out.println();
        }
        System.out.println("\nCELULAS");
        for (int a = 0; a < mfMalha.celula.size(); a++) {
            int x = mfMalha.celula.get(a).getVertex(0);
            int y = mfMalha.celula.get(a).getVertex(1);
            int z = mfMalha.celula.get(a).getVertex(2);
            System.out.println(a + ": " + x + " " + y + " " + z);

	        //c.colaT(x,y,z);
        }

        System.out.println("\nVIZINHAS");
        for (int i = 0; i < mfMalha.celula.size(); i++) {
            System.out.print(mfMalha.celula.get(i).getMate(0) + " ");
            System.out.print(mfMalha.celula.get(i).getMate(1) + " ");
            System.out.println(mfMalha.celula.get(i).getMate(2));

        }

        System.out.println("\nMÃES");

        for (int i = 0; i < mfMalha.vertice.size(); i++) {
            System.out.println(mfMalha.vertice.get(i).getMae());
        }

        //l.leituraOFF(m, "E:/CHEJava/off's/" + malha + ".OFF");
    }
    
    public static TriangleArray play1(String path){
        mfMalha m = new mfMalha();
        m.vertice.clear();
        m.celula.clear();
//        m.posicao.clear();
        m.h = 0;
        m.t = 0;
        m.i = 0;
        m.j = 0;
        
        

        mfSoLe l = new mfSoLe();
        mfColaT c = new mfColaT();
        //mfNivelEstrela e;

        //Decide que tipo de método será usado para carregar vizinhança;
        //mfMalha.comHash = true;
        //mfMalha.posicao = new HashMap<Integer, LinkedList<mfAresta>>();
        return l.leituraOFF(m, path);

        
    }
    
    public static void play2(){
        mfMalha m = new mfMalha();
        mfColaT c = new mfColaT();
        
        int ini = 0;

        for (int fim = 0; fim < mfMalha.celula.size(); fim++) {

            int vertcont = 0;
            int arestacont = 0;
            LinkedList<Integer> vizcoord = new LinkedList<Integer>();
            LinkedList<Integer> vizcell = new LinkedList<Integer>();
            LinkedList<Integer> vizvert = new LinkedList<Integer>();

            int x = mfMalha.celula.get(ini + 1).getVertex(0);
            int y = mfMalha.celula.get(ini + 1).getVertex(1);
            int z = mfMalha.celula.get(ini + 1).getVertex(2);

            if (fim == 0) {
                x = mfMalha.celula.get(ini).getVertex(0);
                y = mfMalha.celula.get(ini).getVertex(1);
                z = mfMalha.celula.get(ini).getVertex(2);
            }
            /*
             System.out.println( x+" "+y +" "+z);
                
             for(int i =0; i< mfMalha.celula.size();i++){ 
             System.out.print(mfMalha.celula.get(i).getMate(0)+" ");
             System.out.print(mfMalha.celula.get(i).getMate(1)+" ");
             System.out.println(mfMalha.celula.get(i).getMate(2));
		        
             }
             */
            //percorre de linha em linha
            for (ini = 0; ini < fim; ini++) {
        		//System.out.println("ini: "+ini+ " fim:"+fim);

        		//System.out.println(x +" "+y+" "+z);
                for (int v1 = 0; v1 < 3; v1++) {

                    for (int v2 = 0; v2 < 3; v2++) {
                        if (m.celula.get(fim).getVertex(v1) == m.celula.get(ini).getVertex(v2)) {
                            //  System.out.println(v1 + " "+ v2 );
                            if (m.celula.get(fim).getVertex((v1 + 2) % 3) == m.celula.get(ini).getVertex((v2 + 1) % 3)) {
                                //vertcont++;
                                vizcoord.add((v1 + 1) % 3);
                                vizcoord.add((v2 + 2) % 3);
                                vizcell.add(ini);
                                vizcell.add(fim);
                                arestacont++;

                            } else if (m.celula.get(fim).getVertex((v1 + 1) % 3) == m.celula.get(ini).getVertex((v2 + 2) % 3)) {
                                //vertcont++;
                                vizcoord.add((v1 + 2) % 3);
                                vizcoord.add((v2 + 1) % 3);
                                vizcell.add(ini);
                                vizcell.add(fim);
                                arestacont++;

                            } else {
                                vertcont++;
                                vizvert.add(v1);
                                vizvert.add(v2);
                            }
                        }
                    }
                }

            }

        	//System.out.println("vert "+ vertcont);
            //System.out.println("aresta "+ arestacont);
                //for(int q=0; q<vizcoord.size();q++){
            //    System.out.print(vizcoord.get(q) + " ");
              //  }
                //System.out.println();
            //for(int q=0; q<vizvert.size();q++){
            //  System.out.print(vizvert.get(q) +  "=== ");
                //}
            // System.out.println(fim+ " ");
            if (fim == 0 || (vertcont == 0 && arestacont == 0)) {

                c.colaT(x, y, z, fim);

            } else if (arestacont == 6) {

                c.colaT(x, y, z, vizcell.get(0), vizcell.get(1), vizcell.get(4), vizcell.get(5), vizcell.get(8), vizcell.get(9), vizcoord.get(1), vizcoord.get(0), vizcoord.get(5), vizcoord.get(4), vizcoord.get(9), vizcoord.get(8), fim);

            } else if (arestacont == 4) {

                c.colaT(x, y, z, vizcell.get(0), vizcell.get(1), vizcell.get(4), vizcell.get(5), vizcoord.get(1), vizcoord.get(0), vizcoord.get(5), vizcoord.get(4), fim);

            } else if (arestacont == 2) {

                c.colaT(x, y, z, vizcell.get(0), vizcell.get(1), vizcoord.get(1), vizcoord.get(0), fim);

            } else if (vertcont == 3) {

                c.colaT(x, y, z, vizvert.get(0), vizvert.get(2), vizvert.get(4), fim);

            } else if (vertcont == 2) {

                c.colaT(x, y, z, vizvert.get(0), vizvert.get(2), fim);
            } else if (vertcont == 1) {

                c.colaT(x, y, z, vizvert.get(0), fim);
            }

//            System.out.println("=========fimdofim=========");
        }
//
//        System.out.println("Caso0v0a " + c.Caso0v0a + "\n"
//                + "Caso1v0a " + c.Caso1v0a + "\n"
//                + "Caso2v0a " + c.Caso2v0a + "\n"
//                + "Caso2v1a " + c.Caso2v1a + "\n"
//                + "Caso3v0a " + c.Caso3v0a + "\n"
//                + "Caso3v2a " + c.Caso3v2a + "\n"
//                + "Caso3v3a " + c.Caso3v3a);
//
//        System.out.println("\nCOORDS");
//        for (int i = 0; i < mfMalha.i; i++) {
//            for (int a = 0; a < 3; a++) {
//
//                System.out.print(mfMalha.vertice.get(i).getCoords(a) + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("\nCELULAS");
//        for (int a = 0; a < mfMalha.celula.size(); a++) {
//            int x = mfMalha.celula.get(a).getVertex(0);
//            int y = mfMalha.celula.get(a).getVertex(1);
//            int z = mfMalha.celula.get(a).getVertex(2);
//            System.out.println(a + ": " + x + " " + y + " " + z);
//
//	        //c.colaT(x,y,z);
//        }
//
//        System.out.println("\nVIZINHAS");
//        for (int i = 0; i < mfMalha.celula.size(); i++) {
//            System.out.print(mfMalha.celula.get(i).getMate(0) + " ");
//            System.out.print(mfMalha.celula.get(i).getMate(1) + " ");
//            System.out.println(mfMalha.celula.get(i).getMate(2));
//
//        }
//
//        System.out.println("\nMÃES");
//
//        for (int i = 0; i < mfMalha.vertice.size(); i++) {
//            System.out.println(mfMalha.vertice.get(i).getMae());
//        }

        //l.leituraOFF(m, "E:/CHEJava/off's/" + malha + ".OFF");
    }
    

}
