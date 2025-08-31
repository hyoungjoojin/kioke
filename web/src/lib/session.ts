'use server';

import { SESSION_COOKIE_KEY } from '@/constant/cookies';
import { cookies } from 'next/headers';
import { cache } from 'react';

interface Session {
  id: string;
}

export const getSession = cache(async (): Promise<Session | null> => {
  const cookieJar = await cookies();
  const session = cookieJar.get(SESSION_COOKIE_KEY)?.value ?? null;
  if (session === null) {
    return null;
  }

  return {
    id: session,
  };
});

export const destroySession = cache(async () => {
  const cookieJar = await cookies();
  cookieJar.delete(SESSION_COOKIE_KEY);
});
