'use client';

import type { Store} from '@/store';
import { createStore } from '@/store';
import { createContext, useContext, useRef } from 'react';
import type { StoreApi} from 'zustand';
import { useStore } from 'zustand';

const StoreContext = createContext<StoreApi<Store> | null>(null);

export function StoreProvider({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const storeRef = useRef<StoreApi<Store>>(null);
  if (!storeRef.current) {
    storeRef.current = createStore();
  }

  return (
    <StoreContext.Provider value={storeRef.current}>
      {children}
    </StoreContext.Provider>
  );
}

export const useBoundStore = <T,>(selector: (store: Store) => T): T => {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error(
      'useBoundStore must be used within a StoreProvider component.',
    );
  }

  return useStore(storeContext, selector);
};
