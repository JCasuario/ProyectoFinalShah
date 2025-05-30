<!-- Componente Vue 3 + Vuetify para gestionar jugadores -->
<template>
  <v-sheet border rounded>
    <v-data-table
      :headers="headers"
      :hide-default-footer="jugadores.length < 8"
      :items="jugadores"
      class="custom-header-table"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>
            <v-icon color="medium-emphasis" icon="mdi-account" size="x-small" start></v-icon>
            Jugadores
          </v-toolbar-title>
        </v-toolbar>
        <v-toolbar flat style="padding: 5px; display: flex; align-items: center;">
          <v-text-field v-model="busqueda" label="Busqueda por Usuario" @input="busquedaUsuario" style="flex-grow: 0; width: 350px;"></v-text-field>
          <v-btn
            class="me-2"
            prepend-icon="mdi-refresh"
            rounded="lg"
            text="Limpiar"
            border
            @click="limpiar"
            style="margin-left: 15px;"
          ></v-btn>
          <v-btn
            class="me-2"
            prepend-icon="mdi-plus"
            rounded="lg"
            text="Nuevo Jugador"
            border
            @click="add"
          ></v-btn>
        </v-toolbar>
      </template>

      <template v-slot:item.usuario="{ value }">
        <v-chip :text="value" border="thin opacity-25" prepend-icon="mdi-account" label></v-chip>
      </template>

      <template v-slot:item.actions="{ item }">
        <div class="d-flex ga-2 justify-end">
          <v-icon color="medium-emphasis" icon="mdi-pencil" size="small" @click="edit(item.id)"></v-icon>
          <v-icon color="medium-emphasis" icon="mdi-delete" size="small" @click="remove(item.id)"></v-icon>
        </div>
      </template>

      <template v-slot:no-data>
        <v-btn
          prepend-icon="mdi-backup-restore"
          rounded="lg"
          text="Reset data"
          variant="text"
          border
          @click="reset"
        ></v-btn>
      </template>
    </v-data-table>
  </v-sheet>

  <v-dialog v-model="dialog" max-width="500">
    <v-card
      :subtitle="`${isEditing ? 'Editar' : 'Añadir'} un Jugador`"
      :title="`${isEditing ? 'Editar' : 'Añadir'} un Jugador`"
    >
      <template v-slot:text>
        <v-row>
          <v-col cols="12">
            <v-text-field v-model="record.usuario" label="Usuario*" :rules="ruleBasic"></v-text-field>
          </v-col>

          <v-col cols="12">
            <v-text-field v-model="record.correo" label="Correo*" :rules="[...ruleBasic, ruleEmail]"></v-text-field>
          </v-col>

          <v-col cols="12" v-if="!isEditing">
            <v-text-field v-model="record.contrasena" label="Contraseña*" :rules="ruleBasic"></v-text-field>
          </v-col>

          <v-col cols="12" md="4" v-if="isEditing">
            <v-text-field v-model.number="record.ranking" label="Ranking" type="number" min="0"></v-text-field>
          </v-col>

          <v-col cols="12" md="4" v-if="isEditing"> 
            <v-text-field v-model.number="record.nivel" label="Nivel" type="number" min="0"></v-text-field>
          </v-col>

          <v-col cols="12" md="4" v-if="isEditing">
            <v-text-field v-model.number="record.monedas" label="Monedas" type="number" min="0"></v-text-field>
          </v-col>

          <v-col cols="12" v-if="isEditing">
            <v-textarea v-model="record.imagen" label="Imagen (Base64)"></v-textarea>
          </v-col>
        </v-row>
      </template>

      <v-divider></v-divider>

      <v-card-actions class="bg-surface-light">
        <v-btn text="Cancelar" variant="plain" @click="dialog = false"></v-btn>
        <v-spacer></v-spacer>
        <v-btn text="Guardar" @click="save" :disabled="botonGuardar"></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import Swal from 'sweetalert2'
import { useDatosStore } from '@/stores/datos'
import { useLoginStore } from '@/stores/login'

