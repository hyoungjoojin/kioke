'use client';

import { StoreContext } from '@/components/providers/StoreProvider';
import { ViewStore } from '@/store/view';
import { useContext } from 'react';
import { useStore } from 'zustand';
import { useShallow } from 'zustand/shallow';

function useViewStore<T>(selector: (store: ViewStore) => T) {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error('usePreferencesStore must be used within a StoreProvider');
  }

  return useStore(storeContext.viewStore, useShallow(selector));
}

function useCurrentView() {
  return useViewStore((store) => store.currentView);
}

function useViewActions() {
  return useViewStore((store) => store.actions);
}

export { useCurrentView, useViewActions };
