import { createRouter, createWebHistory } from 'vue-router'

// Import existing Views
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RegistrationView from '@/views/RegistrationView.vue'
import ItemDetailView from "@/views/ItemDetailView.vue";
import CategoryEditView from "@/views/Administrator/CategoryEditView.vue";

// --- Import Profile related views ---
import ProfileLayout from "@/views/profile/ProfileLayout.vue";
import ProfileOverview from '@/views/profile/ProfileOverview.vue';
import ProfileAdsView from "@/views/profile/ProfileAdsView.vue";
import FavoritesView from "@/views/profile/FavoritesView.vue";
import MapView from "@/views/MapView.vue";
import ConversationView from "@/views/messaging/ConversationView.vue";
import InboxView from "@/views/messaging/InboxView.vue";
// --- End Profile imports ---

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { title: 'Home - Fant' }
  },
  {
    path: '/item-detail/:id',
    name: 'item-detail',
    component: ItemDetailView,
    props: true,
    meta: { title: 'Item - Fant' }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { title: 'Login - Fant' }
  },
  {
    path: '/register',
    name: 'register',
    component: RegistrationView,
    meta: { title: 'Register - Fant' }
  },
  {
    path: '/create-listing/start',
    name: 'create-listing-start',
    component: () => import('../views/NewListingView.vue'),
    meta: { title: 'Create Listing - Fant', requiresAuth: true }
  },
  {
    path: '/admin',
    name: 'admin',
    component: CategoryEditView,
    meta: { title: 'Administrator - category - Fant'} // Consider adding requiresAuth, requiresAdmin?
  },
  // Dynamic Route for CategoryView.vue
  {
    path: '/category/:categoryKey',
    name: 'category',
    component: () => import('../views/CategoryView.vue'),
    props: true,
    meta: { title: 'Category - Fant' }
  },
  {
    path: '/map',
    name: 'map',
    component: MapView,
    props: true,
    meta: { title: 'Map - Fant' }
  },

  {
    path: '/messages',
    name: 'messages-inbox',
    component: InboxView,
    meta: { title: "Message Overview - Fant"}
  },

  {
    path: '/messages/:conversationId',
    name: 'messages-conversation',
    component: ConversationView,
    meta: { title: "Conversation - Fant"}
  },

  {
    path: '/profile',
    component: ProfileLayout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'profile-overview',
        component: ProfileOverview,
        meta: { title: 'Profile Overview - Fant' }
      },
      {
        path: 'listings',
        name: 'profile-listings',
        component: ProfileAdsView,
        meta: { title: 'My Listings - Fant' }
      },
      {
        path: 'favorites',
        name: 'profile-favorites',
        component: FavoritesView,
        meta: { title: 'My Favorites - Fant' }
      },

    ],
  }


]

// Scroll to top when route changes
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 }
  }
})

// Update the document title based on the route's meta title
router.afterEach((to) => {
  // Use the title from the matched route's meta field
  document.title = to.meta.title as string || 'Fant';
})

export default router
