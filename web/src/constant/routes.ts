export const Routes = {
  HOME: '/',
  ONBOARDING: '/onboarding',
  SIGN_IN: '/auth/signin',
  COLLECTIONS: '/collections',
  COLLECTION: (id: string) => {
    return `/collections/${id}` as const;
  },
  JOURNALS: '/journals',
  JOURNAL: (journalId: string) => {
    return `/journals/${journalId}` as const;
  },
  PAGE: (pageId: string) => {
    return `/pages/${pageId}`;
  },
  FRIENDS: '/friends',
  NOTIFICATIONS: '/notifications',
  USER: (userId: string) => {
    return `/users/${userId}`;
  },
};
