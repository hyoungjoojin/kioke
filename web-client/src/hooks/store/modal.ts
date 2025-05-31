'use client';

import { StoreContext } from '@/components/providers/StoreProvider';
import { ModalStore } from '@/store/modal';
import { useContext } from 'react';
import { useStore } from 'zustand';
import { useShallow } from 'zustand/shallow';

function useModalStore<T>(selector: (store: ModalStore) => T) {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error('usePreferencesStore must be used within a StoreProvider');
  }

  return useStore(storeContext.modalStore, useShallow(selector));
}

function useModal() {
  return useModalStore((store) => ({ open: store.open, type: store.type }));
}
function useModalActions() {
  return useModalStore((store) => store.actions);
}

export { useModal, useModalActions };
