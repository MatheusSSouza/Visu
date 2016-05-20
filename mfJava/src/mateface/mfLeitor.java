package mateface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe de leitura do arquivo .OFF que contém a malha
 */
public class mfLeitor {

    private int _numCell;
    private int _numVet;
    private String[] temp;
    private int[] cl = new int[4];

    /**
     * Lê o arquivo .OFF, já chamando os métodos necessários para carregar a
     * malha
     */
    public void leituraOFF(mfMalha malha, String nome) {
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
                while (cont < _numCell) {
                    temp = arquivo.readLine().replaceFirst("  ", " ").split("\\ ");
                    for (int i = 1; i < temp.length; i++) {
                        cl[i - 1] = Integer.parseInt(temp[i]);
                    }
                    malha.addCelula(cl);
                    cont++;
                }
                if (mfMalha.comHash == true) {
                    malha.addMateHash();
                } else {
                    malha.addMateCelula();
                }
            }
            arquivo.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }
}
