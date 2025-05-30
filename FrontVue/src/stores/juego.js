import { defineStore } from 'pinia';
import { computed, ref, watch } from 'vue';

export const useJuegoStore = defineStore("juego", ()=>{
    let tablero = ref([])
    let turno= ref('blancas')
    let puntosBlanca = ref(0)
    let puntosNegra = ref(0)
    const finPartida = computed(() => puntosBlanca.value >= 2 || puntosNegra.value >= 2)
    const ganador = ref("")
    const nFicha = ref(0)
    let logPartida = [];
    let conquistadoCentro = ref(false);

    watch(finPartida, (nuevoValor) => {
    if (nuevoValor) {
        if (puntosBlanca.value >= 2) {
            ganador.value = "jugador rojo";
            
        }
        if (puntosNegra.value >= 2) {
            ganador.value = "jugador verde"
        };
    }
    });

    function crearTablero(x){
        nFicha.value = x
        const nuevoTablero = []

        for(let i = 0; i < x; ++i){
            const fila = []
            for(let j = 0; j < x; ++j){
                switch (true) {
                    case (i==0 && j==0):
                        
                        fila.push(1)
                        break;

                    case (i==0 && j==1):

                        fila.push(2)
                        break;

                    case (i==0 && j==2):

                        fila.push(5)
                        break;

                    case(i==2 && j==2):

                        fila.push(9)
                        break;

                    case (i==(x-1) && j==(x-2)):

                        fila.push(4)
                        break;

                    case (i==(x-1) && j==(x-1)):

                        fila.push(3)
                        break;

                    case (i==(x-1) && j==(x-3)):

                        fila.push(6)
                        break;
                
                    default:
                        fila.push(0)
                        break;
                }
            }
            nuevoTablero.push(fila)
        }
        tablero.value = nuevoTablero;
    }

    function chequearMovimiento([fila, col]) {
        const pieza = tablero.value[fila][col];
        if (!pieza) return [];

        const movimientos = [];

        const color = pieza === 1 || pieza === 2 || pieza === 11 || pieza === 12 || pieza === 5 || pieza === 15? 'blancas' : 'negras';
        const esRival = (dest) => {
            if (color === 'blancas') return dest === 3 || dest === 4 || dest === 13 || dest === 14 || dest === 6 || dest === 16;
            if (color === 'negras') return dest === 1 || dest === 2 || dest === 11 || dest === 12 || dest === 5 || dest === 15;
            return false;
        };

        const alfilDirs = [[-1, -1], [-1, 1], [1, -1], [1, 1]];
        const torreDirs = [[-1, 0], [1, 0], [0, -1], [0, 1]];
        const reyDirs = [[-1, 0], [1, 0], [0, -1], [0, 1],[-1, -1], [-1, 1], [1, -1], [1, 1]];
        const piezaEvolucionada = [[-1, 0], [1, 0], [0, -1], [0, 1],[-1, -1], [-1, 1], [1, -1], [1, 1]];

        const dirs = [];
        if (pieza === 1 || pieza === 3) dirs.push(...alfilDirs);
        if (pieza === 2 || pieza === 4) dirs.push(...torreDirs);
        if (pieza === 11 || pieza === 12 || pieza === 13 || pieza === 14 || pieza === 15 || pieza === 16) dirs.push(...piezaEvolucionada);

        for (const [df, dc] of dirs) {
            let r = fila + df;
            let c = col + dc;

            while (enLimite(r, c)) {
            const destino = tablero.value[r][c];

            if (destino === 0 || destino === 9) {
                movimientos.push([r, c]); // libre
            } else if (esRival(destino)) {
                movimientos.push([r, c]); // atacable
                break;
            } else {
                break; // aliado o cualquier otro bloqueo
            }

            r += df;
            c += dc;
            }
        }

        //Fuera del while para el calculo de movimientos ya que es una pieza estilo rey y solo va a avanzar una en cada dir
        if (pieza === 5 || pieza === 6) {
            for (const [df, dc] of reyDirs) {
                let r = fila + df;
                let c = col + dc;

                if (!enLimite(r, c)) continue;

                const destino = tablero.value[r][c];

                if (destino === 0 || destino === 9 || esRival(destino)) {
                    movimientos.push([r, c]);
                }
            }
        }

        return movimientos;
    }

    function atacar([fila, col]) {
      tablero.value[fila][col] = 0;

      if(turno.value == "blancas"){
        puntosBlanca.value ++;
      }else{
        puntosNegra.value ++;
      }
    }

    function enLimite(fila, col) {
        return fila >= 0 && col >= 0 && fila < nFicha.value && col < nFicha.value;
    }

    function cambiarTurno(){
        if(turno.value =="blancas"){
            turno.value = "negras"
        }else{
            turno.value = "blancas"
        }
    }

    function moverPieza([fromFila, fromCol], [toFila, toCol]) {
        let piezaMovimiento = tablero.value[fromFila][fromCol];
        const destino = tablero.value[toFila][toCol];

        // Validar si la jugada está permitida por las reglas de la pieza
        const movimientosValidos = chequearMovimiento([fromFila, fromCol]);
        const movimientoPermitido = movimientosValidos.some(
            ([f, c]) => f === toFila && c === toCol
        );

        if (!movimientoPermitido) return false;

        // Bloquear mover sobre una ficha aliada
        if (
            (turno.value === "blancas" && (destino === 1 || destino === 2 || destino === 11 || destino === 12 || destino === 5 || destino === 15)) ||
            (turno.value === "negras" && (destino === 3 || destino === 4 ||destino === 13 || destino === 14 || destino === 6 || destino === 16))
        ) {
            return false;
        }

        // Atacar ficha enemiga si corresponde
        if (
            (turno.value === "blancas" && (destino === 3 || destino === 4 ||destino === 13 || destino === 14 || destino === 6 || destino === 16)) ||
            (turno.value === "negras" && (destino === 1 || destino === 2 || destino === 11 || destino === 12 || destino === 5 || destino === 15))
        ) {
            atacar([toFila, toCol]);
            if(piezaMovimiento < 10){ piezaMovimiento += 10;}
        }

        // Conquistar el centro
        if (toFila == 2 && toCol == 2 && !conquistadoCentro.value) {
            if (turno.value == "blancas") {
                puntosBlanca.value++;
            } else {
                puntosNegra.value++;
            }
            conquistadoCentro.value = true;
        }

        tablero.value[toFila][toCol] = piezaMovimiento;
        tablero.value[fromFila][fromCol] = 0;

        logPartida.push(`Origen: ${fromFila}-${fromCol} Destino: ${toFila}-${toCol}`)
        cambiarTurno();
        return true;
    }

    function resetearJuego(){
        puntosBlanca.value = 0
        puntosNegra.value = 0
        turno.value = "blancas"
        conquistadoCentro.value  = false
        ganador.value = ""
        logPartida = []
    }

    function aplicarMovimientoExterno(from, to) {
        // Esto es igual que moverPieza, pero sin validar turno ni emitir socket
        const piezaMovimiento = tablero.value[from[0]][from[1]];
        tablero.value[to[0]][to[1]] = piezaMovimiento;
        tablero.value[from[0]][from[1]] = 0;
        cambiarTurno();
    }


    return {tablero, 
            turno, 
            ganador, 
            puntosBlanca, 
            finPartida, 
            puntosNegra, 
            enLimite, 
            chequearMovimiento, 
            atacar, 
            crearTablero, 
            cambiarTurno, 
            moverPieza, 
            resetearJuego,
            aplicarMovimientoExterno
        }
}) 