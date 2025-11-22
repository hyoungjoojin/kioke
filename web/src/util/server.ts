import KiokeError from './error';
import { ErrorCode } from '@/constant/error';
import logger from '@/lib/logger';
import type { ProblemDetail } from '@/types/server';
import { type Err, type Result, err, ok } from 'neverthrow';

function buildUrl(base: string, searchParams: object): string {
  const url = base;

  const search = new URLSearchParams();
  Object.entries(searchParams)
    .filter(([, value]) => value !== undefined)
    .forEach(([key, value]) => {
      search.append(key, String(value));
    });

  return url + (search.toString() ? `?${search.toString()}` : '');
}

async function parse<T>(response: Response): Promise<Result<T, KiokeError>> {
  return response.ok ? parseSuccess(response) : parseError(response);
}

async function parseSuccess<T>(
  response: Response,
): Promise<Result<T, KiokeError>> {
  if (response.status === 204) {
    return ok(undefined as T);
  }

  const contentType = response.headers.get('Content-Type');
  switch (contentType) {
    case 'application/json':
      return response
        .json()
        .then((data) => ok(data))
        .catch((error) => {
          logger.debug(error);

          return err(
            new KiokeError({
              code:
                error instanceof SyntaxError
                  ? ErrorCode.JSON_PARSE_FAILED
                  : ErrorCode.UNKNOWN_ERROR,
            }),
          );
        });

    default:
      return ok((await response.text()) as T);
  }
}

async function parseError(response: Response): Promise<Err<never, KiokeError>> {
  if (response.headers.get('Content-Type') === 'application/problem+json') {
    return response
      .json()
      .then((detail: ProblemDetail) => {
        logger.debug(detail);

        return err(
          new KiokeError({
            detail,
          }),
        );
      })
      .catch((error) => {
        return err(
          new KiokeError({
            code:
              error instanceof SyntaxError
                ? ErrorCode.JSON_PARSE_FAILED
                : ErrorCode.UNKNOWN_ERROR,
          }),
        );
      });
  }

  return err(
    new KiokeError({
      status: response.status,
    }),
  );
}

export { parse, buildUrl };
