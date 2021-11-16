package edu.epsevg.prop.lab.c4;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class ConnectaBroIo implements Jugador, IAuto {
    private String nom;
    
    public ConnectaBroIo(){
        nom = "ConnectaBroIo";
        
    }
  
    public int moviment(Tauler t, int color){
        int col = 0;
        for(int i=0; i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler(t);
                if(aux.solucio(i, color)){
                    return i;
                } else {
                    // Llamar a minimax, donde yo maximizo y el otro minimza
                    col = 1;
                }

            }
        }
        return col;
    }
  
    public String nom(){
        return nom;
    }
}


