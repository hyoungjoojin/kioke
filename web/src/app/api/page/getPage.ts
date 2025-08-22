import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Page } from '@/types/page';
import type { Result } from 'neverthrow';

interface GetPagePathParams {
  id: string;
}

interface GetPageResponseBody {
  pageId: string;
  journalId: string;
  title: string;
  content: string;
  date: Date;
}

function url({ id }: GetPagePathParams) {
  return `/pages/${id}`;
}

export async function getPage(
  pathParams: GetPagePathParams,
): Promise<Result<Page, KiokeError>> {
  return kioke<GetPageResponseBody>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
