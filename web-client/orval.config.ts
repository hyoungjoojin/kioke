import { defineConfig } from 'orval'

export default defineConfig({
  journal: {
    input: {
      target: "http://127.0.0.1:23057/v3/api-docs/journal"
    },
    output: {
      mode: "split",
      target: "./src/types/generated/journal.ts",
      client: "react-query",
      mock: true,
      prettier: true
    }
  }
})
