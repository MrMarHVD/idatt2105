<!-- frontend/src/views/LanguageSelectorView.vue -->
<template>
  <div>
    <NavbarLanguageSelector :mobile="false" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import NavbarLanguageSelector from '@/views/Navbar/NavbarLanguageSelector.vue';

export default defineComponent({
  name: 'LanguageSelectorView',
  components: {
    NavbarLanguageSelector,
  },
});
</script>

<style scoped>
/* Add your styles here if needed */
</style>
