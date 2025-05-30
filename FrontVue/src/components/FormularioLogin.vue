<script setup>
import router from '@/router'
import { useLoginStore } from '@/stores/login'
import { ref, computed } from 'vue'

    const usuario = ref('')
    const pass = ref('')
    const show1 = ref(false)
    const botonLogin = computed(()=> !usuario.value || !pass.value)
    const mnsjError = ref("")
    let cargando = ref(false)
    const switchLogin = ref(true)

    const ruleUsuario = [
        value => {
            if (value) return true
            return 'Correo Obilgatorio'
        },
    ]

    const rulePass = [
        value => {
            if (value) return true
            return 'Contraseña obligatoria'
        },
    ]


    async function logear(usuario, pass){
        const login = useLoginStore()
        cargando.value = true

        try {
            await login.logear(usuario,pass)
            if(login.login){
                console.log("Redirigiendo a /about")
                await router.push("/inicio")
                console.log("Redirección completada")
            }else{
                console.log("Usuario o contraseña erroneos")
                mnsjError.value = "Usuario o contraseña erroneos"
                cargando.value = false
            }
        } catch (error) {
            console.error("Error en login:", error)
            mnsjError.value = "Usuario o contraseña erroneo"
            cargando.value = false
        }
    }
    async function logearAdmin(usuario, pass){
        const login = useLoginStore()
        cargando.value = true

        try {
            await login.logearAdmin(usuario,pass)
            if(login.login && login.admin){
                console.log("Redirigiendo a /tablaJugadores")
                await router.push("/tablaJugadores") 
                console.log("Redirección completada")
            }else{
                console.log("Usuario o contraseña erroneos")
                mnsjError.value = "Usuario o contraseña erroneos"
                cargando.value = false
            }
        } catch (error) {
            console.error("Error en login:", error)
            mnsjError.value = "Usuario o contraseña erroneo"
            cargando.value = false
        }
    }

</script>

<template>
    <div class="contenedorForm">

        <img src="/public/Logotipos/logo.gif" alt="">

        <v-form @submit.prevent>
            <v-text-field
            v-model="usuario"
            :rules="ruleUsuario"
            required
            type="text"
            label="Usuario*"
             v-if="!cargando"
            ></v-text-field>

            <v-text-field
            v-model="pass"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="rulePass"
            :type="show1 ? 'text' : 'password'"
            @click:append="show1 = !show1"
            required
            label="Contraseña*"
             v-if="!cargando"
            ></v-text-field>

            <v-btn class="mt-2 light-green-lighten-4" type="submit" block @click="logear(usuario, pass)" :disabled="botonLogin"  v-if="!cargando && switchLogin">Iniciar sesión</v-btn>
            <v-btn class="mt-2 light-green-lighten-4" type="submit" block @click="logearAdmin(usuario, pass)" :disabled="botonLogin"  v-if="!cargando && !switchLogin">Iniciar sesión administrador</v-btn>
        </v-form>
        <span class="loader espacioLoader" v-if="cargando"></span>

        <RouterLink to="/registro">Registrate</RouterLink>
        <!-- <RouterLink to="/proyectos">¿Te has olvidado de la contraseña?</RouterLink> -->

        <v-switch
            v-model="switchLogin"
            :label="`Cambiar Login`"
            hide-details
            inset
            color="yellow"
        ></v-switch>
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

.espacioLoader{
    margin-top: 20px;
    margin-bottom: 20px;
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
