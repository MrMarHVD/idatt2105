<script setup lang="ts">
import RegistrationForm from "@/components/login/RegistrationForm.vue";
</script>

<template>
  <main class="registration-view">
    <h1>{{ $t('REGISTRATION_HEADER') }}</h1>
    <RegistrationForm />
  </main>
</template>
