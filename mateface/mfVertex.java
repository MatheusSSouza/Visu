

package Visu.mateface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

/**Classe de vértices da malha */
public class mfVertex extends mfMalha{

  private float[] coords = new float[3];
  private int mae;
 // public LinkedList <Integer> M = new LinkedList <Integer>();
  TreeSet<Integer> M = new TreeSet<Integer>();
  //static TreeSet[] maes= new TreeSet[11];
 // LinkedList<TreeSet> m = new LinkedList<TreeSet>();
  //public static HashSet<Integer, LinkedList<Integer>> mamis = new HashMap(10);
 

  public void setCoords(float[] c){
        System.arraycopy(c, 0, coords, 0, 3);
  }

  public float getCoords(int i){
    return coords[i];}



  public void setMae(int i, int pos){
    mae=i;
    M.add(mae);
    //m.add(pos, M);
   // maes[pos]=M;
    //maes.put(pos, M);
      
  
  }

  public TreeSet getMae(){
	  
	  return M;
    
  }

}