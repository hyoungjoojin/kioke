'use client';

import { createModalStore } from '@/store/modal';
import { createPreferencesStore } from '@/store/preferences';
import { createShelfStore } from '@/store/shelf';
import { createTransactionStore } from '@/store/transaction';
import { createViewStore } from '@/store/view';
import { TransactionsManager } from '@/utils/transactions';
import { createContext, useRef } from 'react';

type StoreApi = {
  preferencesStore: ReturnType<typeof createPreferencesStore>;
  modalStore: ReturnType<typeof createModalStore>;
  shelfStore: ReturnType<typeof createShelfStore>;
  transactionStore: ReturnType<typeof createTransactionStore>;
  viewStore: ReturnType<typeof createViewStore>;
};

const StoreContext = createContext<StoreApi | null>(null);

function StoreProvider({ children }: { children: React.ReactNode }) {
  const storeRef = useRef<StoreApi | null>(null);

  if (!storeRef.current) {
    storeRef.current = {
      preferencesStore: createPreferencesStore(),
      modalStore: createModalStore(),
      shelfStore: createShelfStore(),
      transactionStore: createTransactionStore(),
      viewStore: createViewStore(),
    };
  }

  new TransactionsManager(storeRef.current);

  return (
    <StoreContext.Provider value={storeRef.current}>
      {children}
    </StoreContext.Provider>
  );
}

export default StoreProvider;
export { type StoreApi, StoreContext };
