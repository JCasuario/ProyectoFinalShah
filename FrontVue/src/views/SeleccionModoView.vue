<script setup>

import { ref,onMounted,computed } from 'vue';
import { useDatosStore } from '@/stores/datos';


const datosStore = useDatosStore()

onMounted(async () => {


});


</script>

<template>
  <div class="about">

    <RouterLink class="navLink pirata" to="/vsMaquina">      
        <div class="contenedorModo">
            <img src="/public/ModosJuego/Contra la maquina.jpg" alt="contraLaMaquina">
            <h3 class="pirata">Contra la maquina</h3>
        </div>
    </RouterLink>

    <RouterLink class="navLink pirata" to="/localGame">     
        <div class="contenedorModo">
            <img src="/public/ModosJuego/Local.jpg" alt="local">
            <h3 class="pirata">Modo local</h3>
        </div>
    </RouterLink>

  </div>
</template>

<style scoped>

.v-expansion-panels{
  width: 60%;
}

.about {
  padding-top:15px;
  padding-bottom:15px;
  min-height: 91vh;
  min-width:93vw;
  gap: 20px;
  background-color: #ffffff8f;
  border-radius: 20px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

img{
  border-radius: 10px;
  max-width: 25vw;
}

.contenedorModo{
  display: flex;
  flex-direction: column;
  background-color: #E2E271;
  border-radius: 15px;
  justify-content: center;
  align-items: center;
  transition: transform 0.4s;
}

.contenedorModo:hover{
    transform: scale(1.1);
}

@media (max-width: 1000px) {
  img{
    max-width: 45vw;
  }
}

</style>
