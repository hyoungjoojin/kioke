import { BlockType } from '@/constant/block';
import type { Block, BlockContent } from '@/types/page';
import type { JSONContent } from '@tiptap/react';
import type { Node } from 'prosemirror-model';

interface BlockAttributes {
  blockId: string;
  isNew: boolean;
}

function getBlockContent(block: Node): BlockContent {
  const type = BlockType[block.type.name as keyof typeof BlockType];
  if (type === BlockType.TEXT_BLOCK) {
    return {
      type,
      text: JSON.stringify(block.content.toJSON()),
    };
  } else {
    throw new Error();
  }
}

function deserializeBlock(block: Block): JSONContent | null {
  console.log(block);
  if (block.type === BlockType.TEXT_BLOCK) {
    return {
      type: block.type,
      content: block.text ? JSON.parse(block.text) : '',
      attrs: {
        blockId: block.blockId,
      },
    };
  }

  return null;
}

export * from './Document';
export * from './TextBlock';
export * from './ImageBlock';
export * from './MapBlock';
export * from './CommandPalette';
export { type BlockAttributes, getBlockContent, deserializeBlock };
