import { defineStore } from 'pinia';
import axios from 'axios';
import { ref, computed } from 'vue'
import { useUsuarioStore } from './usuario';

export const useDatosStore = defineStore('datos',()=> {

    const noticias = ref(null)
    const jugadoresRanking = ref(null)
    const amigos = ref(null)
    const articulos = ref(null)
    const articulosAdquiridos = ref(null)

    async function getNoticias(){
        
        try {
            const userStore = useUsuarioStore()
            const response = await axios.get(
            'https://shahbackend-production.up.railway.app/Noticias/all',
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );
            this.noticias = response.data;
        } catch (err) {
            console.error('Error fetching noticias:', err);
        } 
    }

    async function getJugadoresRanking(){
        
        try {
            const userStore = useUsuarioStore()
            const response = await axios.get(
            'https://shahbackend-production.up.railway.app/Jugadores/allOrdenado',
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );
            this.jugadoresRanking = response.data;
        } catch (err) {
            console.error('Error fetching noticias:', err);
        } 
    }

    async function actualizarRanking(modificacion){
        try {
            const userStore = useUsuarioStore()
            const response = await axios.put(
            `https://shahbackend-production.up.railway.app/Jugadores/modificarRanking?id=${userStore.id}&modificacion=${modificacion}`,
            {},
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );

            console.log(response.data)
        } catch (err) {
            console.error('Error actualizandoRanking:', err);
        }
    }


    async function actualizarMonedas(modificacion){
        try {
            const userStore = useUsuarioStore()
            const response = await axios.put(
            `https://shahbackend-production.up.railway.app/Jugadores/modificarMoneda?id=${userStore.id}&modificacion=${modificacion}`,
            {},
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );

            console.log(response.data)
        } catch (err) {
            console.error('Error actualizandoRanking:', err);
        }
    }

    //-------------------AMIGOS------------------------
    async function getAmigos(){
        
        try {
            const userStore = useUsuarioStore()
            const response = await axios.get(
            `https://shahbackend-production.up.railway.app/Amigos/Usuario?usuario=${userStore.usuario}`,
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );
            this.amigos= response.data;
        } catch (err) {
            console.error('Error fetching noticias:', err);
        } 
    }

    async function guardarAmigos(idAmigo){
        try {
            const userStore = useUsuarioStore()
            const response = await axios.post(
            `https://shahbackend-production.up.railway.app/Amigos/Guardar`,
            {
                idJugador: userStore.id,
                idAmigo : idAmigo
            },
            {
                headers: {
                    Authorization: `Bearer ${userStore.token}`,
                },
            }
        )
        console.log(response)
        } catch (error) {
            console.error("Error en el registro de amigos: " , error)
        }
    }

    //------------------------------ARTICULOS---------------------------
    async function getArticulos(){
        
        try {
            const userStore = useUsuarioStore()
            const response = await axios.get(
            `https://shahbackend-production.up.railway.app/Articulos/all`,
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );
            this.articulos= response.data;
        } catch (err) {
            console.error('Error fetching articulos:', err);
        } 
    }


    //------------------------------ARTICULOS ADQUIRIDOS---------------------------
    async function getArticulosAdquiridos(){
        
        try {
            const userStore = useUsuarioStore()
            const response = await axios.get(
            `https://shahbackend-production.up.railway.app/Adquiridos/${userStore.id}`,
            {
                headers: {
                Authorization: `Bearer ${userStore.token}`,
                },
            }
            );
            articulosAdquiridos.value = response.data;
        } catch (err) {
            console.error('Error fetching noticias:', err);
        } 
    }

    async function comprarArticulo(idArticulo){
        try {
            const userStore = useUsuarioStore()
            const response = await axios.post(
            `https://shahbackend-production.up.railway.app/Adquiridos/comprar?idArticulo=${idArticulo}&idJugador=${userStore.id}`,
            {},
            {
                headers: {
                    Authorization: `Bearer ${userStore.token}`,
                },
            }
        )
        await getArticulosAdquiridos()
        console.log(response)
        return response.data 
        } catch (error) {
            console.error("Error en la compra: " , error)
        }
    }

    

    //------------------------------------JUGADORES TABLA EXCLUSIVO--------------------------------------
    async function editarJugador(id, jugador) {
  try {
    const userStore = useUsuarioStore()
    const response = await axios.put(
      `https://shahbackend-production.up.railway.app/Jugadores/modificarJugadorId?id=${id}`,
      jugador,
      {
        headers: {
          Authorization: `Bearer ${userStore.token}`,
        },
      }
    )
    console.log(response.data)
  } catch (err) {
    console.error('Error actualizandoJugador:', err)
    throw err
  }
}

    async function eliminarJugador(id) {
    try {
        const userStore = useUsuarioStore()
        const response = await axios.delete(
        `https://shahbackend-production.up.railway.app/Jugadores/${id}`,
        {
            headers: {
            Authorization: `Bearer ${userStore.token}`,
            },
        }
        )
        console.log('Jugador eliminado:', response.data)
    } catch (err) {
        console.error('Error eliminando jugador:', err.response?.data || err.message)
        throw err  // opcional: relanza si quieres que el componente lo maneje
    }
    }


    return {
    noticias,
    jugadoresRanking,
    articulos,
    amigos,
    articulosAdquiridos,
    getJugadoresRanking,
    getNoticias,
    getAmigos,
    getArticulos,
    getArticulosAdquiridos,
    comprarArticulo,
    guardarAmigos,
    actualizarRanking,
    editarJugador,
    eliminarJugador,
    actualizarMonedas,
    };
});