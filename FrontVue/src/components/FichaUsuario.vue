<script setup>
import { ref, computed } from 'vue'
import { useUsuarioStore } from '@/stores/usuario'

const userStore = useUsuarioStore()
const userName = computed(()=>userStore.nombre)
const userApellido1 = computed(()=>userStore.apellido1)
const userApellido2 = computed(()=>userStore.apellido2)
const userEmail = computed(()=>userStore.email)
const userPuesto = computed(()=>userStore.puesto)
const puestoFormateado = userPuesto.value=="DVLP" ? "Desarrollador" : "Recursos Humanos"

</script>

<template>
    <div class="contenedorFicha">

        <h3>Usuario</h3>
        <p>{{ userName }}</p>
        <p>{{ userApellido1 }}</p>
        <p>{{ userApellido2 }}</p>
        <p>{{ userEmail }}</p>
        <p>{{ userPuesto }}</p>
        <p>{{ puestoFormateado}}</p>
    
    </div>
</template>

<style scoped>
    .contenedorFicha{
        padding: 10px;
        background-color: white;
        border-radius: 10px;
    }
</style>
