import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface UpdatePagePathParams {
  id: string;
}

export interface UpdatePageRequest {
  title?: string;
  content?: string;
  date?: Date;
}

function url({ id }: UpdatePagePathParams) {
  return `/pages/${id}`;
}

export async function updatePage(
  request: UpdatePagePathParams & UpdatePageRequest,
) {
  const { id, ...body } = request;

  return kioke<void>(
    url({
      id,
    }),
    {
      method: 'PATCH',
      body: JSON.stringify(body),
      headers: {
        'Content-Type': MimeType.APPLICATION_JSON,
      },
    },
  );
}
