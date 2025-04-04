<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Category } from '@/models/Category';
import CategoryButton from "./CategoryButton.vue";
import { fetchCategories } from '@/services/CategoryService.ts';

// Import icons directly
import travelIcon from '@/assets/icons/travelIcon.svg';
import applianceIcon from '@/assets/icons/applianceIcon.svg';
import boatIcon from '@/assets/icons/boatIcon.svg';
import bookIcon from '@/assets/icons/bookIcon.svg';
import cameraIcon from '@/assets/icons/cameraIcon.svg';
import carIcon from '@/assets/icons/carIcon.svg';
import clothesIcon from '@/assets/icons/clothesIcon.svg';
import computerIcon from '@/assets/icons/computerIcon.svg';
import furnitureIcon from '@/assets/icons/furnitureIcon.svg';
import motorcycleIcon from '@/assets/icons/motorcycleIcon.svg';
import phoneIcon from '@/assets/icons/phoneIcon.svg';
import artIcon from '@/assets/icons/artIcon.svg';

// Define categories array
const categories = ref<Category[]>([]);
const emit = defineEmits<(e: 'select', categoryLabel: string) => void>();

// Local icon import map
const iconMap: Record<string, string> = {
  Travel: travelIcon,
  Appliance: applianceIcon,
  Boat: boatIcon,
  Book: bookIcon,
  Camera: cameraIcon,
  Car: carIcon,
  Clothes: clothesIcon,
  Computer: computerIcon,
  Furniture: furnitureIcon,
  Motorcycle: motorcycleIcon,
  Phone: phoneIcon,
  Art: artIcon,
};

// Load categories from API
async function loadCategories() {
  try {
    categories.value = await fetchCategories();

    categories.value.forEach(category => {
      let resolvedIcon;

      if (category.description.startsWith('http')) {
        resolvedIcon = category.description;
      } else {
        const matchedIconKey = Object.keys(iconMap).find(key => key.toLowerCase() === category.description);
        resolvedIcon = matchedIconKey ? iconMap[matchedIconKey] : '/fallback-icon.png'; // Use fallback if not found
      }
      category.description = resolvedIcon;
    });
  } catch (error) {
    console.error("Error loading categories:", error);
  }
}

// Handle category selection
function handleFunction(id: string) {
  emit('select', id);
}

onMounted(loadCategories);
</script>

<template>
  <div class="category-grid">
    <CategoryButton
      v-for="category in categories"
      :key="category.id?.toString() || `temp-${Math.random()}`"
      :label="category.name"
      :icon="category.description"
      @click="handleFunction(category.id?.toString() || '')"
    />
  </div>
</template>
