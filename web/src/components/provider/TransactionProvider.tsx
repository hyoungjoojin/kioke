'use client';

import type { Transaction } from '@/types/transaction';
import { createContext, useContext, useEffect, useRef } from 'react';

interface TransactionContextType {
  addTransaction: (transaction: Transaction) => void;
}

const TransactionContext = createContext<TransactionContextType | undefined>(
  undefined,
);

export const useTransaction = () => {
  const context = useContext(TransactionContext);
  if (!context) {
    throw new Error(
      'useTransactionQueue must be used within TransactionProvider',
    );
  }

  return context;
};

export function TransactionProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const workerRef = useRef<Worker | null>(null);

  useEffect(() => {
    if (workerRef.current === null) {
      workerRef.current = new Worker(
        new URL('@/workers/transaction', import.meta.url),
      );
    }
  }, []);

  const addTransaction = (transaction: Transaction) => {
    if (!workerRef.current) {
      return;
    }

    workerRef.current.postMessage(transaction);
  };

  return (
    <TransactionContext.Provider value={{ addTransaction }}>
      {children}
    </TransactionContext.Provider>
  );
}
