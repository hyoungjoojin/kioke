import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { MarkerBlock } from '@/types/page';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetPageMarkersPathParams {
  id: string;
}

type GetPageMarkersResponseBody = MarkerBlock[];

function url({ id }: GetPageMarkersPathParams) {
  return `/pages/${id}/markers`;
}

export async function getPageMarkers(
  pathParams: GetPageMarkersPathParams,
): Promise<Result<MarkerBlock[], KiokeError>> {
  return kioke<GetPageMarkersResponseBody>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
