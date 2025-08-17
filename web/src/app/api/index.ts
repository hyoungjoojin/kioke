import { SESSION_COOKIE_KEY } from '@/constant/cookies';
import { ErrorCode, KiokeError } from '@/constant/error';
import { isBrowser } from '@/lib/browser';
import logger from '@/lib/logger';
import { getSession } from '@/lib/session';
import env from '@/util/env';
import { parseErrorResponse, parseResponse } from '@/util/server';
import type { Result} from 'neverthrow';
import { err, ok } from 'neverthrow';

const BACKEND_URL = env.NEXT_PUBLIC_BACKEND_URL;

async function kioke<T>(
  url: string,
  options?: RequestInit,
): Promise<Result<T, KiokeError>> {
  const requestUrl = `${BACKEND_URL}${url}`;

  const headers = {
    ...(isBrowser()
      ? { credentials: 'include' }
      : { Cookie: `${SESSION_COOKIE_KEY}=${(await getSession())?.id || ''}` }),
    ...options?.headers,
  };

  return await fetch(requestUrl, {
    ...options,
    headers,
  })
    .then(async (response) => {
      if (response.ok) {
        return parseResponse<T>(response);
      } else {
        throw await parseErrorResponse(response);
      }
    })
    .then((result) => ok(result))
    .catch((error) => {
      logger.error(error);

      if (error instanceof KiokeError) {
        return err(error);
      } else if (error instanceof TypeError) {
        return err(new KiokeError(ErrorCode.NETWORK_REQUEST_FAILED));
      } else {
        return err(new KiokeError(ErrorCode.UNKNOWN_ERROR));
      }
    });
}

export default kioke;
