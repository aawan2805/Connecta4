package edu.epsevg.prop.lab.c4;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class ConnectaBroIo implements Jugador, IAuto {
    private String nom;
    private int profundidad;
    
    public ConnectaBroIo(int profundidad){
        nom = "ConnectaBroIo";
        this.profundidad = profundidad;
    }

    public String nom(){
        return nom;
    }
  
    public int moviment(Tauler t, int color){
        int col = 0;
        Integer heuristica = 0;
        Integer Alpha = Integer.MIN_VALUE; // Alpha es -infinito
        Integer Beta = Integer.MAX_VALUE; // Beta es +infinito

        int i = 0;
        boolean solution = false;
        while(i < t.getMida() && !solution){
            Tauler aux = new Tauler(t);
            if(aux.movpossible(i)){ // Miramos si se puede hacer el movimiento
                aux.afegeix(i, color);
                /*if(aux.solucio(i, color)){
                    solution = true;
                    col = i;
                } else {
                */
                // Llamar a minimax, donde yo maximizo y el otro minimza
                // -color porque yo entro con el color del adversario
                // Yo entro maximizando, el adversario minimiza por eso -podaAlphaBetaMegaMax
                alfa = Math.max(alfa, -podaAlphaBetaMegaMax(aux, i, -color, Alpha, Beta, profundidad));
                // TODO 
                col = i;
                // Cut
                /*if(heuristica >= Alpha){
                    col = i;
                    Alpha = heuristica;
                }
                */
            }
            
            i++;
        }

        return col;
    }

    public Integer podaAlphaBetaMegaMax(Tauler t, int columna, int color, Integer alpha, Integer beta, int profundidad){
        return 1;
    }


}
