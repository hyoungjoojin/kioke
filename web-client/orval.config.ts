import { defineConfig } from 'orval';

export default defineConfig({
  user: {
    input: {
      target: 'http://127.0.0.1:23056/user-service/api-docs',
    },
    output: {
      target: './.cache/generated/user.ts',
      schemas: './src/types/server/user/generated',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
  journal: {
    input: {
      target: 'http://127.0.0.1:23057/journal-service/api-docs',
    },
    output: {
      target: './.cache/generated/journal.ts',
      schemas: './src/types/server/journal/generated',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
});
