<script setup>

import { ref,onMounted,computed } from 'vue';
import { useDatosStore } from '@/stores/datos';
import Tienda from '@/components/Tienda.vue';
import { useUsuarioStore } from '@/stores/usuario';


const datosStore = useDatosStore()
const userStore = useUsuarioStore()

const userMonedas = computed(()=>userStore.monedas)


</script>

<template>
  <div class="about">
    <h3 class="pirata">Monedero: {{ userMonedas }}</h3>
    <Tienda></Tienda>
  </div>
</template>

<style scoped>

.v-expansion-panels{
  width: 60%;
}

.about {
  padding-top:5px;
  min-height: 91vh;
  min-width:93vw;
  background-color: #ffffff8f;
  border-radius: 20px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  /* align-items: center; */
  justify-content: center;
  margin-bottom: 10px;
  padding-top: 15px;
}

img{
  border-radius: 10px;
  max-width: 1250px;
}

.logo{
  max-width: 450px;
}

.contenedorNoticias{
  display: flex;
}


</style>
