import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { ImageBlock } from '@/types/page';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetPageImagesPathParams {
  id: string;
}

type GetPageImagesResponseBody = ImageBlock[];

function url({ id }: GetPageImagesPathParams) {
  return `/pages/${id}/images`;
}

export async function getPageImages(
  pathParams: GetPageImagesPathParams,
): Promise<Result<ImageBlock[], KiokeError>> {
  return kioke<GetPageImagesResponseBody>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
