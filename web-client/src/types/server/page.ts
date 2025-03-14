export interface CreatePageResponseBody {
  pageId: string;
}

export interface GetPageResponseBody {
  pageId: string;
  date: string;
  title: string;
  content: string;
}
