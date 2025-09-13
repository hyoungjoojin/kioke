'use client';

import 'client-only';

import { updatePage } from '@/app/api/page';
import { BlockType } from '@/constant/block';
import logger from '@/lib/logger';
import { unwrap } from '@/util/result';
import { createContext, useContext, useEffect, useRef, useState } from 'react';

type BlockContent = {
  attrs: {
    blockId: string | null;
  };
  type: BlockType.TEXT_BLOCK;
  content: any[];
};

type TransactionStatus = 'idle' | 'saving' | 'error';

interface TransactionContextType {
  addTransaction: (pageId: string, content: BlockContent[]) => void;
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
  const queueRef = useRef<Map<string, BlockContent[]>>(new Map());
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  useEffect(() => {
    return () => {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    };
  }, []);

  const flush = async () => {
    setStatus('saving');

    queueRef.current.forEach(async (value, key) => {
      await updatePage({
        id: key,
        blocks: value.map((block) => {
          if (block.type === BlockType.TEXT_BLOCK) {
            const content = JSON.stringify(block.content);
            return {
              ...block,
              content,
            };
          }

          logger.debug(`Unhandled BlockType ${block.type}`);
          return block as never;
        }),
      })
        .then((response) => unwrap(response))
        .catch((error) => {
          logger.debug(error);
          setStatus('error');
        });
    });

    queueRef.current.clear();
    if (status !== 'error') {
      setStatus('idle');
    }

    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }
  };

  const addTransaction = (pageId: string, content: BlockContent[]) => {
    queueRef.current.set(pageId, content);

    if (status === 'idle') {
      setStatus('saving');
    }

    if (!timerRef.current) {
      timerRef.current = setInterval(() => {
        flush();
      }, 2000);
    }
  };

  return (
    <TransactionContext.Provider value={{ addTransaction, status }}>
      {children}
    </TransactionContext.Provider>
  );
}
