import { createRouter, createWebHistory } from 'vue-router'
import { useLoginStore } from '@/stores/login'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login' // Redirige la raíz al login
    },
    {
      path: '/inicio',
      name: 'about',
      meta: { requiresAuth: true },
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/tablaJugadores',
      name: '/tablaJugadores',
      meta: { requiresAdmin: true },
      component: () => import('../views/JugadoresTablaView.vue'),
    },
    {
      path: '/localGame',
      name: 'localGame',
      meta: { requiresAuth: true },
      component: () => import('../views/LocalView.vue'),
    },
    {
      path: '/registro',
      name: 'registro',
      meta: { guestOnly: true },
      component: () => import('../views/RegistroView.vue'),
    },
    {
      path: '/tienda',
      name: 'tienda',
      meta: { requiresAuth: true },
      component: () => import('../views/TiendaView.vue'),
    },
    {
      path: '/ranking',
      name: 'ranking',
      meta: { requiresAuth: true },
      component: () => import('../views/RankingView.vue'),
    },
    {
      path: '/jugar',
      name: 'jugar',
      meta: { requiresAuth: true },
      component: () => import('../views/SeleccionModoView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      meta: { guestOnly: true },
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/vsMaquina',
      name: 'vsMaquina',
      meta: { requiresAuth: true },
      component: () => import('../views/LocalMaquinaView.vue')
    },
    {
    path: '/partida',
    name: 'PartidaSocket',
    meta: { requiresAuth: true },
    component: () => import('../views/PartidaSocketView.vue'),
    },
  ],
})

//BeforeEach es un hook que comprueba antes de viajar a cualquiera de las rutas 
router.beforeEach((to) => {
  const loginStore = useLoginStore()
  
  // Redirigir usuarios autenticados que intentan acceder a rutas guestOnly
  if (to.meta.guestOnly && loginStore.login) {
    return '/inicio' // O la ruta que prefieras
  }
  
  // Redirigir usuarios no autenticados que intentan acceder a rutas protegidas
  if (to.meta.requiresAuth && !loginStore.login) {
    return '/login'
  }

  if (to.meta.requiresAdmin && !loginStore.admin) {
    return '/login'  // o redirigís a otra ruta
  }
})

export default router
