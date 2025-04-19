"use client";

import createBoundStore, { type Store } from "@/store";
import { TransactionsManager } from "@/utils/transactions";
import { createContext, useContext, useRef } from "react";
import { useStore } from "zustand";

export type StoreApi = ReturnType<typeof createBoundStore>;

const StoreContext = createContext<StoreApi | undefined>(undefined);

export const StoreProvider = ({ children }: { children: React.ReactNode }) => {
  const storeRef = useRef<StoreApi>(undefined);
  if (!storeRef.current) {
    storeRef.current = createBoundStore();
  }

  new TransactionsManager(storeRef.current);

  return (
    <StoreContext.Provider value={storeRef.current}>
      {children}
    </StoreContext.Provider>
  );
};

export const useBoundStore = <T,>(selector: (store: Store) => T): T => {
  const storeContext = useContext(StoreContext);
  if (!storeContext) {
    throw new Error(
      "useBoundStore must be used within a StoreProvider component.",
    );
  }

  return useStore(storeContext, selector);
};
