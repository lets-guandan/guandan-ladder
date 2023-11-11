import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { TDesignResolver } from 'unplugin-vue-components/resolvers';

const serverAddress = 'http://localhost:11111'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    AutoImport({
      resolvers: [TDesignResolver({
        library: 'mobile-vue'
      })],
    }),
    Components({
      resolvers: [TDesignResolver({
        library: 'mobile-vue'
      })],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 3000,
    proxy: {
      '^/api': {
        target: serverAddress,
        changeOrigin: true,
        ws: true
      }
    }
  },
  base: '/',
  build: {
    outDir: 'target/dist',
    assetsDir: 'static',
    cssCodeSplit: false
  }
})
