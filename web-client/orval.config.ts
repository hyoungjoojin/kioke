import { defineConfig } from 'orval';

export default defineConfig({
  journal: {
    input: {
      target: 'http://127.0.0.1:23057/v3/api-docs/journal',
    },
    output: {
      target: './.cache/generated/journal.ts',
      schemas: './src/types/server/journal.generated',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
});
