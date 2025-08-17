import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Page } from '@/types/page';
import type { Result } from 'neverthrow';

export interface CreatePageRequestBody {
  journalId: string;
  title: string;
  date: Date;
}

interface CreatePageResponseBody {
  pageId: string;
}

function url() {
  return '/pages';
}

export async function createPage(
  requestBody: CreatePageRequestBody,
): Promise<Result<Page, KiokeError>> {
  return kioke<CreatePageResponseBody>(url(), {
    method: 'POST',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      pageId: data.pageId,
      journalId: requestBody.journalId,
      title: requestBody.title,
      content: '',
      date: requestBody.date,
    })),
  );
}
