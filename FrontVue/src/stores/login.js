import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { useUsuarioStore } from './usuario'

export const useLoginStore = defineStore('login', () => {
  const login = ref(false)
  const admin = ref(false)
  const jugando = ref(false)

  async function logear(usuario, pass){

    try{
      
      const session_urlLogin = `https://shahbackend-production.up.railway.app/auth/login`
      const respuesta = await axios.post(
      session_urlLogin,
      {
        usuario: usuario,
        contrasena: pass
      }
    );

      //Capturamos la respuests
      login.value = Boolean(respuesta.data)

      //Si es positivo en la store de usuario se hace una busqueda para capturar la información
      if(login.value){
        const token = respuesta.data.token
        console.log("Iniciando busqueda de datos")
        console.log(token)
        const userStore = useUsuarioStore()
        userStore.settearToken(token)
        await userStore.obtenerDatosBase()
        console.log("Terminada la busqueda de informacion")
      }else{
        console.log("Fallo en la obtención del usuario")
      }
  

    }catch(error){

        login.value = false
        console.log("Fallo axios " + error)

    }

  }

  async function logearAdmin(usuario, pass){

    try{
      
      const session_urlLogin = `https://shahbackend-production.up.railway.app/auth/loginAdmin`
      const respuesta = await axios.post(
      session_urlLogin,
      {
        usuario: usuario,
        contrasena: pass
      }
    );

      //Capturamos la respuests
      login.value = Boolean(respuesta.data)

      //Si es positivo en la store de usuario se hace una busqueda para capturar la información
      if(login.value){
        const token = respuesta.data.token
        console.log("Iniciando busqueda de datos")
        console.log(token)
        const userStore = useUsuarioStore()
        userStore.settearToken(token)
        await userStore.obtenerDatosBase()
        setAdmin(true)
        console.log("Terminada la busqueda de informacion")
      }else{
        console.log("Fallo en la obtención del usuario")
      }
  

    }catch(error){

        login.value = false
        console.log("Fallo axios " + error)

    }

  }

  async function registrar(correo, usuario, pass){
    try {
      const session_urlRegistro = `https://shahbackend-production.up.railway.app/auth/signup`
      const respuesta = await axios.post(
      session_urlRegistro,
      {
        correo: correo,
        usuario: usuario,
        contrasena: pass
      });

      await logear(usuario, pass)
      console.log(`La respuesta es: ${respuesta}`)
      return true

    } catch (error) {

      console.log(error)
      return false
      
    }
  }

  async function registrarTabla(correo, usuario, pass){
    try {
      const session_urlRegistro = `https://shahbackend-production.up.railway.app/auth/signup`
      const respuesta = await axios.post(
      session_urlRegistro,
      {
        correo: correo,
        usuario: usuario,
        contrasena: pass
      });

      return true

    } catch (error) {

      console.log(error)
      return false
      
    }
  }

  function changeJugando(){
    jugando.value = !jugando.value
  }

function setAdmin(value) {
  admin.value = value
}

  return { login, logear, registrar, jugando, changeJugando, admin, setAdmin , logearAdmin, registrarTabla}
  }

)
