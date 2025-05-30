<script setup>

import { ref,onMounted,computed } from 'vue';
import { useDatosStore } from '@/stores/datos';
import Noticia from '@/components/Noticia.vue';


const datosStore = useDatosStore()
let carga = ref(false)

onMounted(async () => {
  await datosStore.getNoticias();

  carga.value = true

});


</script>

<template>
  <div class="about">
    <div class="contenedorFor" v-if="carga==true" v-for="noticia in datosStore.noticias">
      <Noticia :imagen="noticia.imagenNoticia" :tit="noticia.titulo" :contenido="noticia.noticia"></Noticia>
    </div>
    <div v-else>
      <span class="loader"></span>
    </div>
  </div>
</template>

<style scoped>

.v-expansion-panels{
  width: 60%;
}

.about {
  padding-top:15px;
  min-height: 91vh;
  min-width:93vw;
  gap: 20px;
  background-color: #ffffff8f;
  border-radius: 20px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
  margin-bottom: 10px;
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
