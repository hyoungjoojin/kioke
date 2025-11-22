import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { buildUrl } from '@/util/server';
import type { Result } from 'neverthrow';

interface GetJournalsParams {
  query?: string;
  size?: number;
  cursor?: string;
}

interface GetJournalsResponse {
  journals: Journal[];
  cursor: string;
  hasNext: boolean;
}

async function getJournals({
  params,
}: {
  params: GetJournalsParams;
}): Promise<Result<GetJournalsResponse, KiokeError>> {
  return kioke<GetJournalsResponse>(buildUrl('/journals', params), {
    method: HttpMethod.GET,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export { getJournals };
export type { GetJournalsParams, GetJournalsResponse };
