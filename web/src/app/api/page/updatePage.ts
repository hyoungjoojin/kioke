import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface UpdatePagePathParams {
  id: string;
}

export interface UpdatePageRequest {
  journalId: string;
  title?: string;
  content?: string;
  date?: Date;
}

function url({ id }: UpdatePagePathParams) {
  return `/pages/${id}`;
}

export async function updatePage(
  pathParams: UpdatePagePathParams,
  body: UpdatePageRequest,
) {
  return kioke<void>(url(pathParams), {
    method: 'PATCH',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
