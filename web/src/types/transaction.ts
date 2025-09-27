import type { BlockContent } from './page';

type TransactionCommand = 'create' | 'update' | 'delete';

interface Transaction {
  pageId: string;
  blockId: string;
  command: TransactionCommand;
  content?: BlockContent;
}

export type { TransactionCommand, Transaction };
