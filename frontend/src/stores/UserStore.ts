import { defineStore } from 'pinia';
import { fetchToken } from '@/services/api/authService';
import { register } from '@/services/api/userService';
import { computed, ref } from 'vue';
import api from '@/services/api/axiosInstance';

/**
 * UserStore manages user authentication and profile state.
 * It handles login, registration, profile fetching/updating, and logout.
 * The store persists the JWT token, username, and user profile.
 */
export const useUserStore = defineStore("user", () => {
  // Reactive state for authentication.
  const token = ref<string | null>(null);
  const username = ref<string | null>(null);

  // Reactive state for the user profile.
  // Fields here should match what your backend returns for a user profile.
  const profile = ref({
    email: '',
    firstName: '',
    lastName: '',
    phoneNumber: ''
  });

  /**
   * Updates the store state with token and username if the login response is successful.
   * @param status - HTTP status code from the authentication request.
   * @param tokenStr - JWT token string retrieved from the backend.
   * @param user - Username for the logged-in user.
   * @throws {Error} If the login status is not 200.
   */
  function login(status: number, tokenStr: string, user: string) {
    console.log(status);
    if (status === 200) {
      token.value = tokenStr;
      username.value = user;
    } else {
      throw new Error("Login Info Error");
    }
  }

  /**
   * Verifies login credentials by calling the backend and updating the store state.
   * Expects the HTTP status code to be 200 and the token to be returned in response.data.
   *
   * @param user - The username.
   * @param password - The password.
   * @throws {Error} If login is unsuccessful.
   */
  async function verifyLogin(user: string, password: string) {
    const response = await fetchToken({ username: user, password: password });
    if (response.status !== 200) {
      throw new Error("Login Info Error");
    }
    const tokenStr = response.data as string;
    login(response.status, tokenStr, user);
  }

  /**
   * Registers a new user and automatically logs them in.
   *
   * @param userData - An object containing registration details.
   */
  async function registerUser(userData: {
    username: string;
    password: string;
    email: string;
    firstName: string;
    lastName: string;
    birthDate: string; // If backend still expects birthDate during registration.
  }) {
    await register(userData);
    await verifyLogin(userData.username, userData.password);
  }

  /**
   * Fetches the current user's profile from the backend.
   *
   * The backend endpoint (e.g., '/users/profile') should return an object containing user profile details.
   */
  async function fetchProfile() {
    const response = await api.get('/users/profile');
    profile.value = response.data;
  }

  /**
   * Updates the user's profile by sending updated data to the backend.
   *
   * @param updatedProfile - An object with the updated profile details.
   * @returns A promise that resolves when the profile has been updated.
   */
  async function updateProfile(updatedProfile: typeof profile.value) {
    const response = await api.put('/users/profile', updatedProfile);
    profile.value = response.data;
  }

  /**
   * Logs out the current user by clearing the token, username, and profile data.
   */
  function logout() {
    token.value = null;
    username.value = null;
    profile.value = { email: '', firstName: '', lastName: '', phoneNumber: '' };
  }

  // Computed getters for accessing state reactively.
  const loggedIn = computed(() => token.value !== null);
  const getUsername = computed(() => username.value);
  const getToken = computed(() => token.value);

  return {
    token,
    username,
    profile,
    login,
    verifyLogin,
    registerUser,
    fetchProfile,
    updateProfile,
    logout,
    loggedIn,
    getUsername,
    getToken
  };
});
