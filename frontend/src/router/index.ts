import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/browse',
      name: 'browse',
      component: () => import('../views/ItemView.vue'), // or ItemListView
    },
    {
      path: '/language-selector',
      name: 'language-selector',
      component: () => import('../views/NavbarLanguageSelectorView.vue'),
    },
    {
      path: '/create-listing/start',
      name: 'create-listing-start',
      component: () => import('../views/createListingsView/MarkedListingView.vue'),
    }

  ],
})

export default router
