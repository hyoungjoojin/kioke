'use client';

import { StoreContext } from '@/components/providers/StoreProvider';
import { TransactionStore } from '@/store/transaction';
import { useContext } from 'react';
import { useStore } from 'zustand';
import { useShallow } from 'zustand/shallow';

function useTransactionStore<T>(selector: (store: TransactionStore) => T) {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error('usePreferencesStore must be used within a StoreProvider');
  }

  return useStore(storeContext.transactionStore, useShallow(selector));
}

function useTransactionStatus() {
  return useTransactionStore((store) => store.status);
}

function useTransactionActions() {
  return useTransactionStore((store) => store.actions);
}

export { useTransactionStatus, useTransactionActions };
