package mateface;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe que carrega as estruturas da malha, adicionando vértices células e
 * células opostas
 */
public class mfMalha {

    static ArrayList<mfVertex> vertice = new ArrayList<mfVertex>();
    static ArrayList<mfCelula> celula = new ArrayList<mfCelula>();
    private int h;
    public int t;//numero de vertces considerando repetição
    static boolean comHash;
    public static HashMap<Integer, LinkedList<mfAresta>> posicao;
    static int i = 0;
    static int j = 0;

    /**
     * Adiciona ao Array vertice as coordenadas dos vértices
     */
    public void addVertice(float[] coord) {
        mfVertex v = new mfVertex();
        vertice.add(v);
        vertice.get(i).setCoords(coord);
        i++;
    }

    /**
     * Adiciona ao Array celula, os vértices de cada célula
     */
    public void addCelula(int[] v) {
        //adicionando
        mfCelula c = new mfCelula();
        celula.add(c);
        celula.get(j).setVertex(v);
       // for (int cont = 0; cont < 3; cont++) {
            //vertice.get(v[cont]).setMae(j);
        //}
        //hash
            /*
        if (comHash == true) {
        	System.out.println("hash");
            int v0 = celula.get(j).getVertex(0);
            int v1 = celula.get(j).getVertex(1);
            int v2 = celula.get(j).getVertex(2);
            if (v0 < v1) {
                addHash(v0, v1, j);
            } else {
                addHash(v1, v0, j);
            }
            if (v0 < v2) {
                addHash(v0, v2, j);
            } else {
                addHash(v2, v0, j);
            }
            if (v1 < v2) {
                addHash(v1, v2, j);
            } else {
                addHash(v2, v1, j);
            }
        }
        */
        j++;
    }

    /**
     * Atualiza a tabela Hash, caso esta esteja tiva
     */
//    public void addHash(int a, int b, int c) {
//
//        LinkedList<mfAresta> reta = new LinkedList<mfAresta>();
//        h = ((10 * a + b) % t);
//        if (posicao.get(h) == null) {
//            posicao.put(h, reta);
//        }
//        mfAresta r = new mfAresta();
//        r.addReta(a, b);
//        r.addCelula(c);
//        posicao.get(h).addFirst(r);
//    }

    /**
     * Adiciona as células opostas pelo método de carregamento simples
     */
    public void addMateeCelula() {
        for (int c1 = 0; c1 < celula.size() - 1; c1++) {
            for (int v1 = 0; v1 < 3; v1++) {
                for (int c2 = 1; c2 < celula.size(); c2++) {
                    if (celula.get(c1).isMate(c2) == false) {
                        for (int v2 = 0; v2 < 3; v2++) {
                            if (celula.get(c1).getVertex(v1) == celula.get(c2).getVertex(v2)) {
                                if (celula.get(c1).getVertex((v1 + 2) % 3) == celula.get(c2).getVertex((v2 + 1) % 3)) {
                                    celula.get(c1).setMate(c2, ((v1 + 1) % 3));
                                    celula.get(c2).setMate(c1, ((v2 + 2) % 3));
                                } else if (celula.get(c1).getVertex((v1 + 1) % 3) == celula.get(c2).getVertex((v2 + 2) % 3)) {
                                    celula.get(c1).setMate(c2, ((v1 + 2) % 3));
                                    celula.get(c2).setMate(c1, ((v2 + 1) % 3));
                                }
                                //if(c1>105850)
                                //System.out.println("celula "+c1+" vizinha "+c2);
                            }
                        }
                    }
                }
            }
        }
    }
    

    /**
     * Adiciona as células opostas, utilizando uma tabela hash
     */
    public void addMateHash() {
        int menor;
        int maior;
        //System.out.println("aqui");
        for (int c1 = 0; c1 < celula.size() - 1; c1++) {
            for (int v1 = 0; v1 < 3; v1++) {
                if (celula.get(c1).getVertex((v1 + 1) % 3) < celula.get(c1).getVertex((v1 + 2) % 3)) {
                    menor = celula.get(c1).getVertex((v1 + 1) % 3);
                    maior = celula.get(c1).getVertex((v1 + 2) % 3);
                } else {
                    menor = celula.get(c1).getVertex((v1 + 2) % 3);
                    maior = celula.get(c1).getVertex((v1 + 1) % 3);
                }
                h = ((10 * menor + maior) % t);
                boolean add = false;
                int cont = 0;
                do {
                    mfAresta cMate = posicao.get(h).get(cont);
                    if ((cMate.isReta(menor, maior)) && (c1 != cMate.getCelula())) {
                        celula.get(c1).setMate(cMate.getCelula(), v1);
                        celula.get(cMate.getCelula()).setMate(c1, celula.get(cMate.getCelula()).getOposto(menor, maior));
                        //System.out.println("celula c1 "+c1+" vizinha "+celula.get(c1).getMate(v1)+" ");
                        //System.out.println("celula "+posicao.get(h).get(cont).getCelula()+" vizinha "+celula.get(posicao.get(h).get(cont).getCelula()).getMate(v1));
                        add = true;
                    } else {
                        cont++;
                    }
                } while (!add && cont < posicao.get(h).size());
            }
        }
    }
    
    public void makeoff(String path){
        try{
            File arq = new File(path);
            BufferedWriter w = new BufferedWriter(new FileWriter(arq));
            w.write("OFF");
            w.newLine();
            w.write(i + " " + celula.size() + " 0");
            w.newLine();
            for(int l =0; l< i; l++){
                w.newLine();
                for(int a =0; a<3; a++){ 
                    w.write(String.valueOf(vertice.get(l).getCoords(a)) + " ");
                }
            }
            for(int a =0; a<celula.size();a++){
                w.newLine();
	        int x = celula.get(a).getVertex(0);
	        int y = celula.get(a).getVertex(1);
	        int z = celula.get(a).getVertex(2);
	        w.write("3 " + x + " " + y + " " + z);
	        
        }
            w.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
            
            
    }
     
}