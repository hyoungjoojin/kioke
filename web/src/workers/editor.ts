import { saveBlockOperations } from '@/app/api/block/saveBlockOperations';
import type { BlockOperation } from '@/types/page';
import Queue from '@/util/queue';
import { groupBy } from 'lodash';

const queue = new Queue<BlockOperation>();
const mapper = new Map<string, string>();

self.onmessage = (event: MessageEvent<BlockOperation[]>) => {
  const data = event.data;
  queue.pushAll(data);
};

setInterval(async () => {
  if (queue.empty()) {
    return;
  }

  const ops = Object.entries(
    groupBy(
      queue.flush().filter((op) => op.pageId && op.blockId),
      (op) => [op.pageId, op.blockId],
    ),
  )
    .flatMap(([_, ops]) =>
      ops.sort((a, b) => a.timestamp - b.timestamp).slice(-1),
    )
    .map((op) => ({
      ...op,
      blockId: mapper.get(op.blockId) || op.blockId,
    }));

  const response = await saveBlockOperations(ops);
  if (response.conversions) {
    response.conversions.forEach(({ before, after }) => {
      mapper.set(before, after);
    });
  }
}, 3000);
