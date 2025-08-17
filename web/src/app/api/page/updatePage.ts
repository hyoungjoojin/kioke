import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface UpdatePagePathParams {
  pageId: string;
}

export interface UpdatePageRequestBody {
  title?: string;
  content?: string;
  date?: Date;
}

function url({ pageId }: UpdatePagePathParams) {
  return `/pages/${pageId}`;
}

export async function updatePage(
  pathParams: UpdatePagePathParams,
  requestBody: UpdatePageRequestBody,
) {
  return kioke<void>(url(pathParams), {
    method: 'PUT',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
