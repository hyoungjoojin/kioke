export interface GetJournalResponseBody {
  journalId: string;
  title: string;
  description: string;
  bookmarked: boolean;
  createdAt: string;
  lastModified: string;
  users: { userId: string; role: string }[];
  pages: { pageId: string; title: string; createdAt: string }[];
}

export interface GetJournalsResponseBody {
  journals: GetJournalResponseBody[];
}

export interface CreateJournalResponseBody {
  journalId: string;
  title: string;
}

export interface UpdateJournalRequestBody {
  shelfId?: string;
  title?: string;
  description?: string;
  bookmark?: boolean;
}
