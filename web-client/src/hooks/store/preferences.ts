'use client';

import { StoreContext } from '@/components/providers/StoreProvider';
import { PreferencesStore } from '@/store/preferences';
import { useContext } from 'react';
import { useStore } from 'zustand';
import { useShallow } from 'zustand/shallow';

function usePreferencesStore<T>(selector: (store: PreferencesStore) => T) {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error('usePreferencesStore must be used within a StoreProvider');
  }

  return useStore(storeContext.preferencesStore, useShallow(selector));
}

function usePreferredTheme() {
  return usePreferencesStore((store) => store.theme);
}

function usePreferencesActions() {
  return usePreferencesStore((store) => store.actions);
}

export { usePreferredTheme, usePreferencesActions };
