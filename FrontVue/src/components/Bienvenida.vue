<script setup>
import { ref, computed } from 'vue'
import { useUsuarioStore } from '@/stores/usuario'

const userStore = useUsuarioStore()
const userName = computed(()=>userStore.nombre)

</script>

<template>
    <div class="contenedorBienvenida">

        <h2>Bienvenido {{userName}}!</h2>
    
    </div>
</template>

<style scoped>
    .contenedorBienvenida{
        padding: 10px;
        background-color: white;
        border-radius: 10px;
    }
</style>
