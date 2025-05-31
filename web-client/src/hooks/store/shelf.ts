'use client';

import { StoreContext } from '@/components/providers/StoreProvider';
import { ShelfStore } from '@/store/shelf';
import { useContext } from 'react';
import { useStore } from 'zustand';
import { useShallow } from 'zustand/shallow';

function useShelfStore<T>(selector: (store: ShelfStore) => T) {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error('usePreferencesStore must be used within a StoreProvider');
  }

  return useStore(storeContext.shelfStore, useShallow(selector));
}

function useSelectedShelfId() {
  return useShelfStore((store) => store.selectedShelfId);
}

function useShelfActions() {
  return useShelfStore((store) => store.actions);
}

export { useSelectedShelfId, useShelfActions };
