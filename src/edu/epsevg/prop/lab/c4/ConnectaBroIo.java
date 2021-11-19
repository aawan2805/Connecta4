package edu.epsevg.prop.lab.c4;

import java.util.HashMap;

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
                // El primer turno es del enemigo -> -color
                Integer min = MinValor(aux, i, -color, Alpha, Beta, profundidad-1);
                System.out.println("MIN -> " + min);
                if(valor < min){
                    millorMoviment = i;
                    valor = min;
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
        return this.heuristica(t, color);
        
        // Mirar vertical
        // Mirar horizontal
        
        // Mirar diagonal
    }
    
    /*
    public Integer calculaEval(Integer numFichasPropias, Integer numFichasEnemigas) {
        Integer heur = 100;
        if (numFichasPropias == 1) heur*=numFichasPropias;
        else if (numFichasPropias == 2) heur*=numFichasPropias;
        else if (numFichasPropias == 3) heur*=numFichasPropias;
        else if (numFichasPropias == 4) heur*=numFichasPropias;
        if (numFichasEnemigas == 1) heur-=50;
        else if (numFichasPropias == 2) heur-=75;
        else if (numFichasPropias == 3) heur-=100;
        else if (numFichasPropias == 4) heur-=(heur*2);
        
        return heur;
    }
    */
    
    public Integer calculaEval (HashMap<Integer, Integer> agrupacionesPropias, HashMap<Integer, Integer> agrupacionesEnemigas){
        Integer heur = (100*agrupacionesPropias.get(1)) + (100*agrupacionesPropias.get(2)) + (100*agrupacionesPropias.get(3)) + (2000*agrupacionesPropias.get(4) -
                (50*agrupacionesEnemigas.get(1)) - (75*agrupacionesEnemigas.get(2)) - (100*agrupacionesEnemigas.get(3)) - (2000*agrupacionesEnemigas.get(4)));
        return heur;
    }
    
    public HashMap<Integer, Integer> CuentaAgrupaciones(Tauler t, int color){
        HashMap<Integer, Integer> res = new HashMap<>();
        res.put(1, 0);
        res.put(2, 0);
        res.put(3, 0);
        res.put(4, 0);
        
        int columnaVertical = 0;
        
        for (int i = 0; i < t.getMida(); i++) {
            for (int j = 0; j < t.getMida(); j++) {
                if(t.getColor(i, j) != 0){
                    if(t.getColor(i, j) == color) {
                        
                        
                        int x = 1;
                        // Vertical
                        if((i+1) <= t.getMida() && t.getColor(i+1, j) == color){
                            x += 1;
                            if((i+2) <= t.getMida() && t.getColor(i+2, j) == color){
                                x += 1;
                                if((i+3) <= t.getMida() && t.getColor(i+3, j) == color){
                                    x += 1;
                                }
                            }
                        }
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        System.out.println("Alineadas -> " + x);
                    }
                }
            }
        }
        
        return res;
    }
    
    
  private Integer heuristica (Tauler t, int color){
    Integer heu = 0;
    int colmax= t.getMida();
    int num = 0;
    for (int fila=0; fila<colmax; ++fila) {
      //LLAMAR A LA VERTICAL Y USAR LA FILA COMO SI FUERA UNA COLUMNA
        heu=HeuristicaRecorregutVertical(t, fila,color,heu);
        for (int col = 0; col <colmax; ++col) {
          //Per cada posició ens interessa mirar totes les possiblitats, amunt, avall, dreta, esquerra i les 4 diagonals
          num=Recorregut(t, fila, col, t.getColor(fila, col), "DiagonalEsquerreAvall");
          heu=calculaheuristica(t, heu, num, fila, col, color);
          num=Recorregut(t, fila, col, t.getColor(fila, col), "DiagonalEsquerreAmunt");
          heu=calculaheuristica(t, heu, num, fila, col, color);
          num=Recorregut(t, fila, col, t.getColor(fila, col), "DiagonalDretaAvall");
          heu=calculaheuristica(t, heu, num, fila, col, color);
          num=Recorregut(t, fila, col, t.getColor(fila, col), "DiagonalDretaAmunt");
          heu=calculaheuristica(t, heu, num, fila, col, color);
          num=Recorregut(t, fila, col, t.getColor(fila, col), "HoritzonalEsquerra");
          heu=calculaheuristica(t, heu, num, fila, col, color);
          num=Recorregut(t, fila, col, t.getColor(fila, col), "HoritzonalDreta");
          heu=calculaheuristica(t, heu, num, fila, col, color);
        }
    }
    return heu;
  }

  private Integer calculaheuristica (Tauler t, Integer heuactual, int quantesnostres, int fila, int col, int color){
    //Si el color es el nostre serà 1 pel que serà positiu i se sumarà a la heuristicaactua
    //Si el color es el de l'enemic serà -1 i per tant al ser un número negatiu es restarà a la heuristica
    //Formula=2nostres+20*3nostres+1000*4nostres - (2seves+20*3seves+1000*4seves)
    if (quantesnostres == 2) {
        heuactual = heuactual + 300*t.getColor(fila, col) * color;
    }
    else if (quantesnostres == 3) {
        heuactual = heuactual + 500 * t.getColor(fila, col) * color;
    }
    else if (quantesnostres >= 4) {
        heuactual = heuactual + 100000 * t.getColor(fila, col) * color;
    }
    return heuactual;
  }

  private Integer Recorregut(Tauler t, int f, int c, int color, String TipusMoviment) {
      int colmax= t.getMida();
      Integer nostres = 0;
      int contador = 0;
      int col = c;
      int fila = f;
      boolean target = false;
      String DiagonalEsquerreAvall ="DiagonalEsquerreAvall";
      String DiagonalEsquerreAmunt ="DiagonalEsquerreAmunt";
      String DiagonalDretaAvall ="DiagonalDretaAvall";
      String DiagonalDretaAmunt ="DiagonalDretaAmunt";
      String HoritzonalEsquerra ="HoritzonalEsquerra";
      String HoritzonalDreta ="HoritzonalDreta";
      //Comprobar que la posició que volem mirar està dintre del tauler, forma part de les 4 que volem mirar i no ha incomplert cap restriccio
      //Restriccions: només pot haver-hi un mateix color o buits, si està buit la de sota no pot estar buida
      while (col >= 0 && col < colmax && fila >= 0 && fila < colmax && !target && contador < 4) {
          int coloract=t.getColor(fila, col);
          if ((coloract != color) && (coloract!=0)) {//si  donde estamos es del rival, nos salimos
              target = true;
          }
          else if (fila > 0 && coloract==0 && t.getColor(fila - 1, col) == 0) {//mira si hay alguna ficha debajo, es para las diagonales y horizontales, si no hay nada salimos
              target = true;
          }
          else if (coloract == color) {
              ++nostres;
          }
          ++contador;
          if (DiagonalEsquerreAvall.equals(TipusMoviment)){
            col=col-1;
            fila=fila-1;
          }
          else if (DiagonalEsquerreAmunt.equals(TipusMoviment)){
            col=col-1;
            fila=fila+1;
          }
          else if (DiagonalDretaAvall.equals(TipusMoviment)){
            col=col+1;
            fila=fila-1;
          }
          else if (DiagonalDretaAmunt.equals(TipusMoviment)){
            col=col+1;
            fila=fila+1;
          }
          else if (HoritzonalEsquerra.equals(TipusMoviment)){
            col=col-1;
          }
          else if (HoritzonalDreta.equals(TipusMoviment)){
            col=col+1;
          }
          else{
            //dades incorrectes
            target=true;
            break;
          }
      }
      if (target==true && contador!=4) {
        //No s'ha complert una restriccio o ha quedat fora del tauler
          nostres = 0;
      }
      return nostres;
  }

  private Integer HeuristicaRecorregutVertical(Tauler t, int c, int color, Integer heur){
    int coloract=0; //color de la primera posició
    int seguides=0; //quantes seguides tenim?
    int fil=0; //fila per saber el color que mirem
    for (int fila=t.getMida()-1; fila>=0; --fila){
      int colorpos=t.getColor(fila, c);
      if(t.getColor(fila, c)!=0){ //si no es 0 començem a comptar
        if(seguides==0){  //inicialitzem valors
          coloract=colorpos;
          fil=fila;   //posteriorment per la heuristica
          ++seguides;
        }
        else if (colorpos!=coloract){
          heur=calculaheuristica(t, heur, seguides, fil, c, color);
          return heur;
        }
        else{
          ++seguides;
        }
      }
      if(seguides>=4){
        heur=calculaheuristica(t, heur, seguides, fil, c, color);

        return heur;
      }
    }
    return heur;
  }

}
