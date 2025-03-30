import { defineStore } from 'pinia';
import { fetchToken } from '@/services/api/AuthService.ts';
import { register } from "@/services/api/UserService.ts";
import {computed, ref} from "vue";


export const useUserStore = defineStore("user", () => {
  const token = ref<string | null>(null);
  const username = ref<string | null>(null);

  function login(status: number, tokenStr: string, user: string) {
    if (status === 200) {
      token.value = tokenStr
      username.value = user
    } else {
      throw new Error("Login Info Error");
    }
  }
  async function verifyLogin(user: string, password: string) {
    const response = await fetchToken({ username: user, password: password });
    login(response.status, response.data, user);
  }
  async function registerUser(userData: {
    username: string;
    password: string;
    email: string;
    firstName: string;
    lastName: string;
    birthDate: string;
  }) {
    await register(userData);
    await verifyLogin(userData.username, userData.password);
  }
  function logout() {
    token.value = null;
    username.value = null;
  }

  const loggedIn = computed(()=> token.value !== null);
  const getUsername = computed(() => username.value);
  const getToken = computed(()=> token.value);

  return {
    token,
    username,
    login,
    verifyLogin,
    registerUser,
    logout,
    loggedIn,
    getUsername,
    getToken
  }
})
