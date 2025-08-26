'use client';

import 'client-only';

import { updatePage } from '@/app/api/page';
import logger from '@/lib/logger';
import { unwrap } from '@/util/result';
import { createContext, useContext, useEffect, useRef, useState } from 'react';

interface Transaction {
  pageId: string;
  content: string;
}

type TransactionStatus = 'idle' | 'saving' | 'error';

interface TransactionContextType {
  addTransaction: (transaction: Transaction) => void;
  status: TransactionStatus;
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
  const [status, setStatus] = useState<TransactionStatus>('idle');
  const queueRef = useRef<Transaction[]>([]);
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    return () => {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    };
  }, []);

  const flush = async () => {
    if (queueRef.current.length === 0) {
      setStatus('idle');
      if (timerRef.current) {
        clearInterval(timerRef.current);
        timerRef.current = null;
      }
    } else {
      setStatus('saving');

      queueRef.current.forEach(
        async (transaction, index) =>
          await updatePage({
            id: transaction.pageId,
            content: transaction.content,
          })
            .then((response) => unwrap(response))
            .catch((error) => {
              logger.debug(error);
              setStatus('error');
              queueRef.current = queueRef.current.slice(index);
            }),
      );

      if (status !== 'error') {
        setStatus('idle');
      }
    }
  };

  const addTransaction = (transaction: Transaction) => {
    queueRef.current.push(transaction);
    if (status === 'idle') {
      setStatus('saving');
    }

    if (!timerRef.current) {
      timerRef.current = setInterval(() => {
        flush();
      }, 5000);
    }
  };

  return (
    <TransactionContext.Provider value={{ addTransaction, status }}>
      {children}
    </TransactionContext.Provider>
  );
}
