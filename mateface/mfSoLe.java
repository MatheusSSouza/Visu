package Visu.mateface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

/**
 * Classe de leitura do arquivo .OFF que contém a malha
 */
public class mfSoLe {

    private int _numCell;
    private int _numVet;
    private String[] temp;
    private int[] cl = new int[4];

    /**
     * Lê o arquivo .OFF, já chamando os métodos necessários para carregar a
     * malha
     */
    public TriangleArray leituraOFF(mfMalha malha, String nome) {
        TriangleArray ta = null;
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(nome));

            if (arquivo.readLine().equals("OFF")) {
            }

            while (arquivo.ready()) {

                temp = arquivo.readLine().split("\\ ");
                _numVet = Integer.parseInt(temp[0]);
                _numCell = Integer.parseInt(temp[1]);

                malha.t = 3 * _numCell;

                if (arquivo.readLine().trim().length() == 0) {
                }
                int cont = 0;
                while (cont < _numVet) {
                    temp = arquivo.readLine().split("\\ ");
                    float[] vt = new float[temp.length];
                    for (int i = 0; i < temp.length; i++) {
                        vt[i] = Float.parseFloat(temp[i]);
                    }
                    malha.addVertice(vt);
                    cont++;
                }
                cont = 0;

                ta = new TriangleArray(malha.t, TriangleArray.COORDINATES);
                
                while (cont < _numCell) {
                    temp = arquivo.readLine().replaceFirst("  ", " ").split("\\ ");
                    for (int i = 1; i < temp.length; i++) {
                        cl[i - 1] = Integer.parseInt(temp[i]);
                    }
                    malha.addCelula(cl);
                    cont++;
                    
                    
                    Point3d p0 = new Point3d(malha.vertice.get(cl[0]).getCoords(0)
                            , malha.vertice.get(cl[0]).getCoords(1), malha.vertice.get(cl[0]).getCoords(2));
                    Point3d p1 = new Point3d(malha.vertice.get(cl[1]).getCoords(0)
                            , malha.vertice.get(cl[1]).getCoords(1), malha.vertice.get(cl[1]).getCoords(2));
                    Point3d p2 = new Point3d(malha.vertice.get(cl[2]).getCoords(0)
                            , malha.vertice.get(cl[2]).getCoords(1), malha.vertice.get(cl[2]).getCoords(2));
                    
                    ta.setCoordinate((cont*3)-3, p0);
                    ta.setCoordinate((cont*3)-2, p1);
                    ta.setCoordinate((cont*3)-1, p2);
                }
            }
            arquivo.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return ta;
    }
}
