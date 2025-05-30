<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { ref,onMounted,computed } from 'vue'
import { useLoginStore } from './stores/login'
import { useUsuarioStore } from './stores/usuario'
import { useJuegoStore } from './stores/juego'
import { useDatosStore } from '@/stores/datos';
import { useRouter, useRoute } from 'vue-router';
import { watch } from 'vue';


const route = useRoute();
const router = useRouter();
const loginStore = useLoginStore()
const juego = useJuegoStore();
const login = computed(()=> loginStore.login)
const jugando = computed(()=> loginStore.jugando)
const datosStore = useDatosStore()


const userStore = useUsuarioStore()
const userCorreo = computed(()=>userStore.email)
const userUsuario = computed(()=>userStore.usuario)
const mensajes = computed(()=> userStore.mensajes)
const userImagen = computed(()=>userStore.imagen)
const articulosAdquiridos = computed(()=>datosStore.articulosAdquiridos)
const chatActual = ref(null)
const mensajeChat = ref("")

const mensajesFiltrados = computed(() =>
  mensajes.value.filter(
    msg =>
      (msg.senderId === chatActual.value && msg.receiverId === userUsuario.value) ||
      (msg.senderId === userUsuario.value && msg.receiverId === chatActual.value)
  )
)

function activarChat(amigo){ 
  chatActual.value  = amigo.usuario_amigo
  console.log(`Charlando con ${chatActual.value}`)
}

function enviarMensaje() {
  if (!mensajeChat.value.trim() || !chatActual.value) return

  userStore.enviarMensaje(chatActual.value, mensajeChat.value)
  mensajeChat.value = ''
}

function cambiarImagenPerfil(imagen){
  userStore.setImagen(imagen)
}

function enviarInvitacion(retado) {
  userStore.enviarReto(retado.usuario_amigo)
}

watch( () => route.path, async(newPath) => {
  if(newPath == "/inicio"){
    await datosStore.getAmigos();
    await datosStore.getArticulosAdquiridos();
    console.log(articulosAdquiridos.value)
  }
});

watch(()=>datosStore.amigos, async ()=>{
  await datosStore.getAmigos();
})

function resetearJuego(){
  juego.resetearJuego()
}

function cerrarSesion(){
  userStore.cerrarSesion()
  loginStore.login = false
  loginStore.setAdmin(false)
  router.push('/login')
}

</script>

<template>

  <div v-if="login===false">
    <RouterView/>
  </div>

  <div v-if="login===true && !loginStore.admin" class="cuerpo">
    <v-card class="menu-lateral">
      <v-layout>
        <v-navigation-drawer
          expand-on-hover
          rail
        >
          <v-list>
            <v-list-item
              :prepend-avatar="userImagen"
              :subtitle="userCorreo"
              :title="userUsuario"
            ></v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list density="compact" nav>

            <v-list-item prepend-icon="mdi-heart">
              <v-expansion-panels>
                <v-expansion-panel>

                  <v-expansion-panel-title > Amigos </v-expansion-panel-title>
                  
                  <v-expansion-panel-text>
                    <div v-for="amigo in datosStore.amigos" class="amigo" :key="amigo.usuario_amigo" :data-id="amigo.usuario_amigo">

                      <p>{{ amigo.usuario_amigo }}</p>
                      <v-btn icon="mdi-chat" class="botonPerfil" @click="activarChat(amigo)"></v-btn>
                      <v-btn icon="mdi-shield-sword" class="botonPerfil" @click="enviarInvitacion(amigo)"></v-btn>
                      
                    </div>
                  </v-expansion-panel-text>

                </v-expansion-panel>
              </v-expansion-panels>
            </v-list-item>

            <v-list-item prepend-icon="mdi-chat" v-if="chatActual !=null">
              <v-expansion-panels>
                <v-expansion-panel>

                  <v-expansion-panel-title > Chat </v-expansion-panel-title>
                  
                  <v-expansion-panel-text>
                    <div>
                      <h3 class="pirata">{{ chatActual }}</h3>
                      <div class="chatBox">
                        <p
                        v-for="(mensaje, index) in mensajesFiltrados"
                        :key="index"
                        :class="{ propio: mensaje.senderId === userUsuario, otro: mensaje.senderId !== userUsuario}"
                        >
                          <strong>{{ mensaje.senderId }}:</strong> {{ mensaje.content }}
                        </p>
                      </div>
                      <v-text-field v-model="mensajeChat"></v-text-field>
                      <v-btn @click="enviarMensaje"><v-icon icon="mdi-arrow-right"></v-icon></v-btn>
                    </div>
                  </v-expansion-panel-text>

                </v-expansion-panel>
              </v-expansion-panels>

              
            </v-list-item>

            <v-list-item prepend-icon="mdi-camera">
              <v-expansion-panels>
                <v-expansion-panel>

                  <v-expansion-panel-title > Foto Perfil </v-expansion-panel-title>
                  
                  <v-expansion-panel-text class="contenedorFotosPerfil">
                    <img src="/Perfil/oso.png" alt="" class="fPerfil" @click="cambiarImagenPerfil('/Perfil/oso.png')">
                    <img src="/Perfil/zorro.png" alt="" class="fPerfil" @click="cambiarImagenPerfil('/Perfil/zorro.png')">
                    <img src="/Perfil/pajaro.png" alt="" class="fPerfil" @click="cambiarImagenPerfil('/Perfil/pajaro.png')">
                    <template v-for="articulo in articulosAdquiridos">
                      <img :src="articulo.imagenArticulo" alt="fPerfil"  class="fPerfil" @click="cambiarImagenPerfil(`${articulo.imagenArticulo}`)">
                    </template>
                  </v-expansion-panel-text>

                </v-expansion-panel>
              </v-expansion-panels>

              
            </v-list-item>

            <v-list-item prepend-icon="mdi-exit-to-app">
              <v-btn @click="cerrarSesion">Cerrar Sesión</v-btn>
            </v-list-item>
              
          </v-list>

        </v-navigation-drawer>

        <v-main style="height: 250px"></v-main>
      </v-layout>
    </v-card>
    

    <div class="pagina">
      <nav class="navegador" v-if="jugando===false">
        
          <v-btn :class="{'rounded-pill': true}">
            <RouterLink class="navLink pirata" to="/inicio">NOTICIAS</RouterLink>
          </v-btn>
          <v-btn :class="{'rounded-pill': true}">
            <RouterLink class="navLink pirata" to="/tienda">TIENDA</RouterLink>
          </v-btn>
          <v-btn :class="{'rounded-pill': true}">
            <RouterLink class="navLink pirata" to="/ranking">RANKING</RouterLink>
          </v-btn>
          <v-btn :class="{'rounded-pill': true}" @click="resetearJuego">
            <RouterLink class="navLink pirata" to="/jugar">JUGAR</RouterLink>
          </v-btn>
        
      </nav>
      
      <RouterView/>
    </div>
  </div>

  <div v-if="login===true && loginStore.admin" class="cuerpoAdmin">
    <RouterView/>
    <v-btn @click="cerrarSesion">Cerrar Sesión</v-btn>
  </div>

