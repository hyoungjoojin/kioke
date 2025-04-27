import View from '@/constants/view';

export const KIOKE_ROUTES: {
  [key in View]: (...args: string[]) => string;
} = {
  [View.HOME]: () => {
    return '/';
  },

  [View.BOOKMARKS]: () => {
    return '/bookmarks';
  },

  [View.SHELF]: (shelfId: string) => {
    return `/shelf/${shelfId}`;
  },

  [View.JOURNAL_PREVIEW]: (journalId: string) => {
    return `/journal/${journalId}/preview`;
  },

  [View.JOURNAL_PAGE]: (journalId: string, pageId: string) => {
    return `/journal/${journalId}/${pageId}`;
  },
};
