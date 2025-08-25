export const Routes = {
  HOME: '/',
  ONBOARDING: '/onboarding',
  SIGN_IN: '/auth/signin',
  JOURNALS: '/journals',
  JOURNAL: (journalId: string) => {
    return `/journals/${journalId}` as const;
  },
  PAGE: (pageId: string) => {
    return `/pages/${pageId}`;
  },
  FRIENDS: '/friends',
  NOTIFICATIONS: '/notifications',
};
