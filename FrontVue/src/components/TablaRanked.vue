<template>
    <v-sheet border rounded>
      <v-data-table
        :headers="headers"
        :hide-default-footer="jugadores.length < 2"
        :items="jugadores"
        class="custom-header-table"
      >
        <template v-slot:top>

          <v-toolbar flat>
            <v-toolbar-title>
              <v-icon  icon="mdi-alien" size="x-small" start></v-icon>
                RANKING JUGADORES
            </v-toolbar-title>            
          </v-toolbar>

          <v-toolbar flat style="padding: 5px; display: flex; align-items: center;">
            
              <v-text-field v-model="busqueda" label="Busqueda por Usuario" @blur="busquedaUsuario" style="flex-grow: 0; width: 350px;"></v-text-field>
              <v-btn @click=reset class="pirata reset-btn">Reset</v-btn>

          </v-toolbar>

        </template>

        <template v-slot:item.acciones="{ item }">
          <v-btn 
            class="amarillo" 
            size="small"
            :disabled="esAmigo(item.usuario)"
            @click="añadirAmigo(item.id)"
          >
            <v-icon icon="mdi-plus"></v-icon>
          </v-btn>
        </template>

        <template v-slot:item.imagen="{ item }">
          <img :src="item.imagen" alt="perfilJugador" class="perfilJugador">
        </template>

      </v-data-table>
    </v-sheet>
  
  </template>

<script setup>
import { onMounted, ref} from 'vue'
import { useDatosStore } from '@/stores/datos';
import { useUsuarioStore } from '@/stores/usuario';
import Swal from 'sweetalert2'

const datosStore = useDatosStore()
const userStore = useUsuarioStore()

//Registro actual y referencias al estado de la edición
const busqueda = ref('')

//Datos de los jugadores
const jugadores = ref([])

//Const amigos
const amigos = ref([])

//Esto van a ser las cabeceras de la tabla
const headers = [
  {title: 'Perfil', key:'imagen'},
  { title: 'Ranking', key: 'ranking' },
  { title: 'Usuario', key: 'usuario', align: 'start' },
  { title: 'Nivel', key: 'nivel' },
  { title: 'Monedas', key: 'monedas'},
  { title: 'Añadir amigo', key: 'acciones', align: 'center', sortable: false },
]


//FUNCIONES
onMounted(async () => {
  await datosStore.getJugadoresRanking();
  await datosStore.getAmigos();
  jugadores.value = datosStore.jugadoresRanking

  amigos.value = await datosStore.amigos
})

//Añadir amigos
async function añadirAmigo(idAmigo){
  const respuesta = datosStore.guardarAmigos(idAmigo);
  console.log(`idAmigo: ${idAmigo}`)
  if(respuesta){

    Swal.fire({
      icon: "success",
      title: "Nuevo amigo",
      text: "Se ha guardado el nuevo amigo"
    });

    await datosStore.getAmigos();
    amigos.value = datosStore.amigos; // Actualiza el botón de agregar
    reset();

  }else{

    Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "No se ha podido guardar el amigo"
    });

  }
}

//Carga los trabajadores que cumplen el parametro de busqueda
function busquedaUsuario(){

  console.log("Comienza busqueda de jugadores por usuario")
  //Compruebo que no este vacio
  if(busqueda.value=='') {
    reset()
    return
  } 

    try{
    const terminoBusqueda = busqueda.value.toLowerCase();
    const jugadoresFiltrados = datosStore.jugadoresRanking.filter(jugador => 
      jugador.usuario.toLowerCase().includes(terminoBusqueda)
    );
    
    jugadores.value = jugadoresFiltrados;
  
      }catch(error){
  
        console.log(error)
  
    }
}

function reset(){
    busqueda.value=''
    jugadores.value = datosStore.jugadoresRanking
}

function esAmigo(usuario) {
  if(usuario == userStore.usuario) return true
  return amigos.value.some(amigo => amigo.usuario_amigo === usuario);
}


</script>

<style scoped>
.amarillo{
  background-color: #E2E271;
}

.custom-header-table :deep(th) {
  background-color: #E2E271;
  font-weight: bold;
  color: #31333F;
  font-size: 1rem;
}

.reset-btn{
  min-width: 15vw;
  min-height: 5vh;
  z-index: 1;
  border: 4px solid #31333F;
  background-color: #E2E271;
  border-radius: 40px;
  margin-bottom: 12px;
  margin-left: 4px;
}

.v-table{
  width: 90vw;
}

.perfilJugador{
  max-width: 50px;
  border-radius: 10px;
}

</style>