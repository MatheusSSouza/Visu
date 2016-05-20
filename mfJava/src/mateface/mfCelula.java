package mateface;

/**Classe de células da malha */
public class mfCelula extends mfMalha{


    private int[] vertices = new int[3];
    private int[] mates = new int[3];

    mfCelula(){
      for(int i =0; i<3; i++) {
            mates[i]=-1;
        }
    }
    
    /** Seta os vértices da malha */
    public void setVertex(int[] v){
      for(int i = 0; i <3; i++) {
            vertices[i]=v[i];
        }}

    /**Obtém o vértice do indíce determinado */
    public int getVertex(int id){
      return vertices[id];}

    /** Retorna true caso a célula possua todas as vizinhas possíveis */
    public boolean fullMate(){
      if(mates.length>3) {
            return true;
        }
      else {
            return false;
        }}

    /**Retorna o índice do vértice determinado */
    public int getId(int v){
      int n=-1;
      for(int i =0; i<3; i++){
        if(vertices[i]==v) {
                n=i;
            }}
      return n;
    }
    
    
    /**Dado dois vértices, retorna o terceiro vértice da célula*/
    public int getOposto(int x, int y){
      for (int i=0; i<3; i++){
          //System.out.println("I: "+i+" Vertice[i]: "+vertices[i]);
        if(vertices[i]!=x && vertices[i]!=y) {
                return i;
            }
          }
      return -31;
    }

    
    public void setMate(int cell,int id){
      //System.out.println("Id: "+id+" cell "+cell);
      mates[id]=cell;
    }

    public int getMate(int id){
      return mates[id];}

    public boolean isMate(int id){
      for(int i=0; i<3; i++){
        if(mates[i]==id){
          return true;}}
         return false;}
}