<script setup>
import router from '@/router'
import { useLoginStore } from '@/stores/login'
import { ref, computed } from 'vue'
import Swal from 'sweetalert2'

    const usuario = ref('')
    const pass = ref('')
    const email = ref('')
    const passRepetida = ref('')
    const show1 = ref(false)
    const botonLogin = computed(()=> !usuario.value || !pass.value)
    const mnsjError = ref("")

    const ruleUsuario = [
        value => !!value || 'Usuario obligatorio',
        value => (value || '').length >= 3 || 'Mínimo 3 caracteres'
    ];

    const ruleCorreo = [
        value => !!value || 'Correo obligatorio',
        value => /.+@.+\..+/.test(value) || 'Correo debe ser válido'
    ];

    const rulePass = [
        value => !!value || 'Contraseña obligatoria',
        value => (value || '').length >= 6 || 'Mínimo 6 caracteres'
    ];

    const rulePassRepetida = [
        value => {
            if (value) return true
            return 'Contraseña obligatoria'
        },
        value=>{
            if(value == pass.value) return true
            return 'La contraseña es distinta'
        }
    ]


    async function registrar(){

        const login = useLoginStore()

        try {
            const respuesta = await login.registrar(email.value, usuario.value, pass.value)
            if(respuesta){
                console.log(respuesta)
                const result = await Swal.fire({
                    title: 'Registro realizado',
                    icon: 'success',
                    confirmButtonText: 'Al loby!',
                    confirmButtonColor: '#E2E271',
                })

                if(result.isConfirmed){

                    await router.push("/inicio") // Añade awat

                }

            }else{
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Correo o usuario ya registrados en la base de datos"
                });
            }
        } catch (error) {
            console.error("Error en registro:", error)
        }
    }

</script>

<template>
    <div class="contenedorForm">

        <img src="/public/Logotipos/logo.gif" alt="">

        <h3 class="pirata">Registro</h3>

        <v-form @submit.prevent>
            <v-text-field
            v-model="usuario"
            :rules="ruleUsuario"
            required
            type="text"
            label="Usuario*"
            ></v-text-field>
 
            <v-text-field
            v-model="email"
            :rules="ruleCorreo"
            required
            type="email"
            label="Correo*"
            ></v-text-field>

            <v-text-field
            v-model="pass"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="rulePass"
            :type="show1 ? 'text' : 'password'"
            @click:append="show1 = !show1"
            required
            label="Contraseña*"
            ></v-text-field>

            <v-text-field
            v-model="passRepetida"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="rulePassRepetida"
            :type="show1 ? 'text' : 'password'"
            @click:append="show1 = !show1"
            required
            label="Repite contraseña*"
            ></v-text-field>

            <v-btn class="mt-2 light-green-lighten-4" type="submit" block @click="registrar(usuario, pass)" :disabled="!usuario || !email || !pass || !passRepetida || pass !== passRepetida || !/.+@.+\..+/.test(email)">Registrarse!</v-btn>
        </v-form>

        <RouterLink to="/login">Volver Login</RouterLink>

        <p v-if="mnsjError" class="msnjError">{{ mnsjError }}</p>
    
    </div>
</template>

<style scoped>
.contenedorForm{
    padding: 30px;
    background-color: #F3F7FB;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

form {
    min-width: 320px;
    margin-bottom: 10px;
}

img{
    max-width: 320px;
    margin-bottom: 15px;
}

.msnjError{
    margin-top: 10px;
    color: red;
    font-weight: bold;
}

.v-btn{
    background-color: #E2E271;
    border-radius: 12px;
}

@media (hover: hover) {
  a:hover {
    background-color:#E2E271;
    border-radius: 10px;
    transform: scale(1.05);
    font-weight: bold;
  }
}
</style>
