<script setup>

import Tablero from '@/components/Tablero.vue';
import AnimacionEntrada from '@/components/AnimacionEntrada.vue';
import { useJuegoStore } from "@/stores/juego";
import { useLoginStore } from '@/stores/login';
import { computed, onMounted, ref, watch } from "vue";
import { useUsuarioStore } from '@/stores/usuario'

const loginStore = useLoginStore()
const jugando = computed(()=> loginStore.jugando)

const juego = useJuegoStore();
const turno = computed(()=>juego.turno == "blancas" ? "rojas" : "verdes")
const colorFondo = computed(()=> juego.turno == "blancas" ? "blanca" : "negra")
const colorTipo = computed(()=> juego.turno == "blancas" ? "tNegra" : "tBlanca")
const userStore = useUsuarioStore()
const userUsuario = computed(()=>userStore.usuario)
const puntosBlanca = computed(()=>juego.puntosBlanca)
const puntosNegra = computed(()=>juego.puntosNegra)

onMounted(()=>{
    loginStore.changeJugando()
})


watch(() => juego.finPartida, () => {
  
    loginStore.changeJugando()

  });

</script>

<template>
    <AnimacionEntrada v-if="!jugando"></AnimacionEntrada>
    <div :class="[`about`, colorFondo]">
        <p :class="['titulito','pirata', colorTipo]" v-if="jugando">Turno <span>{{ turno }}</span></p>
        <p :class="['titulito','pirata', colorTipo]" v-if="!jugando">FIN DE LA PARTIDA</p>
        <div class="contenedorPartida">

            <Tablero></Tablero>

            <div>

                <v-card
                    class="mx-auto my-8 tarjetaSombra"
                    elevation="16"
                    max-width="344"
                >
                    <v-card-item>
                        <v-card-title :class="['pirata']">
                            Jugador rojo
                        </v-card-title>

                        <v-card-subtitle :class="['pirata']">
                            {{ userUsuario }}
                        </v-card-subtitle>
                    </v-card-item>

                    <v-card-text :class="['pirata']">
                        Puntos: {{ puntosBlanca }}
                    </v-card-text>
                </v-card>

                <v-card
                    class="mx-auto my-8 tarjetaSombra"
                    elevation="16"
                    max-width="344"
                >
                    <v-card-item>
                        <v-card-title :class="['pirata']">
                            Jugador verde
                        </v-card-title>

                        <v-card-subtitle :class="['pirata']">
                            Invitado
                        </v-card-subtitle>
                    </v-card-item>

                    <v-card-text :class="['pirata']">
                        Puntos: {{ puntosNegra }}
                    </v-card-text>
                </v-card>


            </div>
            

        </div>
    </div>
</template>

<style scoped>
.about {
  padding-top:15px;
  min-height: 91vh;
  min-width:93vw;
  gap: 20px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.titulito{
    font-size: 3vw;
}

.tarjetaSombra{
    box-shadow: #E2E271 0px 0px 5px;
}

.contenedorPartida{
    display: flex;
    flex-direction: row;
    gap: 3vw;
    align-items: center;
}

.blanca{
    animation: colorBlanco 0.5s linear forwards;
    background-image: url("/public/Fondos/FondoNegro.png");
    background-size: 60%;
    background-repeat: repeat;
}

.negra{
    animation: colorNegro 0.5s linear forwards;
    background-image: url("/public/Fondos/FondoBlanco.png");
    background-size: 60%;
    background-repeat: repeat;
}

.tBlanca{
    color: #00B281;
    text-shadow: 2px 2px 4px #31333F;
}

.tNegra{
    color: #F98397;
    text-shadow: 2px 2px 4px white;
}

@keyframes colorBlanco {
    from{
        background-color: #31333F;
    }
    to {
        background-color: white;
    }
}

@keyframes colorNegro {
    from{
        background-color: white;
    }
    to {
        background-color: #31333F;
    }
}

@media (max-width: 1024px) {

    .contenedorPartida{
        flex-direction: column;
    }

    .titulito{
        font-size: 5vw;
    }

  
}



</style>
