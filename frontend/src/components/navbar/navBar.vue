<template>
  <header class="nav">
    <div class="logo">
      <RouterLink to="/">fant<span class="dot">.</span></RouterLink>
    </div>
    <nav class="links">
      <RouterLink to="/">Hjem</RouterLink>
      <RouterLink to="/browse">Utforsk</RouterLink>
      <RouterLink to="/login">Logg inn</RouterLink>
      <RouterLink to="/create-listing/start">{{ $t('APP_LISTING_CREATE_NEW') }}</RouterLink>
    </nav>
  </header>
</template>

<style scoped>
.nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.2rem 2rem;
  background-color: white;
  border-bottom: 1px solid #eee;
  font-family: 'Segoe UI', sans-serif;
}

.logo a {
  font-size: 2rem;
  font-weight: bold;
  color: blue;
  text-decoration: none;
}

.logo .dot {
  color: blue;
}

nav.links {
  display: flex;
  gap: 2rem;
}

nav.links a {
  text-decoration: none;
  color: #333;
  font-size: 1rem;
  font-weight: 500;
}

nav.links a.router-link-exact-active {
  color: blue;
  font-weight: bold;
}
</style>
<script setup lang="ts">
</script>
