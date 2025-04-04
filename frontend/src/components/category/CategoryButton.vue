<script setup lang="ts">
defineProps<{ label: string; icon: string }>()
// Emits events upwards to parent
const emit = defineEmits<(e: "click") => void>()
</script>

<template>
  <button class="category-button" @click="emit('click')">
    <img :src="icon" :alt="label" class="icon"/>
    <span class="label">{{ label }}</span>

  </button>
</template>
