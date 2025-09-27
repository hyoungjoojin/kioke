import { SESSION_COOKIE_KEY } from '@/constant/cookies';
import { ErrorCode } from '@/constant/error';
import logger from '@/lib/logger';
import { getSession } from '@/lib/session';
import { isBrowser } from '@/util/browser';
import env from '@/util/env';
import KiokeError from '@/util/error';
import { parse } from '@/util/server';
import { type Result, err } from 'neverthrow';

const BACKEND_URL = env.NEXT_PUBLIC_BACKEND_URL;

async function kioke<T>(
  url: string,
  options?: RequestInit,
): Promise<Result<T, KiokeError>> {
  const requestUrl = `${BACKEND_URL}${url}`;

  const headers = {
    ...(isBrowser()
      ? { Credentials: 'include' }
      : { Cookie: `${SESSION_COOKIE_KEY}=${(await getSession())?.id || ''}` }),
    ...options?.headers,
  };

  return await fetch(requestUrl, {
    ...options,
    headers,
  })
    .then((response) => parse<T>(response))
    .catch((error) => {
      logger.debug(error);

      return err(
        new KiokeError({
          code:
            error instanceof TypeError
              ? ErrorCode.NETWORK_REQUEST_FAILED
              : ErrorCode.UNKNOWN_ERROR,
        }),
      );
    });
}

export default kioke;
