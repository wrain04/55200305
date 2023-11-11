import {defineConfig, loadEnv} from 'vite'
import createVitePlugins from './vite/plugins'

const {resolve} = require('path')
export default defineConfig(({mode, command}) => {
    const env = loadEnv(mode, process.cwd())
    return {
        server: {
            host:'0.0.0.0',
            port: 8081,
            proxy: {
                '/api/': {
                    // target: 'https://f396010651.goho.co',
                    target: 'http://localhost:9999/',
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, ''),
                    secure: false
                }
            }
        },
        resolve: {
            alias: {
                "@": resolve(__dirname, "./src"),
                "components": resolve(__dirname, "./src/components"),
                "styles": resolve(__dirname, "./src/styles"),
                "utils": resolve(__dirname, "./src/utils"),
            }
        },
        plugins: createVitePlugins(env, command === 'build'),
    }
})
