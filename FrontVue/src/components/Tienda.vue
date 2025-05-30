<script setup>
  import { useDatosStore } from '@/stores/datos';
import { useUsuarioStore } from '@/stores/usuario';
  import { computed, onMounted, ref } from 'vue'

  const tab = ref(null)
  const datos = useDatosStore();
  const user = useUsuarioStore();
  const articulos = ref([])

  onMounted(async () => {
      await datos.getArticulos()
      await datos.getArticulosAdquiridos() 
      articulos.value = datos.articulos
      
    })

  const articulosFiltrados = computed(() => {
    return articulos.value.filter(a => a.tipo === tab.value && a.id)
  })

  async function comprar(id, precio){
    if (yaAdquirido(id)) {
      console.warn('Ya lo tienes');
      return;
    }
    await datos.comprarArticulo(id)
    datos.actualizarMonedas(-precio)
    await datos.getArticulosAdquiridos() 
  }
 
  function mostrarMomia() {
    console.log("Mostrar momia")
  }

  function yaAdquirido(id) {
    return datos.articulosAdquiridos?.some(a => a.idArticulo === id)
  }

  console.log("Mostrando momia")

</script>

<template>
  <v-card class="tienda">
    <v-tabs v-model="tab" align-tabs="center">
      <v-tab :value="'perfil'">Perfil</v-tab>
      <v-tab :value="'skin'">Skin</v-tab>
    </v-tabs>

    <v-tabs-window v-model="tab">
      <v-tabs-window-item :value="'perfil'">
        <v-container fluid>
          <v-row>
            <v-col
              v-for="articulo in articulosFiltrados"
              :key="articulo.id"
              cols="12"
              md="4"
            >
              <div :class="{ comprado: yaAdquirido(articulo.id) }" @click="mostrarMomia">
                <v-img
                  :src="articulo.imagen"
                  height="205"
                  cover
                ></v-img>
                <v-divider></v-divider>
                <h3 class="pirata">{{ articulo.nombre }}</h3>
                <p>{{ articulo.precio }}</p>
                <v-btn class="pirata" disabled v-if="yaAdquirido(articulo.id)">
                  Ya adquirido
                </v-btn>
                <v-btn class="pirata" disabled v-else-if="user.monedas < articulo.precio">
                  No tienes el dinero
                </v-btn>
                <v-btn class="pirata" @click="comprar(articulo.id, articulo.precio)" v-else>
                  Comprar
                </v-btn>
              </div>
            </v-col>

          </v-row>
        </v-container>
      </v-tabs-window-item>

      <v-tabs-window-item :value="'skin'">
        <v-container fluid>
          <v-row>
            <v-col
              v-for="articulo in articulosFiltrados"
              :key="articulo.id"
              cols="12"
              md="4"
            >
              <div class="articulo">
                <v-img
                  :src="articulo.imagen"
                  height="205"
                  cover
                ></v-img>
                <v-divider></v-divider>
                <h3 class="pirata">{{ articulo.nombre }}</h3>
                <p>{{ articulo.precio }}</p>
              </div>
            </v-col>
          </v-row>
        </v-container>
      </v-tabs-window-item>
    </v-tabs-window>
    <v-col v-if="articulosFiltrados.length == 0" class="noDisponible">
    <div class="contenedorDisponibilidad">
      <img src="/public/Noticias/imagen1.jpg" alt="noDisponible">
      <h3 class="pirata">No disponible de momento</h3>
    </div>
  </v-col>
  </v-card>

</template>

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

.comprado {
  opacity: 0.6;
  pointer-events: none;
  border: 2px solid green;
}

.tienda{
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.726);
}

.v-tabs{
  color: #31333F;
}

.articulo{
  box-shadow: 0px 0px 12px black;
  display: flex;
  flex-direction: column;
  padding-left: 10px;
  padding-bottom: 5px;
  padding-right: 5px;
  gap: 3px;
  transition: transform 0.4s;
}

.articulo:hover{
  transform: scale(1.05);
}

.contenedorModo{
  display: flex;
  flex-direction: column;
  background-color: #E2E271;
  border-radius: 15px;
  justify-content: center;
  align-items: center;
  transition: transform 0.4s;
}

.noDisponible{
  height: 350px;
  background-color: #E2E271;
  align-self: center;
}

.contenedorDisponibilidad{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.contenedorDisponibilidad h3{
  margin-top: -80px;
}

</style>