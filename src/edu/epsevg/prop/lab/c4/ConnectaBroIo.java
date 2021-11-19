package edu.epsevg.prop.lab.c4;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class ConnectaBroIo implements Jugador, IAuto {
    private String nom;
    private int profundidad;
    private static final Integer Alpha = Integer.MIN_VALUE; // -infinito
    private static final Integer Beta = Integer.MAX_VALUE; // +infinito
    
    public ConnectaBroIo(int profundidad){
        nom = "ConnectaBroIo";
        this.profundidad = profundidad;
    }

    public String nom(){
        return nom;
    }
  
    
    public int moviment(Tauler t, int color){
        Integer valor = Integer.MIN_VALUE;
        int millorMoviment = 0;
        
        int i = 0;
        boolean solution = false;
        while(i < t.getMida() && !solution){
            Tauler aux = new Tauler(t);
            if(aux.movpossible(i)){ // Miramos si se puede hacer el movimiento
                aux.afegeix(i, color);
                if(aux.solucio(i, color)){
                    solution = true;
                    millorMoviment = i;
                } else {
                    // El primer turno es del enemigo -> -color
                    Integer min = MinValor(aux, i, -color, Alpha, Beta, profundidad-1);
                    System.out.println("MIN -> " + min);
                    if(valor < min){
                        millorMoviment = i;
                        valor = min;
                    }
                } 
            }

            i++;
        }
        System.out.println("COL -> " + millorMoviment);
        System.out.println("VALOR -> " + valor);
        return millorMoviment;
    }
    /*
    public int moviment2(Tauler t, int color){
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
                if(aux.solucio(i, color)){
                    solution = true;
                    col = i;
                } else {
                    // Llamar a minimax, donde yo maximizo y el otro minimza
                    // -color porque yo entro con el color del adversario
                    // Yo entro maximizando, el adversario minimiza por eso -podaAlphaBetaMegaMax
                    Integer min = MinValor(aux, i, -color, Alpha, Beta, profundidad);
                    if(min > Alpha){
                        Alpha = min;
                        col = i;
                    }
                } 
            }
            
            i++;
        }
        
        return Alpha;
    }
    */
    /**
     *
     * @param t
     * @param columna
     * @param color
     * @param alpha
     * @param beta
     * @param profundidad
     * @return
     */
    /*
    public Integer podaAlphaBetaMegaMax(Tauler t, int columna, int color, Integer alpha, Integer beta, int profundidad){
        // Amenaza t.solucio(columna, -color)
        if(profundidad == 0 || t.solucio(columna, -color) || !t.espotmoure()){
            // Calculamos la heuristica
            alpha = Eval(t, color);
        }

        int i = 0;
        //boolean solution = false; ???????
        boolean cut = false;
        while(i < t.getMida() && !cut){
            Tauler aux = new Tauler(t);
            if(aux.movpossible(i)){
                alpha = Math.max(alpha, -podaAlphaBetaMegaMax(aux, i, -color, -alpha, -beta, profundidad-1));
            }
            // Cortamos ramas
            if(alpha >= beta) cut = true;

            i++;
        }   
        return alpha;
    }
    */
    
    public Integer MaxValor(Tauler t, int columna, int color, Integer alpha, Integer beta, int profundidad) {
        if(profundidad == 0 || esTerminal(t, columna, color)){
            // Calculamos la heuristica
            return Eval(t, color);
        }
        
        Integer valor = Integer.MIN_VALUE; // -inf
        for(int i=0; i < t.getMida(); i++){
            Tauler newTauler = new Tauler(t);
            if(newTauler.movpossible(i)){
                newTauler.afegeix(i, color);
                // TODO: Solución?? Devolver maxInf
                if(t.solucio(i, color)){
                    return Integer.MAX_VALUE;
                }
                // TODO: profundidad -1???
                valor = Math.max(valor, MinValor(newTauler, i, color, alpha, beta, profundidad-1));
            }
            // Cortamos ramas
            if(beta <= valor) return valor;
            alpha = Math.max(valor, alpha);
        }
        
        return valor;
    }

    public Integer MinValor(Tauler t, int columna, int color, Integer alpha, Integer beta, int profundidad) {
        if(profundidad == 0 || esTerminal(t, columna, color)){
            // Calculamos la heuristica
            return Eval(t, color);
        }
        
        Integer valor = Integer.MAX_VALUE; // +inf
        for(int i=0; i < t.getMida(); i++){
            Tauler newTauler = new Tauler(t);
            if(newTauler.movpossible(i)){
                newTauler.afegeix(i, color);
                // TODO: Solución?? Devolver minInf
                if(t.solucio(i, color)){
                    return Integer.MIN_VALUE;
                }
                // Es mi turno, me toca maximizar -> -color
                valor = Math.min(alpha, MaxValor(newTauler, i, -color, alpha, beta, profundidad-1));
            }
            // Cortamos ramas
            if(valor <= alpha) return valor;
            beta = Math.min(valor, alpha);
        }
        
        return valor;
    }
    
    /**
     * Función para calcular si el tablero ha llegado a una posible solución o si el tablero está lleno.
     * @param t
     * @param columna
     * @param color
     * @return 
     */
    public boolean esTerminal(Tauler t, int columna, int color){
        boolean res = false;
        if(t.solucio(columna, color)){
            res = true;
        }
        if(!t.espotmoure()) res = true;
        return res;
    }
    
    /**
     * Función para calcular la heurísitca
     * @param t
     * @param color
     * @return 
     */
    public Integer Eval(Tauler t, int color){
        Integer h = 0;
        
        // Mirar vertical
        
        // Mirar horizontal
        
        // Mirar diagonal
        
        return h;
    }

}
