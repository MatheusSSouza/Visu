package Visu.mateface;

public class mfColaT {
	int Caso0v0a;
        int Caso1v0a;
	int Caso2v0a;
        int Caso3v0a;
        int Caso2v1a;
        int Caso3v2a;
        int Caso3v3a;

        
	public void colaT(int x, int y, int z, int id){
                Caso0v0a ++;
                mfMalha.vertice.get(x).setMae(id, x);
		mfMalha.vertice.get(y).setMae(id, y);
		mfMalha.vertice.get(z).setMae(id, z);
		
	}
	
	public void colaT(int x, int y, int z, int v, int id){
                Caso1v0a ++;
                mfMalha.vertice.get(x).setMae(id, x);
		mfMalha.vertice.get(y).setMae(id, y);
		mfMalha.vertice.get(z).setMae(id, z);
		
		
	}
	
	public void colaT(int x, int y, int z, int v1,int v2, int id){
                Caso2v0a++;
                mfMalha.vertice.get(x).setMae(id, x);
		mfMalha.vertice.get(y).setMae(id, y);
		mfMalha.vertice.get(z).setMae(id, z);
                
	}
        
        public void colaT(int x, int y, int z, int v1,int v2,int v3, int id){
                Caso3v0a ++;
                mfMalha.vertice.get(x).setMae(id, x);
		mfMalha.vertice.get(y).setMae(id, y);
		mfMalha.vertice.get(z).setMae(id, z);
                
	}
	
	
	public void colaT(int x, int y, int z, int viz1, int viz2, int v1, int v2, int id){
		Caso2v1a ++;
                mfMalha.celula.get(viz1).setMate(viz2, v1);
                mfMalha.celula.get(viz2).setMate(viz1, v2);
                
                
				
	}
	
	public void colaT(int x, int y, int z, int viz1, int viz2, int viz3, int viz4,int v1, int v2, int v3, int v4, int id) {
                    Caso3v2a++;
                    mfMalha.celula.get(viz1).setMate(viz2, v1);
                    mfMalha.celula.get(viz2).setMate(viz1, v2);
                    
                    mfMalha.celula.get(viz3).setMate(viz4, v3);
                    mfMalha.celula.get(viz4).setMate(viz3, v4);
		
	}
	
	public void colaT(int x, int y, int z, int viz1, int viz2, int viz3, int viz4, int viz5, int viz6 ,int v1, int v2, int v3, int v4, int v5, int v6,int id){
		Caso3v3a++;
                mfMalha.celula.get(viz1).setMate(viz2, v1);
                mfMalha.celula.get(viz2).setMate(viz1, v2);
                   
                mfMalha.celula.get(viz3).setMate(viz4, v3);
                mfMalha.celula.get(viz4).setMate(viz3, v4);
                
		mfMalha.celula.get(viz5).setMate(viz6, v5);
                mfMalha.celula.get(viz6).setMate(viz5, v6);
	}

	
}