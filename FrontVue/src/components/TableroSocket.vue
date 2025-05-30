<script setup>
import { useJuegoStore } from "@/stores/juego";
import { computed, ref } from "vue";

const juego = useJuegoStore();
const tablero = computed(() => juego.tablero);
const nFicha = computed(() => juego.tablero?.length || 0);
let estadoPerspectiva = ref("noPerspectiva");
const seleccion = ref(null);
juego.crearTablero(5);

const movimientosPosibles = computed(() => {
  if (!seleccion.value) return [];
  return juego.chequearMovimiento(seleccion.value);
});



// Cuando el usuario inicia la partida o inicia sesión:
function mover([fromFila, fromCol], [toFila, toCol]) {
  const mov = juego.moverPieza([fromFila, fromCol], [toFila, toCol]);
  if (mov) {
    juego.enviarMovimiento([fromFila, fromCol], [toFila, toCol]);
  }
}

function seleccionarCelda(i, j) {
  if (!seleccion.value) {
    // Solo selecciona si hay una pieza del turno actual
    const pieza = tablero.value[i][j];
    if (
      (juego.turno === 'blancas' && (pieza === 1 || pieza === 2 || pieza === 11 || pieza === 12 || pieza === 5 || pieza === 15)) ||
      (juego.turno === 'negras' && (pieza === 3 || pieza === 4 || pieza === 13 || pieza === 14 || pieza === 6 || pieza === 16))
    ) {
      seleccion.value = [i, j];
    }
  } else {
    const esValido = movimientosPosibles.value.some(([x, y]) => x === i && y === j);
    if (esValido) {
      mover(seleccion.value, [i, j]);
    }
    seleccion.value = null;
  }
}

function esMovible(i, j) {
  return movimientosPosibles.value.some(([x, y]) => x === i && y === j);
}

function claseFicha(valor) {
  switch (valor) {
    case 1: return 'alfil';
    case 2: return 'torre';
    case 3: return 'alfilNegro';
    case 4: return 'torreNegro';
    case 5: return 'peon';
    case 6: return 'peonNegro';
    case 11: return 'alfil-evolucionado';
    case 12: return 'torre-evolucionado';
    case 13: return 'alfil-evolucionadoNegro';
    case 14: return 'torre-evolucionadoNegro';
    case 15: return 'peon-evolucionado';
    case 16: return 'peon-evolucionadoNegro';
    case 9: return 'conquistable';
    default: return '';
  }
}

function clasePerspectiva() {
  const nuevaClase =
    estadoPerspectiva.value === 'noPerspectiva' ? 'perspectiva' : 'noPerspectiva';

  estadoPerspectiva.value = ''; // quitar clase para forzar reanimación
  setTimeout(() => {
    estadoPerspectiva.value = nuevaClase;
  }, 10); // delay mínimo
}

function clasePerspectiva2() {
  const nuevaClase =
    estadoPerspectiva.value === 'noPerspectiva' ? 'perspectiva2' : 'noPerspectiva';

  estadoPerspectiva.value = ''; // quitar clase para forzar reanimación
  setTimeout(() => {
    estadoPerspectiva.value = nuevaClase;
  }, 10); // delay mínimo
}

</script>

<template>
    <v-btn @click = clasePerspectiva() class="rounded-pill bontonPerspectiva"> <v-icon>mdi-eye</v-icon> </v-btn>
    <v-btn @click = clasePerspectiva2() class="rounded-pill bontonPerspectiva2"> <v-icon>mdi-eye</v-icon> </v-btn>
  <div :class="['grid', estadoPerspectiva]" :style="{ gridTemplateColumns: `repeat(${nFicha}, 1fr)` }">
    <div
      v-for="(fila, i) in tablero"
      :key="i"
    >
      <div
        v-for="(celda, j) in fila"
        :key="j"
        @click="seleccionarCelda(i, j)"
        :class="[
            'celda',
            claseFicha(celda),
            seleccion?.[0] === i && seleccion?.[1] === j ? 'seleccionada' : '',
            esMovible(i, j) ? (tablero[i][j] !== 0 ? 'atacable' : 'movible') : ''
        ]"
      >

      </div>
    </div>
  </div>
