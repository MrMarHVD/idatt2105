<template>
  <div class="form-group">
    <label :for="id">{{ label }}</label>
    <input
      type="text"
      :id="id"
      :value="modelValue"
      @input="handleInput"
      :placeholder="placeholder"
      :required="required"
    />
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
    modelValue: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: '',
    },
    required: {
      type: Boolean,
      default: true,
    },
  },
  emits: ['update:modelValue'],
  methods: {
    handleInput(event: Event) {
      const target = event.target as HTMLInputElement;
      if (target) {
        this.$emit('update:modelValue', target.value);
      }
    },
  },
};
</script>
