package edu.epsevg.prop.lab.c4;

/*                 DOCUMENTACION                           */
public class ConectaBroIO implements Jugador, IAuto {
    private int profunditat;
    
    public ConectaBroIO(int profunditat) {
        this.profunditat = profunditat;
    }

    /**
     * @return
     */
    @Override
    public String nom() {
        return "Bro";
    }

    /**
     * @param t Tauler actual de joc
     * @param color Color de la peca que possara
     * @return
     */
    @Override
    public int moviment(Tauler t, int color) {
        int Valor = Integer.MIN_VALUE, col = 0;
        for(int i=0; i < t.getMida(); i++){
            Tauler aux = new Tauler(t);
            if(aux.movpossible(i)){
                aux.afegeix(i, color);
                if(aux.solucio(i, color)) return i;
                
                // MINIMAX
                int x = minimitzar(aux, Integer.MIN_VALUE, Integer.MAX_VALUE, profunditat-1, color);
                if(x > Valor || !t.movpossible(col)){
                    col = i;
                    Valor = x;
                }               
            }
        }
        if(!t.movpossible(col)){
            System.out.println("SIIIIIIII");
            System.out.println("COL -> " + col);
        }
        return col;
    }


    /**
     * Minimitza el valor de la funcio alfabeta
     * @param t
     * @param alfa
     * @param beta
     * @param profunditat
     * @param jugador
     * @return el valor corresponent a minimitzar
     */
    private int minimitzar(Tauler t, int alfa, int beta, int profunditat, int jugador){
        if(profunditat == 0 || !t.espotmoure()) return Eval(t, jugador);
        for(int i = 0; i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler(t);
                aux.afegeix(i, -1*(jugador));
                if (aux.solucio(i, -1*(jugador))) {
                    return Integer.MIN_VALUE;
                }
                beta = Math.min(beta, maximitzar(aux, alfa, beta, profunditat-1, jugador));
                if(beta <= alfa) return beta;
            }
        }
        return beta;
    }

    /**
     * Maximitza el valor de la funcio alfabeta
     * @param t
     * @param alfa
     * @param beta
     * @param profunditat
     * @param jugador
     * @return el valor corresponent a minimitzar
     */
    private int maximitzar(Tauler t, int alfa, int beta, int profunditat, int jugador){
        if(profunditat == 0 || !t.espotmoure()) return Eval(t,jugador);
        for(int i = 0 ;i < t.getMida(); i++){
            if(t.movpossible(i)){
                Tauler aux = new Tauler (t);
                aux.afegeix(i, jugador);
                if(aux.solucio(i,jugador)) {
                    return Integer.MAX_VALUE;
                }
                alfa = Math.max(alfa, minimitzar(aux, alfa, beta, profunditat - 1, jugador));
                if(alfa >= beta) return alfa;
            }
        }
        return alfa;
    }

    /**
     * Evalua cada posicio del tauler i computa una heuristica
     * @param t
     * @param jugador
     * @return la heuristica
     */
    private int Eval(Tauler t, int jugador) {
        int h = 0;
        for (int i = 0; i < t.getMida(); i++) {
            for (int j = 0; j < t.getMida(); j++) {
                h += heuristica(t, i, j, jugador);
            }
        }

        return h;
    }

    /**
     * Puntua la casella del tauler
     * @param t
     * @param fil
     * @param col
     * @param jug
     * @return la puntuacio de la casella
     */
    private int heuristica(Tauler t, int fila, int col, int color) {
        // Mira en un rango de 3.
        int heu = 0;
        int mida = t.getMida();
        int colorCas = t.getColor(fila, col);
        
        // Vertical
        int nostresVertical = 1;
        int x1 = 0;
        for (int i = fila+1; i <= mida-1 && i < fila+4; i++) {
            if(t.getColor(i, col) == colorCas) nostresVertical++;
            else if(t.getColor(i, col) == 0) x1 = vacioVertical(t, i, col, nostresVertical);
        }
        heu += notaCasilla(nostresVertical, colorCas==color?1:-1, x1);
        
        
        // Horizontal
        int nostresHorizontal = 1;
        int x2 = 0;
        for (int i = col+1; i <= mida-1 && i < col+4; i++) {
            if(t.getColor(fila, i) == colorCas) nostresHorizontal++;
            else if(t.getColor(fila, i) == 0) x2 = vacioHorizontal(t, fila, i, nostresHorizontal);
        }
        heu += notaCasilla(nostresHorizontal, colorCas==color?1:-1, x2);

        
        // Diagonal subiendo por la izquierda en escalera
        int nostresDiagonalIzq = 1;
        int x3 = 0;
        int i = fila+1;
        int j = col-1;
        while(i <= mida-1 && j >= 0){
            if(t.getColor(i, j) == colorCas) nostresDiagonalIzq += 1;
            else if(t.getColor(i, j) == 0) x3 = vacioDiagonalDerecha(t, i, j, nostresDiagonalIzq);
            i += 1;
            j -= 1;
        }
        heu += notaCasilla(nostresDiagonalIzq, colorCas==color?1:-1, x3);
        
        
        // Diagonal subiendo por la derecha en escalera
        int nostresDiagonalDer = 1;
        int x4 = 0;
        i = fila+1;
        j = col+1;
        while(i <= mida-1 && j <= mida-1){
            if(t.getColor(i, j) == colorCas) nostresDiagonalDer += 1;
            else if(t.getColor(i, j) == 0) x4 = vacioDiagonalIzq(t, i, j, nostresDiagonalDer);
            i += 1;
            j += 1;
        }
        heu += notaCasilla(nostresDiagonalDer, colorCas==color?1:-1, x4);
        
        
        return heu;

    }

    int vacioHorizontal(Tauler t, int fila, int col, int found){
        int cuantosVacios = 0;
        int z = 4 - found;
        int i = 0;
        while(i < z && col <= t.getMida()-1){
            if(t.getColor(fila, col) == 0) cuantosVacios += 1;
            else break;
            i++;
            col += 1;
        }
        return cuantosVacios;
    }
    
    int vacioVertical(Tauler t, int fila, int col, int found){
        int cuantosVacios = 0;
        int z = 4 - found;
        
        int i = 0;
        while(i < z && fila <= t.getMida()-1){
            if(t.getColor(fila, col) == 0) cuantosVacios += 1;
            else break;
            i++;
            fila+=1;
        }
        
        return cuantosVacios;
    }    
    
    int vacioDiagonalDerecha(Tauler t, int fila, int col, int found){
        int cuantosVacios = 0;
        int z = 4 - found;
        
        int i = 0;
        while(i < z && fila <= t.getMida()-1 && col <= t.getMida()-1){
            if(t.getColor(fila++, col++) == 0) cuantosVacios += 1;
            else break;
            i++;
        }
        
        return cuantosVacios;
    }
    
    int vacioDiagonalIzq(Tauler t, int fila, int col, int found){
        int cuantosVacios = 0;
        int z = 4 - found;
        
        int i = 0;
        while(i < z && fila <= t.getMida()-1 && col >= 0){
            if(t.getColor(fila++, col--) == 0) cuantosVacios += 1;
            else break;
            i++;
        }
        
        return cuantosVacios;
    }
    
    /**
     * Calcula el comput de la casella tenint en compte les fitxes que te i les que queden per completar el 4 en ratlla
     * @param puntuacio
     * @param moviments
     * @return la puntuacio de la casella
     */
    int notaCasilla(int puntuacio, int x, int vacias){
        int p = 4 - vacias;
        if(puntuacio==0) return 0;
        else if(puntuacio==1) return x*1*p;
        else if(puntuacio==2) return x*10*p;
        else if(puntuacio==3) return x*100*p;
        else return x*1000*p;
    }
}