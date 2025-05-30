import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import Swal from 'sweetalert2'
import { useRouter } from 'vue-router'
import { useJuegoStore } from './juego'

export const useUsuarioStore = defineStore('usuario', () => {
  // Datos del usuario
  const email = ref(null)
  const id = ref(null)
  const ranking = ref(null)
  const usuario = ref(null)
  const monedas = ref(null)
  const nivel = ref(null)
  const token = ref(null)
  const imagen = ref(null)
  const articulosAdquiridos = ref([])
  const router = useRouter()
  const juegoStore = useJuegoStore()

  // Cliente STOMP como ref
  const stompClient = ref(null)

  // Mensajes recibidos
  const mensajes = ref([])

  
function settearUsuario(idParam, usuarioParam, emailParam, rankingParam, monedasParam, nivelParam, imagenParam) {
  id.value = idParam
  usuario.value = usuarioParam
  email.value = emailParam
  ranking.value = rankingParam
  monedas.value = monedasParam
  nivel.value = nivelParam
  imagen.value = imagenParam

  stompClient.value = new Client({
    webSocketFactory: () => new SockJS('https://shahbackend-production.up.railway.app/ws'),
    connectHeaders: {
      login: usuarioParam,
    },
    onConnect: () => {
      console.log('✅ Conectado como:', usuarioParam)

      // CHAT
      stompClient.value.subscribe(`/chat/${usuarioParam}`, (message) => {
        const msg = JSON.parse(message.body)
        console.log('📩 Mensaje recibido:', msg)
        mensajes.value.push(msg)
      })

      // GAME
      stompClient.value.subscribe(`/game/${usuarioParam}`, async (message) => {
        const msg = JSON.parse(message.body)

        if (msg.tipo === 'invitacion') {
          const aceptar = await Swal.fire({
            title: `🎲 ${msg.from} quiere jugar contigo. ¿Aceptar?`,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#E2E271',
            confirmButtonText: 'Sí',
            cancelButtonText: 'Cancelar',
          })
          if (aceptar.isConfirmed) {
            juegoStore.resetearJuego()
            stompClient.value.publish({
              destination: `/app/game/${msg.from}`,
              body: JSON.stringify({
                tipo: 'aceptada',
                from: usuarioParam
              })
            })
            router.push(`/partida?vs=${msg.from}`)
          }
        }

        if (msg.tipo === 'movimiento') {
          // Importar y llamar al store juego para aplicar movimiento

          juegoStore.aplicarMovimientoExterno(msg.from, msg.to)
        }

        if (msg.tipo === 'aceptada') {
          router.push(`/partida?vs=${msg.from}`)
        }
      })
    },
    onStompError: (frame) => {
      console.error('❌ Error STOMP:', frame)
    }
  })

  stompClient.value.activate()
}


function enviarReto(retado) {
  console.log(`Enviando invitación a ${retado}`);

  stompClient.value.publish({
    destination: `/app/game/${retado}`,
    body: JSON.stringify({
      tipo: 'invitacion',
      from: usuario.value,
    })
  });
}

function enviarMovimiento(from, to, oponente) {
  stompClient.value.publish({
    destination: `/app/game/mover/${oponente}`,
    body: JSON.stringify({
      tipo: 'movimiento',
      from,
      to,
      jugador: usuario.value,
    }),
  });
}


function enviarMensaje(to, content) {
  if (!stompClient.value || !stompClient.value.connected) {
    console.error("STOMP no está conectado aún.")
    return
  }

  console.log(`nombreEnvio: ${usuario.value},
      receiverId: ${to},
      ${content}`)

  stompClient.value.publish({
    destination: `/app/chat/${to}`,
    body: JSON.stringify({ 
      senderId: usuario.value,
      receiverId: to,
      content
    })
  })

  mensajes.value.push({ 
      senderId: usuario.value,
      receiverId: to,
      content
  })
}

  // Token setter
function settearToken(t) {
  token.value = t
  console.log(`✅ Token seteado: ${token.value}`)
}


  // Obtener datos del usuario usando token
  async function obtenerDatosBase() {
    console.log("🔍 Obteniendo datos del usuario...")
    try {
      const session_urlDatos = `https://shahbackend-production.up.railway.app/Jugadores/jugadorLogueado`
      const respuesta = await axios.get(session_urlDatos, {
        headers: {
          'Authorization': `Bearer ${token.value}`
        }
      })

      console.log("✅ Usuario obtenido:", respuesta.data)
      settearUsuario(
        respuesta.data.id,
        respuesta.data.usuario,
        respuesta.data.correo,
        respuesta.data.ranking,
        respuesta.data.monedas,
        respuesta.data.nivel,
        respuesta.data.imagen
      )

      obtenerArticulos(respuesta.data.id)
    } catch (error) {
      console.error("❌ Error obteniendo usuario:", error)
    }

    async function obtenerArticulos(idUsuario){

      try {
            const response = await axios.get(
            `https://shahbackend-production.up.railway.app/Adquiridos/${idUsuario}`,
            {
                headers: {
                Authorization: `Bearer ${token.value}`,
                },
            }
            );
            articulosAdquiridos.value = response.data;
        } catch (err) {
            console.error('❌ Error fetching articulos:', err);
        } 

    }

  }
  
  function setMonedas(modificador){
    monedas.value += modificador
  }

  async function setImagen(imagenNueva){
    try {
            const userStore = useUsuarioStore()
            const response = await axios.put(
            `https://shahbackend-production.up.railway.app/Jugadores/modificarPerfilImagen?id=${userStore.id}&imagenNueva=${imagenNueva}`,
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

    imagen.value = imagenNueva
  }

  function cerrarSesion() {
  token.value = null
  usuario.value = null
  email.value = null
  id.value = null
  ranking.value = null
  monedas.value = null
  nivel.value = null
  imagen.value = null
  articulosAdquiridos.value = []
  mensajes.value = []

  // Opcional: desactiva STOMP si está conectado
  if (stompClient.value && stompClient.value.active) {
    stompClient.value.deactivate()
    stompClient.value = null
  }

}
  

  return {
    // State
    usuario,
    email,
    id,
    ranking,
    monedas,
    nivel,
    token,
    mensajes,
    imagen,

    // Métodos
    settearUsuario,
    settearToken,
    obtenerDatosBase,
    enviarMensaje,
    setMonedas,
    setImagen,
    cerrarSesion,
    enviarReto,
    enviarMovimiento
  }
})
