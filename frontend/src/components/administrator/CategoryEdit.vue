<template>
  <div class="category-edit">
    <h2>Manage Categories</h2>

    <div class="card">
      <h3>{{ isEditing ? 'Edit' : 'Add' }} Category</h3>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="name">Category Name</label>
          <input id="name" v-model="form.name" placeholder="Enter category name" required />
        </div>

        <div class="form-group">
          <label>Icon</label>
          <div class="icon-selector">
            <select v-model="form.description">
              <option value="" disabled>Select an icon</option>
              <option v-for="icon in availableIcons" :key="icon" :value="icon">{{ icon }}</option>
            </select>
            <div v-if="resolvedIcon" class="icon-preview-container">
              <img :src="resolvedIcon" alt="Icon preview" class="icon-preview" />
            </div>
          </div>
        </div>

        <div class="form-group">
          <label for="custom-icon">Custom Icon URL</label>
          <input
            id="custom-icon"
            v-model="customIconUrl"
            placeholder="https://example.com/icon.svg"
            @input="updateIconUrl"
          />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn primary">
            {{ isEditing ? 'Update' : 'Add' }} Category
          </button>
          <button v-if="isEditing" type="button" class="btn secondary" @click="resetForm">
            Cancel
          </button>
        </div>
      </form>
    </div>

    <div class="card categories-list">
      <h3>Categories</h3>
      <ul>
        <li v-for="category in categories" :key="category.id ?? 'new'" class="category-item">
          <div class="category-info">
            <img :src="getIconForCategory(category)" alt="Category icon" class="icon" />
            <span class="category-name">{{ category.name }}</span>
          </div>
          <div class="category-actions">
            <button class="btn edit" @click="editCategory(category)">Edit</button>
            <button class="btn delete" @click="removeCategory(category.id ?? '')">Delete</button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import type { Category } from '@/models/Category';
import { fetchCategories, addCategory, updateCategory, deleteCategory } from '@/services/CategoryService.ts';

// Import icons
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

const categories = ref<Category[]>([]);
const form = ref<Category>({
  id: null,
  name: '',
  description: '', // This will hold the selected icon name as a string
  parent: null
});
const customIconUrl = ref('');
const isEditing = ref(false);

// Available icon names, no paths or imports
const availableIcons = ref<string[]>([
  'travel', 'appliance', 'boat', 'book', 'camera', 'car', 'clothes', 'computer', 'furniture', 'motorcycle', 'phone', 'art'
]);

// Icon map
const iconMap: Record<string, string> = {
  travel: travelIcon,
  appliance: applianceIcon,
  boat: boatIcon,
  book: bookIcon,
  camera: cameraIcon,
  car: carIcon,
  clothes: clothesIcon,
  computer: computerIcon,
  furniture: furnitureIcon,
  motorcycle: motorcycleIcon,
  phone: phoneIcon,
  art: artIcon,
};

// Computed property to resolve the current icon
const resolvedIcon = computed(() => {
  if (!form.value.description) return null;

  if (form.value.description.startsWith('http')) {
    return form.value.description;
  }

  return iconMap[form.value.description.toLowerCase()] || null;
});

// Function to get icon for a category
function getIconForCategory(category: Category): string {
  if (category.description.startsWith('http')) {
    return category.description;
  }

  return iconMap[category.description.toLowerCase()] || '/fallback-icon.png';
}

async function loadCategories() {
  categories.value = await fetchCategories();
}

async function handleSubmit() {
  console.log(form.value)
  if (isEditing.value) {
    await updateCategory(form.value);
  } else {
    await addCategory(form.value);
  }
  resetForm();
  await loadCategories();
}

function editCategory(category: Category) {
  form.value = { ...category };

  // Set customIconUrl only if it's an actual URL
  customIconUrl.value = category.description.startsWith('http')
    ? category.description
    : '';

  isEditing.value = true;
}

async function removeCategory(id: string | number | null) {
  if (id !== null) {
    await deleteCategory(Number(id));
    await loadCategories();
  }
}

function resetForm() {
  form.value = { id: null, name: '', description: '' };
  customIconUrl.value = '';
  isEditing.value = false;
}

function updateIconUrl() {
  if (customIconUrl.value) {
    form.value.description = customIconUrl.value;
  } else {
    form.value.description = ''; // Reset if no URL is provided
  }
}

onMounted(loadCategories);
</script>

<style scoped>
.category-edit {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h2 {
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

h3 {
  margin: 0 0 15px 0;
  color: #444;
}

.card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 25px;
}

.form-group {
  margin-bottom: 16px;
}

label {
  display: block;
  margin-bottom: 6px;
  font-weight: 500;
  color: #555;
}

input, select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

input:focus, select:focus {
  outline: none;
  border-color: #4a90e2;
  box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
}

.icon-selector {
  display: flex;
  align-items: center;
  gap: 15px;
}

.icon-preview-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  background: #f5f5f5;
  border-radius: 4px;
}

.icon-preview {
  width: 24px;
  height: 24px;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.btn {
  padding: 10px 16px;
  border: none;
  border-radius: 4px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn.primary {
  background-color: #4a90e2;
  color: white;
}

.btn.primary:hover {
  background-color: #3a80d2;
}

.btn.secondary {
  background-color: #f5f5f5;
  color: #333;
}

.btn.secondary:hover {
  background-color: #e5e5e5;
}

.btn.edit {
  background-color: #4a90e2;
  color: white;
  padding: 6px 12px;
  font-size: 12px;
}

.btn.delete {
  background-color: #e74c3c;
  color: white;
  padding: 6px 12px;
  font-size: 12px;
}

.categories-list {
  margin-top: 30px;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.category-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px;
  border-bottom: 1px solid #eee;
}

.category-item:last-child {
  border-bottom: none;
}

.category-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.icon {
  width: 24px;
  height: 24px;
  object-fit: contain;
}

.category-name {
  font-size: 15px;
  font-weight: 500;
}

.category-actions {
  display: flex;
  gap: 8px;
}

@media (max-width: 600px) {
  .category-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }

  .category-actions {
    width: 100%;
    justify-content: flex-end;
  }
}
</style>
