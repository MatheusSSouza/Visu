package mateface;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Classe que opera as estrelas dos vértices, podendo ser com um ou mais níveis
 */
public class mfNivelEstrela {

    static ArrayList<Integer> verticesDeNivel;
    static ArrayList<Integer> celulasDeNivel;

    public void escreve() {
        int tamanho = mfMalha.vertice.size();
        int[] c = new int[tamanho];
        int[] v = new int[tamanho];

        try {
            for (int i = 0; i < tamanho; i++) {
                v[i] = 0;
                RandomAccessFile fileSaida = new RandomAccessFile("" + i + ".off", "rw");
                String lineSeparator = System.getProperty("line.separator");
                fileSaida.writeBytes("OFF");
                fileSaida.writeBytes(lineSeparator);
                fileSaida.writeBytes("V C 0");
                fileSaida.writeBytes(lineSeparator);
                fileSaida.writeBytes(lineSeparator);
                for (int cv = 0; cv < 3; cv++) {
                    fileSaida.writeBytes("\n" + mfMalha.vertice.get(i).getCoords(cv) + " ");
                }
                fileSaida.writeBytes(lineSeparator);
                v[i]++;
                int m = mfMalha.vertice.get(i).getMae();
                int inda = mfMalha.celula.get(m).getVertex((mfMalha.celula.get(m).getId(i) + 1) % 3);
                for (int cv = 0; cv < 3; cv++) {
                    fileSaida.writeBytes(mfMalha.vertice.get(inda).getCoords(cv) + " ");
                }
                fileSaida.writeBytes(lineSeparator);
                v[i]++;
                int n = m;
                int faceAnt = m;
                int ladoA = 1;
                int ladoB = 2;
                int bordo = 0;
                int cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(i) + ladoA) % 3);
                
                while (m != cViz && bordo < 2) {
                    if (cViz == -1) {
                        bordo++;
                        n = m;
                        faceAnt = m;
                        ladoA = 2;
                        ladoB = 1;
                        cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(i) + ladoA) % 3);
                        if (cViz == -1) {
                            bordo++;
                        }
                    }
                    if (bordo < 2) {
                        int indb = mfMalha.celula.get(n).getVertex((mfMalha.celula.get(n).getId(i) + ladoB) % 3);
                        for (int cv = 0; cv < 3; cv++) {
                            fileSaida.writeBytes(mfMalha.vertice.get(indb).getCoords(cv) + " ");
                        }
                        fileSaida.writeBytes(lineSeparator);
                        c[i]++;
                        v[i]++;
                        n = cViz;
                        cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(i) + ladoA) % 3);
                        if (cViz == faceAnt) {
                            cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(i) + ladoB) % 3);
                        }
                        faceAnt = n;
                    }
                }
                int num = 1;
                for (int j = 0; j < c[i] - 1; j++) {
                    fileSaida.writeBytes("3  " + 0 + " " + num + " " + (num + 1));
                    fileSaida.writeBytes(lineSeparator);
                    num++;
                }
                if (bordo < 2) {
                    fileSaida.writeBytes("3  " + 0 + " " + num + " 1");
                    fileSaida.writeBytes(lineSeparator);
                }
                fileSaida.seek(0);
                fileSaida.writeBytes("OFF");
                fileSaida.writeBytes(lineSeparator);
                fileSaida.writeBytes(+v[i] + " " + c[i] + " 0");
                fileSaida.writeBytes(lineSeparator);
                fileSaida.writeBytes(lineSeparator);
                fileSaida.close();
            }


        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void escreveCopia() {

        int tamanhoV = mfMalha.vertice.size();
        int tamanhoC = mfMalha.celula.size();

        try {
            PrintWriter print = new PrintWriter(new FileWriter("copia.txt"));
            print.println("OFF");
            print.println(tamanhoV + " " + tamanhoC + " 0");
            print.println();
            for (int i = 0; i < tamanhoV; i++) {
                print.println(mfMalha.vertice.get(i).getCoords(0) + " " + mfMalha.vertice.get(i).getCoords(1) + " " + mfMalha.vertice.get(i).getCoords(2));
            }
            for (int i = 0; i < tamanhoC; i++) {
                print.println("3  " + mfMalha.celula.get(i).getVertex(0) + " " + mfMalha.celula.get(i).getVertex(1) + " " + mfMalha.celula.get(i).getVertex(2));
            }

            print.close();
        } catch (Exception e) {
        }
    }

    public void escreverVertices(int niv, int v) {

        try {
            PrintWriter print = new PrintWriter(new FileWriter("v" + v + "niv" + niv + ".off"));
            print.println("OFF");
            print.println(verticesDeNivel.size() + " " + celulasDeNivel.size() + " 0");
            print.println();
            for (int i = 0; i < verticesDeNivel.size(); i++) {
                print.println(mfMalha.vertice.get(verticesDeNivel.get(i)).getCoords(0) + " " + mfMalha.vertice.get(verticesDeNivel.get(i)).getCoords(1) + " " + mfMalha.vertice.get(verticesDeNivel.get(i)).getCoords(2));
            }
            for (int i = 0; i < celulasDeNivel.size(); i++) {
                mfCelula c = mfMalha.celula.get(celulasDeNivel.get(i));
                print.println("3  " + verticesDeNivel.indexOf(c.getVertex(0)) + " " + verticesDeNivel.indexOf(c.getVertex(1)) + " " + verticesDeNivel.indexOf(c.getVertex(2)));
            }


            print.close();
        } catch (Exception e) {
        }
    }

    public void nivelDoVertice(int niv, int v) {
        if (niv > 0) {
            niv--;
            if (!verticesDeNivel.contains(v)) {
                verticesDeNivel.add(v);
            }            
            ArrayList<Integer> proxVert = new ArrayList<Integer>();
            int m = mfMalha.vertice.get(v).getMae();
            if (!celulasDeNivel.contains(m)) {
                celulasDeNivel.add(m);
            }
            int inda = mfMalha.celula.get(m).getVertex((mfMalha.celula.get(m).getId(v) + 1) % 3);
            proxVert.add(inda);
            int n = m;
            int ladoA = 1;
            int ladoB = 2;
            int bordo = 0;
            int cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(v) + ladoA) % 3);
            while (m != cViz && bordo < 2) {
                    if (!celulasDeNivel.contains(cViz)) {
                        celulasDeNivel.add(cViz);
                    }
                    int indb = mfMalha.celula.get(n).getVertex((mfMalha.celula.get(n).getId(v) + ladoB) % 3);
                    proxVert.add(indb);
                    n = cViz;
                    cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(v) + ladoA) % 3);
                    if (cViz == -1) {
                    bordo++;
                    ladoA = 2;
                    ladoB = 1;
                    cViz = mfMalha.celula.get(n).getMate((mfMalha.celula.get(n).getId(v) + ladoA) % 3);
                    if (cViz == -1) {
                        bordo++;
                    }
                }
                }
            for (int i = 0; i < proxVert.size(); i++) {
                nivelDoVertice(niv, proxVert.get(i));
            }

            for (int k = 0; k < proxVert.size(); k++) {
                if (!verticesDeNivel.contains(proxVert.get(k))) {
                    verticesDeNivel.add(proxVert.get(k));
                }
            }
        }
    }
}