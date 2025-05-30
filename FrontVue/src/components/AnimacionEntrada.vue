<script setup>
import { useJuegoStore } from '@/stores/juego'
import { computed, onMounted, ref } from 'vue'

const animar = ref(false)
const juegoStore = useJuegoStore()
const ganador = computed(()=>juegoStore.ganador)

onMounted(() => {
  // Dispara la animación tras montarse el componente
  animar.value = true
})
</script>

<template>
  <div :class="['ContenedorAnimacion', { activo: animar }]">
    <img src="/Animación/AnimaciónEntradaSalida.gif" alt="">
    <h3 class="pirata">GANADOR {{ ganador }}</h3>
    <h3 class="pirata"></h3>
  </div>
</template>

<style scoped>
.ContenedorAnimacion {
  position: fixed;
  height: 25vh;
  width: 100vw;
  background-color: #E2E271;
  top: 50vh;
  right: -100vw;
  z-index: 1001;
  transition: right 1s ease-in-out;
  display: flex;
  justify-content: center;
  align-items: center;
  /* flex-direction: column; */
}

/* Se aplica cuando se activa la animación */
.ContenedorAnimacion.activo {
  right: 0;
}

img {
  max-height: 25vh;
}
</style>