<template>
  <!-- <app-header v-bind:propsdata="str"></app-header> -->
  <app-header v-bind:propsdata="str" v-on:renew="renewStr"></app-header>
</template>

<script>
import AppHeader from './components/AppHeader.vue'

export default {
  data: function() {
    return {
      str: 'Header'
    }
  },
  components: {
    'app-header': AppHeader
  },
  methods: {
    renewStr: function() {
      this.str = 'hi';
    }
  }
}
</script>

<style>

</style>