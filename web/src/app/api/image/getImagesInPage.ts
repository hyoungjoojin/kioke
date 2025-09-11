import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type GetImagesInPageResponse = string[];

function url(pageId: string) {
  return `/images/page/${pageId}`;
}

export async function getImagesInPage(
  pageId: string,
): Promise<Result<GetImagesInPageResponse, KiokeError>> {
  return kioke<GetImagesInPageResponse>(url(pageId), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