const DEFAULT_RECORD = {
  id: null,
  correo: '',
  usuario: '',
  contrasena: '',
  ranking: 0,
  nivel: 0,
  monedas: 0,
  imagen: '',
}

const datosStore = useDatosStore()

const record = ref({ ...DEFAULT_RECORD })
const dialog = ref(false)
const isEditing = ref(false)
const busqueda = ref('')
const jugadores = ref([])


const botonGuardar = computed(() => {
  return !record.value.usuario || !record.value.correo || (!isEditing.value && !record.value.contrasena)
})

const headers = [
  { title: 'Usuario', key: 'usuario', align: 'start' },
  { title: 'Correo', key: 'correo', align: 'center' },
  { title: 'Ranking', key: 'ranking', align: 'center' },
  { title: 'Nivel', key: 'nivel', align: 'center' },
  { title: 'Monedas', key: 'monedas', align: 'center' },
  { title: 'Actions', key: 'actions', align: 'center', sortable: false },
]

// Reglas de validación
const ruleBasic = [v => !!v || 'Campo obligatorio']
const ruleEmail = v => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v) || 'Email inválido'

onMounted(async () => {
  await datosStore.getJugadoresRanking()
  jugadores.value = datosStore.jugadoresRanking
})

function add() {
  isEditing.value = false
  record.value = {
    id: null,
    correo: '',
    usuario: '',
    contrasena: '',
    ranking: 0,
    nivel: 0,
    monedas: 0,
    imagen: '',
  }
  dialog.value = true
}

function edit(id) {
  isEditing.value = true
  const jugador = jugadores.value.find(j => j.id === id)
  if (jugador) {
    record.value = { ...jugador, contrasena: '' }
    dialog.value = true
  }
}

async function remove(id) {
  const result = await Swal.fire({
    title: '¿Estás seguro?',
    text: 'Se eliminará el jugador de forma definitiva.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar',
  })
  if (result.isConfirmed) {
    try {
      await datosStore.eliminarJugador(id)
      await datosStore.getJugadoresRanking()
      Swal.fire('Eliminado', 'El jugador ha sido eliminado.', 'success')
    } catch (err) {
      if (err.response?.status === 409) {
        Swal.fire({
          icon: 'error',
          title: 'No se puede eliminar',
          text: err.response.data
        });
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error inesperado',
          text: 'Jugador con amigos, no rompas una bonita relación'
        });
      }
    }
  }
}


async function save() {
  try {
    if (isEditing.value) {
      try {
        await datosStore.editarJugador(record.value.id, record.value)
        Swal.fire('Editado', 'Jugador actualizado correctamente.', 'success')
      } catch (err) {
        const message =
          err.response?.status === 409
            ? 'Correo o usuario ya registrados en la base de datos'
            : 'Ocurrió un error inesperado al editar'

        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: message,
        })
        dialog.value = false
        return
      }
    } else {
      const login = useLoginStore()
      try {
        const respuesta = await login.registrarTabla(
          record.value.correo,
          record.value.usuario,
          record.value.contrasena
        )

        if (respuesta) {
          Swal.fire('Creado', 'Jugador creado correctamente.', 'success')
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Correo o usuario ya registrados en la base de datos',
          })
        }

        dialog.value = false
      } catch (err) {
        const message =
          err.response?.status === 409
            ? 'Correo o usuario ya registrados en la base de datos'
            : 'Ocurrió un error inesperado al registrar'

        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: message,
        })
        dialog.value = false
        return
      }
    }

    dialog.value = false
    await datosStore.getJugadoresRanking()
    jugadores.value = datosStore.jugadoresRanking
  } catch (error) {
    Swal.fire('Error', `No se pudo guardar: ${error.message}`, 'error')
  }
}

async function reset() {
  dialog.value = false
  record.value = { ...DEFAULT_RECORD }
  datosStore.getJugadoresRanking()
  const data = datosStore.jugadoresRanking
  jugadores.value = data || []
}

async function limpiar() {
  busqueda.value = ''
  await reset()
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
</script>

<style scoped>
.custom-header-table :deep(th) {
  background-color: #d4f1ff;
  font-weight: bold;
  color: #026FC1;
  font-size: 1rem;
}

</style>