</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

img{
    max-width: 100px;
    margin-bottom: 15px;
}

.cuerpo{
  width: 100%;
  display: grid;
  grid-template-columns: 0.1fr 2fr;
  align-items: center;
}

.cuerpoAdmin{
  width: 100%;
  display: flex;
  flex-direction: column;
}

.pagina{
  display: flex;
  flex-direction: column;
  gap: 2vh;
  padding-left: 3.7vw;
}

.navegador{
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
  gap: 3vh;
  margin-top: 10px;
}

.navegador button{
  min-width: 15vw;
  min-height: 5vh;
  z-index: 1;
  border: 4px solid #31333F;
  background-color: #E2E271;
  padding-bottom: 42px;
}


.menu-lateral{
  z-index: 20;
}

.navLink{
  font-weight: bolder;
  font-size: xx-large;
}

.activoBoton{
  background-color: #31333F !important;
  color: transparent !important;
}

nav a.router-link-exact-active {
  color: #E2E271;
}

.fPerfil{
  max-width: 5vw;
  max-height: 5vw;
  border-radius: 10px;
  border: 1px solid #31333F;
}

/* Para el botón activo */
.v-btn:has(.router-link-exact-active) {
  background-color: #31333F !important;
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid white;
  font-size: medium;
}

nav a:first-of-type {
  border: 0;
}

.amigo{
  display: flex;
  justify-content: space-around;
  padding: 8px;
  align-items: center;
  border-top:1px solid #31333f8b ;
}

.botonPerfil{
  max-width:  2vw;
  max-height: 2vw;
}

.propio {
  background-color: #dcf8c6;
  align-self: flex-end;
  padding: 5px 10px;
  border-radius: 10px;
}

.otro {
  background-color: #f1f0f0;
  align-self: flex-start;
  padding: 5px 10px;
  border-radius: 10px;
}

.chatBox{
  display: flex;
  flex-direction: column;
  gap: 3px;
  height: 250px;
  width: 10vw;
  background-color: #e2e27192;
  overflow: scroll;
}

.contenedorFotosPerfil{
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 2px;
}

.admin{
  font-size: 80px;
  background-color: white;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  
}

@media (max-width: 1000px) {
  .navegador{
    gap: 0.5vh;
  }

  .fPerfil{
    max-width: 15vw;
    max-height: 15vw;
  }

  .chatBox{
    width: 30vw;
  }
}

@media (hover: hover) {

  .navegador button:hover{
    transform: scale(1.15);
    background-color:#E2E271;
  }

}

</style>
