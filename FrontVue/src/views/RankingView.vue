<script setup>

import { ref,onMounted,computed } from 'vue';
import { useDatosStore } from '@/stores/datos';
import TablaRanked from '@/components/TablaRanked.vue';



</script>

<template>
  <div class="about">
    <TablaRanked></TablaRanked>
  </div>
</template>

<style scoped>

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
}



</style>
