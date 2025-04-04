export interface CreateJournalResponseBody {
  jid: string;
  title: string;
}

export interface GetJournalResponseBody {
  id: string;
  title: string;
  description: string;
  createdAt: string;
  lastModified: string;
  users: { userId: string; role: string }[];
  pages: { pageId: string; title: string; date: string }[];
}
