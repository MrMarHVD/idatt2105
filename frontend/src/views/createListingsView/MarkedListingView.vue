<template>
  <div class="listing-form">
    <h2>{{ $t('APP_LISTING_CREATE_NEW') }}</h2>
    <form @submit.prevent="submitListing">
      <!-- Headline -->
      <TextInput
        id="headline"
        :label="$t('APP_LISTING_CREATE_NEW_HEADER_LABEL')"
        v-model="listing.headline"
        required
        :placeholder="$t('APP_LISTING_CREATE_NEW_HEADER_PLACEHOLDER')"
      />

      <!-- Description -->
      <TextInput
        id="description"
        :label="$t('APP_LISTING_CREATE_NEW_DESCRIPTION_LABEL')"
        v-model="listing.description"
        required
        :placeholder="$t('APP_LISTING_CREATE_NEW_DESCRIPTION_PLACEHOLDER')"
      />

      <!-- Category -->
      <SelectInput
        id="category"
        :label="$t('APP_LISTING_CREATE_NEW_CATEGORY_LABEL')"
        v-model="listing.category"
        :options="categories"
        required
        :placeholder="$t('APP_LISTING_CREATE_NEW_CATEGORY_PLACEHOLDER')"
      />

      <!-- Price -->
      <TextInput
        id="price"
        :label="$t('APP_LISTING_CREATE_NEW_PRICE_LABEL')"
        v-model="listing.price"
        required
        :placeholder="$t('APP_LISTING_CREATE_NEW_PRICE_PLACEHOLDER')"
      />

      <!-- Postal Code -->
      <TextInput
        id="postalCode"
        :label="$t('APP_LISTING_CREATE_NEW_POSTAL_CODE_LABEL')"
        v-model="listing.postalCode"
        required
        :placeholder="$t('APP_LISTING_CREATE_NEW_POSTAL_CODE_PLACEHOLDER')"
      />

      <!-- Images -->
      <FileInput
        id="images"
        :label="$t('APP_LISTING_CREATE_NEW_IMAGES_LABEL')"
        @update:files="handleImageUpload"
        multiple
      />

      <button type="submit">{{ $t('APP_LISTING_CREATE_NEW_SUBMIT_BUTTON') }}</button>
    </form>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import TextInput from '@/components/input/TextInput.vue';
import SelectInput from '@/components/input/SelectInput.vue';
import FileInput from '@/components/input/FileInput.vue';

export default {
  components: {
    TextInput,
    SelectInput,
    FileInput,
  },
  data() {
    return {
      listing: {
        headline: '',
        description: '',
        category: '',
        price: '',
        postalCode: '',
        images: [] as File[],
      },
      categories: ['Electronics', 'Furniture', 'Vehicles', 'Clothing', 'Real Estate'],
    };
  },
  methods: {
    handleImageUpload(files: File[]) {
      this.listing.images = files;
    },
    async submitListing() {
      const formData = new FormData();
      formData.append('headline', this.listing.headline);
      formData.append('description', this.listing.description);
      formData.append('category', this.listing.category);
      formData.append('price', this.listing.price);
      formData.append('postalCode', this.listing.postalCode);
      this.listing.images.forEach((file, index) => {
        formData.append(`images[${index}]`, file);
      });

      try {
        const response = await axios.post('/api/listings', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        console.log('Listing Submitted:', response.data);
        this.resetForm();
      } catch (error) {
        console.error('Error submitting listing:', error);
      }
    },
    resetForm() {
      this.listing.headline = '';
      this.listing.description = '';
      this.listing.category = '';
      this.listing.price = '';
      this.listing.postalCode = '';
      this.listing.images = [];
    },
  },
};
</script>