</template>

<style scoped>
.grid {
  display: grid;
  gap: 2px;
  
}

.perspectiva{
    animation: perspectivaEntrada 0.5s linear forwards;
    /* transform: rotateY(30deg) rotateX(70deg) rotateZ(80deg); */
}

.perspectiva2{
    animation: perspectivaEntrada2 0.5s linear forwards;
    /* transform: rotateY(30deg) rotateX(70deg) rotateZ(80deg); */
}

.noPerspectiva{
    animation: perspectivaSalida 1.5 linear backwards;
    /* transform: rotateY(0deg) rotateX(0deg) rotateZ(0deg); */
}

.celda {
  width: 7vw;
  height: 7vw;
  background-color: #eee;
  border: 4px solid #E2E271;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 5px;
}

.movible{
    background-color: #E2E271;
}

.atacable{
    background-color: rgb(255, 158, 158) !important;
}

.alfil{
    background-image: url("/personajes/pajaroR.png");
    background-size: cover;
    transform-style: preserve-3d;
    transition: background-color 0.4s;
}

.alfilNegro{
    background-image: url("/personajes/parajoV.png");
    background-size: cover;
    transform-style: preserve-3d;
    transition: background-color 0.4s;
}

.alfil-evolucionado{
    background-image: url("/personajes/pajaroHR.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.alfil-evolucionadoNegro{
    background-image: url("/personajes/parajaroHV.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.torre{
    background-image: url("/personajes/zorroR.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.torreNegro{
    background-image: url("/personajes/zorroV.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.torre-evolucionado{
    background-image: url("/personajes/zorroHR.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.torre-evolucionadoNegro{
    background-image: url("/personajes/zorroHV.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.peon{
  background-image: url("/personajes/osoR.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.peonNegro{
  background-image: url("/personajes/osoV.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.peon-evolucionado{
  background-image: url("/personajes/osoHR.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.peon-evolucionadoNegro{
  background-image: url("/personajes/osoHV.png");
    background-size: cover;
    transition: background-color 0.4s;
}

.alfil:hover, .alfil-evolucionado:hover, .torre:hover, .torre-evolucionado:hover, .alfilNegro:hover, .alfil-evolucionadoNegro:hover, .torreNegro:hover, .torre-evolucionadoNegro:hover, .peon:hover, .peonNegro:hover, .peon-evolucionado:hover, .peon-evolucionadoNegro:hover{
    background-color: rgb(233, 255, 134);
    transform: scale(1.2);
}

.alfil, .alfil-evolucionado, .torre, .torre-evolucionado, .alfilNegro, .alfil-evolucionadoNegro, .torreNegro, .torre-evolucionadoNegro, .peon, .peonNegro, .peon-evolucionado, .peon-evolucionadoNegro{
    background-color: white;
    animation: respirar 0.5s linear infinite alternate;
}

.seleccionada {
  border: 4px solid #4CAF50;
}

.conquistable{
  animation: girar 5s linear infinite alternate;
}

.bontonPerspectiva{
    background-color: #E2E271;
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 1000;
}

.bontonPerspectiva2{
    background-color: #E2E271;
    position: fixed;
    bottom: 20px;
    left: 20px;
    z-index: 1000;
}

/* ANIMACIONES */
@keyframes perspectivaEntrada {
  to {
    transform: rotateY(30deg) rotateX(70deg) rotateZ(80deg);
  }
}

@keyframes perspectivaEntrada2 {
  to {
    transform: rotateY(-20deg) rotateX(70deg) rotateZ(-70deg);
  }
}

@keyframes respirar {
  from {
    transform: scale(1);
  }
  to {
    transform: scale(1.02);
  }
}

@keyframes girar {
  from {
    transform: rotatey(0deg);
  }
  to {
    transform: rotatey(360deg);
  }
}

@media (max-width: 1024px) {

    .celda{
        height: 15vw;
        width: 15vw;
    }

    .bontonPerspectiva{

    top: 120px;
    right: 20px;

  }
  
}
</style>


