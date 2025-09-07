import { createEnv } from '@t3-oss/env-nextjs';
import { z } from 'zod';

const env = createEnv({
  client: {
    NEXT_PUBLIC_BACKEND_URL: z.string(),
    NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN: z.string(),
  },
  runtimeEnv: {
    NEXT_PUBLIC_BACKEND_URL: process.env.NEXT_PUBLIC_BACKEND_URL,
    NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN:
      process.env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN,
  },
});
export default env;
