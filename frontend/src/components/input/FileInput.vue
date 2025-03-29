<template>
  <div class="form-group">
    <label :for="id">{{ label }}</label>
    <input
      type="file"
      :id="id"
      @change="handleFileChange"
      :multiple="multiple"
    />
    <div v-if="files.length">
      <h4>{{ $t('APP_LISTING_UPLOADED_FILE') }}</h4>
      <ul>
        <li v-for="(file, index) in files" :key="index">{{ file.name }}</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  props: {
    id: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      files: [] as File[],
    };
  },
  methods: {
    handleFileChange(event: Event) {
      const target = event.target as HTMLInputElement;
      if (target && target.files) {
        this.files = Array.from(target.files);
        this.$emit('update:files', this.files);
      }
    },
  },
};
</script>
