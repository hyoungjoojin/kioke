import { MimeType } from '@/constant/mime';
import type { BlockContent } from '@/types/page';
import type { Transaction } from '@/types/transaction';
import env from '@/util/env';
import Queue from '@/util/queue';
import { unwrap } from '@/util/result';
import { parse } from '@/util/server';
import { type DBSchema, openDB } from 'idb';

const TRANSACTION_DB_NAME = 'kioke-transaction-database';

const BACKEND_URL = env.NEXT_PUBLIC_BACKEND_URL;

interface TransactionDB extends DBSchema {
  blocks: {
    key: string;
    value: {
      pageId: string;
      blockId: string;
      mappedBlockId: string | null;
      content?: BlockContent;
      isPending: boolean;
      isDirty: boolean;
      isDeleted: boolean;
    };
  };
}

const db = await openDB<TransactionDB>(TRANSACTION_DB_NAME, 1, {
  upgrade(database) {
    if (!database.objectStoreNames.contains('blocks')) {
      database.createObjectStore('blocks', {
        keyPath: 'blockId',
      });
    }
  },
});

const queue: Queue<string> = new Queue();

self.onmessage = async (event: MessageEvent<Transaction>) => {
  const { pageId, blockId, command, content } = event.data;

  const block = (await db.get('blocks', blockId)) || null;
  await db.put('blocks', {
    ...(block === null
      ? {
          mappedBlockId: null,
          content,
          isPending: false,
        }
      : block),
    pageId,
    blockId,
    isDirty: true,
    isDeleted: command === 'delete',
  });

  queue.push(blockId);
};

setInterval(async () => {
  if (queue.empty()) {
    return;
  }

  while (!queue.empty()) {
    const blockId = queue.front();

    if (!blockId) {
      queue.pop();
      continue;
    }

    const block = (await db.get('blocks', blockId)) || null;
    if (block === null || !block.isDirty) {
      queue.pop();
      continue;
    }

    await db.put('blocks', {
      ...block,
      isPending: true,
    });

    if (block.isDeleted) {
      await deleteBlock({ blockId });
    } else if (block.mappedBlockId === null) {
      block.mappedBlockId = await createBlock({
        pageId: block.pageId,
        content: block.content,
      })
        .then((response) => unwrap(response))
        .then((data) => data.blockId);
    } else if (block.content) {
      await updateBlock({ blockId, content: block.content });
    }

    await db.put('blocks', {
      ...block,
      isPending: false,
      isDirty: false,
    });
    queue.pop();
  }
}, 1000);

async function createBlock({
  pageId,
  content,
}: {
  pageId: string;
  content?: BlockContent;
}) {
  return fetch(`${BACKEND_URL}/blocks`, {
    method: 'POST',
    credentials: 'include',
    body: JSON.stringify({
      pageId,
      content,
    }),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) => parse<{ blockId: string }>(response));
}

async function updateBlock({
  blockId,
  content,
}: {
  blockId: string;
  content: BlockContent;
}) {
  return fetch(`${BACKEND_URL}/blocks/${blockId}`, {
    method: 'PATCH',
    credentials: 'include',
    body: JSON.stringify({
      content,
    }),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

async function deleteBlock({ blockId }: { blockId: string }) {
  return fetch(`${BACKEND_URL}/blocks/${blockId}`, {
    method: 'DELETE',
    credentials: 'include',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
